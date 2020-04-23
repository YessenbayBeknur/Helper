package com.example.helper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText stud_id,password;
    Button sign_in;
    static FirebaseAuth mAuth;
    static String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stud_id = findViewById(R.id.student_id);
        password = findViewById(R.id.student_password);
        sign_in = findViewById(R.id.sign_in);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();




        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!stud_id.getText().toString().equals("") && !password.getText().toString().equals("")){
                callLogin(stud_id.getText().toString().trim(),password.getText().toString());
                }
                else
                Toast.makeText(MainActivity.this, "Login or password pustoi",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            email = user.getEmail();
            Intent intent = new Intent(getApplicationContext(), Nav_activity.class);
            startActivity(intent);
        }
    }




    public void callLogin(String stud_id, String password){
        mAuth.signInWithEmailAndPassword(stud_id, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), Nav_activity.class);
                            startActivity(intent);

                        } else {

                            Log.w("$$$$$", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}
