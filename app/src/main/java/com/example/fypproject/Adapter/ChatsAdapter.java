package com.example.fypproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.R;
import com.example.fypproject.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {


    Context context;
    ArrayList<UserModel> chatList;

    public ChatsAdapter(Context context, ArrayList<UserModel> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewAdapter = LayoutInflater.from(context).inflate(R.layout.user_chat_sample, parent, false);

        return new ViewHolder(viewAdapter);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserModel model = chatList.get(position);
        Picasso.get().load(model.getProfileImage()).placeholder(R.drawable.face).into(holder.chatProfileImage);
        holder.username.setText(model.getUserName());
//        holder.lastMessage.setText(model.);
//        holder.lastMessageTimeStamp.setText();
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView chatProfileImage;
        TextView lastMessage, lastMessageTimeStamp, username;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chatProfileImage = itemView.findViewById(R.id.chat_profile_pic);
            username = itemView.findViewById(R.id.chatNameText);
            lastMessage = itemView.findViewById(R.id.messageText);
            lastMessageTimeStamp = itemView.findViewById(R.id.messageTime);
        }
    }
}
