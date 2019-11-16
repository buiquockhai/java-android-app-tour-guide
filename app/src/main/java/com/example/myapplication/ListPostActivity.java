package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPostActivity extends AppCompatActivity {

    private ListView listPost;
    private Button btn_dangBai,btnReset;
    private LinePostAdapter adapter;
    private ArrayList<Destinationclass> linePost= new ArrayList<>();
    private DatabaseReference databaseReference,databaseReferenceKey;
    private String key;
    private ArrayList<String> listkey= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);
        listPost= (ListView)findViewById(R.id.listPost);
        btn_dangBai= (Button)findViewById(R.id.btn_dangINLIST);
        btnReset=(Button)findViewById(R.id.btnReset);
        key=getIntent().getStringExtra("keyAccount");
        databaseReferenceKey=FirebaseDatabase.getInstance().getReference().child("Account").child(key).child("keyUser");
        databaseReferenceKey.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=dataSnapshot.getValue(String.class);
                listkey.add(key);
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
        btn_dangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListPostActivity.this,post.class);
                intent.putExtra("keyAccount",key);
                startActivity(intent);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter=new LinePostAdapter(ListPostActivity.this,R.layout.line_bai_dang,linePost);
                for (int i=0;i<listkey.size();i++){
                    databaseReference=FirebaseDatabase.getInstance().getReference().child("User").child(listkey.get(i));
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String Name= dataSnapshot.child("Name").getValue().toString();
                            String Add= dataSnapshot.child("AddressLocation").getValue().toString();
                            String Diss=dataSnapshot.child("Discount").getValue().toString();
                            String Time= dataSnapshot.child("ActiveTime").getValue().toString();
                            linePost.add(new Destinationclass(Name,Add,Diss,Time));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                listPost.setAdapter(adapter);
            }
        });



    }




}
