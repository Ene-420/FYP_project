package com.example.fypproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.fypproject.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class CreateAccountActivity extends AppCompatActivity {

    EditText firstName, lastName, userName, dob;
    ImageView add_icon,profile_img;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String userID;

    UserModel user;
    DatePickerDialog.OnDateSetListener dateSetListener;


/*    public void addProfilePic(View view){
        PopupMenu popupMenu = new PopupMenu(CreateAccountActivity.this, add_icon);
        popupMenu.getMenuInflater().inflate(R.menu.camera_popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.select_existing:
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 25);
                        break;
                    case R.id.open_camera:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null){

            Uri sFile = data.getData();
            profile_img.setImageURI(sFile);
            user.setProfileImage(sFile.toString());

            final StorageReference reference = storage.getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("ProfileImage");

            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setProfileImage(uri.toString());
                        }
                    });
                }
            });

        }
    }

    public void confirmDetails(View view){

        if(firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || dob.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Fill All Spaces", Toast.LENGTH_SHORT).show();
        }

        else{

            if(!userName.getText().toString().isEmpty()){
                database.getReference().child("Users").orderByChild("userName").equalTo(userName.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getValue() != null){

                            Toast.makeText(CreateAccountActivity.this, "Username Already Exists \nPlease Enter Another Username", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            user.setFullName(firstName.getText().toString(), lastName.getText().toString());
                            user.setDob(dob.getText().toString());
                            user.setUserName("@"+userName.getText().toString());

                            Intent intent = new Intent(getApplicationContext(), AccountDetails.class);
                            intent.putExtra("user", user);
                            startActivity(intent);

                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else {
                user.setFullName(firstName.getText().toString(), lastName.getText().toString());
                user.setDob(dob.getText().toString());
                user.setUserName("@"+firstName.getText().toString()+"_"+new RandomIdGenerator().randomAlphaValue());

                Intent intent = new Intent(getApplicationContext(), AccountDetails.class);
                intent.putExtra("user", user);
                startActivity(intent);

                finish();

            }

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_create_account);
        profile_img = findViewById(R.id.profile_imageView);
        firstName = findViewById(R.id.firstNameText);
        lastName = findViewById(R.id.lastNameText);
        userName = findViewById(R.id.chatNameText);
        dob = findViewById(R.id.ageText);
        add_icon = findViewById(R.id.addProfilePic);
        user = new UserModel();

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        if(getIntent().hasExtra("userID")){
            userID = getIntent().getStringExtra("userID");
        }

        add_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(CreateAccountActivity.this, add_icon);
                popupMenu.getMenuInflater().inflate(R.menu.camera_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.select_existing:
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, 25);
                                break;
                            case R.id.open_camera:
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar date = Calendar.getInstance();

                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH);
                int day = date.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog picker = new DatePickerDialog(CreateAccountActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, year, month, day);
                picker.show();
            }//dob.setText(day+"/"+ (month+1)+"/"+year);
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dob.setText(day+"/"+ (month+1)+"/"+year);
            }
        };

    }
}