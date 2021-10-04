package com.dinith.rdp_hotels.ui.admin;

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

import androidx.recyclerview.widget.RecyclerView;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.dashboard.select_room;
import com.dinith.rdp_hotels.ui.home.ImageAdapter;
import com.dinith.rdp_hotels.ui.home.Upload;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class room_adapter extends RecyclerView.Adapter<room_adapter.ImageViewHolder> {
    private Context mContext;
    private List<room_list> mUploads;
    private room_adapter.OnItemClickListener mListener;

    public room_adapter(Context context, List<room_list> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public room_adapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.admin_room_view, parent, false);
        return new room_adapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(room_adapter.ImageViewHolder holder, int position) {


        room_list uploadCurrent = mUploads.get(position);
        //  String palce_name = uploadCurrent.getNameOfPlace();

        // holder.textViewName.setText(palce_name);
        holder.text.setText(uploadCurrent.getname());
        holder.room_type.setText(uploadCurrent.getroom_type());
        holder.price.setText(uploadCurrent.getprice());
        holder.position1.setText(String.valueOf(position));
        Picasso.get()
                .load(uploadCurrent.getimageUrl())
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
        public TextView textViewName,position1,price,room_type,text;
        public ImageView imageView;
        Button update,delete;
        DatabaseReference nm;

        public ImageViewHolder(View itemView) {
            super(itemView);
            delete= itemView.findViewById(R.id.delete69);
            text= itemView.findViewById(R.id.text);
            price= itemView.findViewById(R.id.price);
            position1 = itemView.findViewById(R.id.key);
            update = itemView.findViewById(R.id.update);
            room_type = itemView.findViewById(R.id.room_type);
            textViewName = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.imageview);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    room_list uploadCurrent = mUploads.get(Integer.parseInt(position1.getText().toString()));

                    Intent i = new Intent(mContext, edit_room.class);
                    i.putExtra("key",uploadCurrent.getkey());
                    i.putExtra("img",uploadCurrent.getimageUrl());
                    i.putExtra("name",uploadCurrent.getname());
                    i.putExtra("rate",uploadCurrent.getrate());
                    i.putExtra("price",uploadCurrent.getprice());
                    i.putExtra("food",uploadCurrent.getfood());
                    i.putExtra("gym",uploadCurrent.getgym());
                    i.putExtra("transport",uploadCurrent.gettransport());
                    i.putExtra("wifi",uploadCurrent.getwifi());

                    i.putExtra("room_type",uploadCurrent.getroom_type());
                    mContext.startActivity(i);

                    // Toast.makeText(mContext,"sss"+key.getText().toString(),Toast.LENGTH_SHORT).show();

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    room_list uploadCurrent = mUploads.get(Integer.parseInt(position1.getText().toString()));

                    nm = FirebaseDatabase.getInstance().getReference("uploads").child(uploadCurrent.getkey());
                    nm.getRef().removeValue();

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

    public void setOnItemClickListener(room_adapter.OnItemClickListener listener) {
        mListener = listener;
    }
}