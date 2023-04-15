package com.example.fypproject.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fypproject.Adapter.RequestAdapter;
import com.example.fypproject.FriendRequestActivity;
import com.example.fypproject.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;


public class SearchFragment extends Fragment {

    TextView userRequestCount, friendRequest;
    SeekBar searchRadius;
    RecyclerView searchResultView;
    RequestAdapter adapter;
    AutoCompleteTextView  unit;
    TextInputEditText maxDistance, distance;
    FirebaseDatabase database;

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

        String[] unitArray = getResources().getStringArray(R.array.unit);


        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, unitArray);
        unit.setAdapter(unitAdapter);


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
                //keyEvent.getAction() == MO
                if(textView.getEditableText() != null){
                    searchRadius.setMax(Integer.parseInt(textView.getEditableText().toString()));
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

        return view;



    }
}