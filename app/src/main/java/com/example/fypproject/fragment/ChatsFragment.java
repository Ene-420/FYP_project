package com.example.fypproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fypproject.Adapter.ChatsAdapter;
import com.example.fypproject.Adapter.ContactsAdapter;
import com.example.fypproject.R;
import com.example.fypproject.Model.UserModel;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    RecyclerView chatsView, contactsView;
    ContactsAdapter contactsAdapter;
    ChatsAdapter chatsAdapter;
    ArrayList<UserModel> userList;


    public ChatsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);

        chatsView = view.findViewById(R.id.chatsRecyclerView);
        contactsView = view.findViewById(R.id.contactsRecyclerView);

        userList = new ArrayList<>();
        chatsAdapter = new ChatsAdapter(getContext(), userList);
        contactsAdapter = new ContactsAdapter(getContext(),  userList);

        UserModel user = new UserModel();
        user.setUserName("Ene400");



        userList.add(user);

        chatsView.setAdapter(chatsAdapter);
        contactsView.setAdapter(contactsAdapter);

        LinearLayoutManager chatsLayout = new LinearLayoutManager(getContext());
        LinearLayoutManager contactsLayout = new LinearLayoutManager(getContext());
        chatsView.setLayoutManager(chatsLayout);
        contactsView.setLayoutManager(contactsLayout);
        //chatsAdapter.notifyDataSetChanged();



        return view;
    }
}