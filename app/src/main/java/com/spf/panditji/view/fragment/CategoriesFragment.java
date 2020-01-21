package com.spf.panditji.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.view.CategoryListActivity;
import com.spf.panditji.view.GridAdapter;
import com.spf.panditji.view.RoundImageAdapter;
import com.spf.panditji.view.SignInActivity;
import com.spf.panditji.view.SpacesItemDecoration;

public class CategoriesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpCategoryList(view);
    }

    private void setUpCategoryList(View view){
        RecyclerView recyclerView = view.findViewById(R.id.nav_my_bookings);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        GridAdapter adapter = new GridAdapter(new OnItemClick<CategoryModel>() {
            @Override
            public void onClick(CategoryModel categoryModel) {
                //onItemClick handling
                startActivity(new Intent(getContext(), CategoryListActivity.class).putExtra("cat",categoryModel.getCat()));
            }
        });
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        recyclerView.setAdapter(adapter);
        adapter.setData(ApplicationDataController.getInstance().getCategoriesList());
    }

}
