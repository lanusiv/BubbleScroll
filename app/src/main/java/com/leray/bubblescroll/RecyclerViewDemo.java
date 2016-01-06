package com.leray.bubblescroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDemo extends AppCompatActivity {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final List<String> list = new ArrayList<String>();
        for (int i = 1; i <= 6; i++) {
            list.add(getString(R.string.test_content));
        }
        SimpleArrayAdapter adapter = new SimpleArrayAdapter(getApplicationContext(), R.layout.simple_list_item, R.id.textView, list);
        recyclerView.setAdapter(adapter);

        BubbleFactory.createBubble(recyclerView);
    }

}
