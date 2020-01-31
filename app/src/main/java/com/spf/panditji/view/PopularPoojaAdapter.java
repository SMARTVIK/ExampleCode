package com.spf.panditji.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.PopularPoojaModel;

import java.util.List;

public class PopularPoojaAdapter extends RecyclerView.Adapter<PopularPoojaAdapter.ItemViewHolder> {
    private static final int GRID_TYPE = 1;
    private static final int NORMAL_TYPE = 2;
    private final OnItemClick<PopularPoojaModel> onItemClick;
    private final boolean isGrid;
    private List<PopularPoojaModel> models;

    public PopularPoojaAdapter(boolean isGrid, OnItemClick<PopularPoojaModel> popularPoojaModelOnItemClick) {
        this.onItemClick = popularPoojaModelOnItemClick;
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == GRID_TYPE) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_pooja_item_grid, parent, false));
        }else{
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_pooja_item, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isGrid?GRID_TYPE:NORMAL_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String baseUrl = "https://vaidiksewa.in/img_big/";
        PopularPoojaModel categoryModel = models.get(position);
        Glide.with(holder.imageView.getContext())
                .load(baseUrl+categoryModel.getImg())
                .into(holder.imageView);

        String s = categoryModel.getName();
        s = checkForEngagement(s);
        holder.name.setText(s);
        holder.price.setText("₹"+categoryModel.getPrice());

        if (holder.itemView.getContext() instanceof PopularPoojaList) {
            holder.name.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.darkBlack));
            holder.price.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.darkBlack));
        } else {
            holder.name.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.price.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        }
    }

    private String checkForEngagement(String s) {

        s = (s.contains("Engagement")?(s.substring(0,s.lastIndexOf("Puja")+4)):s);

        return s;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClick.onClick(models.get(getLayoutPosition()));

                }
            });
        }
    }
}
