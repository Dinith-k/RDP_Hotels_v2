package com.dinith.rdp_hotels.ui.home;

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

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.dashboard.select_room;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private ImageAdapter.OnItemClickListener mListener;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.roomview, parent, false);
        return new ImageAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ImageViewHolder holder, int position) {


        Upload uploadCurrent = mUploads.get(position);
      //  String palce_name = uploadCurrent.getNameOfPlace();

       // holder.textViewName.setText(palce_name);
        holder.room_type.setText(uploadCurrent.getroom_type());
        holder.price.setText(uploadCurrent.getprice());
        holder.position1.setText(String.valueOf(position));
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
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
        public TextView textViewName,position1,price,room_type;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            price= itemView.findViewById(R.id.price);
            position1 = itemView.findViewById(R.id.key);
            room_type = itemView.findViewById(R.id.room_type);
            textViewName = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.imageview);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Upload uploadCurrent = mUploads.get(Integer.parseInt(position1.getText().toString()));

                    Intent i = new Intent(mContext, select_room.class);
                    i.putExtra("key",uploadCurrent.getKey());
                    i.putExtra("img",uploadCurrent.getImageUrl());
                    i.putExtra("name",uploadCurrent.getName());
                    i.putExtra("rate",uploadCurrent.getrate());
                    i.putExtra("price",uploadCurrent.getprice());
                    i.putExtra("room_type",uploadCurrent.getroom_type());
                    mContext.startActivity(i);

                   // Toast.makeText(mContext,"sss"+key.getText().toString(),Toast.LENGTH_SHORT).show();

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

    public void setOnItemClickListener(ImageAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}