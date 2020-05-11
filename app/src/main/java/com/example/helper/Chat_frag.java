package com.example.helper;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Chat_frag extends Fragment {
    View view;
    ChatAdapter chatAdapter;
    ArrayList<Chat> chats;
    DatabaseReference mDatabase;

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_frag_lay,container,false);

        Nav_activity.button_tool.setVisibility(View.INVISIBLE);

        TextView frag_title = Nav_activity.toolbar.findViewById(R.id.frag_title);
        frag_title.setText("Chat");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        chats = new ArrayList<>();
        chatAdapter = new ChatAdapter(getContext(),chats);

        final ListView lv = view.findViewById(R.id.Chat_list_view);
        lv.setAdapter(chatAdapter);


        final EditText text_c = view.findViewById(R.id.chat_send_text);
        Button send = view.findViewById(R.id.send_chat);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = mDatabase.push().getKey();
                mDatabase.child("chat").child(key).setValue(new Chat(Nav_activity.username.getText().toString(),text_c.getText().toString()));
                text_c.setText("");
                Collections.reverse(chats);
                chatAdapter.notifyDataSetChanged();
                lv.setSelection(chatAdapter.getCount() - 1);
            }
        });

        mDatabase.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    chats.add(d.getValue(Chat.class));
                }
                chatAdapter.notifyDataSetChanged();
                lv.setSelection(chatAdapter.getCount() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("$$$$$$", "loadPost:onCancelled", databaseError.toException());
            }
        });

        return view;
    }
}
