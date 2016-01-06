package com.leray.bubblescroll;

import android.animation.ValueAnimator;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Leray on 2016/1/5.
 */
public class BubbleFactory {


    public static ViewGroup createBubble(ViewGroup viewGroup) {
        Bubble bubble = new Bubble(viewGroup);
        return viewGroup;
    }


    private static class Bubble {
        private DragListener mDragListener = new DragListener();
        private View topJelly, bottomJelly;

        private boolean top = true;
        private int height;
        private ViewGroup viewGroup;

        public Bubble(final ViewGroup viewGroup) {
            this.viewGroup = viewGroup;
            topJelly = new View(viewGroup.getContext());
            bottomJelly = new View(viewGroup.getContext());
            if (viewGroup instanceof ScrollView) {
                ((ViewGroup)(viewGroup.getChildAt(0))).addView(topJelly, 0);
                ((ViewGroup)(viewGroup.getChildAt(0))).addView(bottomJelly, -1);
            } else if (viewGroup instanceof RecyclerView) {

            } else if (viewGroup instanceof LinearLayout) {
                LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                viewGroup.addView(topJelly, 0, topParams);
                viewGroup.addView(bottomJelly, -1, bottomParams);
            }
            viewGroup.setOnTouchListener(mDragListener);
        }

        private void update(int height) {
            if (height >= 0) {
                top = true;
                ViewGroup.LayoutParams params = topJelly.getLayoutParams();
                float hh = height >= 0 ? (float) (Math.sqrt(height * 220)) : 0;
                params.height = this.height = (int) hh;
                topJelly.requestLayout();
            } else {
                top = false;
                height = Math.abs(height);
                ViewGroup.LayoutParams bparams = bottomJelly.getLayoutParams();
                float bhh = (float) (Math.sqrt(height * 220));
                bparams.height = this.height = (int) bhh;
                bottomJelly.requestLayout();
            }
        }

        private void disappear() {
            ValueAnimator animator = ValueAnimator.ofFloat(height, 0);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(300);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    height = (int) value;
                    if (top) {
                        ViewGroup.LayoutParams params = topJelly.getLayoutParams();
                        params.height = height;
                        topJelly.requestLayout();
                    } else {
                        ViewGroup.LayoutParams bparams = bottomJelly.getLayoutParams();
                        bparams.height = height;
                        bottomJelly.requestLayout();
                    }
                }
            });
            animator.start();
        }

        private class DragListener implements View.OnTouchListener {

            private float mLastTouchX, mLastTouchY;
            private int mActivePointerId;
            private float mPosX, mPosY;
            private static final int INVALID_POINTER_ID = -1;

            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                int action = MotionEventCompat.getActionMasked(ev);
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Remember where we started (for dragging)
                        mLastTouchX = x;
                        mLastTouchY = y;
                        // Save the ID of this pointer (for dragging)
                        mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        // Find the index of the active pointer and fetch its position
                        final int pointerIndex =
                                MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                        final float x = MotionEventCompat.getX(ev, pointerIndex);
                        final float y = MotionEventCompat.getY(ev, pointerIndex);

                        // Calculate the distance moved
                        final float dx = x - mLastTouchX;
                        final float dy = y - mLastTouchY;

                        mPosX += dx;
                        mPosY += dy;

                        height = (int) (dy);

                        update(height);

                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
//                        mActivePointerId = INVALID_POINTER_ID;
                        resetTouchPosition(ev);
                        disappear();
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_UP: {

                        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

                        if (pointerId == mActivePointerId) {
                            // This was our active pointer going up. Choose a new
                            // active pointer and adjust accordingly.
                            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                            mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
                            mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
                            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                        }
                        break;
                    }
                }
                return false;
            }

            private void resetTouchPosition(MotionEvent ev) {
                final int pointerIndex =
                        MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);

                // Calculate the distance moved
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mLastTouchX = x;
                mLastTouchY = y;
            }

        }
    }

}
