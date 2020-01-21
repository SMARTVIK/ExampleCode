package com.spf.panditji.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.model.SignInResponse;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.util.Constants;
import com.spf.panditji.util.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private static final int OPEN_BOOKING = 100;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final EditText userName = findViewById(R.id.email_edit_text);
        final EditText password = findViewById(R.id.password_edit_text);

        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userName.getText().toString().isEmpty()){
                    userName.setError("Can't be empty");
                    userName.requestFocus();
                    return;
                }else if(password.getText().toString().isEmpty()){
                    password.setError("Can't be empty");
                    password.requestFocus();
                    return;
                }
                goForInternallySignIn(userName.getText().toString().trim(), password.getText().toString().trim(), getIntent().hasExtra(Constants.OPEN_BOOKING));
            }
        });

        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SignInActivity.this,SignUpActivity.class)
                        .putExtra(Constants.OPEN_BOOKING,getIntent().getStringExtra(Constants.OPEN_BOOKING)),OPEN_BOOKING);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPEN_BOOKING && resultCode == Activity.RESULT_OK){
            setResult(Activity.RESULT_OK,data);
            finish();
        }
    }

    private void goForInternallySignIn(final String user, final String pass, final boolean openBookingScreen) {

        progressDialog = ProgressDialog.show(this, "","Please Wait...", true);

        ApiUtil.getInstance().signIn(user, pass, new Callback<SignInResponse>() {

            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {

                if (response.code() == 200 && response.body().getSuccess() == 1) {
                    SharedPreferences sharedPreferences = VadikSewaApplication.getInstance().getSharedPrefs();
                    sharedPreferences.edit().putString("user_id", response.body().getUser_id());
                    sharedPreferences.edit().putString("email", user);
                    sharedPreferences.edit().putString("password", pass);
                    L.d("SignIn Successful " + response.body().getSuccess());
                    ApplicationDataController.getInstance().setUserLoggedIn(true);
                    ApplicationDataController.getInstance().setUserId(response.body().getUser_id());
                    ApplicationDataController.getInstance().setCurrentUserResponse(response.body());

                    ApiUtil.getInstance().getUserProfile(response.body().getUser_id());

                    if (openBookingScreen) {
                        setResult(Activity.RESULT_OK, new Intent().putExtra("user_id", response.body().getUser_id()));
                    }else{
                        startActivity(new Intent(SignInActivity.this,HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    finish();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                L.d("onFailure "+t.getMessage());
                progressDialog.dismiss();
            }
        });
    }


}
