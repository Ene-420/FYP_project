package com.example.fypproject.Adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypproject.Model.MessageModel;
import com.example.fypproject.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MessageModel> messageModels;

    public MessageAdapter(Context context, ArrayList<MessageModel> messageModels) {
        this.context = context;
        this.messageModels = messageModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class IncomingViewHolder extends RecyclerView.ViewHolder {


        public IncomingViewHolder(@NonNull View itemView) {
            super(itemView);

            TextView incomingMessage, incomingTimeStamp;

            incomingMessage = itemView.findViewById(R.id.message_incoming_text);
            incomingTimeStamp = itemView.findViewById(R.id.message_incoming_timestamp);
        }
    }

    public class IncomingExtraViewHolder extends RecyclerView.ViewHolder{

        public IncomingExtraViewHolder(@NonNull View itemView) {
            super(itemView);

            TextView incomingExtraMessage, incomingExtraTimeStamp;

            incomingExtraMessage = itemView.findViewById(R.id.message_outgoing_extra_text);
            incomingExtraTimeStamp = itemView.findViewById(R.id.message_outgoing_extra_timestamp);
        }
    }

    public class OutgoingViewHolder extends RecyclerView.ViewHolder{

        public OutgoingViewHolder(@NonNull View itemView) {
            super(itemView);

            TextView outgoingMessage, outgoingTimeStamp;

            outgoingMessage = itemView.findViewById(R.id.message_outgoing_text);
            outgoingTimeStamp = itemView.findViewById(R.id.message_outgoing_timestamp);
        }
    }

    public class OutgoingExtraViewHolder extends RecyclerView.ViewHolder{

        public OutgoingExtraViewHolder(@NonNull View itemView) {
            super(itemView);

            TextView outgoingExtraMessage, outgoingExtraTimeStamp;

            outgoingExtraMessage = itemView.findViewById(R.id.message_outgoing_extra_text);
            outgoingExtraTimeStamp = itemView.findViewById(R.id.message_outgoing_extra_timestamp);
        }
    }
}
