package com.spf.panditji.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.PujaModel;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.view.fragment.ViewAllCategories;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Pooja List");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

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

                Log.d("Puja Model ",categoryModel.getId());

                startActivity(new Intent(CategoryListActivity.this, DetailScreen.class).putExtra("id",categoryModel.getId()));

            }
        });
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }
}
