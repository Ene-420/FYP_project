package com.example.fypproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.fypproject.Adapter.MessageAdapter;
import com.example.fypproject.Model.MessageModel;
import com.example.fypproject.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    ImageButton send, gallery;
    ImageView profileImg;
    TextView username;
    EditText messageText;
    RecyclerView message;
    MessageAdapter adapter;
    FirebaseAuth auth;
    FirebaseDatabase database;


    public void back(View view){
        finish();
    }



    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        UserModel user = (UserModel) intent.getSerializableExtra("user");
        String receiverId = user.getUserID();
        String senderId = auth.getCurrentUser().getUid();

        send = findViewById(R.id.user_chat_send);
        gallery = findViewById(R.id.user_chat_gallery_icon);
        profileImg = findViewById(R.id.user_chat_profile_pic);
        username = findViewById(R.id.user_chat_username);
        messageText = findViewById(R.id.user_chat_messagebox);
        message = findViewById(R.id.chatRecyclerView);
        ArrayList<MessageModel> chats = new ArrayList<>();

        adapter = new MessageAdapter(this, chats);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        message.setLayoutManager(layoutManager);
        message.setAdapter(adapter);






        Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.face).into(profileImg);
        username.setText(user.getUserName());

        messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() !=0){
                    gallery.setVisibility(View.GONE);
                }
                else{
                    gallery.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        database.getReference().child("Chats").child(senderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot data: snapshot.getChildren()){
                    MessageModel messageModel = data.getValue(MessageModel.class);
                    messageModel.setUserId(data.getKey());

                    chats.add(messageModel);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageModel model = new MessageModel(messageText.getText().toString(), senderId, new Date().getTime(), "Message");
                chats.add(model);

                database.getReference().child("Chats").child(senderId).child(receiverId).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child(receiverId).child(senderId).push().setValue(model);
                    }
                });


            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ChatActivity.this, gallery);
                popupMenu.getMenuInflater().inflate(R.menu.camera_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.select_existing:
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent.createChooser(intent, "Select Image"), 25);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 25 && resultCode ==RESULT_OK && data.getData()!= null){


        }
    }
}