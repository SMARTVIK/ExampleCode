package com.spf.panditji.util;

import com.google.gson.Gson;
import com.spf.panditji.listener.WebApi;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.OtpResponse;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.model.SignUp;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtil {

    private static final String BASE_URL = "https://vaidiksewa.in/mob_api/";
    private static ApiUtil instance;
    private WebApi api;

    private ApiUtil() {
    }

    public static synchronized ApiUtil getInstance() {
        if (instance == null) {
            instance = new ApiUtil();
        }

        return instance;
    }

    public WebApi getApi() {
        if (this.api == null) {
            this.api = (WebApi)(new Retrofit.Builder()).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(new Gson())).build().create(WebApi.class);
        }
        return this.api;
    }


    public void signUpApi(String name, String email, String mobile, String password, Callback<SignUp> callback) {
        Map<String, String> params = new HashMap();
        params.put("name", name);
        params.put("email", email);
        params.put("mobile", mobile);
        params.put("password", password);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestString.getBytes());
        Call<SignUp> call = this.getApi().loginWithPhone(requestBody);
        call.enqueue(callback);
    }

    public void sendingOtpToServer(String otp, Callback<OtpResponse> callback) {
        Map<String, String> params = new HashMap();
        params.put("otp", otp);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestString.getBytes());
        Call<OtpResponse> call = this.getApi().sendingOtp(requestBody);
        call.enqueue(callback);
    }

    public void getCategories(Callback<List<CategoryModel>> categoryModelCallback) {
        Call<List<CategoryModel>> call = this.getApi().getCategories();
        call.enqueue(categoryModelCallback);
    }

    public void getPopularPanditList(Callback<List<PopularPanditModel>> listCallback) {
        Call<List<PopularPanditModel>> call = this.getApi().getPopularPandit();
        call.enqueue(listCallback);
    }
}
