package com.example.myapplication;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
         TaskLoadedCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleMap mMap;
    private ImageButton btnGetDirection,btnDetail;
    private Polyline currentPolyline;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private LatLng lastAddress1, lastAddress2;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private double latitude, longitude;
    private int ProximityRadius = 10000;
    private DatabaseReference databaseReference;
    private List<Destinationclass> destinationclasses = new ArrayList<Destinationclass>();
    private List<String> listkey = new ArrayList<String>();
    private int index;
    private int Flag;
    private TextView txtdirection,txtdetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkuserLocationPermission();
        }

        btnGetDirection = (ImageButton) findViewById(R.id.btnGetDirection);
        btnDetail = (ImageButton) findViewById(R.id.btnDetail);
        txtdirection= (TextView)findViewById(R.id.txtDirection);
        txtdetail= (TextView)findViewById(R.id.txtdetail);


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                Destinationclass destinationclass = dataSnapshot.getValue(Destinationclass.class);
                destinationclasses.add(destinationclass);
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

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search: {
                btnDetail.setVisibility(View.INVISIBLE);
                btnGetDirection.setVisibility(View.INVISIBLE);
                EditText addressField = (EditText) findViewById(R.id.search_place);
                String address = addressField.getText().toString();
                List<Address> addresses = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();
                if (!TextUtils.isEmpty(address)) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addresses = geocoder.getFromLocationName(address, 6);
                        if (addresses != null) {
                            for (int i = 0; i < addresses.size(); i++) {
                                Address userAddress = addresses.get(i);
                                //lastAddress= userAddress;
                                mMap.clear();
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());
                                lastAddress2 = latLng;
                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(address);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                mMap.addMarker(userMarkerOptions);

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                            }
                        } else {
                            Toast.makeText(this, "Location not found ...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Please write any location name ...", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.btn_Baverage:
                mMap.clear();
                btnGetDirection.setVisibility(View.INVISIBLE);
                btnDetail.setVisibility(View.INVISIBLE);
                txtdirection.setVisibility(View.INVISIBLE);
                txtdetail.setVisibility(View.INVISIBLE);
                for (int i = 0; i < destinationclasses.size(); i++) {
                    if (destinationclasses.get(i).Type.equals("Đồ uống")) {
                        MarkerOptions userMarkerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(destinationclasses.get(i).Lat, destinationclasses.get(i).Log);
                        userMarkerOptions.position(latLng);
                        userMarkerOptions.title(destinationclasses.get(i).Name+": " +destinationclasses.get(i).AddressLocation);
                        userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addMarker(userMarkerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }
                }
                break;
            case R.id.btn_shop:
                mMap.clear();
                btnGetDirection.setVisibility(View.INVISIBLE);
                btnDetail.setVisibility(View.INVISIBLE);
                txtdirection.setVisibility(View.INVISIBLE);
                txtdetail.setVisibility(View.INVISIBLE);
                for (int i = 0; i < destinationclasses.size(); i++) {
                    if (destinationclasses.get(i).Type.equals("Mua sắm")) {
                        MarkerOptions userMarkerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(destinationclasses.get(i).Lat, destinationclasses.get(i).Log);
                        userMarkerOptions.position(latLng);
                        userMarkerOptions.title(destinationclasses.get(i).Name+": " +destinationclasses.get(i).AddressLocation);
                        userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addMarker(userMarkerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }
                }
                break;
            case R.id.btn_restaurant:
                mMap.clear();
                btnGetDirection.setVisibility(View.INVISIBLE);
                btnDetail.setVisibility(View.INVISIBLE);
                txtdirection.setVisibility(View.INVISIBLE);
                txtdetail.setVisibility(View.INVISIBLE);
                for (int i = 0; i < destinationclasses.size(); i++) {
                    if (destinationclasses.get(i).Type.equals("Đồ ăn")) {
                        MarkerOptions userMarkerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(destinationclasses.get(i).Lat, destinationclasses.get(i).Log);
                        userMarkerOptions.position(latLng);
                        userMarkerOptions.title(destinationclasses.get(i).Name+": " +destinationclasses.get(i).AddressLocation);
                        userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addMarker(userMarkerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }
                }
                break;
        }
    }

    private String getUrl1(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + getString(R.string.google_map_api_key));
        return googleURL.toString();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        try {
            Intent intent = getIntent();
            Flag = Integer.valueOf(intent.getStringExtra("Flag"));
            if (Flag==1){
                showAlertDialog();
            }
        } catch (Exception e) {
            Flag = 0;
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                float result[] = new float[10];
                Location.distanceBetween(latitude, longitude, marker.getPosition().latitude, marker.getPosition().longitude, result);
                marker.setSnippet("Khoảng cách: " + result[0] / 1000 + " km");
                btnGetDirection.setVisibility(View.VISIBLE);
                txtdirection.setVisibility(View.VISIBLE);
                btnGetDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = getUrl(lastAddress1, new LatLng(marker.getPosition().latitude, marker.getPosition().longitude), "driving");
                        new FetchURL(MainActivity.this).execute(url, "driving");
                    }
                });

                for (int i = 0; i < destinationclasses.size(); i++) {
                    if (marker.getPosition().latitude == destinationclasses.get(i).Lat && marker.getPosition().longitude == destinationclasses.get(i).Log) {
                        index = i;
                        btnDetail.setVisibility(View.VISIBLE);
                        txtdetail.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MainActivity.this, BuiQuocKhai.class);
                        intent.putExtra("key", listkey.get(index));
                        startActivity(intent);
//                        overridePendingTransition(R.anim.animate1,R.anim.animate2);

                    }
                });
                return false;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {  //
            @Override
            public void onMapClick(LatLng latLng) {
                btnGetDirection.setVisibility(View.INVISIBLE);
                btnDetail.setVisibility(View.INVISIBLE);
            }
        });

}

    public  boolean checkuserLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_Code );
            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_Code );
            }
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case Request_User_Location_Code:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                        if (googleApiClient==null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this, "Permission Denied ...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_map_api_key);
        return url;
    }


    protected  synchronized  void buildGoogleApiClient(){
        googleApiClient= new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline!=null){
            currentPolyline.remove();
        }
        currentPolyline=mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude=location.getLatitude();
        longitude=location.getLongitude();
        lastLocation= location;
        if (currentUserLocationMarker!= null){
            currentUserLocationMarker.remove();
        }
        LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());
        lastAddress1=latLng;

        MarkerOptions markerOptions= new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("user current");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        currentUserLocationMarker= mMap.addMarker(markerOptions);
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
        if (googleApiClient!=null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest=new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Đường đi")
                .setIcon(R.drawable.route123)
                .setMessage("Bạn có muốn vẽ đường đi đến địa điểm vừa tham khảo ?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mMap.clear();
                        int vitri=0;
                        Intent intent= getIntent();
                        String key=intent.getStringExtra("key");
                        for (int i=0;i<listkey.size();i++){
                            if (key.equals(listkey.get(i))){
                                vitri=i;
                                break;
                            }
                        }
                        MarkerOptions userMarkerOptions= new MarkerOptions();
                        LatLng latLng= new LatLng(destinationclasses.get(vitri).Lat,destinationclasses.get(vitri).Log);
                        userMarkerOptions.position(latLng);
                        userMarkerOptions.title(destinationclasses.get(vitri).Name+": "+destinationclasses.get(vitri).AddressLocation);
                        userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addMarker(userMarkerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        String url= getUrl(lastAddress1,latLng,"driving");
                        new FetchURL(MainActivity.this).execute(url,"driving");
                        Flag=0;

                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
