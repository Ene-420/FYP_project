package com.example.fypproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fypproject.Adapter.ChatsAdapter;
import com.example.fypproject.Adapter.ContactsAdapter;
import com.example.fypproject.R;


public class ChatsFragment extends Fragment {

    RecyclerView chatsView, contactsView;
    ContactsAdapter contactsAdapter;
    ChatsAdapter chatsAdapter;


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


        return view;
    }
}