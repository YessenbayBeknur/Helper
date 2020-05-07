package com.example.helper;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class Full_post extends Fragment {
    View view;

    private DatabaseReference mDatabase;
    CommentAdapter commentAdapter;
    ArrayList<Comment> comments;
    DateTimeFormatter dtf;
    LocalDate localDate;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.full_post_lay,container,false);

        TextView full_date = view.findViewById(R.id.full_date);
        full_date.setText(Problem_frag.checked_post.getDate());

        TextView full_type = view.findViewById(R.id.full_type);
        full_type.setText(Problem_frag.checked_post.getType());

        ImageView circle_type = view.findViewById(R.id.circle_im);

        GradientDrawable gr = (GradientDrawable) circle_type.getBackground();
        gr.setColor(Problem_frag.checked_post.getColor());

        TextView full_title = view.findViewById(R.id.full_title);
        full_title.setText(Problem_frag.checked_post.getTitle());

        TextView full_content = view.findViewById(R.id.full_content);
        full_content.setText(Problem_frag.checked_post.getText());

        RelativeLayout r = view.findViewById(R.id.rel_lay);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(getContext(),comments);

        final ListView lv = view.findViewById(R.id.full_comment);
        lv.setAdapter(commentAdapter);
        setListViewHeightBasedOnItems(lv);


        final EditText send_text = view.findViewById(R.id.comment_send);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();
        final String date = df.format(dateobj);

        final TextView comment_count = view.findViewById(R.id.comment_count);
        final TextView comment_like_count = view.findViewById(R.id.like_count);

        LinkedHashSet<String> hashSet = new LinkedHashSet<>(Problem_frag.checked_post.getLikes());
        ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);

        ImageButton like_full = view.findViewById(R.id.full_like);
        for(int i = 0; i < listWithoutDuplicates.size(); i++){
            if(listWithoutDuplicates.get(i).equals(Nav_activity.username.getText().toString())){
                like_full.setImageResource(R.drawable.favorite_full);
            }
        }

        ImageView solve_full = view.findViewById(R.id.Solve_prob_full);
        if(Problem_frag.checked_post.isSolved()){
            solve_full.setImageResource(R.drawable.ic_check_box_black_24dp);
        }


        final Button send_comment = view.findViewById(R.id.send_comm);
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = mDatabase.child("comments").child(Problem_frag.checked_post.getId()).push().getKey();
                mDatabase.child("comments").child(Problem_frag.checked_post.getId()).child(key).setValue(new Comment(Nav_activity.username.getText().toString(),send_text.getText().toString(),date));
                send_text.setText("");
            }
        });


        mDatabase.child("comments").child(Problem_frag.checked_post.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    comments.add(d.getValue(Comment.class));
                }
                LinkedHashSet<String> hashSet = new LinkedHashSet<>(Problem_frag.checked_post.getLikes());
                ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
                comment_count.setText(comments.size() + " " + comment_count.getText().toString().split(" ")[1]);
                comment_like_count.setText(listWithoutDuplicates.size()-1 + " " +  comment_like_count.getText().toString().split(" ")[1]);
                Collections.reverse(comments);
                commentAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnItems(lv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("$$$$$$", "loadPost:onCancelled", databaseError.toException());
            }
        });


        return view;
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
}
