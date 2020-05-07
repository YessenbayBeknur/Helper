package com.example.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PostAdapter extends ArrayAdapter {
    ArrayList<Post> post_adap;
    DatabaseReference mDatabase;
    public PostAdapter(@NonNull Context context, ArrayList<Post> t) {
        super(context, R.layout.list);
        post_adap = t;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list ,parent,false);

        TextView post_text = view.findViewById(R.id.post_text);
        TextView post_date = view.findViewById(R.id.post_date);
        TextView post_title = view.findViewById(R.id.post_title);
        LinearLayout color_type = view.findViewById(R.id.color_type);
        ImageButton post_solve = view.findViewById(R.id.Check_problem);
        ImageButton post_like = view.findViewById(R.id.likes);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        post_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> s = post_adap.get(position).getLikes();
                s.add(Nav_activity.username.getText().toString());
                Post p = new Post(post_adap.get(position).getTitle(),post_adap.get(position).getText(),post_adap.get(position).getDate(),
                        post_adap.get(position).getType(),post_adap.get(position).getColor(),post_adap.get(position).getId(),
                        post_adap.get(position).getWho(),post_adap.get(position).isSolved(),s);
                mDatabase.child("posts").child(post_adap.get(position).getId()).removeValue();
                mDatabase.child("posts").child(post_adap.get(position).getId()).setValue(p);
            }
        });

        for(int i = 0; i < post_adap.get(position).getLikes().size() + 0;i++){
            if(post_adap.get(position).getLikes().get(i).equals(Nav_activity.username.getText().toString())){
                post_like.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        }

        if(post_adap.get(position).getText().length() > 30){
            if(post_adap.get(position).isSolved()){
                post_text.setText(post_adap.get(position).getText().substring(0,30)+"...");
                post_date.setText(post_adap.get(position).getDate());
                post_title.setText(post_adap.get(position).getTitle());
                color_type.setBackgroundColor(post_adap.get(position).getColor());
                post_solve.setImageResource(R.drawable.ic_check_box_black_24dp);
            }else {
                post_text.setText(post_adap.get(position).getText().substring(0,30)+"...");
                post_date.setText(post_adap.get(position).getDate());
                post_title.setText(post_adap.get(position).getTitle());
                color_type.setBackgroundColor(post_adap.get(position).getColor());
                post_solve.setImageResource(R.drawable.ic_check_box_outline_24dp);
            }

        }else {
            if(post_adap.get(position).isSolved()){
                post_text.setText(post_adap.get(position).getText());
                post_date.setText(post_adap.get(position).getDate());
                post_title.setText(post_adap.get(position).getTitle());
                color_type.setBackgroundColor(post_adap.get(position).getColor());
                post_solve.setImageResource(R.drawable.ic_check_box_black_24dp);
            }
            else {
                post_text.setText(post_adap.get(position).getText());
                post_date.setText(post_adap.get(position).getDate());
                post_title.setText(post_adap.get(position).getTitle());
                color_type.setBackgroundColor(post_adap.get(position).getColor());
                post_solve.setImageResource(R.drawable.ic_check_box_outline_24dp);
            }
        }



        return view;
    }

    @Override
    public int getCount() {
        return post_adap.size();
    }
}

