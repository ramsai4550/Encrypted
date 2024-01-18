package com.p1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class reg extends AppCompatActivity {
    private Button regs;
    private EditText pass1;
    private EditText name;
    private EditText pass2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        //firebaseDatabase = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users");
        regs=findViewById(R.id.activity_register_Button);
        name=findViewById(R.id.activity_usernameEditText);
        pass1=findViewById(R.id.passwordEditText);
        pass2=findViewById(R.id.activity_confirm_passwordEditText);
        final String[] token =new String[1];
        token[0] = "";
        regs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass1.getText().toString().equals(pass2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "password match", Toast.LENGTH_SHORT).show();
                    String uname = name.getText().toString();
                    String pas = pass1.getText().toString();
                    String cpas = pass2.getText().toString();
                    ref.child(uname).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful()){
                                if(task.getResult().exists()){
                                Toast.makeText(reg.this, "user exists", Toast.LENGTH_SHORT).show();
                                    //setContentView(R.layout.activity_main);
                                }
                                else{
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("U_name", uname);
                                    hashMap.put("password", pas);
                                    hashMap.put("Token",token[0]);
                                    ref.child(uname).setValue(hashMap);
                                    Toast.makeText(reg.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                                    //setContentView(R.layout.activity_main);
                                }
                            }
                        }
                    });



                }
        else{
                        Toast.makeText(reg.this, "password didn't matched", Toast.LENGTH_SHORT).show();
                    /*HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("U_name", uname);
                    hashMap.put("password", pas);
                    ref.child("users").child(uname).setValue(hashMap)*/
                    }
            }
        });

        }
        }