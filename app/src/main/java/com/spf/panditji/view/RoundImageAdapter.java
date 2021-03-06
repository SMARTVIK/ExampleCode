package com.spf.panditji.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.CategoryModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoundImageAdapter extends RecyclerView.Adapter<RoundImageAdapter.ItemViewHolder> {
    private final OnItemClick<CategoryModel> onItemClick;
    private List<CategoryModel> categories;

    public RoundImageAdapter(OnItemClick<CategoryModel> onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RoundImageAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.round_image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        String baseUrl = "https://vaidiksewa.in/img_mob_cat/";

        CategoryModel categoryModel = categories.get(position);

        Glide.with(holder.imageView.getContext())
                .load(baseUrl+categoryModel.getImg())
                .into(holder.imageView);

        holder.textView.setText(categoryModel.getCat());
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    public void setData(List<CategoryModel> categoryModels) {
        this.categories = categoryModels;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);

            textView = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClick.onClick(categories.get(getLayoutPosition()));
                }
            });
        }
    }
}
