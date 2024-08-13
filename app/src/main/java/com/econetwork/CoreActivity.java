package com.econetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.econetwork.ui.login.LoginActivity;

public class CoreActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_map));

        setContentView(R.layout.activity_core);

//        dbHelper = new DBHelper(this);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor c = db.query("econetworktable", null, null, null, null, null, null);
//        c.moveToFirst();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                Intent intent = new Intent(CoreActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();

                if (Util.utoken.equals("token")){
                    Intent intent = new Intent(CoreActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(CoreActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);

    }
}