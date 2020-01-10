package com.spf.panditji.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.view.CategoryListActivity;
import com.spf.panditji.view.GridAdapter;
import com.spf.panditji.view.SpacesItemDecoration;

import java.util.List;

public class ViewAllCategories extends AppCompatActivity {

    private List<CategoryModel> categoryModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_categories);
        categoryModels = getIntent().getParcelableArrayListExtra("list");
        initViews();
        setUpList();
    }

    private void setUpList() {
        RecyclerView recyclerView = findViewById(R.id.list);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        GridAdapter adapter = new GridAdapter(new OnItemClick<CategoryModel>() {
            @Override
            public void onClick(CategoryModel categoryModel) {

                //onItemClick handling

                startActivity(new Intent(ViewAllCategories.this, CategoryListActivity.class).putExtra("cat",categoryModel.getCat()));


            }
        });

        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(adapter);
        adapter.setData(categoryModels);
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Category");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();



        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return true;
    }
}
