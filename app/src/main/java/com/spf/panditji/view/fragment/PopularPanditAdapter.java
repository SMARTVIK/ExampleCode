package com.spf.panditji.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spf.panditji.R;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.view.PopularPoojaAdapter;

import java.util.List;

public class PopularPanditAdapter extends RecyclerView.Adapter<PopularPanditAdapter.ItemViewHolder> {
    private List<PopularPanditModel> models;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_pandit_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        String baseUrl = "https://vaidiksewa.in/pandit_img/";

        PopularPanditModel categoryModel = models.get(position);

        Glide.with(holder.imageView.getContext())
                .load(baseUrl+categoryModel.getImg())
                .into(holder.imageView);

        holder.name.setText(categoryModel.getName());

    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public void setData(List<PopularPanditModel> popularPanditModels) {
        this.models = popularPanditModels;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        LinearLayout oneStar,twoStar,threeStar,fourStar,fiveStar;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);

            oneStar = itemView.findViewById(R.id.one_star);
            twoStar = itemView.findViewById(R.id.two_star);
            threeStar = itemView.findViewById(R.id.three_star);
            fourStar = itemView.findViewById(R.id.four_star);
            fiveStar = itemView.findViewById(R.id.five_star);
        }
    }
}
