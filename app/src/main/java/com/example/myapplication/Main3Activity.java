package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    public static final String KEYREPORT = "KEYREPORT";
    private TextView textName1,textName2,textName3,textName4,textName5,textName6,textName7,textName8;
    private TextView textStatus1,textStatus2,textStatus3,textStatus4,textStatus5,textStatus6,textStatus7,textStatus8;
    private Button buttonReport1,buttonReport2,buttonReport3,buttonReport4,buttonReport5,buttonReport6,buttonReport7,buttonReport8;
    private DatabaseReference mCommentRef =  FirebaseDatabase.getInstance().getReference().child("User");
    private List<User> list = new ArrayList<>();
    private List<String> listReport = new ArrayList<>();
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        key = intent.getStringExtra(FagmentAnalyst.USERKEY);

        //Anh xa=================================================================================
        textName1 = findViewById(R.id.text_name1);
        textName2 = findViewById(R.id.text_name2);
        textName3 = findViewById(R.id.text_name3);
        textName4 = findViewById(R.id.text_name4);
        textName5 = findViewById(R.id.text_name5);
        textName6 = findViewById(R.id.text_name6);
        textName7 = findViewById(R.id.text_name7);
        textName8 = findViewById(R.id.text_name8);
        textStatus1 = findViewById(R.id.text_status1);
        textStatus2 = findViewById(R.id.text_status2);
        textStatus3 = findViewById(R.id.text_status3);
        textStatus4 = findViewById(R.id.text_status4);
        textStatus5 = findViewById(R.id.text_status5);
        textStatus6 = findViewById(R.id.text_status6);
        textStatus7 = findViewById(R.id.text_status7);
        textStatus8 = findViewById(R.id.text_status8);
        buttonReport1 = findViewById(R.id.btnreport1);
        buttonReport2 = findViewById(R.id.btnreport2);
        buttonReport3 = findViewById(R.id.btnreport3);
        buttonReport4 = findViewById(R.id.btnreport4);
        buttonReport5 = findViewById(R.id.btnreport5);
        buttonReport6 = findViewById(R.id.btnreport6);
        buttonReport7 = findViewById(R.id.btnreport7);
        buttonReport8 = findViewById(R.id.btnreport8);
        //=======================================================================================

        //======================================================================================

        mCommentRef.child(key).child("Comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String keyReport = postSnapshot.getKey();
                    User user = postSnapshot.getValue(User.class);
                    if(!user.Name.equals("")&& !user.Status.equals("")){
                        list.add(user);
                        listReport.add(keyReport);
                    }
                }
                setView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Dialog1(View view){
        if(listReport.size()>0){
            showAlertDialog(listReport.get(listReport.size()-1));
        }
    }

    public void Dialog2(View view){
        if(listReport.size()>1){
            showAlertDialog(listReport.get(listReport.size()-2));
        }
    }

    public void Dialog3(View view){
        if(listReport.size()>2){
            showAlertDialog(listReport.get(listReport.size()-3));
        }
    }

    public void Dialog4(View view){
        if(listReport.size()>3){
            showAlertDialog(listReport.get(listReport.size()-4));
        }
    }

    public void Dialog5(View view){
        if(listReport.size()>4){
            showAlertDialog(listReport.get(listReport.size()-5));
        }
    }

    public void Dialog6(View view){
        if(listReport.size()>5){
            showAlertDialog(listReport.get(listReport.size()-6));
        }
    }

    public void Dialog7(View view){
        if(listReport.size()>6){
            showAlertDialog(listReport.get(listReport.size()-7));
        }
    }

    public void Dialog8(View view){
        if(listReport.size()>7){
            showAlertDialog(listReport.get(listReport.size()-8));
        }
    }

    public void setView(){
        int count = list.size();
        if(count > 0){
            for(;;){
                if(count>8){
                    textName1.setText(list.get(count-1).Name);
                    textName2.setText(list.get(count-2).Name);
                    textName3.setText(list.get(count-3).Name);
                    textName4.setText(list.get(count-4).Name);
                    textName5.setText(list.get(count-5).Name);
                    textName6.setText(list.get(count-6).Name);
                    textName7.setText(list.get(count-7).Name);
                    textName8.setText(list.get(count-8).Name);
                    textStatus1.setText(list.get(count-1).Status);
                    textStatus2.setText(list.get(count-2).Status);
                    textStatus3.setText(list.get(count-3).Status);
                    textStatus4.setText(list.get(count-4).Status);
                    textStatus5.setText(list.get(count-5).Status);
                    textStatus6.setText(list.get(count-6).Status);
                    textStatus7.setText(list.get(count-7).Status);
                    textStatus8.setText(list.get(count-8).Status);
                    break;
                }
                else {
                    textName1.setText(list.get(count-1).Name);
                    textStatus1.setText(list.get(count-1).Status);
                    if(count-1 <= 0) break;
                    textName2.setText(list.get(count-2).Name);
                    textStatus2.setText(list.get(count-2).Status);
                    if(count-2 <= 0) break;
                    textName3.setText(list.get(count-3).Name);
                    textStatus3.setText(list.get(count-3).Status);
                    if(count-3 <= 0) break;
                    textName4.setText(list.get(count-4).Name);
                    textStatus4.setText(list.get(count-4).Status);
                    if(count-4 <= 0) break;
                    textName5.setText(list.get(count-5).Name);
                    textStatus5.setText(list.get(count-5).Status);
                    if(count-5 <= 0) break;
                    textName6.setText(list.get(count-6).Name);
                    textStatus6.setText(list.get(count-6).Status);
                    if(count-6 <= 0) break;
                    textName7.setText(list.get(count-7).Name);
                    textStatus7.setText(list.get(count-7).Status);
                    if(count-7 <= 0) break;
                    textName8.setText(list.get(count-8).Name);
                    textStatus8.setText(list.get(count-8).Status);
                    break;
                }
            }
        }
    }

    private void showAlertDialog(final String report){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Báo cáo")
                .setMessage("Bạn có muốn báo cáo bình luận này.")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mCommentRef.child(key).child("Comment").child(report).child("Report").setValue("1");

                        Intent intent = new Intent(Main3Activity.this,BuiQuocKhai.class);
                        intent.putExtra("key",key);
                        startActivity(intent);
//                        Toast.makeText(Main3Activity.this,"Báo cáo thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Main3Activity.this,"Hủy báo cáo",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
