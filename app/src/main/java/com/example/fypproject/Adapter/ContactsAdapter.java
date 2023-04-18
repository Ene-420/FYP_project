package com.example.fypproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.ChatActivity;
import com.example.fypproject.R;
import com.example.fypproject.Model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    Context context;
    ArrayList<UserModel> userContacts;

    public ContactsAdapter(Context context, ArrayList<UserModel> userContacts) {
        this.context = context;
        this.userContacts = userContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View uAdapter = LayoutInflater.from(context).inflate(R.layout.user_contact_card, parent, false);
        return new ViewHolder(uAdapter);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel model = userContacts.get(position);


        Picasso.get().load(model.getProfileImage()).placeholder(R.drawable.face).into(holder.contactProfilePic);
        String[] name = model.getFullName().split("_");
        holder.contactName.setText(name[0]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, ChatActivity.class);
                intent.putExtra("user", model);
                context.startActivity(intent);
                holder.newContact.setVisibility(View.GONE);


            }
        });




    }

    @Override
    public int getItemCount() {
        return userContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView newContact, contactName;
        ImageView contactProfilePic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newContact = itemView.findViewById(R.id.new_contact_icon);
            contactName = itemView.findViewById(R.id.chatNameText);
            contactProfilePic = itemView.findViewById(R.id.contact_profile_img);

            //newContact.setVisibility(View.GONE);
        }


    }
}
