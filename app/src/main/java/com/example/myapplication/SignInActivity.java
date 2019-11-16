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

public class SignInActivity extends AppCompatActivity {

    private EditText Name,Pass,Cau1,Cau2,Cau3;
    private Button btnsign_in;
    private DatabaseReference databaseReference;
    private List<AccountUser> accountUsers = new ArrayList<AccountUser>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Account");
        Name= (EditText)findViewById(R.id.edtName);
        Pass= (EditText)findViewById(R.id.edtPass);
        Cau1= (EditText)findViewById(R.id.edtCau1);
        Cau2= (EditText)findViewById(R.id.edtCau2);
        Cau3= (EditText)findViewById(R.id.edtCau3);
        btnsign_in=(Button)findViewById(R.id.btnSignin);
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
        btnsign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=Name.getText().toString();
                String pass=Pass.getText().toString();
                if (name.equals("")|| pass.equals("")|| Cau1.getText().toString().equals("")
                        || Cau2.getText().toString().equals("")
                        || Cau3.getText().toString().equals("")){

                    Toast.makeText(SignInActivity.this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    for (int i=0;i<accountUsers.size();i++){
                        if (name.equals(accountUsers.get(i).Name.toString())){
                            Toast.makeText(SignInActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            Name.setText("");
                        }
                    }
                    if (Name.getText().toString().length()<6 || Pass.getText().toString().length()<6){
                        Toast.makeText(SignInActivity.this, "Tài khoản hoặc mật khẩu chưa đủ ký tự (tối thiểu 6 ký tự)", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        AccountUser user= new AccountUser(Name.getText().toString(),
                                Pass.getText().toString(),Cau1.getText().toString(),
                                Cau2.getText().toString(),Cau3.getText().toString());
                        databaseReference.push().setValue(user);
                        Intent intent= new Intent(SignInActivity.this, TrangChu.class);
                        intent.putExtra("auto","1");
                        startActivity(intent);
                    }
                }

            }
        });

    }
}
