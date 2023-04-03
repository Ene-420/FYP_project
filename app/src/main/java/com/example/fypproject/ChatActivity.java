package com.example.fypproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fypproject.Model.UserModel;
import com.squareup.picasso.Picasso;

public class ChatActivity extends AppCompatActivity {

    ImageButton send, gallery;
    ImageView profileImg;
    TextView username;
    EditText messageText;
    RecyclerView message;


    public void back(View view){
        finish();
    }



    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        UserModel user = intent.getSerializableExtra("user", UserModel.class);

        send = findViewById(R.id.user_chat_send);
        gallery = findViewById(R.id.user_chat_gallery_icon);
        profileImg = findViewById(R.id.user_chat_profile_pic);
        username = findViewById(R.id.user_chat_username);
        messageText = findViewById(R.id.user_chat_messagebox);
        message = findViewById(R.id.chatRecyclerView);


        Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.face).into(profileImg);
        username.setText(user.getUserName());

        messageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageText.getText().toString().isEmpty()){
                    gallery.setVisibility(View.GONE);
                }
                else{
                    gallery.setVisibility(View.VISIBLE);
                }
            }
        });





    }
}