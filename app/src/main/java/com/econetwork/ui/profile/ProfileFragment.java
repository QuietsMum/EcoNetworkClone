package com.econetwork.ui.profile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

import com.econetwork.ExpandebleReportGridview;
import com.econetwork.R;
import com.econetwork.Reportadapter;
import com.econetwork.ui.dashboard.DashboardViewModel;
import com.econetwork.ui.home.Edu_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel profileViewModel;
    LinearLayout lineranimtext;
    ConstraintLayout touchlayout;
    ConstraintLayout.LayoutParams layoutParams_old;
    ConstraintLayout.LayoutParams layoutParams;
    FragmentContainerView fragmentContainerView;
    RelativeLayout topbar;
    RelativeLayout touchevent;
    RelativeLayout touchevent_name;
    CardView cardtrans;
    NestedScrollView nest_anim;
    LinearLayout statistic;
    LinearLayout statistic_view;
    LinearLayout report;
    GridView gridview;
    TextView text_stat;
    TextView text_rep;
    View view_stat;
    View view_rep;
    SimpleAdapter gridArrayAdapter;
    ArrayList<Map<String, Object>> data;
//    LinearLayout carrdChageParams;
    FrameLayout.LayoutParams layoutParamsCardMain;
    LinearLayout linCardRep;
    int pxCardstat;
    int pxCardrep;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        topbar = getActivity().findViewById(R.id.topbar);
        touchevent = root.findViewById(R.id.touch_event_lay);
        touchevent_name = root.findViewById(R.id.touch_namelay);
        cardtrans = root.findViewById(R.id.card_trans);
        linCardRep = root.findViewById(R.id.lin_card_rep);
        nest_anim = root.findViewById(R.id.scroll_view_anim);
        statistic = root.findViewById(R.id.button_statistic);
        statistic_view = root.findViewById(R.id.cnange_statistic);
        report = root.findViewById(R.id.button_report);
        gridview = root.findViewById(R.id.view_report_grid);
        text_stat = root.findViewById(R.id.text_statistic);
        text_rep = root.findViewById(R.id.text_report);
        view_stat = root.findViewById(R.id.line_statistic);
        view_rep = root.findViewById(R.id.line_report);

        pxCardstat = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                500,
                getActivity().getResources().getDisplayMetrics()
        );

        pxCardrep = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                800,
                getActivity().getResources().getDisplayMetrics()
        );

        layoutParamsCardMain = (FrameLayout.LayoutParams)
                touchevent.getLayoutParams();

        touchListener(touchevent);
        fragmentContainerView = getActivity().findViewById(R.id.nav_host_fragment);
        layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                80,
                getActivity().getResources().getDisplayMetrics()
        );
        layoutParams_old = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();
        layoutParams.setMargins(0, 20, 0, px);
        fragmentContainerView.setLayoutParams(layoutParams);
        topbar.setVisibility(View.GONE);

        lineranimtext = root.findViewById(R.id.linear_animtext);
        touchlayout = root.findViewById(R.id.container);

        touchevent_name.setOnClickListener(this);
        statistic.setOnClickListener(this);
        report.setOnClickListener(this);

//        nest_anim.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
//        {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
//            {
//                if (v.getChildAt(v.getChildCount() - 1) != null)
//                {
//                    if (scrollY < oldScrollY)
//                    {
//                        if (scrollY <= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight()))
//                        {
//                            Log.e("ERRRERERER", "UPUPUP");
//                            TransitionManager.beginDelayedTransition(lineranimtext,  new Fade(Fade.IN));
//                            TransitionManager.beginDelayedTransition(cardtrans,  new Fade(Fade.IN));
//                            TransitionManager.beginDelayedTransition(touchevent_name, new Fade(Fade.OUT));
//                            lineranimtext.setVisibility(View.VISIBLE);
//                            cardtrans.setVisibility(View.VISIBLE);
//                            touchevent_name.setVisibility(View.GONE);
//                        }
//                    }
//
//                }
//            }
//        });

        int[] mThumbIds = { R.drawable.almaty1, R.drawable.almaty1,
            R.drawable.almaty1, R.drawable.almaty1, R.drawable.almaty1,
            R.drawable.almaty1, R.drawable.almaty1, R.drawable.almaty1,
            R.drawable.almaty1, R.drawable.almaty1, R.drawable.almaty1,
            R.drawable.almaty1, R.drawable.almaty1, R.drawable.almaty1,
            R.drawable.almaty1};

        int[] icons = { R.drawable.ic_garbage, R.drawable.ic_garbage,
                R.drawable.ic_garbage, R.drawable.ic_garbage, R.drawable.ic_garbage,
                R.drawable.ic_garbage, R.drawable.ic_garbage, R.drawable.ic_garbage,
                R.drawable.ic_garbage, R.drawable.ic_garbage, R.drawable.ic_garbage,
                R.drawable.ic_garbage, R.drawable.ic_garbage, R.drawable.ic_garbage,
                R.drawable.ic_garbage};

        data = new ArrayList<>(
                mThumbIds.length);
        Map<String, Object> m;
        for (int i = 0; i < mThumbIds.length; i++) {
            m = new HashMap<String, Object>();
            m.put("Image", mThumbIds[i]);
            m.put("Icon", icons[i]);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { "Image", "Icon" };

// массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.profile_events_image, R.id.profile_events_icon};

////        GridView
//        gridview = new GridView(getActivity().getBaseContext());

        gridArrayAdapter = new SimpleAdapter(getActivity(),  data, R.layout.profile_events_item,
                from, to);

        gridview.setAdapter(gridArrayAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ProfileFragment.this, icons[position]+" Clicked", Toast.LENGTH_SHORT).show();
            }
        });

//        gridview =  getView().findViewById(R.id.profile_events_image);
//        gridview.setExpanded(true);
//        gridArrayAdapter = new Reportadapter(getActivity().getBaseContext(), R.layout.profile_events_item, mThumbIds);
//        gridview.setAdapter(gridArrayAdapter);
//        gridArrayAdapter.notifyDataSetChanged();
        Log.e("GridItemCounter!!!!!", String.valueOf(gridview.getChildCount()));
//        gridview.setOnItemClickListener(gridviewOnItemClickListener);
//        gridview.setNumColumns(3);
//        gridview.setExpanded(true);
//        gridArrayAdapter.notifyDataSetChanged();


        return root;
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            // выводим номер позиции

        }
    };

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        TransitionManager.beginDelayedTransition(lineranimtext,  new Fade(Fade.OUT));
                        TransitionManager.beginDelayedTransition(cardtrans,  new Fade(Fade.OUT));
                        TransitionManager.beginDelayedTransition(touchevent_name, new Fade(Fade.IN));
                        lineranimtext.setVisibility(View.GONE);
                        cardtrans.setVisibility(View.INVISIBLE);
                        touchevent_name.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentContainerView.setLayoutParams(layoutParams);
        topbar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentContainerView.setLayoutParams(layoutParams_old);
        topbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.touch_namelay){
            TransitionManager.beginDelayedTransition(lineranimtext,  new Fade(Fade.IN));
            TransitionManager.beginDelayedTransition(cardtrans,  new Fade(Fade.IN));
            TransitionManager.beginDelayedTransition(touchevent_name, new Fade(Fade.OUT));
            lineranimtext.setVisibility(View.VISIBLE);
            cardtrans.setVisibility(View.VISIBLE);
            touchevent_name.setVisibility(View.GONE);
        }  if (v.getId() == R.id.button_statistic){
            touchevent.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, pxCardstat));

            linCardRep.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

//            carrdChageParams.setMinimumHeight(10);
            statistic_view.setVisibility(View.VISIBLE);
            gridview.setVisibility(View.INVISIBLE);

            text_stat.setTextColor(getResources().getColor(R.color.textmainHeader2));
            text_rep.setTextColor(getResources().getColor(R.color.neutral1));
            view_stat.setVisibility(View.VISIBLE);
            view_rep.setVisibility(View.INVISIBLE);


             int pxCardscroll = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                     110,
                    getActivity().getResources().getDisplayMetrics()
            );

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nest_anim
                    .getLayoutParams();

            layoutParams.setMargins(0, pxCardscroll, 0, 0);
            nest_anim.setLayoutParams(layoutParams);
//            nest_anim.fullScroll(View.FOCUS_DOWN);

        } if (v.getId() == R.id.button_report){
            touchevent.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, pxCardrep));

            linCardRep.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, pxCardrep));

//            carrdChageParams.setMinimumHeight(10);
            statistic_view.setVisibility(View.GONE);
            gridview.setVisibility(View.VISIBLE);
            gridArrayAdapter.notifyDataSetChanged();
            text_rep.setTextColor(getResources().getColor(R.color.textmainHeader2));
            text_stat.setTextColor(getResources().getColor(R.color.neutral1));
            view_rep.setVisibility(View.VISIBLE);
            view_stat.setVisibility(View.INVISIBLE);

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nest_anim
                    .getLayoutParams();

            layoutParams.setMargins(0, 0, 0, 0);
            nest_anim.setLayoutParams(layoutParams);

        }

    }
}
