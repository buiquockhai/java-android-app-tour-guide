package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterAcount extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<AccountUser> accountUsers;
    private ArrayList<AccountUser> accountUsers1= new ArrayList<>();
    private DatabaseReference databaseReference,databaseReferenceKey;

    public AdapterAcount(Context context, int layout, List<AccountUser> accountUsers) {
        this.context = context;
        this.layout = layout;
        this.accountUsers = accountUsers;
    }

    @Override
    public int getCount() {
        return accountUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view= inflater.inflate(layout,null);

        final TextView txtTaikhoan= (TextView)view.findViewById(R.id.txtTaiKhoan);
        TextView txtMatkhau= (TextView)view.findViewById(R.id.txtMatKhau);
        AccountUser accountUser= accountUsers.get(i);
        txtTaikhoan.setText(accountUser.Name);
        txtMatkhau.setText(accountUser.Password);

        return view;
    }
}
