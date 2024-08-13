package com.econetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Circle;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.CircleMapObject;
import com.yandex.mapkit.map.Cluster;
import com.yandex.mapkit.map.ClusterListener;
import com.yandex.mapkit.map.ClusterTapListener;
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.map.SizeChangedListener;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapActivity extends AppCompatActivity implements View.OnClickListener, ClusterListener, ClusterTapListener, MapObjectTapListener {


    private final String MAPKIT_API_KEY = "c1d3aee1-2a05-476e-b012-023b44b753a8";
    private final Point TARGET_LOCATION = new Point(43.238949, 76.889709);
    private MapView mapView;
    private MapObjectCollection mapObjects;
    private Handler animationHandler;
    private final Point CAMERA_TARGET = new Point(43.238949, 76.889709);
    private final Point RECTANGLE_POINT1 = new Point(43.358949, 76.789709);
    private final Point RECTANGLE_POINT2_new2 = new Point(43.272949, 76.799709);
    private final Point RECTANGLE_POINT2_new3 = new Point(43.255949, 76.769709);
    private final Point RECTANGLE_POINT2_new4 = new Point(43.230949, 76.769709);
    private final Point RECTANGLE_POINT2 = new Point(43.232949, 76.789709);
    private final Point RECTANGLE_POINT3_new2 = new Point(43.244949, 76.835709);
    private final Point RECTANGLE_POINT3_new3 = new Point(43.255949, 76.842709);
    private final Point RECTANGLE_POINT3 = new Point(43.270949, 76.88709);
    private final Point RECTANGLE_POINT4_new2 = new Point(43.300949, 76.89409);
    private final Point RECTANGLE_POINT4_new3 = new Point(43.339949, 76.918909);
    private final Point RECTANGLE_POINT4 = new Point(43.348949, 76.919709);
    private final Point RECTANGLE_POINT4_new4 = new Point(43.349949, 76.904709);
    private final Point RECTANGLE_POINT4_new5 = new Point(43.339949, 76.891709);
    private final Point RECTANGLE_POINT4_new6 = new Point(43.341949, 76.881709);
    private final Point RECTANGLE_POINT4_new7 = new Point(43.357949, 76.874709);
    private final Point RECTANGLE_POINT4_new8 = new Point(43.353849, 76.834709);

    private final Point RECTANGLE_POINT1_2 = new Point(43.505000, 76.919709);
    private final Point RECTANGLE_POINT2_2 = new Point(43.358949, 76.789709);
    private final Point RECTANGLE_POINT3_2_new5 = new Point(43.349949, 76.904709);
    private final Point RECTANGLE_POINT3_2_new4 = new Point(43.339949, 76.891709);
    private final Point RECTANGLE_POINT3_2_new3 = new Point(43.341949, 76.881709);
    private final Point RECTANGLE_POINT3_2_new2 = new Point(43.357949, 76.874709);
    private final Point RECTANGLE_POINT3_2_new = new Point(43.353849, 76.834709);
    private final Point RECTANGLE_POINT3_2 = new Point(43.348949, 76.919709);
    private final Point RECTANGLE_POINT3_2_new6 = new Point(43.354949, 76.941709);
    private final Point RECTANGLE_POINT3_2_new7 = new Point(43.355149, 76.948309);
    private final Point RECTANGLE_POINT3_2_new8 = new Point(43.339249, 76.982309);
    private final Point RECTANGLE_POINT3_2_new9 = new Point(43.345249, 76.995309);
    private final Point RECTANGLE_POINT3_2_new10 = new Point(43.348249, 77.060309);
    private final Point RECTANGLE_POINT3_2_new11 = new Point(43.388249, 77.120309);
    private final Point RECTANGLE_POINT3_2_new12 = new Point(43.399249, 77.169209);
    private final Point RECTANGLE_POINT4_2 = new Point(43.460949, 77.139809);
    private final Point RECTANGLE_POINT3_2_new13 = new Point(43.470949, 77.060309);
    private final Point RECTANGLE_POINT3_2_new14 = new Point(43.496500, 76.992479);

    private final Point RECTANGLE_POINT1_3 = new Point(43.348949, 76.919709);
    private final Point RECTANGLE_POINT1_3_new3 = new Point(43.300949, 76.89409);
    private final Point RECTANGLE_POINT1_3_new2 = new Point(43.339949, 76.918909);
    private final Point RECTANGLE_POINT2_3 = new Point(43.270949, 76.88709);
    private final Point RECTANGLE_POINT2_3_new3 = new Point(43.271049, 76.946009);
    private final Point RECTANGLE_POINT2_3_new2 = new Point(43.2690949, 76.92309);
    private final Point RECTANGLE_POINT2_3_new1 = new Point(43.260949, 76.88709);
    private final Point RECTANGLE_POINT3_3 = new Point(43.270949, 76.959709);
    private final Point RECTANGLE_POINT4_3 = new Point(43.339249, 76.982309);
    private final Point RECTANGLE_POINT4_3_new3 = new Point(43.354949, 76.941709);
    private final Point RECTANGLE_POINT4_3_new2 = new Point(43.355149, 76.948309);

    private final Point RECTANGLE_POINT1_4 = new Point(43.232949, 76.789709);
    private final Point RECTANGLE_POINT2_4_new2 = new Point(43.230949, 76.769709);
    private final Point RECTANGLE_POINT2_4_new3 = new Point(43.226249, 76.742609);
    private final Point RECTANGLE_POINT2_4_new4 = new Point(43.176249, 76.754609);
    private final Point RECTANGLE_POINT2_4_new5 = new Point(43.170249, 76.774609);
    private final Point RECTANGLE_POINT2_4_new6 = new Point(43.187249, 76.834609);
    private final Point RECTANGLE_POINT2_4 = new Point(43.194949, 76.896709);
    private final Point RECTANGLE_POINT3_4 = new Point(43.228249, 76.951209);
    private final Point RECTANGLE_POINT4_4 = new Point(43.271049, 76.946009);
    private final Point RECTANGLE_POINT5_4 = new Point(43.270949, 76.88709);
    private final Point RECTANGLE_POINT5_4_new2_1 = new Point(43.2690949, 76.92309);
    private final Point RECTANGLE_POINT5_4_new2_2 = new Point(43.260949, 76.88709);
    private final Point RECTANGLE_POINT5_4_new3 = new Point(43.244949, 76.835709);
    private final Point RECTANGLE_POINT5_4_new2 = new Point(43.255949, 76.842709);

    private final Point RECTANGLE_POINT1_5 = new Point(43.339249, 76.982309);
    private final Point RECTANGLE_POINT2_5 = new Point(43.270949, 76.959709);
    private final Point RECTANGLE_POINT2_5_new2 = new Point(43.271049, 76.946009);
    private final Point RECTANGLE_POINT3_5 = new Point(43.228249, 76.951209);
    private final Point RECTANGLE_POINT4_5 = new Point(43.252949, 77.023709);
    private final Point RECTANGLE_POINT5_5 = new Point(43.348249, 77.060309);
    private final Point RECTANGLE_POINT5_5_new2 = new Point(43.345249, 76.995309);

    private final Point TRIANGLE_CENTER = new Point(43.268949, 76.889709);
    private final Point POLYLINE_CENTER = CAMERA_TARGET;
    private final Point CIRCLE_CENTER = new Point(43.218949, 76.889709);
    private final Point HOME_CENTER = new Point(43.218949, 76.889709);
    private final Point DRAGGABLE_PLACEMARK_CENTER = new Point(43.238949, 76.889709);
    private final double OBJECT_SIZE = 0.01;

    PolygonMapObject rect;
    PolygonMapObject rect2;
    PolygonMapObject rect3;
    PolygonMapObject rect4;
    PolygonMapObject rect5;
    PlacemarkMapObject homePlacemark;
    CircleMapObject circle;
    PlacemarkMapObject markerTask1;
    PlacemarkMapObject markerTask2;
    PlacemarkMapObject markerTask3;
    PlacemarkMapObject markerTask4;
    PlacemarkMapObject markerTask5;
    PlacemarkMapObject markerTask6;

    float zoom;
    Point target;

    ImageView but_plus;
    ImageView but_minus;
    ImageView but_zoomout;
    ImageView backpress_map;

    LinearLayout but_mytasks;
    LinearLayout but_newtasks;
    TextView myTaskstext;
    TextView taskHeader;
    View myTasksline;
    TextView newTaskstext;
    TextView taskDisc;
    View newTasksline;
    RelativeLayout.LayoutParams relativeParams;

    RelativeLayout task_card;
    RelativeLayout task_invis;
    int touch = 0;
    private final int MAX_ZOOM = 30;

    private static final String TAG = "GeoJsonActivity";

    private static final float FONT_SIZE = 15;
    private static final float MARGIN_SIZE = 3;
    private static final float STROKE_SIZE = 3;
    private static final int PLACEMARKS_NUMBER = 2000;
    private final List<Point> CLUSTER_CENTERS = Arrays.asList(
            new Point(43.271831, 76.886388)
    );

    private final List<PlacemarkMapObject> points_objects = new ArrayList<>();

    @Override
    public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {

        Log.e("LOGSERROR", "Entering");
        task_card.setVisibility(View.VISIBLE);

        return false;
    }

    public class TextImageProvider extends ImageProvider {
        @Override
        public String getId() {
            return "text_" + text;
        }

        private final String text;
        @Override
        public Bitmap getImage() {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(metrics);

            Paint textPaint = new Paint();
            textPaint.setTextSize(FONT_SIZE * metrics.density);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setAntiAlias(true);

            float widthF = textPaint.measureText(text);
            Paint.FontMetrics textMetrics = textPaint.getFontMetrics();
            float heightF = Math.abs(textMetrics.bottom) + Math.abs(textMetrics.top);
            float textRadius = (float)Math.sqrt(widthF * widthF + heightF * heightF) / 2;
            float internalRadius = textRadius + MARGIN_SIZE * metrics.density;
            float externalRadius = internalRadius + STROKE_SIZE * metrics.density;

            int width = (int) (2 * externalRadius + 0.5);

            Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);

            Paint backgroundPaint = new Paint();
            backgroundPaint.setAntiAlias(true);
            backgroundPaint.setColor(getResources().getColor(R.color.colorPrimary));
            canvas.drawCircle(width / 2, width / 2, externalRadius, backgroundPaint);

            backgroundPaint.setColor(Color.WHITE);
            canvas.drawCircle(width / 2, width / 2, internalRadius, backgroundPaint);

            canvas.drawText(
                    text,
                    width / 2,
                    width / 2 - (textMetrics.ascent + textMetrics.descent) / 2,
                    textPaint);

            return bitmap;
        }

        public TextImageProvider(String text) {
            this.text = text;
        }
    }

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int pxCardtop = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        200,
                        getResources().getDisplayMetrics()
                );

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (touch == 0 ){
                            TransitionManager.beginDelayedTransition(task_card, new Fade(Fade.IN));
                            task_invis.setVisibility(View.GONE);
                            task_card.setVisibility(View.VISIBLE);
                            relativeParams = (RelativeLayout.LayoutParams)task_card.getLayoutParams();
                            relativeParams.setMargins(0, pxCardtop, 0, 0);
                            task_card.setLayoutParams(relativeParams);
                            touch = touch + 1;
                        } else if(touch == 2) {
                            TransitionManager.beginDelayedTransition(task_card, new Fade(Fade.OUT));
                            task_invis.setVisibility(View.VISIBLE);
                            task_card.setVisibility(View.INVISIBLE);
                            relativeParams = (RelativeLayout.LayoutParams)task_card.getLayoutParams();
                            relativeParams.setMargins(0, 0, 0, 0);
                            task_card.setLayoutParams(relativeParams);
                            touch = 0;
                        } else {
                            Toast.makeText(MapActivity.this, "Для закрытия нажмите еще раз", Toast.LENGTH_SHORT).show();
                            touch = touch + 1;
                        }

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

    public void getHttpTasksMap() throws IOException {

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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONArray array = new JSONArray(mMessage);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_map));

        setContentView(R.layout.activity_map);



        mapView = (MapView)findViewById(R.id.mapviewFull);
        but_plus = (ImageView)findViewById(R.id.map_but_plus);
        but_minus = (ImageView)findViewById(R.id.map_but_minus);
        but_zoomout = (ImageView)findViewById(R.id.map_but_zoomout);
        but_mytasks = (LinearLayout) findViewById(R.id.button_myevents_map);
        but_newtasks = (LinearLayout)findViewById(R.id.button_newevents_map);
        myTaskstext = (TextView) findViewById(R.id.text_events_map);
        newTaskstext = (TextView) findViewById(R.id.text_newevents_map);
        myTasksline = (View) findViewById(R.id.line_events_map);
        newTasksline = (View) findViewById(R.id.line_newevents_map);
        backpress_map = (ImageView) findViewById(R.id.topbar_backpres_map);
        task_card = (RelativeLayout) findViewById(R.id.map_task_cardfull);
        task_invis = (RelativeLayout) findViewById(R.id.invisible_rel);

        but_plus.setOnClickListener(this);
        but_minus.setOnClickListener(this);
        but_zoomout.setOnClickListener(this);
        but_mytasks.setOnClickListener(this);
        but_newtasks.setOnClickListener(this);
        backpress_map.setOnClickListener(this);
        task_invis.setOnClickListener(this);

//        layoutParamsCardMain = (FrameLayout.LayoutParams)
//                task_card.getLayoutParams();

//        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nest_scroll_map_relative);
//        scrollView.setFillViewport (false);

        touchListener(task_card);

        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);


        mapObjects = mapView.getMap().getMapObjects().addCollection();

        zoom = mapView.getMap().getCameraPosition().getZoom();

        animationHandler = new Handler();

        createMapObjects();

        Bitmap bitmapMarker = getBitmapFromVectorDrawable(this, R.drawable.ic_map_marker);

        ImageProvider imageProvider = ImageProvider.fromResource(
                MapActivity.this, R.drawable.ic_marker_small);

        // Note that application must retain strong references to both
        // cluster listener and cluster tap listener
        ClusterizedPlacemarkCollection clusterizedCollection =
                mapView.getMap().getMapObjects().addClusterizedPlacemarkCollection(this);

        List<Point> points = createPoints(PLACEMARKS_NUMBER);
        clusterizedCollection.addPlacemarks(points, imageProvider, new IconStyle());

        // Placemarks won't be displayed until this method is called. It must be also called
        // to force clusters update after collection change
        clusterizedCollection.clusterPlacemarks(60, 15);

        mapObjects.addTapListener(this);
    }


    @Override
    public void onClusterAdded(Cluster cluster) {
        // We setup cluster appearance and tap handler in this method
        cluster.getAppearance().setIcon(
                new TextImageProvider(Integer.toString(cluster.getSize())));
        cluster.addClusterTapListener(this);
    }

    @Override
    public boolean onClusterTap(Cluster cluster) {

        // We return true to notify map that the tap was handled and shouldn't be
        // propagated further.
        return true;
    }

    private List<Point> createPoints(int count) {
        ArrayList<Point> points = new ArrayList<Point>();
        Random random = new Random();

        for (int i = 0; i < count; ++i) {
            Point clusterCenter = CLUSTER_CENTERS.get(random.nextInt(CLUSTER_CENTERS.size()));
            double latitude = clusterCenter.getLatitude() + (Math.random() - 0.5) * 0.23;
            double longitude = clusterCenter.getLongitude() + (Math.random() - 0.5) * 0.23;

            points.add(new Point(latitude, longitude));
        }

        return points;
    }

    @Override
    public void onStop() {
        super.onStop();
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    private void createMapObjects() {

        ArrayList<Point> rectPoints = new ArrayList<>();
        rectPoints.add(new Point(
                RECTANGLE_POINT1.getLatitude(),
                RECTANGLE_POINT1.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT2_new2.getLatitude(),
                RECTANGLE_POINT2_new2.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT2_new3.getLatitude(),
                RECTANGLE_POINT2_new3.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT2_new4.getLatitude(),
                RECTANGLE_POINT2_new4.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT2.getLatitude(),
                RECTANGLE_POINT2.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT3_new2.getLatitude(),
                RECTANGLE_POINT3_new2.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT3_new3.getLatitude(),
                RECTANGLE_POINT3_new3.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT3.getLatitude(),
                RECTANGLE_POINT3.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT4_new2.getLatitude(),
                RECTANGLE_POINT4_new2.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT4_new3.getLatitude(),
                RECTANGLE_POINT4_new3.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT4.getLatitude(),
                RECTANGLE_POINT4.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT4_new4.getLatitude(),
                RECTANGLE_POINT4_new4.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT4_new5.getLatitude(),
                RECTANGLE_POINT4_new5.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT4_new6.getLatitude(),
                RECTANGLE_POINT4_new6.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT4_new7.getLatitude(),
                RECTANGLE_POINT4_new7.getLongitude()));
        rectPoints.add(new Point(
                RECTANGLE_POINT4_new8.getLatitude(),
                RECTANGLE_POINT4_new8.getLongitude()));
        rect = mapObjects.addPolygon(
                new Polygon(new LinearRing(rectPoints), new ArrayList<LinearRing>()));
        rect.setStrokeColor(Color.TRANSPARENT);

        rect.setFillColor(getResources().getColor(R.color.layermap));


        ArrayList<Point> rectPoints2 = new ArrayList<>();
        rectPoints2.add(new Point(
                RECTANGLE_POINT1_2.getLatitude(),
                RECTANGLE_POINT1_2.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT2_2.getLatitude(),
                RECTANGLE_POINT2_2.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new.getLatitude(),
                RECTANGLE_POINT3_2_new.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new2.getLatitude(),
                RECTANGLE_POINT3_2_new2.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new3.getLatitude(),
                RECTANGLE_POINT3_2_new3.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new4.getLatitude(),
                RECTANGLE_POINT3_2_new4.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new5.getLatitude(),
                RECTANGLE_POINT3_2_new5.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2.getLatitude(),
                RECTANGLE_POINT3_2.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new6.getLatitude(),
                RECTANGLE_POINT3_2_new6.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new7.getLatitude(),
                RECTANGLE_POINT3_2_new7.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new8.getLatitude(),
                RECTANGLE_POINT3_2_new8.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new9.getLatitude(),
                RECTANGLE_POINT3_2_new9.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new10.getLatitude(),
                RECTANGLE_POINT3_2_new10.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new11.getLatitude(),
                RECTANGLE_POINT3_2_new11.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new12.getLatitude(),
                RECTANGLE_POINT3_2_new12.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT4_2.getLatitude(),
                RECTANGLE_POINT4_2.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new13.getLatitude(),
                RECTANGLE_POINT3_2_new13.getLongitude()));
        rectPoints2.add(new Point(
                RECTANGLE_POINT3_2_new14.getLatitude(),
                RECTANGLE_POINT3_2_new14.getLongitude()));

        rect2 = mapObjects.addPolygon(
                new Polygon(new LinearRing(rectPoints2), new ArrayList<LinearRing>()));
        rect2.setStrokeColor(Color.TRANSPARENT);
        rect2.setFillColor(getResources().getColor(R.color.layermap2));

        Bitmap test = Bitmap.createBitmap(640, 200, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(test);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient linearGradient;
//        { // initialize a linear gradient
//            linearGradient = new LinearGradient(250, 0, 350, 150, colors, positions, Shader.TileMode.CLAMP);
//            paint.setShader(linearGradient);
//        }
       // c.drawPath(rect2, paint);
        c.drawText("LinearGradient", 260, 190, paint);


        ArrayList<Point> rectPoints3 = new ArrayList<>();
        rectPoints3.add(new Point(
                RECTANGLE_POINT1_3.getLatitude(),
                RECTANGLE_POINT1_3.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT1_3_new2.getLatitude(),
                RECTANGLE_POINT1_3_new2.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT1_3_new3.getLatitude(),
                RECTANGLE_POINT1_3_new3.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT2_3.getLatitude(),
                RECTANGLE_POINT2_3.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT2_3_new1.getLatitude(),
                RECTANGLE_POINT2_3_new1.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT2_3_new2.getLatitude(),
                RECTANGLE_POINT2_3_new2.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT2_3_new3.getLatitude(),
                RECTANGLE_POINT2_3_new3.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT3_3.getLatitude(),
                RECTANGLE_POINT3_3.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT4_3.getLatitude(),
                RECTANGLE_POINT4_3.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT4_3_new2.getLatitude(),
                RECTANGLE_POINT4_3_new2.getLongitude()));
        rectPoints3.add(new Point(
                RECTANGLE_POINT4_3_new3.getLatitude(),
                RECTANGLE_POINT4_3_new3.getLongitude()));

        rect3 = mapObjects.addPolygon(
                new Polygon(new LinearRing(rectPoints3), new ArrayList<LinearRing>()));
        rect3.setStrokeColor(Color.TRANSPARENT);
        rect3.setFillColor(getResources().getColor(R.color.layermap3));

        ArrayList<Point> rectPoints4 = new ArrayList<>();
        rectPoints4.add(new Point(
                RECTANGLE_POINT1_4.getLatitude(),
                RECTANGLE_POINT1_4.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT2_4_new2.getLatitude(),
                RECTANGLE_POINT2_4_new2.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT2_4_new3.getLatitude(),
                RECTANGLE_POINT2_4_new3.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT2_4_new4.getLatitude(),
                RECTANGLE_POINT2_4_new4.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT2_4_new5.getLatitude(),
                RECTANGLE_POINT2_4_new5.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT2_4_new6.getLatitude(),
                RECTANGLE_POINT2_4_new6.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT2_4.getLatitude(),
                RECTANGLE_POINT2_4.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT3_4.getLatitude(),
                RECTANGLE_POINT3_4.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT4_4.getLatitude(),
                RECTANGLE_POINT4_4.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT5_4_new2_1.getLatitude(),
                RECTANGLE_POINT5_4_new2_1.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT5_4_new2_2.getLatitude(),
                RECTANGLE_POINT5_4_new2_2.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT5_4.getLatitude(),
                RECTANGLE_POINT5_4.getLongitude()));

        rectPoints4.add(new Point(
                RECTANGLE_POINT5_4_new2.getLatitude(),
                RECTANGLE_POINT5_4_new2.getLongitude()));
        rectPoints4.add(new Point(
                RECTANGLE_POINT5_4_new3.getLatitude(),
                RECTANGLE_POINT5_4_new3.getLongitude()));

        rect4 = mapObjects.addPolygon(
                new Polygon(new LinearRing(rectPoints4), new ArrayList<LinearRing>()));
        rect4.setStrokeColor(Color.TRANSPARENT);
        rect4.setFillColor(getResources().getColor(R.color.layermap4));

        ArrayList<Point> rectPoints5 = new ArrayList<>();
        rectPoints5.add(new Point(
                RECTANGLE_POINT1_5.getLatitude(),
                RECTANGLE_POINT1_5.getLongitude()));
        rectPoints5.add(new Point(
                RECTANGLE_POINT2_5.getLatitude(),
                RECTANGLE_POINT2_5.getLongitude()));
        rectPoints5.add(new Point(
                RECTANGLE_POINT2_5_new2.getLatitude(),
                RECTANGLE_POINT2_5_new2.getLongitude()));
        rectPoints5.add(new Point(
                RECTANGLE_POINT3_5.getLatitude(),
                RECTANGLE_POINT3_5.getLongitude()));
        rectPoints5.add(new Point(
                RECTANGLE_POINT4_5.getLatitude(),
                RECTANGLE_POINT4_5.getLongitude()));
        rectPoints5.add(new Point(
                RECTANGLE_POINT5_5.getLatitude(),
                RECTANGLE_POINT5_5.getLongitude()));
        rectPoints5.add(new Point(
                RECTANGLE_POINT5_5_new2.getLatitude(),
                RECTANGLE_POINT5_5_new2.getLongitude()));

        rect5 = mapObjects.addPolygon(
                new Polygon(new LinearRing(rectPoints5), new ArrayList<LinearRing>()));
        rect5.setStrokeColor(Color.TRANSPARENT);
        rect5.setFillColor(getResources().getColor(R.color.layermap5));

        Bitmap bitmap = getBitmapFromVectorDrawable(this, R.drawable.ic_map_homemark);
        homePlacemark =
                mapObjects.addPlacemark(HOME_CENTER, ImageProvider.fromBitmap(bitmap));

        createTappableCircle();

        createPlacemarkMapObjectWithViewProvider();

    }


    private void createPlacemarkMapObjectWithViewProvider() {
        final TextView textView = new TextView(this);
        final int[] colors = new int[] { Color.RED, Color.GREEN, Color.BLACK };
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams params_mutch = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams params_mutch_wrap = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);

        textView.setTextColor(Color.RED);
        textView.setText("Hello, World!");




        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                80,
                120
        );

        final ConstraintLayout layoutcon = new ConstraintLayout(this);
        ConstraintLayout.LayoutParams layoutParamsparent = new ConstraintLayout.LayoutParams(params3);
        layoutcon.setLayoutParams(layoutParamsparent);

//        LayoutInflater inflater = LayoutInflater.from(this);
//        View layout = inflater.inflate(R.layout.map_cardview, layoutcon, false);
//        layout.setLayoutParams(params3);
//        layoutcon.addView(layout);
//        CardView card = new CardView(this);
//        card.setLayoutParams(params3);
//        card.setRadius(9);
//        card.setContentPadding(5, 5, 5, 5);
//        card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
//        card.setMaxCardElevation(15);
//        card.setCardElevation(9);
//        card.addView(layoutcon);
//
//        final ViewProvider viewProvider_new = new ViewProvider(layout);
//        final PlacemarkMapObject viewPlacemark_new =
//                mapObjects.addPlacemark(new Point(43.238949, 76.809709), viewProvider_new);

//
//
//        CardView card = new CardView(this);
//        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
//                80,
//                80
//        );
//        card.setLayoutParams(params2);
//        card.setRadius(9);
//        card.setContentPadding(5, 0, 5, 5);
//        card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
//        card.setMaxCardElevation(15);
//        card.setCardElevation(9);
//        LinearLayout linear = new LinearLayout(this);
//        linear.setOrientation(LinearLayout.VERTICAL);
//        linear.setLayoutParams(params_mutch);
//        ImageView image = new ImageView(this);
//        image.setImageDrawable(getResources().getDrawable(R.drawable.ecopic));
//        image.setLayoutParams(params2);
//        TextView text = new TextView(this);
//        text.setText("Название задания");
//        text.setTypeface(Typeface.DEFAULT_BOLD);
//        text.setLayoutParams(params_mutch_wrap);
//        text.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
//        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
//        text.setTextColor(getResources().getColor(R.color.colorAccent));
//        TextView text2 = new TextView(this);
//        text2.setText("описание задания");
//        text2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
//        text2.setTextColor(Color.parseColor("#000000"));
//        linear.addView(image);
//        linear.addView(text);
//        linear.addView(text2);
//        card.addView(linear);

//        final ViewProvider viewProvider = new ViewProvider(card);

        Bitmap bitmap = getBitmapFromVectorDrawable(this, R.drawable.ic_placemark_sum);

        Bitmap bitmapMarker = getBitmapFromVectorDrawable(this, R.drawable.ic_map_marker);


        markerTask1 =
                mapObjects.addPlacemark(new Point(43.220949, 76.890709), ImageProvider.fromBitmap(bitmapMarker));

        markerTask2 =
                mapObjects.addPlacemark(new Point(43.217949, 76.891709), ImageProvider.fromBitmap(bitmapMarker));

        markerTask3 =
                mapObjects.addPlacemark(new Point(43.216949, 76.887709), ImageProvider.fromBitmap(bitmapMarker));

        markerTask4 =
                mapObjects.addPlacemark(new Point(43.216949, 76.892709), ImageProvider.fromBitmap(bitmapMarker));

        markerTask5 =
                mapObjects.addPlacemark(new Point(43.220949, 76.886709), ImageProvider.fromBitmap(bitmapMarker));

        markerTask6 =
                mapObjects.addPlacemark(new Point(43.220949, 76.892709), ImageProvider.fromBitmap(bitmapMarker));


        markerTask1.setVisible(false);
        markerTask2.setVisible(false);
        markerTask3.setVisible(false);
        markerTask4.setVisible(false);
        markerTask5.setVisible(false);
        markerTask6.setVisible(false);

    }

    private void createTappableCircle() {

        circle = mapObjects.addCircle(
                new Circle(CIRCLE_CENTER, 750), Color.WHITE, 1, Color.TRANSPARENT);

        circle.setZIndex(100.0f);
        circle.setUserData(new CircleMapObjectUserData(42, "Tappable circle"));
        circle.setVisible(false);
        // Client code must retain strong reference to the listener.
//        circle.addTapListener(circleMapObjectTapListener);
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.topbar_backpres_map){
            finish();
        }

        if (v.getId() == R.id.button_myevents_map){

            myTaskstext.setTextColor(getResources().getColor(R.color.textmainHeader1));
            newTaskstext.setTextColor(getResources().getColor(R.color.neutral1));
            myTasksline.setVisibility(View.VISIBLE);
            newTasksline.setVisibility(View.INVISIBLE);

            mapView.getMap().move(
                    new CameraPosition(HOME_CENTER, 15.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 1),
                    null);
            zoom = mapView.getMap().getCameraPosition().getZoom();
            target =  mapView.getMap().getCameraPosition().getTarget();
        }

        if (v.getId() == R.id.button_newevents_map){

            newTaskstext.setTextColor(getResources().getColor(R.color.textmainHeader1));
            myTaskstext.setTextColor(getResources().getColor(R.color.neutral1));
            newTasksline.setVisibility(View.VISIBLE);
            myTasksline.setVisibility(View.INVISIBLE);

            mapView.getMap().move(
                    new CameraPosition(HOME_CENTER, 13.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 1),
                    null);
            zoom = mapView.getMap().getCameraPosition().getZoom();
            target =  mapView.getMap().getCameraPosition().getTarget();
        }

        if (v.getId() == R.id.map_but_plus){

            mapView.getMap().move(new CameraPosition(mapView.getMap().getCameraPosition().getTarget(),
                            mapView.getMap().getCameraPosition().getZoom()+1, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 1),
                    null);

            target =  mapView.getMap().getCameraPosition().getTarget();
            zoom = mapView.getMap().getCameraPosition().getZoom();


        } if (v.getId() == R.id.map_but_minus){

            mapView.getMap().move(new CameraPosition(mapView.getMap().getCameraPosition().getTarget(),
                            mapView.getMap().getCameraPosition().getZoom()-1, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 1),
                    null);

            zoom = mapView.getMap().getCameraPosition().getZoom();
            target =  mapView.getMap().getCameraPosition().getTarget();

        } if (v.getId() == R.id.map_but_zoomout){

            mapView.getMap().move(
                    new CameraPosition(HOME_CENTER, 15.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 1),
                    null);
            zoom = mapView.getMap().getCameraPosition().getZoom();
            target =  mapView.getMap().getCameraPosition().getTarget();

            myTaskstext.setTextColor(getResources().getColor(R.color.textmainHeader1));
            newTaskstext.setTextColor(getResources().getColor(R.color.neutral1));
            myTasksline.setVisibility(View.VISIBLE);
            newTasksline.setVisibility(View.INVISIBLE);

        }
    }

    private class CircleMapObjectUserData {
        final int id;
        final String description;

        CircleMapObjectUserData(int id, String description) {
            this.id = id;
            this.description = description;
        }
    }

}