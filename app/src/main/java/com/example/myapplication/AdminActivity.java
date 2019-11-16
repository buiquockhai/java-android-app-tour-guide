package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private ListView listPost,listPost1;
    private Button btnAcc,btnPost;
    private AdapterAcount adapter1;
    private LinePostAdapter adapter2;
    private ArrayList<AccountUser> accountUsers= new ArrayList<>();
    private DatabaseReference databaseReference,databaseReferenceKey,databaseReference2;
    private ArrayList<String> listKey=new ArrayList<>();
    private ArrayList<Destinationclass> destinationclasses= new ArrayList<>();
    private ArrayList<String> listKey2=new ArrayList<>();
    String keyAcount;
    String keyPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listPost= (ListView)findViewById(R.id.List);
        listPost1= (ListView)findViewById(R.id.List1);
        btnAcc= (Button)findViewById(R.id.btn_show_account);
        btnPost=(Button)findViewById(R.id.btn_show_post);
        databaseReferenceKey= FirebaseDatabase.getInstance().getReference().child("Account");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference2= FirebaseDatabase.getInstance().getReference();

        databaseReferenceKey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listKey.clear();
                accountUsers.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    AccountUser accountUser = dataSnapshot1.getValue(AccountUser.class);
//                    accountUsers.add(accountUser);
                    String Name=dataSnapshot1.child("Name").getValue().toString();
                    String Pass=dataSnapshot1.child("Password").getValue().toString();
                    String key= dataSnapshot1.getKey();
                    listKey.add(key);
                    accountUsers.add(new AccountUser(Name,Pass));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                destinationclasses.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String report= dataSnapshot1.child("Report").getValue().toString();
                    if (report.equals("1")){
                        String name = dataSnapshot1.child("Name").getValue().toString();
                        String address= dataSnapshot1.child("AddressLocation").getValue().toString();
                        String diss= dataSnapshot1.child("Discount").getValue().toString();
                        String activeTime= dataSnapshot1.child("ActiveTime").getValue().toString();
                        destinationclasses.add(new Destinationclass(name,address,diss,activeTime));
                        String key= dataSnapshot1.getKey();
                        listKey2.add(key);keyPost=dataSnapshot.getKey();
//                                                databaseReference.child("Account").child(keyAcount).child("keyUser").child(keyPost).setValue(null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPost1.setAdapter(null);
                listPost1.setVisibility(View.INVISIBLE);
                listPost.setVisibility(View.VISIBLE);
                adapter1= new AdapterAcount(AdminActivity.this,R.layout.line_account,accountUsers);
                listPost.setAdapter(adapter1);
            }
        });
        listPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAlertDialog(i);

            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPost.setAdapter(null);
                listPost.setVisibility(View.INVISIBLE);
                listPost1.setVisibility(View.VISIBLE);
                adapter2= new LinePostAdapter(AdminActivity.this,R.layout.line_bai_dang,destinationclasses);
                listPost1.setAdapter(adapter2);
            }
        });
        listPost1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAlertDialog1(i);
            }
        });


    }
    private void showAlertDialog(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Xóa tài khoản")
                .setMessage("Bạn có muốn xóa tài khoản này")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("Account").child(listKey.get(i)).setValue(null);
                        Toast.makeText(AdminActivity.this, "Xóa tài khoản thanh công", Toast.LENGTH_SHORT).show();
                        adapter1.notifyDataSetChanged();
                        finish();
                        startActivity(getIntent());

                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter1.notifyDataSetChanged();
                        finish();
                        startActivity(getIntent());

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showAlertDialog1(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Xóa bài đăng")
                .setMessage("Bạn có muốn xóa bài đăng này")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("User").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                if (dataSnapshot.getKey().equals(listKey2.get(i))){
                                    Destinationclass destinationclass= dataSnapshot.getValue(Destinationclass.class);
                                    keyAcount=destinationclass.AccountPost;
                                    databaseReference2.child("Account").child(keyAcount).child("keyUser").addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                            if (dataSnapshot.getValue(String.class).equals(listKey2.get(i))){
                                                keyPost=dataSnapshot.getKey();
                                                databaseReference.child("Account").child(keyAcount).child("keyUser").child(keyPost).setValue(null);
                                                int j=0;
                                                while (j!=60000){
                                                    j++;
                                                }
                                                databaseReference.child("User").child(listKey2.get(i)).setValue(null);

                                            }
                                        }

                                        @Override
                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }

                                    });
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



//                        databaseReference.child("User").child(listKey2.get(i)).setValue(null);
                        Toast.makeText(AdminActivity.this, "Xóa bài đăng thành công", Toast.LENGTH_SHORT).show();
                        adapter2.notifyDataSetChanged();
                        finish();
                        startActivity(getIntent());

                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("User").child(listKey2.get(i)).child("Report").setValue("0");
                        adapter2.notifyDataSetChanged();
                        finish();
                        startActivity(getIntent());
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
