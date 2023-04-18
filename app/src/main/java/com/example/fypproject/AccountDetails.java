package com.example.fypproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fypproject.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountDetails extends AppCompatActivity {


    AutoCompleteTextView university, courseYr;
    FirebaseDatabase database;
    EditText course;
    TextView hobbies;
    ProgressDialog progressDialog;


    public void confirm(View view){
        String[] array = hobbies.getText().toString().split(", ");
        ArrayList<String> userHobbies = new ArrayList<>(Arrays.asList(array));

        if(university.getText().toString().isEmpty() || courseYr.getText().toString().isEmpty() || course.getText().toString().isEmpty()||
        hobbies.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Fill All Spaces", Toast.LENGTH_SHORT).show();
        }

        else{
            //progressDialog.show();
            Intent intent = getIntent();
            UserModel userDetails = (UserModel) intent.getSerializableExtra("user");

            userDetails.setCourse(course.getText().toString());
            userDetails.setUniversity(university.getText().toString());
            userDetails.setCourseYr(courseYr.getText().toString());
            userDetails.setHobbies(userHobbies);

            Intent intent1 = new Intent(getApplicationContext(), ConfirmActivity.class);
            intent1.putExtra("user", userDetails);
            startActivity(intent1);

            finish();


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating User");
        progressDialog.setMessage("User Account is being Created");
        university = findViewById(R.id.universityAutoCompleteTextView);
        courseYr = findViewById(R.id.courseYrAutoCompleteTextView);
        hobbies = findViewById(R.id.hobbiesList);
        course = findViewById(R.id.courseName);


        String[] universityName = getResources().getStringArray(R.array.university);
        String[] courseYrValue = getResources().getStringArray(R.array.courseYr);
        String[] hobbyArray = getResources().getStringArray(R.array.hobbies);
        boolean[] selectedHobbies = new boolean[hobbyArray.length];
        ArrayList<Integer> arrayList = new ArrayList<>();


        ArrayAdapter<String> universityAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, universityName);
        ArrayAdapter<String> courseYrAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, courseYrValue);

        university.setAdapter(universityAdapter);
        courseYr.setAdapter(courseYrAdapter);


        hobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder  dialog = new AlertDialog.Builder(AccountDetails.this);
                dialog.setTitle("Hobbies");

                dialog.setCancelable(false);
                dialog.setMultiChoiceItems(hobbyArray, selectedHobbies, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            arrayList.add(i);
                        }
                        else{
                            arrayList.remove(i);
                        }
                    }
                });

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder builder = new StringBuilder();

                        for(int x= 0; x< arrayList.size(); x++){
                            builder.append(hobbyArray[arrayList.get(x)]);

                            if (x != arrayList.size()-1){
                                builder.append(", ");
                            }
                        }
                        hobbies.setText(builder.toString());
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
}