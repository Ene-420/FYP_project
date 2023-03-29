package com.example.fypproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText signInEmail;
    TextInputEditText signInPassword;
    ProgressDialog dialog;

    public void signIn(View view){
        if(signInEmail.getText().toString().isEmpty() || signInPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Details", Toast.LENGTH_SHORT).show();
        }

        else{
            dialog.show();
            auth.signInWithEmailAndPassword(signInEmail.getText().toString(), signInPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        dialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                        finish();


                    }

                    else{
                        Toast.makeText(SignInActivity.this, "Please Enter Valid Details \nTry Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInEmail = findViewById(R.id.signInEmailText);
        signInPassword = findViewById(R.id.signInPasswordText);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Sign In");
        dialog.setMessage("Verifying User Details");





    }
}