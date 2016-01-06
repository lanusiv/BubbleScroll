package com.leray.bubblescroll;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinearLayoutDemo extends AppCompatActivity {

    private LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        container = (LinearLayout) findViewById(R.id.container);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        for (int i = 0; i < 5; i++) {
            TextView tv = new TextView(getApplicationContext());
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(21);
            tv.setText(getString(R.string.test_content));
            container.addView(tv, params);
        }

        BubbleFactory.createBubble(container);
    }

}
