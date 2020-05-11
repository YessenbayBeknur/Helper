package com.example.helper;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Photo_full extends Fragment {
    View view;
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.photo_full,container,false);

        ImageView im = view.findViewById(R.id.photo_frag);
        im.setImageBitmap(Full_post.current_bitmap);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Fragment fragment = new Full_post();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Frame_lt,fragment).commit();
                    return true;
                }
                return false;
            }
        });

        return view;
    }


}
