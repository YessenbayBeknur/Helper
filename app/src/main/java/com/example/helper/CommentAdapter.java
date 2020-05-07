package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CommentAdapter extends ArrayAdapter {
    ArrayList<Comment> com_adap;

    public CommentAdapter(@NonNull Context context, ArrayList<Comment> t) {
        super(context, R.layout.list_comment);
        com_adap = t;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_comment ,parent,false);

        TextView comment_username = view.findViewById(R.id.comment_username);
        TextView comment_content = view.findViewById(R.id.comment_content);
        TextView comment_date = view.findViewById(R.id.comment_date);

        comment_username.setText(com_adap.get(position).getUsername());
        comment_content.setText(com_adap.get(position).getContent());
        comment_date.setText(com_adap.get(position).getDate());

        return view;


    }

    @Override
    public int getCount() {
        return com_adap.size();
    }
}
