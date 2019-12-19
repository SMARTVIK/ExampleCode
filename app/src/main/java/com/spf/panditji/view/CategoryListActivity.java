package com.spf.panditji.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.PujaModel;
import com.spf.panditji.util.ApiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity {

    private CategoriesListAdapter categoriesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_list);

        setUpList();


        getListByCategory(getIntent().getStringExtra("cat"));

    }

    private void getListByCategory(String cat) {

        ApiUtil.getInstance().getListOfPujas(cat,new Callback<List<PujaModel>>() {

            @Override
            public void onResponse(Call<List<PujaModel>> call, Response<List<PujaModel>> response) {

                if(response.isSuccessful() && response.code() == 200){
                    categoriesAdapter.setData(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<PujaModel>> call, Throwable t) {

            }
        });
    }

    private void setUpList() {
        RecyclerView recyclerView = findViewById(R.id.book_by_cat_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        categoriesAdapter = new CategoriesListAdapter(new OnItemClick<PujaModel>() {

            @Override
            public void onClick(PujaModel categoryModel) {


            }
        });
        recyclerView.setAdapter(categoriesAdapter);
    }
}
