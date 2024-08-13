package com.econetwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.econetwork.ui.events.EventAdapterImages;
import com.econetwork.ui.events.EventModelImages;
import com.econetwork.ui.task.TaskAdapterImages;
import com.econetwork.ui.task.TaskModelImages;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {

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

        setContentView(R.layout.activity_event);

        back = findViewById(R.id.topbar_backpres_event);

        back.setOnClickListener(this);

        ArrayList<EventModelImages> list= new ArrayList();
        list.add(new EventModelImages(R.drawable.almaty4));
        list.add(new EventModelImages(R.drawable.almaty5));
        list.add(new EventModelImages(R.drawable.almaty6));

        EventAdapterImages adapter = new EventAdapterImages(list, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        linearLayoutManager.scrollToPositionWithOffset(0, 20);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_event_image);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.smoothScrollBy(-40, 0);

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.scroll_nest_event);
        scrollView.setFillViewport (true);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}