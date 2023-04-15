package com.example.fypproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fypproject.Adapter.ActivityAdapter;
import com.example.fypproject.Model.MessageModel;
import com.example.fypproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ActivityFragment extends Fragment {


    FloatingActionButton feedPost;
    RecyclerView feedRecyclerView;
    ActivityAdapter adapter;


    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View activityView = inflater.inflate(R.layout.fragment_activity, container, false);

        feedPost = activityView.findViewById(R.id.activity_post);
        feedRecyclerView = activityView.findViewById(R.id.activityRecyclerView);

        ArrayList<MessageModel> feedPost = new ArrayList<>();

        adapter = new ActivityAdapter(getContext(), feedPost);

        LinearLayoutManager layout = new LinearLayoutManager(getContext());

        MessageModel model = new MessageModel();
        model.setMessage("Who understood the Software Engineering lecture today");


        feedPost.add(model);
        feedRecyclerView.setAdapter(adapter);
        feedRecyclerView.setLayoutManager(layout);



        return activityView;
    }
}