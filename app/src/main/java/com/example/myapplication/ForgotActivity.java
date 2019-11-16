package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ForgotActivity extends AppCompatActivity {

    private EditText name,cau1,cau2,cau3;
    private Button btn_tim;
    private DatabaseReference databaseReference;
    private List<AccountUser> accountUsers = new ArrayList<AccountUser>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        name= (EditText)findViewById(R.id.edt_tenTK);
        cau1= (EditText)findViewById(R.id.edt_cau1);
        cau2= (EditText)findViewById(R.id.edt_cau2);
        cau3= (EditText)findViewById(R.id.edt_cau3);
        btn_tim= (Button)findViewById(R.id.btn_timmk);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Account");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AccountUser accountUser = dataSnapshot.getValue(AccountUser.class);
                accountUsers.add(accountUser);
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
        btn_tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp=0;
                int index=-1;
                for (int i=0;i<accountUsers.size();i++){
                    if (name.getText().toString().equals(accountUsers.get(i).Name)){
                        temp=1;
                        index=i;
                        break;
                    }
                }
                if (temp == 0) {
                    Toast.makeText(ForgotActivity.this, "Tên tài khoản chưa tồn tại", Toast.LENGTH_SHORT).show();

                }
                else{
                    if (cau1.getText().toString().equals(accountUsers.get(index).Cauhoibaomat1) &&
                            cau2.getText().toString().equals(accountUsers.get(index).Cauhoibaomat2) &&
                            cau3.getText().toString().equals(accountUsers.get(index).Cauhoibaomat3)){
                            Toast.makeText(ForgotActivity.this, "Mật khẩu của bạn: "+accountUsers.get(index).Password, Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(ForgotActivity.this,TrangChu.class);
                        intent.putExtra("auto","2");
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
