package com.example.fypproject;

import static java.util.Arrays.asList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fypproject.Adapter.RequestAdapter;

import java.util.ArrayList;

public class FriendRequestActivity extends AppCompatActivity {


    ArrayList<UserModel> friendRequestList;
    RequestAdapter adapter;
    RecyclerView requestView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        UserModel user = new UserModel();
        user.setUserName("Ene");
        user.setUniversity("NTU");
        user.setCourse("Comp Sci");
        user.setCourseYr("1st");
        user.setHobbies(new ArrayList<>(asList("Netflix", "Fifa", "MMA", "Sleeping")));

        ArrayList<UserModel> list = new ArrayList<>();
        list.add(user);

        requestView = findViewById(R.id.friendRequestRecyclerView);

        adapter = new RequestAdapter(getApplicationContext(), list);
        requestView.setAdapter(adapter);
        requestView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }
}