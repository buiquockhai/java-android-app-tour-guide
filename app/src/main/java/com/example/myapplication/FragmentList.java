package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentList extends Fragment {

    private String key;
    private Button btnBackMap;
    View view;
    private TextView tvAddress, tvDiscount, tvTime, tvPhoneNumber, tvNotification;
    private DatabaseReference myRef =  FirebaseDatabase.getInstance().getReference().child("User");
    private ImageButton report;

    public FragmentList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_information,container,false);

        tvAddress = (TextView)view.findViewById(R.id.tvdiachi_id);
        tvDiscount = (TextView)view.findViewById(R.id.tvgiamgia_id);
        tvTime = (TextView)view.findViewById(R.id.tvthoigian_id);
        tvPhoneNumber = (TextView)view.findViewById(R.id.tvsodienthoai_id);
        tvNotification = (TextView)view.findViewById(R.id.tvghichu_id);
        btnBackMap = (Button)view.findViewById(R.id.backMap_id);
        report=(ImageButton)view.findViewById(R.id.report_post);


        btnBackMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backMapIntent = new Intent(getActivity(),MainActivity.class);
                backMapIntent.putExtra("key",key);
                backMapIntent.putExtra("Flag","1");
                startActivity(backMapIntent);

            }
        });

        Bundle bundle= getArguments();
        key=bundle.getString("key");
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String dataLocation = dataSnapshot.child("AddressLocation").getValue().toString();
                String dataDiscount = dataSnapshot.child("Discount").getValue().toString() + "%";
                String dataTime = dataSnapshot.child("ActiveTime").getValue().toString();
                String dataPhoneNumber = dataSnapshot.child("PhoneNumber").getValue().toString();
                String dataNotification = dataSnapshot.child("Notification").getValue().toString();



                tvAddress.setText(dataLocation);
                tvDiscount.setText(dataDiscount);
                tvTime.setText(dataTime);
                tvPhoneNumber.setText(dataPhoneNumber);
                tvNotification.setText(dataNotification);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Báo cáo bài đăng")
                .setMessage("Bạn có muốn báo cáo bài đăng này")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.child(key).child("Report").setValue("1");
                        Toast.makeText(getActivity(), "Báo cáo thành công", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
