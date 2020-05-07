package com.example.helper;

import android.app.Dialog;
import android.os.Bundle;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MyPost_frag extends Fragment {
    View view;
    DatabaseReference mDatabase;
    ArrayList<Post> posts;
    ArrayList<Post> sort_posts;
    PostAdapter postAdapter;
    Dialog dialog;

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mypost_frag_lay,container,false);

        final TextView frag_title = Nav_activity.toolbar.findViewById(R.id.frag_title);
        frag_title.setText("My posts");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        posts = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(),posts);



        ListView lv = view.findViewById(R.id.problem_list_mypost);
        lv.setAdapter(postAdapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int i, long l) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_mypost);
                dialog.getWindow().setBackgroundDrawableResource(R.color.tran);
                dialog.getWindow().setLayout(500,400);
                dialog.show();
                final int check_item_pos = i;

                Button button_delete = dialog.findViewById(R.id.delete_button_dialog);
                Button button_solved = dialog.findViewById(R.id.solved_button_dialog);

                button_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child("posts").child(posts.get(check_item_pos).getId()).removeValue();
                        dialog.dismiss();
                    }
                });

                button_solved.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child("posts").child(posts.get(check_item_pos).getId()).removeValue();
                        posts.get(check_item_pos).setSolved(true);
                        mDatabase.child("posts").child(posts.get(check_item_pos).getId()).setValue(posts.get(check_item_pos));
                        dialog.dismiss();
                    }
                });
                postAdapter.notifyDataSetChanged();
                return true;
            }
        });

        mDatabase.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    posts.add(d.getValue(Post.class));
                }
                sort_posts = (ArrayList<Post>) posts.clone();
                posts.clear();
                for(int i = 0; i < sort_posts.size(); i++){
                    if(sort_posts.get(i).getWho().equals(Nav_activity.username.getText().toString().trim())){
                        posts.add(sort_posts.get(i));
                    }
                }
                Collections.reverse(posts);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("$$$$$$", "loadPost:onCancelled", databaseError.toException());
            }
        });

        return view;
    }
}
