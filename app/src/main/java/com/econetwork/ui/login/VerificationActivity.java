package com.econetwork.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.econetwork.MainActivity;
import com.econetwork.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
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
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    Button button_sms;
    String mPhone;
    String username;
    String password;
    ProgressBar progbar;
    String mMessage;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private FirebaseAuth fbAuth;
    private String phoneVerificationId;
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
        getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_login));
        setContentView(R.layout.activity_verification);

        button_sms = findViewById(R.id.button_sms);
        progbar = findViewById(R.id.loading_sms);

        mPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mPrefs.contains(APP_PREFERENCES_TASK_PHONE)) {
            mPhone = mPrefs.getString(APP_PREFERENCES_TASK_PHONE, "");
            username = mPrefs.getString(APP_PREFERENCES_TASK_NAME, "");
            password = mPrefs.getString(APP_PREFERENCES_TASK_PASS, "");
            Log.e("Phonenumber", mPhone);
            Log.e("UserName", username);
            Log.e("Password", password);
        }



        sendCode();

        button_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progbar.setVisibility(View.VISIBLE);
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
            }
        });

    }

    public void sendCode() {

        fbAuth = FirebaseAuth.getInstance();
        setUpVerificatonCallbacks();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(fbAuth)
                        .setPhoneNumber(mPhone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(verificationCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void setUpVerificatonCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("Firebase", "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("Firebase", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("Firebase", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId;
//                mResendToken = token;
            }
        };
//                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(
//                            PhoneAuthCredential credential) {
////                        signInWithPhoneAuthCredential(credential);
//                        Toast.makeText(VerificationActivity.this, "Успешно доставлен", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onVerificationFailed(FirebaseException e) {
//                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                            // Invalid request
//                            final AlertDialog alertDialog = new AlertDialog.Builder(VerificationActivity.this).create();
//                            alertDialog.setTitle("");
//                            alertDialog.setMessage("Не правильный номер");
//                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Готово",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog1, int which) {
//                                            progbar.setVisibility(View.INVISIBLE);
//                                            alertDialog.dismiss();
//                                        }
//                                    });
//                            alertDialog.show();
//                        } else if (e instanceof FirebaseTooManyRequestsException) {
//                            final AlertDialog alertDialog = new AlertDialog.Builder(VerificationActivity.this).create();
//                            alertDialog.setTitle("");
//                            alertDialog.setMessage("На сегодня ваш лимит исчерпан. Повторите позже");
//                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Готово",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog1, int which) {
//                                            alertDialog.dismiss();
//                                            progbar.setVisibility(View.INVISIBLE);
//                                            startActivity(new Intent(VerificationActivity.this, LoginActivity.class));
//                                            finish();
//                                        }
//                                    });
//                            alertDialog.show();
//                        } else {
//                            Log.wtf("ASdasd",e+"");
//                            final AlertDialog alertDialog = new AlertDialog.Builder(VerificationActivity.this).create();
//                            alertDialog.setTitle("");
//                            alertDialog.setMessage("Нету интернет подключения");
//                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Готово",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog1, int which) {
//                                            progbar.setVisibility(View.INVISIBLE);
//                                            alertDialog.dismiss();
//                                        }
//                                    });
//                            alertDialog.show();
//                        }
//                    }
//
//                    @Override
//                    public void onCodeSent(String verificationId,
//                                           PhoneAuthProvider.ForceResendingToken token) {
//                        phoneVerificationId = verificationId;
////                        resendToken = token;
//                        Toast.makeText(VerificationActivity.this, "Код отправлен", Toast.LENGTH_SHORT).show();
//                    }
//                };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

//                            FirebaseUser user = task.getResult().getUser();
                            try {
                                getHttpResponseRegist();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                //dialog.dismiss();
                                Toast.makeText(VerificationActivity.this, "Код не правильный", Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void getHttpResponseRegist() throws IOException {
        String url = "https://eztecheconetwork.pythonanywhere.com/api/register";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = new FormEncodingBuilder()
                .add("username", username)
                .add("password", password)
                .add("phone_number", mPhone)
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
        String url = "https://eztecheconetwork.pythonanywhere.com/api/register";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = new FormEncodingBuilder()
                .add("username", username)
                .add("password", password)
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