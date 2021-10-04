package com.dinith.rdp_hotels.ui.notifications;

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
import com.dinith.rdp_hotels.ui.menu.Food;

import com.dinith.rdp_hotels.ui.menu.view_food;
import com.dinith.rdp_hotels.ui.qr.purchase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class paymentdapter extends RecyclerView.Adapter<paymentdapter.ImageViewHolder> {
    private Context mContext;
    private List<purchase> mUploads;
    private paymentdapter.OnItemClickListener mListener;

    public paymentdapter(Context context, List<purchase> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public paymentdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.paymentview, parent, false);
        return new paymentdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(paymentdapter.ImageViewHolder holder, int position) {


        purchase uploadCurrent = mUploads.get(position);

        holder.date.setText(uploadCurrent.getdate());
        holder.total.setText("Total :"+uploadCurrent.gettotal_price());




    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView date,total;


        public ImageViewHolder(View itemView) {
            super(itemView);
            date= itemView.findViewById(R.id.date);
            total= itemView.findViewById(R.id.total);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);


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

    public void setOnItemClickListener(paymentdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}