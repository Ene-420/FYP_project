package com.example.fypproject.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fypproject.Adapter.RequestAdapter;
import com.example.fypproject.Adapter.SearchAdapter;
import com.example.fypproject.DistanceCalculator;
import com.example.fypproject.FriendRequestActivity;
import com.example.fypproject.Model.LocationModel;
import com.example.fypproject.Model.UserModel;
import com.example.fypproject.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class SearchFragment extends Fragment {

    TextView userRequestCount, friendRequest;
    SeekBar searchRadius;
    RecyclerView searchRecyclerView;
    SearchAdapter adapter;
    AutoCompleteTextView  unit;
    TextInputEditText maxDistance, distance;
    LocationModel userModel, model;
    ArrayList<UserModel> searchList;
    FirebaseDatabase database;
    double distanceBtwnUsers;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        database = FirebaseDatabase.getInstance();
        userRequestCount = view.findViewById(R.id.user_request_count);
        friendRequest = view.findViewById(R.id.requestText);
        unit = view.findViewById(R.id.unitAutoCompleteTextView);
        maxDistance = view.findViewById(R.id.max_Distance);
        searchRadius = view.findViewById(R.id.searchDistance);
        distance = view.findViewById(R.id.distance);

        searchList = new ArrayList<>();
        searchRecyclerView = view.findViewById(R.id.search_RecyclerView);
        adapter = new SearchAdapter(getContext(), searchList);
        searchRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        searchRecyclerView.setLayoutManager(layoutManager);


        String[] unitArray = getResources().getStringArray(R.array.unit);


        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, unitArray);
        unit.setAdapter(unitAdapter);

        unit.setText(unitAdapter.getItem(1), false);


        maxDistance.setText("10");
        searchRadius.setMax(Integer.parseInt(maxDistance.getEditableText().toString()));

        searchRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                distance.setText(String.valueOf(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        distance.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(textView.getEditableText() != null){
                    searchRadius.setProgress(Integer.parseInt(textView.getEditableText().toString()));
                    return true;
                }
                return false;
            }
        });

        maxDistance.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(textView.getEditableText() != null){
                    searchRadius.setMax(Integer.parseInt(textView.getEditableText().toString()));

                    searchRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            distance.setText(String.valueOf(i));

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    return true;
                }
                return false;
            }
        });
        friendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FriendRequestActivity.class);
                startActivity(intent);
            }
        });


        database.getReference().child("Location").orderByKey().equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel = snapshot.getChildren().iterator().next().getValue(LocationModel.class);
                Log.i("Location", String.valueOf(userModel.getUserLatittude()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getUsers();

        return view;



    }

    public void getUsers(){

        DistanceCalculator calculator = new DistanceCalculator();
        ArrayList<String> userIdList = new ArrayList<>();
        ArrayList<String> inRequestList = new ArrayList<>();



        database.getReference().child("Location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    if(!Objects.equals(data.getKey(), FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        model = data.getValue(LocationModel.class);
                        Log.i("Model data ", String.valueOf(model.getUserLongitude()));
                        if(unit.getEditableText().toString().equals("Km")){
                            if(model != null) {
                                double distanceBtwnUsers = calculator.distanceInKm(userModel.getUserLatittude(), userModel.getUserLongitude(), model.getUserLatittude(), model.getUserLongitude());
                                Log.i("Value", String.valueOf(distanceBtwnUsers));

                                if (distanceBtwnUsers <= Double.parseDouble(maxDistance.getEditableText().toString())) {

                                    userIdList.add(data.getKey());
                                    Log.i("Data state: ", "Data Added");


                                }
                            }
                            else{
                                Toast.makeText(getContext(), "No Locations Available", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            LatLng userLatLng = new LatLng(userModel.getUserLatittude(),userModel.getUserLongitude());
                            LatLng modelLatLng = new LatLng(model.getUserLatittude(), model.getUserLongitude());
                            double distance = calculator.greatCircleInMeters(userLatLng, modelLatLng);

                            if(distance <= Double.parseDouble(maxDistance.getEditableText().toString())){
                                userIdList.add(data.getKey());
                            }

                        }
                    }
                }
                database.getReference().child("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()){
                            inRequestList.add(data.getKey());
                            Log.i("Requests", data.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data : snapshot.getChildren()){
                            database.getReference().child("Requests").child(data.getKey()).orderByKey().limitToFirst(1).equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChildren()){
                                        Log.i("User", snapshot.getKey());

                                    }
                                    else{
                                        if(userIdList.contains(data.getKey())){
                                            if (!inRequestList.contains(data.getKey())) {

                                                UserModel model = data.getValue(UserModel.class);
                                                model.setUserID(data.getKey());
                                                searchList.add(model);
                                                Log.i("Destination", "Bottom");
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        adapter.notifyDataSetChanged();
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