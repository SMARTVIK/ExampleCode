package com.spf.panditji.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.CategoryModel;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ItemViewHolder> {

    private List<CategoryModel> categoryModels;
    private OnItemClick<CategoryModel> onItemClick;

    public GridAdapter(OnItemClick<CategoryModel> categoryModelOnItemClick) {
        this.onItemClick = categoryModelOnItemClick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        String baseUrl = "https://vaidiksewa.in/img_mob_cat/";

        CategoryModel categoryModel = categoryModels.get(position);

        Glide.with(holder.imageView.getContext())
                .load(baseUrl+categoryModel.getImg())
                .into(holder.imageView);

        holder.textView.setText(categoryModel.getCat());

    }


    public void setData(List<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categoryModels == null ? 0 : categoryModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        RoundRectCornerImageView imageView;
        TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClick(categoryModels.get(getLayoutPosition()));
                }
            });
        }
    }
}
