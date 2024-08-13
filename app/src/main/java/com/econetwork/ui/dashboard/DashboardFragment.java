package com.econetwork.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.R;
import com.econetwork.VideoMultiViewTypeAdapter;
import com.econetwork.ui.home.Edu_model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements View.OnClickListener {


    String[] string;
    FragmentContainerView fragmentContainerView;
    ConstraintLayout.LayoutParams layoutParams;
    ConstraintLayout.LayoutParams layoutParams_old;
    TextView text_even;
    TextView text_news;
    View view_even;
    View view_news;
    LinearLayout but_even;
    LinearLayout but_news;
    RecyclerView myListTags;
    RecyclerView mRecyclerViewEvents;
    RecyclerView mRecyclerViewNews;
    TagsAdapter tagsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        fragmentContainerView = getActivity().findViewById(R.id.nav_host_fragment);
        layoutParams_old = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                55,
                getActivity().getResources().getDisplayMetrics()
        );
        layoutParams = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, px);
        fragmentContainerView.setLayoutParams(layoutParams);

        text_even = root.findViewById(R.id.text_events);
        text_news = root.findViewById(R.id.text_news);
        view_even = root.findViewById(R.id.line_events);
        view_news = root.findViewById(R.id.line_news);
        but_even = root.findViewById(R.id.button_events);
        but_news = root.findViewById(R.id.button_news);

        but_even.setOnClickListener(this);
        but_news.setOnClickListener(this);

        NestedScrollView scrollView = (NestedScrollView) root.findViewById (R.id.scroll_nest_dash);
        scrollView.setFillViewport (true);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        myListTags = root.findViewById(R.id.tagcontainer);
        myListTags.setLayoutManager(layoutManager);
        string = new  String[]{"Алматы","Бостандыкский район","Улица Жарокова", "Улица Абая"};
        tagsAdapter = new TagsAdapter(string);
        myListTags.setAdapter(tagsAdapter);



        ArrayList<EventsModelMainItem> list2= new ArrayList();
        list2.add(new EventsModelMainItem("Посадка деревьев в Дендрарии",R.drawable.ecopic));
        list2.add(new EventsModelMainItem("Посадка деревьев в Дендрарии",R.drawable.ecopic));
        list2.add(new EventsModelMainItem("Посадка деревьев в Дендрарии",R.drawable.ecopic));

        Eventsadapter adapter2 = new Eventsadapter(list2,getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerViewEvents = root.findViewById(R.id.events_container);
        mRecyclerViewEvents.setLayoutManager(linearLayoutManager2);
        mRecyclerViewEvents.setAdapter(adapter2);

        ArrayList<NewsModelMainItem> list= new ArrayList();
        list.add(new NewsModelMainItem("Шесть человек спасли в горах",R.drawable.ecopic));
        list.add(new NewsModelMainItem("Какая погода ждет Казахстанцев",R.drawable.ecopic));
        list.add(new NewsModelMainItem("Розыгрыш от партнеров",R.drawable.ecopic));
        list.add(new NewsModelMainItem("Шесть человек спасли в горах",R.drawable.ecopic));
        list.add(new NewsModelMainItem("Какая погода ждет Казахстанцев",R.drawable.ecopic));
        list.add(new NewsModelMainItem("Розыгрыш от партнеров",R.drawable.ecopic));

        NewsAdapter adapter_news = new NewsAdapter(list,getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerViewNews = root.findViewById(R.id.news_container);
        mRecyclerViewNews.setLayoutManager(linearLayoutManager);
        mRecyclerViewNews.setAdapter(adapter_news);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentContainerView.setLayoutParams(layoutParams);
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentContainerView.setLayoutParams(layoutParams_old);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_events){
            text_even.setTextColor(getResources().getColor(R.color.textmainHeader1));
            text_news.setTextColor(getResources().getColor(R.color.neutral1));
            view_even.setVisibility(View.VISIBLE);
            view_news.setVisibility(View.INVISIBLE);
            mRecyclerViewEvents.setVisibility(View.VISIBLE);
            string = new  String[]{"Алматы","Бостандыкский район","Улица Жарокова", "Улица Абая"};
            tagsAdapter = new TagsAdapter(string);
            tagsAdapter.notifyDataSetChanged();
            myListTags.setAdapter(tagsAdapter);
            mRecyclerViewEvents.setVisibility(View.VISIBLE);
            mRecyclerViewNews.setVisibility(View.GONE);

        } if (v.getId() == R.id.button_news){
            text_news.setTextColor(getResources().getColor(R.color.textmainHeader1));
            text_even.setTextColor(getResources().getColor(R.color.neutral1));
            view_news.setVisibility(View.VISIBLE);
            view_even.setVisibility(View.INVISIBLE);
            mRecyclerViewEvents.setVisibility(View.INVISIBLE);
            string = new  String[]{"Астана","Район такой-то","Улица Абая", "Улица Толе би"};
            tagsAdapter = new TagsAdapter(string);
            tagsAdapter.notifyDataSetChanged();
            myListTags.setAdapter(tagsAdapter);
            mRecyclerViewEvents.setVisibility(View.GONE);
            mRecyclerViewNews.setVisibility(View.VISIBLE);

        }
    }
}