package com.econetwork.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.econetwork.MainActivity;
import com.econetwork.R;

public class ForgetPassword extends AppCompatActivity {


    EditText password1;
    EditText password2;
    TextView passwordmist;
    Button forget;
    ProgressBar barreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_login));
        }
        setContentView(R.layout.activity_forget_password);


        password1 = findViewById(R.id.password_forg);
        password2 = findViewById(R.id.password_forg_rep);
        passwordmist = findViewById(R.id.wrong_pass_forg);
        forget = findViewById(R.id.forget);
        barreg = findViewById(R.id.loading_forg);

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

//                    loginViewModel.login(username.getText().toString(),
//                            password2.getText().toString());
                }


                return false;
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barreg.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                startActivity(intent);
            }
        });
    }
}