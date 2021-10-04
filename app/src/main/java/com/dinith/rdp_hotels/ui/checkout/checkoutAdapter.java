package com.dinith.rdp_hotels.ui.checkout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.menu.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class checkoutAdapter extends RecyclerView.Adapter<checkoutAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Food> mUploads;
    private checkoutAdapter.OnItemClickListener mListener;

    public checkoutAdapter(Context context, List<Food> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public checkoutAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.checkout_menu_view, parent, false);
        return new checkoutAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(checkoutAdapter.ImageViewHolder holder, int position) {
        int toal = 0;
        int c2 = 0;
        Food uploadCurrent = mUploads.get(position);

        holder.textViewName.setText(uploadCurrent.getname().toString()+"   "+uploadCurrent.getprice().toString());

       // holder.item_total.setText(uploadCurrent.getprice().toString());
        String prce_l;
        prce_l =uploadCurrent.getprice().toString() ;
        prce_l = prce_l.replaceAll("[^-?0-9]+", "");
        prce_l = prce_l.replaceAll(" ", "");

         c2 = Integer.valueOf(uploadCurrent.getquantity());
          toal = Integer.valueOf(prce_l)*c2;


        holder.item_total.setText(String.valueOf(toal));





        holder.desc_.setText(uploadCurrent.getdesc());

        holder.q_count.setText(uploadCurrent.getquantity());
        holder.S_key.setText(String.valueOf(position));
        Picasso.get()
                .load(uploadCurrent.getimage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);


        Intent intent = new Intent("custom-message");
        intent.putExtra("total",String.valueOf(toal));
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);





    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName,S_key,desc_,q_count,remove_t,item_total;
        public ImageView imageView;
        public Button add_cart;
        DatabaseReference nm;
        Spinner date_count;
        FirebaseUser user;
        public ImageViewHolder(View itemView) {
            super(itemView);

            user = FirebaseAuth.getInstance().getCurrentUser();
            item_total= itemView.findViewById(R.id.item_total);
            date_count= itemView.findViewById(R.id.date_count);
            remove_t = itemView.findViewById(R.id.remove_t);
            q_count= itemView.findViewById(R.id.q_count);
            desc_= itemView.findViewById(R.id.desc_);
            S_key= itemView.findViewById(R.id.S_key);
            add_cart = itemView.findViewById(R.id.add_cart);
            textViewName = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image_v);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);




            List<String> list = new ArrayList<String>();
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list){
                @Override
                public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);




                    return view;
                }};
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            list.add("select");
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("5");
            list.add("6");
            list.add("7");
            list.add("8");
            list.add("9");
            list.add("10");
            date_count.setAdapter(adapter2);




            date_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                  // parent.getItemAtPosition(position).toString();




if(parent.getItemAtPosition(position).toString().equals("select")){

}else {



                      Food uploadCurrent = mUploads.get(Integer.parseInt(S_key.getText().toString()));

                      HashMap hashMap = new HashMap();
                    hashMap.put("quantity",parent.getItemAtPosition(position).toString());
                     nm = FirebaseDatabase.getInstance().getReference("users").child(user.getUid().toString()).child("cart").child(uploadCurrent.getkey().toString());
                      nm.updateChildren(hashMap);


}

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }


            });


            remove_t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Food uploadCurrent = mUploads.get(Integer.parseInt(S_key.getText().toString()));
                    nm = FirebaseDatabase.getInstance().getReference("users").child(user.getUid().toString()).child("cart").child(uploadCurrent.getkey().toString());
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

    public void setOnItemClickListener(checkoutAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}