package com.econetwork.ui.notifications;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

import com.econetwork.CustomExpandableListAdapter;
import com.econetwork.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment implements View.OnClickListener{

    private static final TimeInterpolator GAUGE_ANIMATION_INTERPOLATOR = new DecelerateInterpolator(2);
    private static final int MAX_LEVEL = 40;
    private static final long GAUGE_ANIMATION_DURATION = 1500;
    private ProgressBar mProgressBar;
    FragmentContainerView fragmentContainerView;
    ConstraintLayout.LayoutParams layoutParams;
    ConstraintLayout.LayoutParams layoutParams_old;
    AnimatedVectorDrawable animation;
    TextView textProgress;
    ListView list_events_item;
    ArrayAdapter arrayAdapter;
    private MapView mapView;
    private final String MAPKIT_API_KEY = "d871680e-eb24-4d28-a46b-c15eda2ad6a1";
    private final Point TARGET_LOCATION = new Point(43.238949, 76.889709);
    HashMap<String, List<String>> expandableListDetail;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    TextView text_my;
    TextView text_new;
    View view_my;
    View view_new;
    LinearLayout button_my;
    LinearLayout button_new;
    LinearLayout lay_my;
    LinearLayout lay_new;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);

        MapKitFactory.initialize(getActivity().getApplicationContext());

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        fragmentContainerView = getActivity().findViewById(R.id.nav_host_fragment);
        layoutParams_old = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();

//        mProgressBar = root.findViewById(R.id.edu_progressbar);

        int pxTop = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                60,
                getActivity().getResources().getDisplayMetrics()
        );

        int pxBot = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                55,
                getActivity().getResources().getDisplayMetrics()
        );

        mapView = root.findViewById(R.id.mapview_tasks_main);
        textProgress = root.findViewById(R.id.text_prog_bar);
//        textProgress.setText(String.valueOf(mProgressBar.getProgress()));
        layoutParams = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();
        layoutParams.setMargins(0, pxTop, 0, pxBot);
        fragmentContainerView.setLayoutParams(layoutParams);

//        list_events_item = root.findViewById(R.id.listview_events_main);
        String[] textEvents = { "Расчистить двор","Посадить деревья и живую изгородь во дворе","Собрать листья и мусор на аллее"};
        //int[] imageEvents = { R.drawable.ic_right_chevron, R.drawable.ic_right_chevron, R.drawable.ic_right_chevron};
        String[] from = { "Text_event_item","Image_event_item"};
        int[] to = { R.id.list_events_text_small, R.id.list_events_image_small};

        HashMap<String, List<String>> data = new HashMap<>(
                textEvents.length);

        List<String> m = null;
        for (int i = 0; i < textEvents.length; i++) {
            m =  new ArrayList<String>();
            m.add( textEvents[i]);
//            m.put("Image_event_item",  R.drawable.ic_right_chevron);
            data.put("Text_event_item", m);
        }

        text_my = root.findViewById(R.id.my_tasks_main_text);
        text_new = root.findViewById(R.id.available_tasks_main_text);
        button_my = root.findViewById(R.id.button_my);
        button_new = root.findViewById(R.id.button_new);
        lay_my = root.findViewById(R.id.linear_my_tasks);
        lay_new = root.findViewById(R.id.linear_new_tasks);
        view_my = root.findViewById(R.id.my_tasks_main_line);
        view_new = root.findViewById(R.id.available_tasks_main_line);

        button_my.setOnClickListener(this);
        button_new.setOnClickListener(this);

//        SimpleAdapter sAdapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.events_item_small,
//                from, to);
//
//        expandableListView = (ExpandableListView) root.findViewById(R.id.listview_events_main);
//        expandableListAdapter = new CustomExpandableListAdapter(getActivity().getBaseContext(), data);
//        expandableListView.setAdapter(expandableListAdapter);
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//
//            }
//        });
//
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//
//
//            }
//        });
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//
//                return false;
//            }
//        });

//        list_events_item = root.findViewById(R.id.listview_events_main);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
//               R.layout.events_item_small, R.id.list_events_text_small, textEvents);

        // присваиваем адаптер списку
//        list_events_item.setAdapter(sAdapter);




//        list_events_item.setAdapter(sAdapter);

        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);

//        ObjectAnimator animator = ObjectAnimator.ofInt(mProgressBar, "progress", 0, MAX_LEVEL);
//        animator.setInterpolator(GAUGE_ANIMATION_INTERPOLATOR);
//        animator.setDuration(GAUGE_ANIMATION_DURATION);
//        animator.start();
          ImageView image = (ImageView) root.findViewById(R.id.object);
          Drawable d = image.getDrawable();
          animation = (AnimatedVectorDrawable) d;
          animation.start();

//        ArrayList<Events_item_small> list2= new ArrayList();
//        list2.add(new Events_item_small("Расчистить двор", R.drawable.ic_right_chevron));
//        list2.add(new Events_item_small("Посадить деревья и живую изгородь во дворе", R.drawable.ic_right_chevron));
//        list2.add(new Events_item_small("Собрать листья и мусор на аллее", R.drawable.ic_right_chevron));


//        Events_main_item_adapter adapter2 = new Events_main_item_adapter(list2, getActivity().getApplicationContext());
//        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
//        RecyclerView mRecyclerView2 = root.findViewById(R.id.listview_events_main);
//        mRecyclerView2.setLayoutManager(linearLayoutManager2);
//        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView2.setAdapter(adapter2);

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
    public void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        mapView.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_my){

            text_my.setTextColor(getResources().getColor(R.color.textmainHeader1));
            text_new.setTextColor(getResources().getColor(R.color.neutral1));
            view_my.setVisibility(View.VISIBLE);
            view_new.setVisibility(View.INVISIBLE);
            lay_my.setVisibility(View.VISIBLE);
            lay_new.setVisibility(View.GONE);

        } if (v.getId() == R.id.button_new){

            text_new.setTextColor(getResources().getColor(R.color.textmainHeader1));
            text_my.setTextColor(getResources().getColor(R.color.neutral1));
            view_new.setVisibility(View.VISIBLE);
            view_my.setVisibility(View.INVISIBLE);
            lay_my.setVisibility(View.GONE);
            lay_new.setVisibility(View.VISIBLE);

        }

    }

}