package com.econetwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.econetwork.ui.home.Events_model;
import com.econetwork.ui.task.TaskAdapterImages;
import com.econetwork.ui.task.TaskModelImages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    TextView header;
    TextView discription;
    SharedPreferences mPrefs;
    public static final String APP_PREFERENCES = "myprefs";

    public static final String APP_PREFERENCES_TASK_HEADER = "Header";
    public static final String APP_PREFERENCES_TASK_DISCRIPTION = "Discription";
    ListView lvSimple;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_map));

        setContentView(R.layout.activity_task);
        header = findViewById(R.id.task_activity_header);
        back = findViewById(R.id.topbar_backpres);
        discription = findViewById(R.id.task_activity_discription);

        back.setOnClickListener(this);

        mPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mPrefs.contains(APP_PREFERENCES_TASK_HEADER)) {
            header.setText(mPrefs.getString(APP_PREFERENCES_TASK_HEADER, ""));
            discription.setText(mPrefs.getString(APP_PREFERENCES_TASK_DISCRIPTION, ""));
        }


        ArrayList<TaskModelImages> list= new ArrayList();
        list.add(new TaskModelImages(R.drawable.almaty1));
        list.add(new TaskModelImages(R.drawable.almaty1));
        list.add(new TaskModelImages(R.drawable.almaty1));

        TaskAdapterImages adapter = new TaskAdapterImages(list, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        linearLayoutManager.scrollToPositionWithOffset(0, 20);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_tasks_image);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.smoothScrollBy(-40, 0);

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.scroll_nest_task);
        scrollView.setFillViewport (true);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}