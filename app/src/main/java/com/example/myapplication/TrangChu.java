package com.example.myapplication;


import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends AppCompatActivity {

    private Button btnMap,btnSort,btnUpload,btnAdmin;
    private DatabaseReference databaseReference;
    private List<AccountUser> accountUsers = new ArrayList<AccountUser>();
    private String auto="0";
    private List<String> listkey= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Account");
        btnMap = findViewById(R.id.btnBando_id);
        btnSort = findViewById(R.id.btnSapxep_id);
        btnUpload = findViewById(R.id.btnDangbai_id);
        btnAdmin = findViewById(R.id.btnQuantri_id);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listkey.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String keyReport = postSnapshot.getKey();
                    listkey.add(keyReport);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(TrangChu.this,MainActivity.class);
                startActivity(mapIntent);
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUser();
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogAdmin();
            }
        });
        try{
            auto=getIntent().getStringExtra("auto");
            if (auto.equals("1")){
                btnUpload.callOnClick();
                Toast.makeText(this, "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
            }
            else if (auto.equals("2")){
                btnUpload.callOnClick();
            }
        }catch (Exception e){

        }

    }
    private void dialogAdmin(){
        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialog_admin_login);
        final EditText edtName = (EditText)dialog.findViewById(R.id.edt_nameAdmin);
        final EditText edtPass = (EditText)dialog.findViewById(R.id.edt_passAdmin);
        Button btndongy= (Button)dialog.findViewById(R.id.btn_dang_nhap);
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtName.getText().toString().equals("")|| edtPass.getText().toString().equals("")){
                    Toast.makeText(TrangChu.this, "Tài khoản hoặc mật khẩu chưa điền", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(edtName.getText().toString().equals("Admin") && edtPass.getText().toString().equals("1234")){
                        startActivity(new Intent(TrangChu.this, AdminActivity.class));
                    }
                    else{
                        Toast.makeText(TrangChu.this, "Không phải tài khoản của quản trị viên", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                }
            }
        });

        dialog.show();
    }
    private void dialogUser(){
        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialog_user_login);
        final EditText edtName = (EditText)dialog.findViewById(R.id.edt_nameuser);
        final EditText edtPass = (EditText)dialog.findViewById(R.id.edt_passuser);
        Button btn_dang_nhap =(Button)dialog.findViewById(R.id.btn_log_in_user);
        Button btn_dang_ky=(Button)dialog.findViewById(R.id.btn_sign_in_user);
        TextView forgot=(TextView)dialog.findViewById(R.id.forgot);
        btn_dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(edtName.getText().toString(),edtPass.getText().toString() );
            }
        });
        btn_dang_ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangChu.this,SignInActivity.class));
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangChu.this,ForgotActivity.class));
            }
        });

        dialog.show();
    }
    private void validate (String userName, String userPass){

        int temp=1;
        if (userName.equals("")|| userPass.equals("")){
            Toast.makeText(this, "Tài khoản hoặc mật khẩu chưa điền", Toast.LENGTH_SHORT).show();
        }
        else{
            for (int i=0;i<accountUsers.size();i++){

                if (userName.equals(accountUsers.get(i).Name) && userPass.equals(accountUsers.get(i).Password)){
                    Intent intent= new Intent(TrangChu.this,ListPostActivity.class);
                    intent.putExtra("keyAccount",listkey.get(i));
                    startActivity(intent);
                    temp=0;
                    break;
                }
                else if(userName.equals(accountUsers.get(i).Name)&& !userPass.equals(accountUsers.get(i).Password)){
                    Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    temp++;
                }

            }
            if(temp==1){
                Toast.makeText(this, "Tai khoản chưa được đăng ký", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
