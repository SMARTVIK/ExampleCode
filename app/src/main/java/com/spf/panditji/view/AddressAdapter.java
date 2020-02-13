package com.spf.panditji.view;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.spf.panditji.R;
import com.spf.panditji.model.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ItemViewHolder> {

    private static final int ADD_ADDRESS_VIEW = 1;
    private static final int NORMAL_VIEW = 2;
    List<AddressModel> addressModels;
    int selected = -1;


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == NORMAL_VIEW) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
        }else{
            return new AddAddressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_address_item, parent, false));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return addressModels == null ? ADD_ADDRESS_VIEW : (position == addressModels.size() ? ADD_ADDRESS_VIEW : NORMAL_VIEW);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        if(!(holder instanceof AddAddressViewHolder)){

            AddressModel addressModel = addressModels.get(position);

            holder.address.setText(addressModel.getAddress()+"\n"
                                  +addressModel.getCity()+"\n"
                                  +addressModel.getState()+"\n"
                                  +addressModel.getLandmark()+"\n"
                                  +addressModel.getPin());

            if (selected == position) {
                holder.radioButton.setChecked(true);
                holder.threeDots.setVisibility(View.VISIBLE);
            } else {
                holder.radioButton.setChecked(false);
                holder.threeDots.setVisibility(View.GONE);
            }

            holder.threeDots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(holder.itemView.getContext(), view);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.pop_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            if(item.getItemId() == R.id.one){
                                ((SelectAddressScreen)holder.itemView.getContext()).editAddress(addressModels.get(position));
                            }

                            if(item.getItemId() == R.id.two){
                                ((SelectAddressScreen)holder.itemView.getContext()).deleteAddress(addressModels.get(position));
                            }


                            return true;
                        }
                    });

                    popup.show();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return addressModels == null ? 1 : addressModels.size()+1;
    }

    public void setData(List<AddressModel> addresses) {
        this.addressModels = addresses;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView address;
        RadioButton radioButton;
        ImageButton threeDots;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.text);
            radioButton = itemView.findViewById(R.id.radio);
            threeDots = itemView.findViewById(R.id.three_dots);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected = getLayoutPosition();
                    ((SelectAddressScreen)itemView.getContext()).setSelectedAddress(addressModels.get(getLayoutPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }

    public class AddAddressViewHolder extends ItemViewHolder {

        TextView addAddress;

        public AddAddressViewHolder(@NonNull final View itemView) {
            super(itemView);
            addAddress = itemView.findViewById(R.id.add);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SelectAddressScreen)itemView.getContext()).openDialogToAdd();
                }
            });
        }
    }
}
