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
import com.spf.panditji.model.BookingListModel;
import com.spf.panditji.model.BookingModel;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ItemViewHolder> {

    private List<BookingListModel> items;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        BookingListModel bookingModel = items.get(position);
        holder.name.setText(bookingModel.getPooja_name());
        holder.amount.setText("₹"+bookingModel.getAmount());
        if(bookingModel.getImg()!=null){
            String baseUrl = "https://vaidiksewa.in/img_big/";
            Glide.with(holder.itemView.getContext())
                    .load(baseUrl+bookingModel.getImg())
                    .into(holder.imageView);
        }
        holder.bookingTime.setText(bookingModel.getPooja_date());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setData(List<BookingListModel> body) {
        this.items = body;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name, amount, bookingTime;
        ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            bookingTime = itemView.findViewById(R.id.booking_time);

        }
    }
}
