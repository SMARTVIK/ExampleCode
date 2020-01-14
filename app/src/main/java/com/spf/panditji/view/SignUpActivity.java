package com.spf.panditji.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.SignInResponse;
import com.spf.panditji.model.SignUp;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.util.Constants;
import com.spf.panditji.util.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final int VERIFY_OTP = 100;
    EditText name,email,phone,password,confirmPassword;
    private boolean openBookingScreen;
    private boolean signInInternally;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getIntent().hasExtra("show_signin")){
            findViewById(R.id.sign_in_text).setVisibility(View.GONE);
            openBookingScreen = true;
        }

        if(getIntent().hasExtra("sign_in_internally")){
            findViewById(R.id.sign_in_text).setVisibility(View.GONE);
            signInInternally = true;
        }

        initViews();

        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLoader();

                ApiUtil.getInstance().signUpApi(
                        name.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString(),
                        password.getText().toString(),
                        new Callback<SignUp>() {

                            @Override
                            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                                L.d("SignUp successful "+response.body().getSuccess());
                                ApplicationDataController.getInstance().setUserSignUp(true);
                                if (openBookingScreen || signInInternally) {
                                    startActivityForResult(new Intent(SignUpActivity.this, VerifyEmailActivity.class).putExtra("mobile",phone.getText().toString().trim()), VERIFY_OTP);
                                } else {
                                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                }
                            }

                            @Override
                            public void onFailure(Call<SignUp> call, Throwable t) {
                                Log.d("onFailure", t.getMessage() + "");
                            }
                        });

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == VERIFY_OTP && resultCode == Activity.RESULT_OK){
            if(openBookingScreen || signInInternally){
                goForInternallySignIn(email.getText().toString(),password.getText().toString(),openBookingScreen);
            }
        }

    }

    private void goForInternallySignIn(String user, String pass, final boolean openBookingScreen) {

        ApiUtil.getInstance().signIn(user, pass, new Callback<SignInResponse>() {

            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                SharedPreferences sharedPreferences = VadikSewaApplication.getInstance().getSharedPrefs();
                sharedPreferences.edit().putString("user_id",response.body().getUser_id());
                sharedPreferences.edit().putString("name",name.getText().toString());
                sharedPreferences.edit().putString("email",email.getText().toString());
                sharedPreferences.edit().putString("mobile",phone.getText().toString());
                sharedPreferences.edit().putString("password",password.getText().toString());

                L.d("SignIn Successful "+response.body().getSuccess());

                ApplicationDataController.getInstance().setUserLoggedIn(true);
                ApplicationDataController.getInstance().setUserId(response.body().getUser_id());
                ApplicationDataController.getInstance().setCurrentUserResponse(response.body());
                if (openBookingScreen) {
                    setResult(Activity.RESULT_OK, new Intent().putExtra("user_id", response.body().getUser_id()));
                }
                finish();
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                L.d("onFailure "+t.getMessage());
            }
        });


    }

    private void initViews() {
        name = findViewById(R.id.name_edit_text);
        email = findViewById(R.id.email_edit_text);
        phone = findViewById(R.id.mobile_edit_text);
        password = findViewById(R.id.pass_edit_text);
        confirmPassword = findViewById(R.id.confirm_pass_edit_text);
    }

    private void showLoader() {

    }
}
