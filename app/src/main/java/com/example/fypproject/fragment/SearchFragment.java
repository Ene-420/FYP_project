package com.example.fypproject.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fypproject.Adapter.RequestAdapter;
import com.example.fypproject.FriendRequestActivity;
import com.example.fypproject.R;


public class SearchFragment extends Fragment {

    TextView userRequestCount, friendRequest;
    SeekBar searchRadius;
    RecyclerView searchResultView;
    RequestAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        userRequestCount = view.findViewById(R.id.user_request_count);
        friendRequest = view.findViewById(R.id.requestText);


        friendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FriendRequestActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }
}