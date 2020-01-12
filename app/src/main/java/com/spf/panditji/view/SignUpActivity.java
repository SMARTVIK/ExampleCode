package com.spf.panditji.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.SignInResponse;
import com.spf.panditji.model.SignUp;
import com.spf.panditji.util.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText name,email,phone,password,confirmPassword;
    private boolean openBookingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getIntent().hasExtra("show_signin")){
            findViewById(R.id.sign_in_text).setVisibility(View.GONE);
            openBookingScreen = true;
        }

        initViews();

        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLoader();

                ApiUtil.getInstance().signUpApi(name.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString(), new Callback<SignUp>() {

                    @Override
                    public void onResponse(Call<SignUp> call, Response<SignUp> response) {

                        if (openBookingScreen) {
                            goForInternallySignIn(email.getText().toString().trim(), password.getText().toString().trim(), openBookingScreen);
                        } else {
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                        }

                        Log.d("onResponse", response.body().getSuccess() + "");

                    }

                    @Override
                    public void onFailure(Call<SignUp> call, Throwable t) {

                        Log.d("onFailure",t.getMessage()+"");

                    }
                });

            }
        });

    }

    private void goForInternallySignIn(String user, String pass, boolean openBookingScreen) {

        ApiUtil.getInstance().signIn(user, pass, new Callback<SignInResponse>() {

            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                ApplicationDataController.getInstance().setCurrentUserResponse(response.body());
                setResult(Activity.RESULT_OK,new Intent().putExtra("user_id",response.body().getUser_id()));
                finish();
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {

            }
        });


    }

    private void initViews() {
        name = findViewById(R.id.name_edit_text);
        email = findViewById(R.id.email_edit_text);
        phone = findViewById(R.id.mobile_edit_text);
        password = findViewById(R.id.password_edit_text);
        confirmPassword = findViewById(R.id.confirm_pass_edit_text);
    }

    private void showLoader() {

    }
}
