package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FagmentAnalyst extends Fragment {

    View view;
    String key;
    public static final String USERKEY = "USERKEY";

    public FagmentAnalyst() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.analyst_information,container,false);

        Bundle bundle = getArguments();
        key = bundle.getString("key");

        //Set up for button
        Button btnAnalyst = (Button)view.findViewById(R.id.btnanalyst_id);
        Button btnComment = (Button)view.findViewById(R.id.btncomment_id);

        btnAnalyst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAnalyst = new Intent(getActivity(), StarAnalystActivity.class);
                intentToAnalyst.putExtra(USERKEY,key);
                startActivity(intentToAnalyst);
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToComment = new Intent(getActivity(), Main3Activity.class);
                intentToComment.putExtra(USERKEY,key);
                startActivity(intentToComment);
            }
        });



        return view;
    }
}
