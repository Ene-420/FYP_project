package com.example.fypproject.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.Model.MessageModel;
import com.example.fypproject.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MessageModel> messageModels;

    final int INCOMING_VIEW_TYPE = 1;
    final int OUTGOING_VIEW_TYPE = 2;

    int incomingCount = 0;
    int outgoingCount= 0;

    public MessageAdapter(Context context, ArrayList<MessageModel> messageModels) {
        this.context = context;
        this.messageModels = messageModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == OUTGOING_VIEW_TYPE){
            View outView = LayoutInflater.from(context).inflate(R.layout.message_outgoing, parent, false);

            return new OutgoingViewHolder(outView);

/*            if(outgoingCount > 1) {
                View outExtraView = LayoutInflater.from(context).inflate(R.layout.message_outgoing_extra,parent, false);
                return new OutgoingExtraViewHolder(outExtraView);
            }
            else{
                View outView = LayoutInflater.from(context).inflate(R.layout.message_outgoing, parent, false);

                return new OutgoingViewHolder(outView);
            }*/
        }

        else {
            View incomingView = LayoutInflater.from(context).inflate(R.layout.message_incoming, parent, false);

            return new IncomingViewHolder(incomingView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel model =  messageModels.get(position);

        if(holder.getClass() == OutgoingViewHolder.class){

            ((OutgoingViewHolder)holder).outgoingMessage.setText(model.getMessage());
            ((OutgoingViewHolder)holder).outgoingTimeStamp.setText(model.setTimeStamp(model.getTimeStamp()));

        }
        /*else if (holder.getClass() == OutgoingExtraViewHolder.class) {
            ((OutgoingExtraViewHolder)holder).outgoingExtraMessage.setText(model.getMessage());
            ((OutgoingExtraViewHolder)holder).outgoingExtraTimeStamp.setText(model.getTimeStamp());

        }*/
        else if (holder.getClass() == IncomingViewHolder.class) {
            ((IncomingViewHolder)holder).incomingMessage.setText(model.getMessage());
            ((IncomingViewHolder)holder).incomingTimeStamp.setText(model.setTimeStamp(model.getTimeStamp()));

        }
        /*else if (holder.getClass() == IncomingExtraViewHolder.class) {
            ((IncomingExtraViewHolder)holder).incomingExtraMessage.setText(model.getMessage());
            ((IncomingExtraViewHolder)holder).incomingExtraTimeStamp.setText(model.getTimeStamp());
        }*/



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("holder", holder.getClass().toString());

                if(holder.getClass() == IncomingViewHolder.class){
                    //((OutgoingViewHolder)holder).outgoingTimeStamp.setVisibility(View.GONE);
                    ((IncomingViewHolder)holder).incomingTimeStamp.setVisibility(View.VISIBLE);
                }
                else if(holder.getClass() == OutgoingViewHolder.class){
                    //((IncomingViewHolder)holder).incomingTimeStamp.setVisibility(View.GONE);
                    ((OutgoingViewHolder)holder).outgoingTimeStamp.setVisibility(View.VISIBLE);
                }


            }
        });


    }

    @Override
    public int getItemViewType(int position) {

        if(messageModels.get(position).getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            outgoingCount++;
            incomingCount =0;
            return OUTGOING_VIEW_TYPE;

        }
        else{
            incomingCount++;
            outgoingCount=0;
            return INCOMING_VIEW_TYPE;

        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class IncomingViewHolder extends RecyclerView.ViewHolder {

        TextView incomingMessage, incomingTimeStamp;

        public IncomingViewHolder(@NonNull View itemView) {
            super(itemView);



            incomingMessage = itemView.findViewById(R.id.message_incoming_text);
            incomingTimeStamp = itemView.findViewById(R.id.message_incoming_timestamp);

            incomingTimeStamp.setVisibility(View.GONE);
        }
    }

    public class IncomingExtraViewHolder extends RecyclerView.ViewHolder{
        TextView incomingExtraMessage, incomingExtraTimeStamp;

        public IncomingExtraViewHolder(@NonNull View itemView) {
            super(itemView);


            incomingExtraMessage = itemView.findViewById(R.id.message_outgoing_extra_text);
            incomingExtraTimeStamp = itemView.findViewById(R.id.message_outgoing_extra_timestamp);

            incomingExtraTimeStamp.setVisibility(View.GONE);
        }
    }

    public class OutgoingViewHolder extends RecyclerView.ViewHolder{

        TextView outgoingMessage, outgoingTimeStamp;
        public OutgoingViewHolder(@NonNull View itemView) {
            super(itemView);



            outgoingMessage = itemView.findViewById(R.id.message_outgoing_text);
            outgoingTimeStamp = itemView.findViewById(R.id.message_outgoing_timestamp);

            outgoingTimeStamp.setVisibility(View.GONE);
        }
    }

    public class OutgoingExtraViewHolder extends RecyclerView.ViewHolder{
        TextView outgoingExtraMessage, outgoingExtraTimeStamp;

        public OutgoingExtraViewHolder(@NonNull View itemView) {
            super(itemView);



            outgoingExtraMessage = itemView.findViewById(R.id.message_outgoing_extra_text);
            outgoingExtraTimeStamp = itemView.findViewById(R.id.message_outgoing_extra_timestamp);

            outgoingExtraTimeStamp.setVisibility(View.GONE);
        }
    }
}
