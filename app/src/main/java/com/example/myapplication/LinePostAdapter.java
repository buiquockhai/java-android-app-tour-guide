package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LinePostAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<Destinationclass> destinationclassList;

    public LinePostAdapter(Context context, int layout, List<Destinationclass> destinationclassList) {
        this.context = context;
        this.layout = layout;
        this.destinationclassList = destinationclassList;
    }

    @Override
    public int getCount() {
        return destinationclassList.size();
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
        TextView txtTen= (TextView)view.findViewById(R.id.txtLineName);
        TextView txtAdd= (TextView)view.findViewById(R.id.txtLineAddress);
        TextView txtDiss= (TextView)view.findViewById(R.id.txtLineDisscount);
        TextView txtTime= (TextView)view.findViewById(R.id.txtTime);

        Destinationclass destinationclass=destinationclassList.get(i);
        txtTen.setText(destinationclass.Name);
        String disscount1="Disscount: "+destinationclass.Discount+" %";
        txtAdd.setText(destinationclass.AddressLocation);
        txtDiss.setText(disscount1);
        txtTime.setText(destinationclass.ActiveTime);

        return view;
    }
}
