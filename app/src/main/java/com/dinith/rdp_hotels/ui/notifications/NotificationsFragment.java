package com.dinith.rdp_hotels.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.menu.Food;
import com.dinith.rdp_hotels.ui.menu.foodAdapter;
import com.dinith.rdp_hotels.ui.menu.menu;
import com.dinith.rdp_hotels.ui.qr.purchase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    DatabaseReference postRef;
RecyclerView recyclerview;
    FirebaseUser user;
    private paymentdapter f_Adapter;
    private List<purchase> f_Uploads;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);



        ValueLineChart mCubicValueLineChart = (ValueLineChart) root.findViewById(R.id.cubiclinechart);

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFFffb900);


        f_Uploads = new ArrayList<>();
        f_Adapter = new paymentdapter(getContext(), f_Uploads);
        recyclerview = root.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(f_Adapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 1));






        user = FirebaseAuth.getInstance().getCurrentUser();
        postRef = FirebaseDatabase.getInstance().getReference().child("purchased").child(user.getUid());

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
int i = 0;
                f_Uploads.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()) {


                    purchase upload = child.getValue(purchase.class);

                    f_Uploads.add(upload);

                    String price = dataSnapshot.child(child.getKey()).child("total_price").getValue().toString();
i++;
                   // Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();


                    series.addPoint(new ValueLinePoint( "Room "+i, Float.valueOf(price)));

                  //  mCubicValueLineChart.addSeries(series);
                   // mCubicValueLineChart.startAnimation();
                }

                f_Adapter.notifyDataSetChanged();
                mCubicValueLineChart.addSeries(series);
                mCubicValueLineChart.startAnimation();

                //






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });






        return root;
    }
}