package com.example.fypproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.Model.UserModel;
import com.example.fypproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserModel> list;

    public SearchAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sView = LayoutInflater.from(context).inflate(R.layout.user_search_contact_sample, parent, false);

        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel model = list.get(position);

        if(model.getUniversity().equals("Nottingham Trent University")){
            model.setUniversity("NTU");
        }
        Picasso.get().load(model.getProfileImage()).placeholder(R.drawable.face).into(holder.profile_img);
        holder.username.setText(model.getUserName());
        holder.university.setText(model.getUniversity());
        holder.courseYr.setText(model.getCourseYr());
        holder.hobbies.setText(model.getHobbies().toString());
        holder.courseName.setText(model.getCourse());


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data : snapshot.getChildren()){
                            if (data.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                UserModel user = data.getValue(UserModel.class);
                                String userId = data.getKey();
                                Log.i("Userid: ", model.getUserID());

                                FirebaseDatabase.getInstance().getReference().child("Requests").child(model.getUserID()).child(userId).setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                list.remove(holder.getAdapterPosition());
                                                notifyItemRemoved(holder.getAdapterPosition());
                                                notifyItemRangeChanged(holder.getAdapterPosition(), list.size());

                                            }
                                        });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_img;
        TextView username, courseYr, university, hobbies, courseName;
        ImageButton addBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_img = itemView.findViewById(R.id.search_profile_image);
            username = itemView.findViewById(R.id.search_usernameId);
            courseYr = itemView.findViewById(R.id.search_courseYr);
            university = itemView.findViewById(R.id.search_university);
            addBtn = itemView.findViewById(R.id.search_addUser);
            hobbies = itemView.findViewById(R.id.search_hobbies);
            courseName = itemView.findViewById(R.id.search_courseNameId);

        }
    }
}
