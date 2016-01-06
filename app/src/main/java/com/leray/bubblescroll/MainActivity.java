package com.leray.bubblescroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button:
                startActivity(new Intent(this, ScrollViewDemo.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, RecyclerViewDemo.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, LinearLayoutDemo.class));
                break;
        }
    }
}
