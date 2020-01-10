package com.spf.panditji.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.R;

import java.util.List;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ItemViewHolder> {

    private List<String> keyPoints;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.textView.setText((position+1)+". "+keyPoints.get(position).trim());
    }

    @Override
    public int getItemCount() {
        return keyPoints == null ? 0 : keyPoints.size();
    }

    public void setData(List<String> keyPoints) {
        this.keyPoints = keyPoints;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
