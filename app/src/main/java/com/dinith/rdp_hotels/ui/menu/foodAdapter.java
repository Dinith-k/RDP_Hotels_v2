package com.dinith.rdp_hotels.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dinith.rdp_hotels.MainActivity;
import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.dashboard.select_room;
import com.dinith.rdp_hotels.ui.home.ImageAdapter;
import com.dinith.rdp_hotels.ui.home.Upload;
import com.squareup.picasso.Picasso;

import java.util.List;

public class foodAdapter extends RecyclerView.Adapter<foodAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Food> mUploads;
    private foodAdapter.OnItemClickListener mListener;

    public foodAdapter(Context context, List<Food> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public foodAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.food_view, parent, false);
        return new foodAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(foodAdapter.ImageViewHolder holder, int position) {


        Food uploadCurrent = mUploads.get(position);

        //  String palce_name = uploadCurrent.getNameOfPlace();


        // holder.textViewName.setText(palce_name);
        holder.S_key.setText(String.valueOf(position));
        Picasso.get()
                .load(uploadCurrent.getimage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName,S_key;
        public ImageView imageView;
        public Button add_cart;

        public ImageViewHolder(View itemView) {
            super(itemView);
            S_key= itemView.findViewById(R.id.S_key);
            add_cart = itemView.findViewById(R.id.add_cart);
            textViewName = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image_v);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

            add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                   Food uploadCurrent = mUploads.get(Integer.parseInt(S_key.getText().toString()));
                    Intent intent = new Intent(mContext, view_food.class);
                    intent.putExtra("key",uploadCurrent.getkey());
                    intent.putExtra("img",uploadCurrent.getimage());
                    intent.putExtra("desc",uploadCurrent.getdesc());
                    intent.putExtra("name",uploadCurrent.getname());
                    intent.putExtra("price",uploadCurrent.getprice());
                    mContext.startActivity(intent);



                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);


    }

    public void setOnItemClickListener(foodAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}