package com.example.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ChatAdapter extends ArrayAdapter {
    ArrayList<Chat> chat_adap;
    DatabaseReference mDatabase;

    public ChatAdapter(@NonNull Context context, ArrayList<Chat> chats) {
        super(context, R.layout.list_chat);
        chat_adap = chats;
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_chat ,parent,false);

        TextView chat_text = view.findViewById(R.id.chat_text);
        final ImageView image_chat = view.findViewById(R.id.image_chat);

        TextView chat_text2 = view.findViewById(R.id.chat_text2);
        final ImageView image_chat2 = view.findViewById(R.id.image_chat2);

        mDatabase = FirebaseDatabase.getInstance().getReference();




        if(chat_adap.get(position).getWho().equals(Nav_activity.username.getText().toString())){
            mDatabase.child("users").child(Nav_activity.username.getText().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String imageUrl = dataSnapshot.child("url").getValue(String.class);
                    Picasso.get().load(imageUrl).transform(new CircleTransform()).resize(50,60).centerCrop().into(image_chat2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            RelativeLayout rel = view.findViewById(R.id.rel_lay);
            rel.setVisibility(View.INVISIBLE);

            chat_text2.setText(chat_adap.get(position).getText_chat());

        }else {

            mDatabase.child("users").child(chat_adap.get(position).getWho()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String imageUrl = dataSnapshot.child("url").getValue(String.class);
                    Picasso.get().load(imageUrl).transform(new CircleTransform()).resize(50,60).centerCrop().into(image_chat);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            RelativeLayout rel = view.findViewById(R.id.rel_lay2);
            rel.setVisibility(View.INVISIBLE);

            chat_text.setText(chat_adap.get(position).getText_chat());


        }


        return view;


    }

    @Override
    public int getCount() {
        return chat_adap.size();
    }
}

