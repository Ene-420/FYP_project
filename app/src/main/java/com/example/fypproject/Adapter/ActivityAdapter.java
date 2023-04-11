package com.example.fypproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.Model.MessageModel;
import com.example.fypproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    Context context;
    ArrayList<MessageModel> list;
    @NonNull
    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vAdapter = LayoutInflater.from(context).inflate(R.layout.user_feed_sample, parent, false);
        return new ViewHolder(vAdapter);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ViewHolder holder, int position) {
        MessageModel model = list.get(position);

        //holder.username.setText(model.);
        //Picasso.get().load().placeholder(R.drawable.face).into(holder.profile_img);
        holder.message.setText(model.getMessage());
        holder.timestamp.setText(model.getTimeStamp());

        holder.addReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(context, holder.addReaction);
                menu.getMenuInflater().inflate(R.menu.reaction_menu, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch(menuItem.getItemId()){
                            case R.id.laughing_reaction:
                                holder.addReaction.setImageResource(R.drawable.laughing);
                                break;
                            case R.id.love_reaction:
                                holder.addReaction.setImageResource(R.drawable.love);
                                break;

                            case R.id.cool_reaction:
                                holder.addReaction.setImageResource(R.drawable.cool);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
            }
        });


        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(context, holder.menu);
                menu.getMenuInflater().inflate(R.menu.f_menu_icon, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.feed_menu_delete:
                                break;
                            case R.id.feed_menu_likes:
                                break;
                            case R.id.feed_menu_reactions:
                                break;
                        }
                        return false;
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

        ImageView profile_img, thumbs_up, addReaction,  menu;
        TextView username, message, timestamp;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            username = itemView.findViewById(R.id.feed_username);
            message = itemView.findViewById(R.id.feed_message);
            timestamp = itemView.findViewById(R.id.feed_time);
            profile_img = itemView.findViewById(R.id.feed_profile_img);
            thumbs_up = itemView.findViewById(R.id.feed_like);
            addReaction = itemView.findViewById(R.id.feed_reaction);
            menu = itemView.findViewById(R.id.feed_menu);
        }
    }
}
