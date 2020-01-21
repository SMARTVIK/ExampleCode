package com.spf.panditji.view;

import android.app.DialogFragment;
import android.app.ProgressDialog;
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
import com.spf.panditji.util.Constants;
import com.spf.panditji.view.fragment.ViewAllCategories;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity {

    private CategoriesListAdapter categoriesAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_list);

        setUpList();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra(Constants.TITLE));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        getListByCategory(getIntent().getStringExtra("cat"));
    }

    private void hideLoader() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", "Please Wait...", true);
        } else {
            progressDialog.show();
        }
    }

    private void getListByCategory(String cat) {

         showProgressDialog();

        ApiUtil.getInstance().getListOfPujas(cat,new Callback<List<PujaModel>>() {

            @Override
            public void onResponse(Call<List<PujaModel>> call, Response<List<PujaModel>> response) {

                if(response.isSuccessful() && response.code() == 200){
                    categoriesAdapter.setData(response.body());
                }

                hideLoader();
            }

            @Override
            public void onFailure(Call<List<PujaModel>> call, Throwable t) {

                hideLoader();
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
