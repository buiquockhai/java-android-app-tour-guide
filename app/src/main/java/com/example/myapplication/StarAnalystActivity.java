package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StarAnalystActivity extends AppCompatActivity {


    public static final String NUMSTAR = "NUMSTAR";
    public static final String USERKEY1 = "USEKEY1";
    private ProgressBar prgOneStar,prgTwoStar,prgThreeStar,prgFourStar,prgFiveStar;
    private TextView tvAll;
    private RatingBar rtbBig, rtbSmall;
    private DatabaseReference myRefStar =  FirebaseDatabase.getInstance().getReference().child("User");
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_analyst);

        //Anh xa ================================================================================
        rtbBig = (RatingBar)findViewById(R.id.rtbbig_id);
        rtbSmall = (RatingBar)findViewById(R.id.rtbsmall_id);
        prgOneStar = findViewById(R.id.prgonestar_id);
        prgTwoStar = findViewById(R.id.prgtwostar_id);
        prgThreeStar = findViewById(R.id.prgthreestar_id);
        prgFourStar = findViewById(R.id.prgfourstar_id);
        prgFiveStar = findViewById(R.id.prgfivestar_id);
        tvAll = findViewById(R.id.tvall_id);

        //When use rating bar ===================================================================
        rtbBig.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                sendNumStar((int)ratingBar.getRating());
            }
        });

        Intent intent = getIntent();
        key = intent.getStringExtra(FagmentAnalyst.USERKEY);
    }

    @Override
    protected void onStart() {
        super.onStart();

        myRefStar.child(key).child("Star").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Show detail star analyst ======================================================
                String strOneStar = dataSnapshot.child("1").getValue().toString();
                String strTwoStar = dataSnapshot.child("2").getValue().toString();
                String strThreeStar = dataSnapshot.child("3").getValue().toString();
                String strFourStar = dataSnapshot.child("4").getValue().toString();
                String strFiveStar = dataSnapshot.child("5").getValue().toString();

                int intOneStar =Integer.parseInt(strOneStar);
                int intTwoStar =Integer.parseInt(strTwoStar);
                int intThreeStar =Integer.parseInt(strThreeStar);
                int intFourStar =Integer.parseInt(strFourStar);
                int intFiveStar =Integer.parseInt(strFiveStar);

                int sum = intOneStar + intTwoStar + intThreeStar + intFourStar + intFiveStar;
                int max = Math.max(intOneStar,Math.max(intTwoStar,Math.max(intThreeStar,Math.max(intFourStar,intFiveStar)))) + 1;

                //Set data for .xml =============================================================

                prgOneStar.setProgress(intOneStar*100/max);
                prgTwoStar.setProgress(intTwoStar*100/max);
                prgThreeStar.setProgress(intThreeStar*100/max);
                prgFourStar.setProgress(intFourStar*100/max);
                prgFiveStar.setProgress(intFiveStar*100/max);

                double doubleAll =(double)(intOneStar + intTwoStar*2 + intThreeStar*3 + intFourStar*4 + intFiveStar*5)/sum;
                tvAll.setText(String.valueOf((double)Math.round(doubleAll*10)/10));
                rtbSmall.setRating((float)Math.round(doubleAll*10)/10);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void sendNumStar(int numStar){
        Intent intent = new Intent(StarAnalystActivity.this, Main2Activity.class);
        intent.putExtra(NUMSTAR, String.valueOf(numStar));
        intent.putExtra(USERKEY1,key);
        startActivity(intent);
    }
}
