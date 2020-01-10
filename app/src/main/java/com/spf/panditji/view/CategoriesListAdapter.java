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
import com.spf.panditji.model.PujaModel;

import java.util.List;

class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ItemViewHolder> {

    private OnItemClick<PujaModel> itemClick;
    private List<PujaModel> models;

    public CategoriesListAdapter(OnItemClick<PujaModel> onItemClick) {
         this.itemClick = onItemClick;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {


        String baseUrl = "https://vaidiksewa.in/img_big/";

        PujaModel categoryModel = models.get(position);

        Glide.with(holder.imageView.getContext())
                .load(baseUrl+categoryModel.getImg())
                .into(holder.imageView);

        holder.name.setText(categoryModel.getName());
        holder.price.setText("₹"+categoryModel.getPrice());

    }

    @Override
    public int getItemCount() {
        return models == null ? 0: models.size();
    }

    public void setData(List<PujaModel> body) {
        this.models = body;
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

                    itemClick.onClick(models.get(getLayoutPosition()));

                }
            });
        }
    }
}
