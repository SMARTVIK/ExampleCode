package com.spf.panditji.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.model.PujaModel;
import com.spf.panditji.view.fragment.PopularPanditAdapter;

public class PopularPurohitList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_purohit_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        PopularPanditAdapter popularPanditAdapter = new PopularPanditAdapter(new OnItemClick<PopularPanditModel>() {
            @Override
            public void onClick(PopularPanditModel popularPanditModel) {
                startActivity(new Intent(PopularPurohitList.this, PanditProfile.class).putExtra("pandit_model", popularPanditModel));
            }
        });
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(popularPanditAdapter);
    }
}
