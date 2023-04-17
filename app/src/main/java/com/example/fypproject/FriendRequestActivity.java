package com.example.fypproject;

import static java.util.Arrays.asList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fypproject.Adapter.RequestAdapter;
import com.example.fypproject.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendRequestActivity extends AppCompatActivity {


    ArrayList<UserModel> friendRequestList;
    RequestAdapter adapter;
    RecyclerView requestView;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        database = FirebaseDatabase.getInstance();

/*        UserModel user = new UserModel();
        user.setUserName("Ene");
        user.setUniversity("NTU");
        user.setCourse("Comp Sci");
        user.setCourseYr("1st");
        user.setUserID("007ene419data");
        user.setHobbies(new ArrayList<>(asList("Netflix", "Fifa", "MMA", "Sleeping")));*/

        ArrayList<UserModel> list = new ArrayList<>();
        //list.add(user);

        requestView = findViewById(R.id.friendRequestRecyclerView);

        adapter = new RequestAdapter(getApplicationContext(), list);
        requestView.setAdapter(adapter);
        requestView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        database.getReference().child("Request").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data: snapshot.getChildren()){
                            UserModel user = data.getValue(UserModel.class);
                            user.setUserID(data.getKey());

                            list.add(user);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}