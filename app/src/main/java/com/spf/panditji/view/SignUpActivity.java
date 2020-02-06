package com.spf.panditji.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    private String userName;
    private String pass;
    private String nameString;
    private String mobileString;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getIntent().hasExtra(Constants.OPEN_BOOKING)){
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

                if (!isValidInput()) {
                    return;
                }

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
                                if (response.code() == 200 && response.body().getSuccess() == 1) {
                                    ApplicationDataController.getInstance().setUserSignUp(true);
                                    userName = email.getText().toString();
                                    pass = password.getText().toString();
                                    nameString = name.getText().toString();
                                    mobileString = phone.getText().toString();
                                    startActivityForResult(new Intent(SignUpActivity.this, VerifyEmailActivity.class).putExtra("mobile", phone.getText().toString().trim()), VERIFY_OTP);
                                } else {
                                    L.d(" response is not successfull");
                                }

                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<SignUp> call, Throwable t) {
                                Log.d("onFailure", t.getMessage() + "");
                                progressDialog.dismiss();
                            }
                        });
            }
        });

    }

    private boolean isValidInput() {
        boolean result = true;
        if (name.getText().toString().isEmpty()) {
            name.setError("Can not be empty");
            name.requestFocus();
            result = false;
        } else if (email.getText().toString().isEmpty()) {
            email.setError("Can not be empty");
            email.requestFocus();
            result = false;
        } else if (phone.getText().toString().isEmpty()) {
            phone.setError("Can not be empty");
            phone.requestFocus();
            result = false;
        } else if (password.getText().toString().isEmpty()) {
            password.setError("Can not be empty");
            password.requestFocus();
            result = false;
        } else if (confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError("Can not be empty");
            confirmPassword.requestFocus();
            result = false;
        } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "password and confirm password should be same", Toast.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == VERIFY_OTP && resultCode == Activity.RESULT_OK){
            if(openBookingScreen || signInInternally){
                goForInternallySignIn(userName,pass,openBookingScreen);
            }
        }

    }

    private void goForInternallySignIn(final String user, final String pass, final boolean openBookingScreen) {

        ApiUtil.getInstance().signIn(user, pass, new Callback<SignInResponse>() {

            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {

                if (response.code() == 200 && response.body().getSuccess() == 1) {
                    SharedPreferences sharedPreferences = VadikSewaApplication.getInstance().getSharedPrefs();
                    sharedPreferences.edit().putString("user_id", response.body().getUser_id());
                    sharedPreferences.edit().putString("name", nameString);
                    sharedPreferences.edit().putString("email", user);
                    sharedPreferences.edit().putString("mobile", mobileString);
                    sharedPreferences.edit().putString("password", pass);

                    L.d("SignIn Successful " + response.body().getSuccess());

                    ApplicationDataController.getInstance().setUserLoggedIn(true);
                    ApplicationDataController.getInstance().setUserId(response.body().getUser_id());
                    ApplicationDataController.getInstance().setCurrentUserResponse(response.body());
                    if (openBookingScreen) {
                        setResult(Activity.RESULT_OK, new Intent().putExtra("user_id", response.body().getUser_id()));
                    }else{
                        startActivity(new Intent(SignUpActivity.this,HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    finish();
                }
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
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void showLoader() {
        progressDialog = ProgressDialog.show(this, "","Please Wait...", true);
    }
}
