package com.econetwork.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.econetwork.DBHelper;
import com.econetwork.MainActivity;
import com.econetwork.R;
import com.econetwork.Util;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegistActivity extends AppCompatActivity {

    EditText password1;
    EditText password2;
    EditText username;
    EditText phonenumber;
    LoginViewModel loginViewModel;
    TextView passwordmist;
    Button regist;
    ProgressBar barreg;
    DBHelper dbHelper;
    String idUser;
    String mMessage;
    SharedPreferences mPrefs;
    public static final String APP_PREFERENCES_TASK_PHONE = "PhoneNumber";
    public static final String APP_PREFERENCES_TASK_NAME = "UserName";
    public static final String APP_PREFERENCES_TASK_PASS = "Password";
    public static final String APP_PREFERENCES = "myprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_login));
        }
        setContentView(R.layout.activity_regist);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_REQUEST);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_REQUEST);

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_REQUEST);
//            }
//        }
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        password1 = findViewById(R.id.password_reg);
        password2 = findViewById(R.id.password_reg_rep);
        username = findViewById(R.id.username_reg);
        passwordmist = findViewById(R.id.wrong_pass);
        regist = findViewById(R.id.regist);
        barreg = findViewById(R.id.loading_reg);
        phonenumber = findViewById(R.id.userphone_reg);
        dbHelper = new DBHelper(this);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                regist.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    username.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    password1.setError(getString(loginFormState.getPasswordError()));
                }

            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                barreg.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(username.getText().toString(),
                        password1.getText().toString());
            }
        };

        password1.addTextChangedListener(afterTextChangedListener);
        password1.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    loginViewModel.login(username.getText().toString(),
                            password1.getText().toString());
                }
                return false;
            }
        });


        TextWatcher afterTextChangedListener2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(username.getText().toString(),
                        password2.getText().toString());
                if (password1 != password2) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(60,0,0,20);
                    passwordmist.setLayoutParams(params);
                    passwordmist.setVisibility(View.VISIBLE);
                }
            }
        };

        password2.addTextChangedListener(afterTextChangedListener2);
        password2.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        loginViewModel.login(username.getText().toString(),
                                password2.getText().toString());
                    }

                return false;
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barreg.setVisibility(View.VISIBLE);
                loginViewModel.login(username.getText().toString(),
                        password1.getText().toString());
                mPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString(APP_PREFERENCES_TASK_PHONE, String.valueOf(phonenumber.getText().toString()));
                editor.putString(APP_PREFERENCES_TASK_NAME, String.valueOf(username.getText().toString()));
                editor.putString(APP_PREFERENCES_TASK_PASS, String.valueOf(password1.getText().toString()));
                editor.apply();
                try {
                    getHttpResponseRegist();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
            }
        });

    }


    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome);
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private static final int MY_REQUEST = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Хорошо", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(this, "Напишите код вручную", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void getHttpResponseRegist() throws IOException {
        String url = "https://eztecheconetwork.pythonanywhere.com/api/register";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = new FormEncodingBuilder()
                .add("username", username.getText().toString())
                .add("password", password1.getText().toString())
                .add("phone_number", phonenumber.getText().toString())
                .build();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("Authorization", "header value")
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

                mMessage = r.body().string();
                getHttpResponseLogin();
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
//                    barreg.setVisibility(View.INVISIBLE);
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

    public void getHttpResponseLogin() throws IOException {
        String url = "https://eztecheconetwork.pythonanywhere.com/api/token/obtain";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = new FormEncodingBuilder()
                .add("username", username.getText().toString())
                .add("password", password1.getText().toString())
                .build();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("Authorization", "header value")
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

//
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                mMessage = r.body().string();
                mPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(mMessage);
                    String mToken = obj.getString("access");
                    Util.utoken = mToken;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

}