package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    private String getNumStar,numStarEqual;
    private Long value;
    private TextView tvNumStar;
    private EditText edtName, edtText;
    private Button btnCancel, btnAgree;
    private DatabaseReference myRefStarChange =  FirebaseDatabase.getInstance().getReference().child("User");
    private DatabaseReference myRefCommentChange =  FirebaseDatabase.getInstance().getReference().child("User");
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        key = intent.getStringExtra(StarAnalystActivity.USERKEY1);

        //Anh xa ===============================================================================

        edtName = findViewById(R.id.edtnguoicmt_id);
        edtText = findViewById(R.id.edtbinhluan_id);
        tvNumStar = (TextView)findViewById(R.id.tvnumstar_id);
        btnCancel = (Button)findViewById(R.id.btnhuycmt_id);
        btnAgree = (Button)findViewById(R.id.btndangcmt_id);

        //Get rate of star rating ==============================================================
        numStarEqual = getNumStar();

        //Click handle event for button ========================================================

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelComment();
            }
        });



        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRefStarChange.child(key).child("Star").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        value = (Long)dataSnapshot.child(numStarEqual).getValue();
                        value = value + 1;
                        dataSnapshot.getRef().child(numStarEqual).setValue(value);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                if (edtName.getText().equals("")||edtText.getText().equals("")){
                    Intent back = new Intent(Main2Activity.this,BuiQuocKhai.class);
                    back.putExtra("key",key);
                    startActivity(back);
                }
                else {
                    HashMap<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("Name",edtName.getText().toString().trim());
                    dataMap.put("Status",edtText.getText().toString().trim());
                    dataMap.put("Report","0");
                    myRefCommentChange.child(key).child("Comment").push().setValue(dataMap);
                    Toast.makeText(Main2Activity.this,"Cảm ơn bạn đã đánh giá.", Toast.LENGTH_SHORT).show();
                    Intent back = new Intent(Main2Activity.this,BuiQuocKhai.class);
                    back.putExtra("key",key);
                    startActivity(back);
                }

            }
        });
    }

    public String getNumStar(){
        Intent intent = getIntent();
        getNumStar = intent.getStringExtra(StarAnalystActivity.NUMSTAR);
        String setNumStar = "Kết quả sao của bạn: " + getNumStar;
        tvNumStar.setText(setNumStar);
        return getNumStar;
    }

    public void cancelComment(){
//        Intent intent = new Intent(Main2Activity.this, StarAnalystActivity.class);
//        intent.putExtra("key",key);
//        startActivity(intent);
        edtName.setText("");
        edtText.setText("");
    }
}
