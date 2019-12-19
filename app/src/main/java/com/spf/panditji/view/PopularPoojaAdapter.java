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
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.model.PopularPoojaModel;

import java.util.List;

public class PopularPoojaAdapter extends RecyclerView.Adapter<PopularPoojaAdapter.ItemViewHolder> {
    private List<PopularPoojaModel> models;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_pooja_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        String baseUrl = "https://vaidiksewa.in/img_big/";

        PopularPoojaModel categoryModel = models.get(position);

        Glide.with(holder.imageView.getContext())
                .load(baseUrl+categoryModel.getImg())
                .into(holder.imageView);

        holder.name.setText(categoryModel.getName());
        holder.price.setText("₹"+categoryModel.getPrice());
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public void setData(List<PopularPoojaModel> bodypoojaModels) {
        this.models = bodypoojaModels;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name , price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
