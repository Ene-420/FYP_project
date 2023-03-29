package com.example.fypproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateNewAccountActivity extends AppCompatActivity {

    EditText emailText;
    TextInputEditText passwordText, confirmPasswordText;
    FirebaseAuth auth;
    ProgressDialog dialog;

    public void CreateAccount(View view){
        if(emailText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Fill All Empty Space", Toast.LENGTH_LONG).show();
        }
        else if (!passwordText.getText().toString().equals(confirmPasswordText.getText().toString())) {
            Toast.makeText(this, "Please Enter Matching Passwords", Toast.LENGTH_SHORT).show();
        } else{
            dialog.show();
            String[] emailId = emailText.getText().toString().split("@");

            if(emailId[1].equals("my.ntu.ac.uk")){
                auth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            String id = task.getResult().getUser().getUid();
                            Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                            intent.putExtra("userID", id);
                            startActivity(intent);
                            finish();

                        }

                        else{
                            Toast.makeText(CreateNewAccountActivity.this, "Account Couldn't Be Created \nTry Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else{
                Toast.makeText(this, "Please Enter A Valid Email", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        auth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Creating Account");
        dialog.setMessage("Creating Being Created");



    }
}