package com.example.fypproject.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.R;
import com.example.fypproject.Model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    Context context;
    ArrayList<UserModel> list;

    public RequestAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewAdapter = LayoutInflater.from(context).inflate(R.layout.user_friend_request, parent, false);

        return new ViewHolder(viewAdapter);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel user = list.get(position);

        //String uni;
        if(user.getUniversity().equals("Nottingham Trent University")){
             //uni = "NTU";
            holder.requestUniversity.setText("NTU");
            user.setUniversity("NTU");
        }
        String[] name = user.getFullName().split("_");

        Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.face).into(holder.requestProfileImg);
        holder.requestUserName.setText(name[0]);
        holder.requestCourseName.setText(user.getCourse());
        holder.requestCourseYr.setText(user.getCourseYr());
        holder.requestHobbies.setText(user.getHobbies().toString());

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot data : snapshot.getChildren()){
                                if(data.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                    UserModel model = data.getValue(UserModel.class);


                                    UserModel modelUser = new UserModel();
                                    modelUser.setFullName(user.getFullName());
                                    modelUser.setCourse(user.getCourse());
                                    modelUser.setCourseYr(user.getCourseYr());
                                    modelUser.setDob(user.getDob());
                                    modelUser.setHobbies(user.getHobbies());
                                    modelUser.setUniversity(user.getUniversity());
                                    modelUser.setUserName(user.getUserName());
                                    modelUser.setProfileImage(user.getProfileImage());

                                    FirebaseDatabase.getInstance().getReference().child("Contacts").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child(user.getUserID()).setValue(modelUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    FirebaseDatabase.getInstance().getReference().child("Contacts").child(user.getUserID()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    FirebaseDatabase.getInstance().getReference().child("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {
                                                                            list.remove(holder.getAdapterPosition());
                                                                            notifyItemRemoved(holder.getAdapterPosition());
                                                                            notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
                                                                        }
                                                                    });

                                                                }
                                                            });


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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View pView = inflater.inflate(R.layout.pop_up_user_profile, null);

                PopupWindow popUp = new PopupWindow(pView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                CircleImageView imageView = pView.findViewById(R.id.pop_up_profile_image);
                TextView popUsername = pView.findViewById(R.id.pop_up_username);
                TextView popUniversity = pView.findViewById(R.id.pop_up_university);
                TextView popCourse = pView.findViewById(R.id.pop_up_coursename);
                TextView popCourseYr = pView.findViewById(R.id.pop_up_courseYr);
                TextView popHobbies = pView.findViewById(R.id.pop_up_hobbies);
                ImageButton popClose = pView.findViewById(R.id.pop_up_close);

                Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.face).into(imageView);
                popUsername.setText(user.getUserName());
                popCourse.setText(user.getCourse());
                popUniversity.setText(user.getUniversity());
                popCourseYr.setText(user.getCourseYr());
                popHobbies.setText(user.getHobbies().toString());

                popClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popUp.dismiss();
                    }
                });


                popUp.showAtLocation(view, Gravity.CLIP_HORIZONTAL,0,0);

                /*pView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popUp.dismiss();
                        return false;
                    }
                });*/


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView requestProfileImg;
        TextView requestUserName, requestUniversity, requestCourseName, requestCourseYr, requestHobbies;
        Button addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            requestProfileImg = itemView.findViewById(R.id.user_request_profile_image);
            requestUserName = itemView.findViewById(R.id.user_request_usernameId);
            requestUniversity = itemView.findViewById(R.id.user_request_uniName);
            requestCourseName = itemView.findViewById(R.id.user_request_courseNameId);
            requestCourseYr = itemView.findViewById(R.id.user_request_courseyr);
            requestHobbies = itemView.findViewById(R.id.user_request_hobbies);
            addBtn = itemView.findViewById(R.id.user_request_btn);

        }
    }
}
