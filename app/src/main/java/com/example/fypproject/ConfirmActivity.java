package com.example.fypproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fypproject.Model.LocationModel;
import com.example.fypproject.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmActivity extends AppCompatActivity {

    FirebaseDatabase database;
    Button agree;
    ProgressDialog progressDialog;
    LocationManager locationManager;
    Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        database = FirebaseDatabase.getInstance();
        agree = findViewById(R.id.agreeBtn);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        ActivityCompat.requestPermissions(ConfirmActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1 );

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
                        userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                progressDialog.show();
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
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

                            }
                        });
            }
        });



    }
}