package com.example.fypproject;

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
import com.google.firebase.auth.FirebaseAuth;
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


    public void back(View view){
        finish();
    }



    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        UserModel user = (UserModel) intent.getSerializableExtra("user");

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


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageModel model = new MessageModel(messageText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(), new Date().getTime());
                chats.add(model);

                adapter.notifyDataSetChanged();
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
    }
}