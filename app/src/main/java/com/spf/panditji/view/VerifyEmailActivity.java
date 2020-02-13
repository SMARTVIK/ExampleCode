package com.spf.panditji.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.spf.panditji.R;
import com.spf.panditji.model.OtpResponse;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.util.Constants;
import com.spf.panditji.util.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText password;
    private String otp;
    private TextView submitButton;
    private String mobile;
    private ProgressDialog progressDialog;
    private boolean createPass;
    private EditText pass;
    private EditText conPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        mobile = getIntent().getStringExtra(Constants.MOBILE);

        password = findViewById(R.id.password_edit_text);
        submitButton = findViewById(R.id.submit);
        password.requestFocus();

        if(getIntent().hasExtra(Constants.CREATE_NEW_PASS)){
            findViewById(R.id.pass_layout).setVisibility(View.VISIBLE);
            submitButton.setText("Done");
            createPass = true;
        }

        pass = findViewById(R.id.pass);
        conPass = findViewById(R.id.con_pass);


        pass.setEnabled(false);
        conPass.setEnabled(false);

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0 && editable.toString().length()<=6){
                    if (conPass.getText().toString().equals(editable.toString())) {
                        enableButton(true);
                    } else {
                        enableButton(false);
                    }
                }
            }
        });

        conPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().length()>0 && editable.toString().length()<=6){
                    if (pass.getText().toString().equals(editable.toString())) {
                        enableButton(true);
                    } else {
                        enableButton(false);
                    }
                }

            }
        });

        findViewById(R.id.layout_1).setOnClickListener(this);
        findViewById(R.id.layout_2).setOnClickListener(this);
        findViewById(R.id.layout_3).setOnClickListener(this);
        findViewById(R.id.layout_4).setOnClickListener(this);
        findViewById(R.id.layout_5).setOnClickListener(this);
        findViewById(R.id.layout_6).setOnClickListener(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Utility.checkInternetConnection(VerifyEmailActivity.this)) {
                    Toast.makeText(VerifyEmailActivity.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                showProgressDialog();

                if (createPass) {
                    ApiUtil.getInstance().createPassword(mobile, otp, pass.getText().toString(),new Callback<OtpResponse>() {

                        @Override
                        public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                            if (response.code() == 200 && response.body().getError() == 0) {
                                Toast.makeText(VerifyEmailActivity.this, "" + response.body().getAlert(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VerifyEmailActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                Toast.makeText(VerifyEmailActivity.this, "" + response.body().getAlert(), Toast.LENGTH_SHORT).show();
                            }
                            hideLoader();
                        }

                        @Override
                        public void onFailure(Call<OtpResponse> call, Throwable t) {
                            L.d("onFailure " + t.getMessage());
                            hideLoader();
                        }
                    });
                } else {
                    ApiUtil.getInstance().sendingOtpToServer(mobile, otp, new Callback<OtpResponse>() {

                        @Override
                        public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                            if (response.code() == 200 && response.body().getError() == 0) {
                                setResult(Activity.RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(VerifyEmailActivity.this, "" + response.body().getAlert(), Toast.LENGTH_SHORT).show();
                            }
                            hideLoader();
                        }

                        @Override
                        public void onFailure(Call<OtpResponse> call, Throwable t) {
                            L.d("onFailure " + t.getMessage());
                            hideLoader();
                        }
                    });
                }
            }
        });

        final TextView t1 = findViewById(R.id.text1);
        final TextView t2 = findViewById(R.id.text2);
        final TextView t3 = findViewById(R.id.text3);
        final TextView t4 = findViewById(R.id.text4);
        final TextView t5 = findViewById(R.id.text5);
        final TextView t6 = findViewById(R.id.text6);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String string = editable.toString();

                switch(string.length()){
                    case 0:
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");
                        t4.setText("");
                        t5.setText("");
                        t6.setText("");
                        otp = "";
                        enableButton(false);
                    case 1:
                        t1.setText(string);
                        t2.setText("");
                        t3.setText("");
                        t4.setText("");
                        t5.setText("");
                        t6.setText("");
                        otp = "";
                        enableButton(false);
                        break;
                    case 2:
                        t1.setText(string.substring(0,1));
                        t2.setText(string.substring(1,2));
                        t3.setText("");
                        t4.setText("");
                        t5.setText("");
                        t6.setText("");
                        otp = "";
                        enableButton(false);
                        break;
                    case 3:
                        t1.setText(string.substring(0,1));
                        t2.setText(string.substring(1,2));
                        t3.setText(string.substring(2,3));
                        t4.setText("");
                        t5.setText("");
                        t6.setText("");
                        otp = "";
                        enableButton(false);
                        break;
                    case 4:
                        t1.setText(string.substring(0,1));
                        t2.setText(string.substring(1,2));
                        t3.setText(string.substring(2,3));
                        t4.setText(string.substring(3,4));
                        t5.setText("");
                        t6.setText("");
                        otp = "";
                        enableButton(false);
                        break;
                    case 5:
                        t1.setText(string.substring(0,1));
                        t2.setText(string.substring(1,2));
                        t3.setText(string.substring(2,3));
                        t4.setText(string.substring(3,4));
                        t5.setText(string.substring(4,5));
                        t6.setText("");
                        otp = "";
                        enableButton(false);
                        break;
                    case 6:
                        t1.setText(string.substring(0,1));
                        t2.setText(string.substring(1,2));
                        t3.setText(string.substring(2,3));
                        t4.setText(string.substring(3,4));
                        t5.setText(string.substring(4,5));
                        t6.setText(string.substring(5,6));
                        otp = string;

                        if (!createPass) {
                            enableButton(true);
                        }


                        pass.setEnabled(true);
                        conPass.setEnabled(true);

                        break;
                }


            }
        });
    }

    private void enableButton(boolean enable) {
        submitButton.setEnabled(enable);
        submitButton.setBackground(ContextCompat.getDrawable(this, enable ? R.drawable.sign_in_gradient : R.drawable.sign_in_gradient_disable));
    }

    @Override
    public void onClick(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }


    private void showProgressDialog() {
        if(progressDialog == null){
            progressDialog = ProgressDialog.show(this,"","Please wait..",false);
        }else{
            progressDialog.show();
        }
    }

    private void hideLoader() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
