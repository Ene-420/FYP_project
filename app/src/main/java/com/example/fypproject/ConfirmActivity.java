package com.example.fypproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fypproject.Model.LocationModel;
import com.example.fypproject.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.net.URISyntaxException;

public class ConfirmActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseStorage storage;
    Button agree;
    ProgressDialog progressDialog;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Account Being Created");
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        agree = findViewById(R.id.agreeBtn);

        ActivityCompat.requestPermissions(ConfirmActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onLocationChanged(@NonNull Location location) {
                /*if (ActivityCompat.checkSelfPermission(ConfirmActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ConfirmActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;*/
                //}
                Log.i("Location", locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).toString());
            }
        };

        Intent intent = getIntent();
        UserModel model = (UserModel) intent.getSerializableExtra("user");


        agree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                try{
                    if(ContextCompat.checkSelfPermission(ConfirmActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ConfirmActivity.this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                    else {
                        progressDialog.show();
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        Location userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        if (userLocation != null) {
                                            LocationModel model = new LocationModel();
                                            model.setUserLongitude(userLocation.getLongitude());
                                            model.setUserLatittude(userLocation.getLatitude());
                                            database.getReference().child("Location").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialog.dismiss();

                                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                    startActivity(intent);

                                                    finish();

                                                }
                                            });

                                        } else {
                                            Toast.makeText(ConfirmActivity.this, "Unable To Get Location", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });




                    }
                }catch(Exception e){
                    e.printStackTrace();
                }


            }
        });



    }
}