package com.econetwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.econetwork.ui.events.EventAdapterImages;
import com.econetwork.ui.events.EventModelImages;
import com.econetwork.ui.news.NewsAdapterImages;
import com.econetwork.ui.news.NewsModelImages;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_map));

        setContentView(R.layout.activity_news);

        back = findViewById(R.id.topbar_backpres_news);
        close = findViewById(R.id.button_close);

        back.setOnClickListener(this);
        close.setOnClickListener(this);

        ArrayList<NewsModelImages> list= new ArrayList();
        list.add(new NewsModelImages(R.drawable.news1));
        list.add(new NewsModelImages(R.drawable.news2));
        list.add(new NewsModelImages(R.drawable.news3));

        NewsAdapterImages adapter = new NewsAdapterImages(list, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        linearLayoutManager.scrollToPositionWithOffset(0, 20);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_news_image);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.smoothScrollBy(-40, 0);

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.scroll_nest_news);
        scrollView.setFillViewport (true);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}