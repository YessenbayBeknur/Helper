package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PostAdapter extends ArrayAdapter {
    ArrayList<Post> post_adap;
    public PostAdapter(@NonNull Context context, ArrayList<Post> t) {
        super(context, R.layout.list);
        post_adap = t;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list ,parent,false);

        TextView post_text = view.findViewById(R.id.post_text);
        TextView post_date = view.findViewById(R.id.post_date);
        TextView post_title = view.findViewById(R.id.post_title);
        LinearLayout color_type = view.findViewById(R.id.color_type);

        if(post_adap.get(position).getText().length() > 37){
            post_text.setText(post_adap.get(position).getText().substring(0,37)+"...");
            post_date.setText(post_adap.get(position).getDate());
            post_title.setText(post_adap.get(position).getTitle());
            color_type.setBackgroundColor(post_adap.get(position).getColor());

        }else {
            post_text.setText(post_adap.get(position).getText());
            post_date.setText(post_adap.get(position).getDate());
            post_title.setText(post_adap.get(position).getTitle());
            color_type.setBackgroundColor(post_adap.get(position).getColor());
        }



        return view;
    }

    @Override
    public int getCount() {
        return post_adap.size();
    }
}

