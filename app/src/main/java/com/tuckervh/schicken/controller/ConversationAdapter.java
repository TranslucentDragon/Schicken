package com.tuckervh.schicken.controller;

/**
 * Created by will on 1/28/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuckervh.schicken.R;
import com.tuckervh.schicken.model.Conversation;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {

    private List<Conversation> conversationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView initials, name, messageText;

        public MyViewHolder(View view) {
            super(view);
            initials = (TextView) view.findViewById(R.id.list_initials);
            messageText = (TextView) view.findViewById(R.id.message_text_list);
            name = (TextView) view.findViewById(R.id.name_list);
        }
    }


    public ConversationAdapter(List<Conversation> conversationList) {
        this.conversationList = conversationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_list_row, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Conversation converation = conversationList.get(position);
        holder.name.setText(converation.getName());
        holder.initials.setText(converation.getInitials());
        holder.messageText.setText(converation.getMostRecent().getText());
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }
}