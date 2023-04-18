package com.example.fypproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fypproject.Adapter.ChatsAdapter;
import com.example.fypproject.Adapter.ContactsAdapter;
import com.example.fypproject.R;
import com.example.fypproject.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    RecyclerView chatsView, contactsView;
    ContactsAdapter contactsAdapter;
    ChatsAdapter chatsAdapter;
    ArrayList<UserModel> contactList, chatList;
    FirebaseDatabase database;
    FirebaseAuth auth;


    public ChatsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        chatsView = view.findViewById(R.id.chatsRecyclerView);
        contactsView = view.findViewById(R.id.contactsRecyclerView);

        contactList = new ArrayList<>();
        chatList = new ArrayList<>();
        chatsAdapter = new ChatsAdapter(getContext(), chatList);
        contactsAdapter = new ContactsAdapter(getContext(),  contactList);

        UserModel user = new UserModel();
        user.setUserName("Ene400");



        contactList.add(user);

        chatsView.setAdapter(chatsAdapter);
        contactsView.setAdapter(contactsAdapter);

        LinearLayoutManager chatsLayout = new LinearLayoutManager(getContext());
        LinearLayoutManager contactsLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        chatsView.setLayoutManager(chatsLayout);
        contactsView.setLayoutManager(contactsLayout);
        //chatsAdapter.notifyDataSetChanged();



        database.getReference().child("Chats").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (!dataSnapshot.hasChildren()){
                        Log.i("Chats: ", "Empty" );
                    }

                    else{
                        String userId= dataSnapshot.getKey();
                        database.getReference().child("Users").child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserModel users = snapshot.getValue(UserModel.class);
                                users.setUserID(snapshot.getKey());
                                database.getReference().child("Chats").child(auth.getCurrentUser().getUid()).child(userId).orderByChild("timeStamp").limitToLast(1).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot data : snapshot.getChildren() ){
                                            users.setLastMessage(data.child("message").getValue(String.class));
                                        }
                                        chatList.add(users);
                                        chatsAdapter.notifyDataSetChanged();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Contacts").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel model = dataSnapshot.getValue(UserModel.class);
                    model.setUserID(dataSnapshot.getKey());

                    contactList.add(model);
                }
                contactsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}