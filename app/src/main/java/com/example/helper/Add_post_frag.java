package com.example.helper;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class Add_post_frag extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    String[] types = { "Portal", "Ashana", "Univer", "Club", "LoseFind"};
    static int cur_pos;
    private DatabaseReference mDatabase;
    static int color;
    public static final int PICK_IMAGE = 1;
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
    private Uri filePath;
    static ImageButton but1;
    static ImageButton but2;
    static ImageButton but3;
    static ImageButton curr_but;
    static Bitmap bitmap;
    ArrayList<String> bitmaps = new ArrayList<>();
    static String imageB64;


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

        Nav_activity.button_tool.setVisibility(View.INVISIBLE);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        but1 = view.findViewById(R.id.photo1);
        but2 = view.findViewById(R.id.photo2);
        but3 = view.findViewById(R.id.photo3);


        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr_but = but1;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> like = new ArrayList<>();
                like.add(" ");
                String hexColor = "#" + Integer.toHexString(color).substring(2);
                String key = mDatabase.child("posts").push().getKey();
                String id = key;
                mDatabase.child("posts").child(id).setValue(new Post(post_title.getText().toString(),
                        post_content.getText().toString(),date,types[cur_pos],hexColor, id,Nav_activity.username.getText().toString().trim(),
                        false,like,bitmaps));
                Fragment fragment = new Problem_frag();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Frame_lt,fragment).commit();

            }
        });

        ArrayAdapter aa = new ArrayAdapter(getContext(),R.layout.spinner_item,types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_type.setAdapter(aa);

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Context applicationContext = getActivity().getApplicationContext();


                bitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), filePath);
                curr_but.setImageBitmap(bitmap);

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] byteArray = bao.toByteArray();
                imageB64 = Base64.encodeToString(byteArray, Base64.URL_SAFE);
                bitmaps.add(imageB64);


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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
        else if(types[i].equals("LoseFind")){
            color = getResources().getColor(R.color.Glavni);
            t.setTextColor(color);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
