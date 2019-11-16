package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.LayoutDirection;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.CancellableTask;
import com.google.firebase.storage.ControllableTask;
import com.google.firebase.storage.StorageTask.SnapshotBase;
import com.google.android.gms.tasks.Task;
import java.lang.Object;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;


//import com.google.firebase.database.FirebaseStorage;
import android.widget.CalendarView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class post extends AppCompatActivity {
    Button btnUpdate;


    String arr[] = {
            "Đồ uống",
            "Đồ ăn",
            "Mua sắm"};
    TextView selection;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    ImageButton btnChoose;
    ImageView imgPicture;
    Bitmap selectedBitmap;
    EditText edtStoreName, edtDescription, edtActiveTime, edtPhone, edtAddress, edtDiscount;
    private String key;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ImageButton btn_home1=(ImageButton)findViewById(R.id.btn_home1);
        btn_home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        Button btn_clear= (Button)findViewById(R.id.btn_clear);
        key=getIntent().getStringExtra("keyAccount");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Account").child(key).child("keyUser");
        addControls();
        addEvents();
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtStoreName.getText().clear();
                edtDescription.getText().clear();
                edtActiveTime.getText().clear();
                edtPhone.getText().clear();
                edtAddress.getText().clear();
                edtDiscount.getText().clear();
            }
        });

        selection = (TextView) findViewById(R.id.selection);
        //Lấy đối tượng Spinner ra
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        //Gán Data source (arr) vào Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arr
                );
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spin.setAdapter(adapter);
        //thiết lập sự kiện chọn phần tử cho Spinner
        spin.setOnItemSelectedListener(new MyProcessEvent());
    }

    public void addControls() {
        btnChoose = findViewById(R.id.btnChoose);
        imgPicture = findViewById(R.id.imgPicture);
        edtStoreName = findViewById(R.id.edtStoreName);
        edtActiveTime = findViewById(R.id.edtActiveTime);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtDescription = findViewById(R.id.edtDescription);
        edtDiscount = findViewById(R.id.edtDiscount);


    }

    private class MyProcessEvent implements
            AdapterView.OnItemSelectedListener {
        //Khi có chọn lựa thì vào hàm này
        public void onItemSelected(AdapterView<?> arg0,
                                   View arg1,
                                   int arg2,
                                   long arg3) {
            //arg2 là phần tử được chọn trong data source
            selection.setText(arr[arg2]);
        }

        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {
            selection.setText("");
        }

    }


    public void addEvents() {
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
    }


    private void choosePicture() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 200);//one can be replaced with any action code
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgPicture.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();


            }
        }

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    public void xuLyCapNhat(View view) {

        try {

            StorageReference ref=storage.getReferenceFromUrl("gs://myapplication8-eaa0c.appspot.com/");  // can edit lai cho nay de dong bo du lieu
            final StorageReference mountainsRef = ref.child("image"+ UUID.randomUUID().toString() +"png");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
//Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
            final DatabaseReference myRef = database.getReference("User");
            final String userId = myRef.push().getKey();

            String StoreName=edtStoreName.getText().toString();
            String ActiveTime = edtActiveTime.getText().toString();
            String Address = edtAddress.getText().toString();
            LatLng latLng;
            latLng=getLocationFromAddress(this,Address);
            String Phone =  edtPhone.getText().toString();
            String Description = edtDescription.getText().toString();
            String Discount =  edtDiscount.getText().toString();
            String sel=selection.getText().toString();
            myRef.child(userId).child("AccountPost").setValue(key);
            myRef.child(userId).child("Name").setValue(StoreName);
            myRef.child(userId).child("ActiveTime").setValue(ActiveTime);
            myRef.child(userId).child("AddressLocation").setValue(Address);
            myRef.child(userId).child("PhoneNumber").setValue(Phone);
            myRef.child(userId).child("Notification").setValue(Description);
            myRef.child(userId).child("Discount").setValue(Discount);
            myRef.child(userId).child("Type").setValue(sel);

            myRef.child(userId).child("Comment").child(userId).child("Name").setValue("Admin");
            myRef.child(userId).child("Comment").child(userId).child("Report").setValue("0");
            myRef.child(userId).child("Comment").child(userId).child("Status").setValue("Hiển thị bình luận");
            myRef.child(userId).child("Report").setValue("0");
            myRef.child(userId).child("Log").setValue(latLng.longitude);
            myRef.child(userId).child("Lat").setValue(latLng.latitude);



            myRef.child(userId).child("Star").child("1").setValue(0);
            myRef.child(userId).child("Star").child("2").setValue(0);
            myRef.child(userId).child("Star").child("3").setValue(0);
            myRef.child(userId).child("Star").child("4").setValue(0);
            myRef.child(userId).child("Star").child("5").setValue(0);

            databaseReference.push().setValue(userId);



//đưa bitmap về base64string:

            imgPicture.setDrawingCacheEnabled(true);
            imgPicture.buildDrawingCache();
            Bitmap bitmap =  imgPicture.getDrawingCache();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String imgeEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                  myRef.child(userId).child("Image").setValue(imgeEncoded);


            UploadTask uploadTask = mountainsRef.putBytes(byteArray);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
//                    Uri downloadUrl =taskSnapshot.getUploadSessionUri();
                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myRef.child(userId).child("Image").setValue(uri.toString());
                        }
                    });


                }
            });


//            UploadTask uploadTask = mountainsRef.putBytes(byteArray);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//
//
//                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            myRef.child(userId).child("image").setValue(uri.toString());
//                        }
//                    });
//                }
//            });



            int i=0;
            while (i!=20000){
                i++;
            }
            finish();

        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error:"+ex.toString(),Toast.LENGTH_LONG).show();
        }
        Intent a=new Intent(post.this,report.class);
        a.putExtra("keyAccount",key);
        startActivity(a);


    }
    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Thoát khỏi tài khoản đăng bài. Quay về trang chủ.")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(post.this,TrangChu.class));
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(post.this,"Hủy đăng xuất",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}




