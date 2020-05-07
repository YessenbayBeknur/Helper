package com.example.helper;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class Problem_frag extends Fragment {
    View view;
    private DatabaseReference mDatabase;
    PostAdapter postAdapter;
    ArrayList<Post> posts;
    ArrayList<Post> sort_posts;
    static int last_id;
    static Post checked_post;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.problem_frag_lay,container,false);

        final TextView frag_title = Nav_activity.toolbar.findViewById(R.id.frag_title);
        frag_title.setText("Problems");

        Button add_button = view.findViewById(R.id.add_prob_post);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Add_post_frag();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Frame_lt,fragment).commit();
            }
        });



        final ChipGroup choiceChipGroup = view.findViewById(R.id.Chip_group);

        final Chip all_chip = choiceChipGroup.findViewById(R.id.all_chip);
        all_chip.setChecked(true);

        final Chip portal_chip = choiceChipGroup.findViewById(R.id.portal_chip);
        final Chip ashana_chip = choiceChipGroup.findViewById(R.id.ashana_chip);
        final Chip university_chip = choiceChipGroup.findViewById(R.id.university_chip);
        final Chip club_chip = choiceChipGroup.findViewById(R.id.club_chip);

        last_id = choiceChipGroup.getCheckedChipId();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        posts = new ArrayList<>();
        sort_posts = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(),posts);



        ListView lv = view.findViewById(R.id.problem_list);
        lv.setAdapter(postAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checked_post = posts.get(i);
                Fragment fragment = new Full_post();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Frame_lt,fragment).commit();
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
                Collections.reverse(posts);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("$$$$$$", "loadPost:onCancelled", databaseError.toException());
            }
        });



        choiceChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(ChipGroup group,@IdRes int checkedId) {
                    if(checkedId != last_id && checkedId != -1){
                        posts.clear();
                        Chip c_chip;
                        if(checkedId != all_chip.getId()) {
                            c_chip = choiceChipGroup.findViewById(checkedId);
                            for(int i = 0;i<sort_posts.size();i++){
                                if(c_chip.getText().toString().equals(sort_posts.get(i).getType().toString())){
                                    posts.add(sort_posts.get(i));
                                }
                            }
                            Collections.reverse(posts);
                            postAdapter.notifyDataSetChanged();
                        }else {
                            for(int i = 0;i<sort_posts.size();i++){
                                    posts.add(sort_posts.get(i));
                                }
                            Collections.reverse(posts);
                            postAdapter.notifyDataSetChanged();
                        }

                        if(checkedId == all_chip.getId()) {
                            Chip last_chip = group.findViewById(last_id);
                            last_chip.setChipBackgroundColorResource(R.color.tran);
                            last_chip.setTextColor(getResources().getColor(R.color.Grey));
                            Chip current_chip = group.findViewById(checkedId);
                            current_chip.setChipBackgroundColorResource(R.color.Glavni);
                            current_chip.setTextColor(getResources().getColor(R.color.White));

                            if (last_id == portal_chip.getId()) {
                                choiceChipGroup.setPaddingRelative(choiceChipGroup.getPaddingStart() + 240, 0, 0, 0);
                            }
                        }
                        else if(checkedId == portal_chip.getId()) {
                            Chip last_chip = group.findViewById(last_id);
                            last_chip.setChipBackgroundColorResource(R.color.tran);
                            last_chip.setTextColor(getResources().getColor(R.color.Grey));
                            Chip current_chip = group.findViewById(checkedId);
                            current_chip.setChipBackgroundColorResource(R.color.Red_Portal);
                            current_chip.setTextColor(getResources().getColor(R.color.White));



                            if (last_id == all_chip.getId()) {
                                choiceChipGroup.setPaddingRelative(choiceChipGroup.getPaddingStart() - 240, 0, 0, 0);
                            } else {
                                choiceChipGroup.setPaddingRelative(choiceChipGroup.getPaddingStart() + 240, 0, 0, 0);
                            }
                        }
                        else if(checkedId == ashana_chip.getId()) {
                            Chip last_chip = group.findViewById(last_id);
                            last_chip.setChipBackgroundColorResource(R.color.tran);
                            last_chip.setTextColor(getResources().getColor(R.color.Grey));
                            Chip current_chip = group.findViewById(checkedId);
                            current_chip.setChipBackgroundColorResource(R.color.Yellow_Ashana);
                            current_chip.setTextColor(getResources().getColor(R.color.White));

                            if (last_id == portal_chip.getId()) {
                                choiceChipGroup.setPaddingRelative(choiceChipGroup.getPaddingStart() - 240, 0, 0, 0);
                            } else {
                                choiceChipGroup.setPaddingRelative(choiceChipGroup.getPaddingStart() + 240, 0, 0, 0);
                            }
                        }
                        else if(checkedId == university_chip.getId()) {
                            Chip last_chip = group.findViewById(last_id);
                            last_chip.setChipBackgroundColorResource(R.color.tran);
                            last_chip.setTextColor(getResources().getColor(R.color.Grey));
                            Chip current_chip = group.findViewById(checkedId);
                            current_chip.setChipBackgroundColorResource(R.color.Green_Univer);
                            current_chip.setTextColor(getResources().getColor(R.color.White));

                            if (last_id == ashana_chip.getId()) {
                                choiceChipGroup.setPaddingRelative(choiceChipGroup.getPaddingStart() - 240, 0, 0, 0);
                            } else {
                                choiceChipGroup.setPaddingRelative(choiceChipGroup.getPaddingStart() + 240, 0, 0, 0);
                            }
                        }
                        else if(checkedId == club_chip.getId()) {
                            Chip last_chip = group.findViewById(last_id);
                            last_chip.setChipBackgroundColorResource(R.color.tran);
                            last_chip.setTextColor(getResources().getColor(R.color.Grey));
                            Chip current_chip = group.findViewById(checkedId);
                            current_chip.setChipBackgroundColorResource(R.color.Black_Club);
                            current_chip.setTextColor(getResources().getColor(R.color.White));

                            if (last_id == university_chip.getId()) {
                                choiceChipGroup.setPaddingRelative(choiceChipGroup.getPaddingStart() - 240, 0, 0, 0);
                            }
                        }
                        last_id = checkedId;
                    }


            }
        });

        return view;
    }

}
