package com.spf.panditji.util;

import com.google.gson.Gson;
import com.spf.panditji.listener.WebApi;
import com.spf.panditji.model.AddressModel;
import com.spf.panditji.model.AvailabilityModel;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.OrderModel;
import com.spf.panditji.model.OtpResponse;
import com.spf.panditji.model.PanditDetailsModel;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.model.PopularPoojaModel;
import com.spf.panditji.model.PujaDetailModel;
import com.spf.panditji.model.PujaModel;
import com.spf.panditji.model.SignInResponse;
import com.spf.panditji.model.SignUp;
import com.spf.panditji.model.UserProfileModel;
import com.spf.panditji.view.AddressResponse;

import org.json.JSONException;
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

    public void getUserProfile(String userId, Callback<List<UserProfileModel>> callBack){
        Map<String, String> values = new HashMap<>();
        values.put("user_id",userId);
        String requestString = new JSONObject(values).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE),requestString.getBytes());
        Call<List<UserProfileModel>> call = this.getApi().getUserProfile(requestBody);
        call.enqueue(callBack);
    }

    public void signIn(String userMail, String pass, Callback<SignInResponse> callBack){
        Map<String, String> values = new HashMap<>();
        values.put("user",userMail);
        values.put("pass",pass);
        String requestString = new JSONObject(values).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE),requestString.getBytes());
        Call<SignInResponse> call = this.getApi().signIn(requestBody);
        call.enqueue(callBack);
    }


    public void signUpApi(String name, String email, String mobile, String password, Callback<SignUp> callback) {
        Map<String, String> params = new HashMap();
        params.put("name", name);
        params.put("email", email);
        params.put("mobile", mobile);
        params.put("password", password);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE), requestString.getBytes());
        Call<SignUp> call = this.getApi().loginWithPhone(requestBody);
        call.enqueue(callback);
    }

    public void sendingOtpToServer(String mobile, String otp, Callback<OtpResponse> callback) {
        Map<String, String> params = new HashMap();
        params.put("mobile", mobile);
        params.put("otp", otp);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE), requestString.getBytes());
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

    public void getPopularPoojaList(Callback<List<PopularPoojaModel>> listCallback) {
        Call<List<PopularPoojaModel>> call = this.getApi().getPopularPoojaList();
        call.enqueue(listCallback);
    }

    public void getListOfPujas(String cat, Callback<List<PujaModel>> listCallback) {
        Map<String, String> params = new HashMap();
        params.put("main", cat);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE), requestString.getBytes());
        Call<List<PujaModel>> call = this.getApi().getListPujas(requestBody);
        call.enqueue(listCallback);
    }

    public void getPujaDetails(String id, Callback<List<PujaDetailModel>> listCallback) {
        Map<String, String> params = new HashMap();
        params.put("id", id);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE), requestString.getBytes());
        Call<List<PujaDetailModel>> call = this.getApi().getPujaDetails(requestBody);
        call.enqueue(listCallback);
    }


    public void getReviewsList() {


    }

    public void getPujaList() {



    }

    public void getPanditDetails(String id, Callback<PanditDetailsModel> panditDetailsModelCallback) {
        Map<String, String> params = new HashMap();
        params.put("id", id);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE), requestString.getBytes());
        Call<PanditDetailsModel> call = this.getApi().getPanditDetails(requestBody);
        call.enqueue(panditDetailsModelCallback);
    }

    public void getPandit(String locationCity, String date, String poojaName, Callback<AvailabilityModel> availabilityModelCallback) {
        Map<String, String> params = new HashMap();
        params.put("city", locationCity);
        params.put("date", date);
        params.put("pooja", poojaName);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE), requestString.getBytes());
        Call<AvailabilityModel> call = this.getApi().getPandit(requestBody);
        call.enqueue(availabilityModelCallback);
    }

    public void booking(String json, Callback<List<OrderModel>> orderModelCallback) {
        String requestString = null;
        try {
            requestString = (new JSONObject(json)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE), requestString.getBytes());
        Call<List<OrderModel>> call = this.getApi().order(requestBody);
        call.enqueue(orderModelCallback);
    }

    public void addAddress(String name, String userId, String address, String city, String state, String landmark, String pin, Callback<AddressResponse> callback) {





    }

    public void getAllAddresses(String userId, Callback<List<AddressModel>> listCallback) {

        Map<String, String> params = new HashMap();
        params.put("user_id", userId);
        String requestString = (new JSONObject(params)).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(Constant.MEDIA_TYPE), requestString.getBytes());
        Call<List<AddressModel>> call = this.getApi().getAllAddresses(requestBody);
        call.enqueue(listCallback);

    }
}
