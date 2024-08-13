package com.econetwork.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.MainActivity;
import com.econetwork.MapActivity;
import com.econetwork.MultiViewTypeAdapter;
import com.econetwork.NewsActivity;
import com.econetwork.PartnersViewTypeAdapter;
import com.econetwork.R;
import com.econetwork.TaskActivity;
import com.econetwork.Util;
import com.econetwork.VideoMultiViewTypeAdapter;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.TileId;
import com.yandex.mapkit.Version;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.geo.Projection;
import com.yandex.mapkit.geometry.geo.Projections;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.layers.Layer;
import com.yandex.mapkit.layers.LayerOptions;
import com.yandex.mapkit.resource_url_provider.DefaultUrlProvider;
import com.yandex.mapkit.tiles.UrlProvider;
import com.yandex.runtime.image.ImageProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    private final String MAPKIT_API_KEY = "d871680e-eb24-4d28-a46b-c15eda2ad6a1";
    private final Point TARGET_LOCATION = new Point(43.238949, 76.889709);

    private UrlProvider urlProvider;
    private DefaultUrlProvider resourceUrlProvider;
    private Projection projection;
    FragmentContainerView fragmentContainerView;
    private MapView mapView;
    ImageView imageClick;
    TextView task_first;
    String task_first_disc;
    RelativeLayout task_first_but;
    TextView task_second;
    String task_second_disc;
    RelativeLayout task_second_but;
    RelativeLayout news_first;
    RelativeLayout news_second;

    ConstraintLayout.LayoutParams layoutParams;
    ConstraintLayout.LayoutParams layoutParams_old;

    SharedPreferences mPrefs;

    public static final String APP_PREFERENCES = "myprefs";

    public static final String APP_PREFERENCES_TASK_HEADER = "Header";
    public static final String APP_PREFERENCES_TASK_DISCRIPTION = "Discription";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(getActivity().getApplicationContext());
        // Создание MapView.
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        fragmentContainerView = getActivity().findViewById(R.id.nav_host_fragment);
        layoutParams_old = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                60,
                getActivity().getResources().getDisplayMetrics()
        );
        layoutParams = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();
        layoutParams.setMargins(0, px, 0, 0);
        fragmentContainerView.setLayoutParams(layoutParams);
        mapView = root.findViewById(R.id.mapview);
        imageClick = root.findViewById(R.id.imageClick);
        task_first = root.findViewById(R.id.task_first_home);
        task_first_but = root.findViewById(R.id.task_first_home_but);
        task_second = root.findViewById(R.id.task_second_home);
        task_second_but = root.findViewById(R.id.task_second_home_but);
        news_first = root.findViewById(R.id.news_first);
        news_second = root.findViewById(R.id.news_second);
        imageClick.setOnClickListener(this);
        task_first.setOnClickListener(this);
        task_second.setOnClickListener(this);
        news_first.setOnClickListener(this);
        news_second.setOnClickListener(this);

        ArrayList<Events_model> list= new ArrayList();
        list.add(new Events_model("Поход в Чимбулак",R.drawable.image1));
        list.add(new Events_model("Посадка деревьев в ботаническом саду",R.drawable.image2));
        list.add(new Events_model("Очистка Сайрана",R.drawable.image3));


        MultiViewTypeAdapter adapter = new MultiViewTypeAdapter(list,getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        linearLayoutManager.scrollToPositionWithOffset(1, 20);
        RecyclerView mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.smoothScrollBy(-40, 0);


        ArrayList<Edu_model> list2= new ArrayList();
        list2.add(new Edu_model("Сортировка",R.drawable.video21));
        list2.add(new Edu_model("Экология",R.drawable.video22));
        list2.add(new Edu_model("Ренджеры",R.drawable.video23));


        VideoMultiViewTypeAdapter adapter2 = new VideoMultiViewTypeAdapter(list2,getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        RecyclerView mRecyclerView2 = root.findViewById(R.id.recyclerViewVIdeo);
        mRecyclerView2.setLayoutManager(linearLayoutManager2);
        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView2.setAdapter(adapter2);


        ArrayList<Partners_model> list_partners= new ArrayList();
        list_partners.add(new Partners_model("Наш новый партнер -компания Nike!",R.drawable.nike));
        list_partners.add(new Partners_model("Теперь вы можете приобрести эко-товары со скидкой 15%",R.drawable.sber));
        list_partners.add(new Partners_model("Starbucks запустили новые товары",R.drawable.starbucks));

        PartnersViewTypeAdapter adapter3= new PartnersViewTypeAdapter(list_partners,getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        linearLayoutManager3.scrollToPositionWithOffset(1, 20);
        RecyclerView mRecyclerView3 = root.findViewById(R.id.recyclerView_partners);
        mRecyclerView3.setLayoutManager(linearLayoutManager3);
        mRecyclerView3.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView3.setAdapter(adapter3);
        mRecyclerView3.smoothScrollBy(-60, 0);

        // Перемещение камеры в центр Санкт-Петербурга.
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);

        try {
            getHttpTasksHome();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }


    public void getHttpTasksHome() throws IOException {

        String url = "https://eztecheconetwork.pythonanywhere.com/api/tasks/available";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + Util.utoken)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                Log.e("HttpService", "onFailure() Request was: " + request);

                e.printStackTrace();
            }

            @Override
            public void onResponse(Response r) throws IOException {


                final String mMessage = r.body().string();
                Log.e("RESPONSE", mMessage);

//                mPrefs = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONArray array = new JSONArray(mMessage);
                                final JSONObject obj1 = new JSONObject(String.valueOf(array.getJSONObject(0)));
                                final JSONObject obj2 = new JSONObject(String.valueOf(array.getJSONObject(1)));
                                task_first.setText(obj1.getString("title"));
                                task_first_disc = obj1.getString("description");
                                task_second.setText(obj2.getString("title"));
                                task_second_disc = obj2.getString("description");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });




//                LoginActivity.finish();

//                try {
//                    JSONObject obj = new JSONObject(mMessage);
//                    Long mID = obj.getLong("id");
//                    SQLiteDatabase db = dbHelper.getWritableDatabase();
//                    ContentValues cv = new ContentValues();
//                    cv.put("id", mID);
//                    cv.put("name", username.getText().toString());
//                    db.insert("econetworktable", null, cv);
//                    db.close();
//                    dbHelper.close();
////                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                    startActivity(intent);
////                    barreg.setVisibility(View.INVISIBLE);
//                    finish();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

//                if (mMessage.equals("\"This user alreday registered\"")) {
//                    RegistActivity.this.runOnUiThread(new Runnable() {
//                        public void run() {
//                            AlertDialog alertDialog = new AlertDialog.Builder(RegistActivity.this).create();
//                            alertDialog.setTitle("");
//                            alertDialog.setMessage("Этот номер уже зарегистрирован");
//                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Готово",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog1, int which) {
//                                            dialog.dismiss();
//                                            Intent intent = new Intent(getApplicationContext(), PhoneLoginActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                    });
//                            alertDialog.show();
//                            Toast.makeText(LoginActivity2.this, "", Toast.LENGTH_SHORT).show();
//                            Common.referrer = "";
//                        }
//                    });
//
//                } else {
//                    try {
//                        obj = new JSONObject(mMessage);
//                        mStatus = obj.getString("auth_status");
//                        mToken = obj.getString("_token");
//                        mID = obj.getLong("id");
//                        SQLiteDatabase db = dbHelper.getWritableDatabase();
//                        ContentValues cv = new ContentValues();
//                        cv.put("id", mID);
//                        cv.put("token", mToken);
//                        cv.put("name", mName);
//                        db.insert("orzutable", null, cv);
//                        db.close();
//                        dbHelper.close();
//                        Intent intent = new Intent(getApplicationContext(), RegistCity.class);
//                        startActivity(intent);
//                        progressBar.setVisibility(View.INVISIBLE);
//                        finish();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }

            }
        });
    }



    @Override
    public void onStop() {
        super.onStop();
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        MapKitFactory.getInstance().onStop();
        mapView.onStop();
    }

    @Override
    public void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.news_second) {
            Intent intent = new Intent(getActivity(), NewsActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.news_first) {
            Intent intent = new Intent(getActivity(), NewsActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.imageClick) {
            Intent intent = new Intent(getActivity(), MapActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.task_first_home) {
            Intent intent = new Intent(getActivity(), TaskActivity.class);
            mPrefs = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(APP_PREFERENCES_TASK_HEADER, String.valueOf(task_first.getText()));
            editor.putString(APP_PREFERENCES_TASK_DISCRIPTION, task_first_disc);
            editor.apply();
            startActivity(intent);
        }
        if (v.getId() == R.id.task_second_home) {
            Intent intent = new Intent(getActivity(), TaskActivity.class);
            mPrefs = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(APP_PREFERENCES_TASK_HEADER, String.valueOf(task_second.getText()));
            editor.putString(APP_PREFERENCES_TASK_DISCRIPTION, task_second_disc);
            editor.apply();
            startActivity(intent);
        }
    }


}