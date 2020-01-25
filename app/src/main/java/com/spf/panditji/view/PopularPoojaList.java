package com.spf.panditji.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.View;

import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.model.PopularPoojaModel;
import com.spf.panditji.view.fragment.PopularPanditAdapter;

import java.util.List;

public class PopularPoojaList extends AppCompatActivity {

    private List<PopularPoojaModel> poojaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_pooja_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        poojaList = getIntent().getParcelableExtra("list");
        initViews();
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        PopularPoojaAdapter popularPanditAdapter = new PopularPoojaAdapter(new OnItemClick<PopularPoojaModel>() {
            @Override
            public void onClick(PopularPoojaModel popularPanditModel) {
                startActivity(new Intent(PopularPoojaList.this, DetailScreen.class).putExtra("id", popularPanditModel.getId()));
            }
        });
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(popularPanditAdapter);
    }
}
