package com.example.fypproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fypproject.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmActivity extends AppCompatActivity {

    FirebaseDatabase database;
    Button agree;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        database = FirebaseDatabase.getInstance();
        agree = findViewById(R.id.agreeBtn);

        Intent intent = getIntent();
        UserModel model = (UserModel) intent.getSerializableExtra("user");


        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
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
}