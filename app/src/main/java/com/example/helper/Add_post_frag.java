package com.example.helper;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class Add_post_frag extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    String[] types = { "Portal", "Ashana", "Univer", "Club", "Other"};
    static int cur_pos;
    private DatabaseReference mDatabase;
    DateTimeFormatter dtf;
    LocalDate localDate;
    static int color;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_frag_lay,container,false);

        TextView frag_title = Nav_activity.toolbar.findViewById(R.id.frag_title);
        frag_title.setText("Post");

        final EditText post_title = view.findViewById(R.id.add_title_post);
        final EditText post_content = view.findViewById(R.id.add_content_post);
        Spinner spin_type = (Spinner) view.findViewById(R.id.Spinner_type);
        Button add_button = view.findViewById(R.id.post_add_for_database);

        spin_type.setOnItemSelectedListener(this);


        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        Date dateobj = new Date();
        final String date = df.format(dateobj);


        int random = (int)(Math.random() * 999999 + 100000);
        final String id = Integer.toString(random);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> like = new ArrayList<>();
                like.add(" ");
                mDatabase.child("posts").child(id).setValue(new Post(post_title.getText().toString(),
                        post_content.getText().toString(),date,types[cur_pos],color,id,Nav_activity.username.getText().toString().trim(),false,like));
                Fragment fragment = new Problem_frag();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Frame_lt,fragment).commit();

            }
        });

        ArrayAdapter aa = new ArrayAdapter(getContext(),R.layout.spinner_item,types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_type.setAdapter(aa);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
        TextView t = v.findViewById(R.id.spin_text);
        cur_pos = i;
        color = getResources().getColor(R.color.Red_Portal);
        t.setTextColor(color);
        if(types[i].equals("Ashana")){
            color = getResources().getColor(R.color.Yellow_Ashana);
            t.setTextColor(color);
        }
        else if(types[i].equals("Univer")){
            color = getResources().getColor(R.color.Green_Univer);
            t.setTextColor(color);
        }
        else if(types[i].equals("Club")) {
            color = getResources().getColor(R.color.Black_Club);
            t.setTextColor(color);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
