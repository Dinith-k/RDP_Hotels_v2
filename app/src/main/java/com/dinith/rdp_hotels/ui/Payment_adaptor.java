package com.dinith.rdp_hotels.ui.home;

import android.content.Context;
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
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.imageview);
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

    public void setOnItemClickListener(ImageAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
/*package com.dinith.rdp_hotels.ui.home;

import android.content.Context;
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
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.imageview);
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

    public void setOnItemClickListener(ImageAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
} */

/* <?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.notifications.NotificationsFragment">

    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:background="#ffb900"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".ui.dashboard.DashboardFragment">

        <RelativeLayout
            android:background="@drawable/round_gb_white"
            android:layout_alignParentBottom="true"
            android:layout_width="match_parent"
            android:layout_marginTop="50dp"
            android:layout_height="match_parent">

            <LinearLayout
                android:layout_marginTop="20dp"
                android:orientation="vertical"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

                <LinearLayout
                    android:orientation="horizontal"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content">


                    <TextView
                        android:id="@+id/text"
                        android:layout_width="wrap_content"
                        android:text="Services"
                        android:textSize="23dp"
                        android:fontFamily="@font/acuminvariableconcept"
                        android:textColor="#000"
                        android:layout_marginStart="40dp"
                        android:layout_height="wrap_content"/>

                    <TextView
                        android:id="@+id/price"
                        android:layout_width="match_parent"
                        android:text="See All"
                        android:textColor="#9F94F3"
                        android:textSize="18dp"
                        android:fontFamily="@font/acuminvariableconcept"
                        android:textAlignment="viewEnd"
                        android:layout_marginEnd="40dp"
                        android:layout_marginStart="20dp"
                        android:layout_height="wrap_content"/>






                </LinearLayout>

                <HorizontalScrollView
                    android:layout_marginTop="30dp"
                    android:layout_width="fill_parent"
                    android:layout_height="wrap_content"
                    android:layout_alignParentTop="true"
                    android:fillViewport="true"
                    android:measureAllChildren="false"
                    android:scrollbars="none" >

                    <LinearLayout
                        android:id="@+id/innerLay"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:gravity="center_vertical"
                        android:orientation="horizontal" >

                        <ImageButton
                            android:layout_marginStart="40dp"

                            android:layout_width="100dp"
                            android:background="@drawable/wifi_dmdpi"
                            android:layout_height="110dp"/>

                        <ImageButton
                            android:layout_marginStart="40dp"
                            android:layout_width="100dp"
                            android:background="@drawable/gym_dmdpi"
                            android:layout_height="110dp"/>

                        <ImageButton
                            android:layout_marginStart="40dp"
                            android:layout_width="100dp"
                            android:background="@drawable/f_lmdpi"
                            android:layout_height="110dp"/>
                        <ImageButton
                            android:layout_marginStart="40dp"
                            android:layout_width="100dp"
                            android:background="@drawable/tra_dmdpi"
                            android:layout_height="110dp"/>


                    </LinearLayout>


                </HorizontalScrollView>

                <TextView
                    android:layout_marginTop="30dp"
                    android:layout_width="wrap_content"
                    android:text="Room Type"
                    android:textSize="23dp"
                    android:fontFamily="@font/acuminvariableconcept"
                    android:textColor="#000"
                    android:layout_marginStart="40dp"
                    android:layout_height="wrap_content"/>




                <androidx.recyclerview.widget.RecyclerView
                    android:visibility="visible"
                    android:layout_marginTop="50dp"
                    android:id="@+id/recyclerview"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"/>



            </LinearLayout>

            <ProgressBar
                android:layout_gravity="center"
                android:id="@+id/progress"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerInParent="true" />

        </RelativeLayout>

    </RelativeLayout>
</androidx.constraintlayout.widget.ConstraintLayout> */