package com.econetwork.ui.login;

import android.app.Activity;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.econetwork.DBHelper;
import com.econetwork.MainActivity;
import com.econetwork.R;
import com.econetwork.Util;
import com.econetwork.ui.login.LoginViewModel;
import com.econetwork.ui.login.LoginViewModelFactory;
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

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    ProgressBar loadingProgressBar;
    EditText passwordEditText;
    EditText username;
    EditText phonenumber;
    String mMessage;
    DBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_login));

        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        username = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password_login);
        final Button loginButton = findViewById(R.id.login_but);
        final TextView regButton = findViewById(R.id.reg);
        final TextView forgetButton = findViewById(R.id.forget_password);
        loadingProgressBar = findViewById(R.id.loading);
        dbHelper = new DBHelper(this);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    username.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));

                } else {  passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {

                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful

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
                        passwordEditText.getText().toString());
            }
        };
        username.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(username.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(username.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
                startActivity(intent);
            }
        });
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome);
        // TODO : initiate successful logged in experience
        loadingProgressBar.setVisibility(View.VISIBLE);
        try {
            getHttpResponseLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
        if (passwordEditText.getText().length() < 6) {
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else  passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    public void getHttpResponseLogin() throws IOException {
        String url = "https://eztecheconetwork.pythonanywhere.com/api/token/obtain";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = new FormEncodingBuilder()
                .add("username", username.getText().toString())
                .add("password", passwordEditText.getText().toString())
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

//                String response = r.body().string();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);

                mMessage = r.body().string();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                try {
                    Log.e("TOKEN", mMessage);
                    JSONObject obj = new JSONObject(mMessage);
                    String mToken = obj.getString("access");
                    Util.utoken = mToken;

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
//                    SQLiteDatabase db = dbHelper.getWritableDatabase();
//                    ContentValues cv = new ContentValues();
//                    cv.put("token", mToken);
//                    cv.put("name", username.getText().toString());
//                    db.insert("econetworktable", null, cv);
//                    db.close();
//                    dbHelper.close();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    barreg.setVisibility(View.INVISIBLE);
//                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                finish();
            }
        });
    }
}
