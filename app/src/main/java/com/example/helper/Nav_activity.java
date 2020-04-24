package com.example.helper;





import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Nav_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    static Fragment fragment;
    static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);



        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.replace(R.id.Frame_lt,new Problem_frag());
        tran.commit();





        FirebaseApp.initializeApp(this);
        mAuth = mAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.inflateHeaderView(R.layout.nav_header_nav_activity);

        TextView username = header.findViewById(R.id.username);

        username.setText(MainActivity.email.replaceAll("@stu.sdu.edu.kz",""));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_activity, menu);
        return true;
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragment = new Problem_frag();
        if (id == R.id.nav_problem) {
            fragment = new Problem_frag();
        } else if (id == R.id.nav_posts) {
            fragment = new MyPost_frag();
        } else if (id == R.id.nav_chat) {
            fragment = new Chat_frag();
        } else if (id == R.id.nav_survey) {
            fragment = new Survey_frag();
        } else if (id == R.id.nav_exit) {
            mAuth.signOut();
            finish();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.Frame_lt,fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
