package com.spf.panditji.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.R;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.view.PopularPoojaAdapter;
import com.spf.panditji.view.RoundImageAdapter;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    private Slider slider;
    private RoundImageAdapter categoriesAdapter;
    private PopularPoojaAdapter popularPoojaAdapter;
    private PopularPanditAdapter popularPanditAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
           setUpViewPager(view);
           setUpBookByCategory(view);
           setUpPopularPoojaList(view);
           setUpPopularPanditList(view);

            getBookByCategory();
            getPopularPoojaList();
            getPopularPanditList();
    }

    private void getPopularPanditList() {

        ApiUtil.getInstance().getPopularPanditList(new Callback<List<PopularPanditModel>>() {

            @Override
            public void onResponse(Call<List<PopularPanditModel>> call, Response<List<PopularPanditModel>> response) {

                if(response.isSuccessful() && response.code() == 200){
                    popularPanditAdapter.setData(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<PopularPanditModel>> call, Throwable t) {

            }
        });

    }

    private void getPopularPoojaList() {



    }

    private void getBookByCategory() {
        ApiUtil.getInstance().getCategories(new Callback<List<CategoryModel>>() {

            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {

                if(response.isSuccessful() && response.code() == 200){
                    categoriesAdapter.setData(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }

    private void setUpBookByCategory(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.book_by_cat_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        categoriesAdapter = new RoundImageAdapter();
        recyclerView.setAdapter(categoriesAdapter);
    }

    private void setUpPopularPoojaList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.trending_pooja_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        popularPoojaAdapter = new PopularPoojaAdapter();
        recyclerView.setAdapter(popularPoojaAdapter);
    }

    private void setUpPopularPanditList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.popular_pandit_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        popularPanditAdapter = new PopularPanditAdapter();
        recyclerView.setAdapter(popularPanditAdapter);
    }


    private void setUpViewPager(View view) {

        slider = view.findViewById(R.id.slider);

        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0, getUrlToLoad(R.drawable.email_bg), getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1, getUrlToLoad(R.drawable.email_bg), getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2, getUrlToLoad(R.drawable.email_bg), getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));

        //handle slider click listener
        slider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //do what you want
            }
        });

        //add slides to slider
        slider.addSlides(slideList);
    }

    private int getUrlToLoad(int resourceId) {
        String resourceScheme = "res";
        Uri uri = new Uri.Builder()
                .scheme(resourceScheme)
                .path(String.valueOf(resourceId))
                .build();
        return resourceId;
    }

}
