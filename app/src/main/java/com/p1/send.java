package com.p1;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class send extends AppCompatActivity {
    private EditText mtitle,mmessage1;
    private Button sendmsg;
    String title;
    DatabaseReference db;
    int k=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        //mtitle=findViewById(R.id.);
        mmessage1=findViewById(R.id.msg_txt);
        sendmsg=findViewById(R.id.send_msg);
        db= FirebaseDatabase.getInstance().getReference("Users");
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String title=mtitle.getText().toString().trim();
                 title ="Hello";
                String message=mmessage1.getText().toString().trim();
                List<String> nodenames = new ArrayList<>();
                Intent intent = getIntent();
                String regds = MainActivity.getText();
                //Toast.makeText(send.this,""+regds, Toast.LENGTH_SHORT).show();
                DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("msg");
                HashMap<String, String> userMap = new HashMap<>();
                //userMap.put("Name", regds);
                userMap.put("message", message);
                dbref.child(regds).setValue(userMap);
                //Toast.makeText(send.this,"welcomeee", Toast.LENGTH_SHORT).show();
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            String t = userSnapshot.child("Token").getValue().toString();
                            Toast.makeText(send.this,t, Toast.LENGTH_SHORT).show();

                            if (t.isEmpty()) {
                                continue;
                            } else {
                                if (!title.equals("") && !message.equals("")) {



                        /*int n;
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            // Get details from the snapshot
                            String key = snapshot1.getKey().toString();
                            String value = snapshot1.getValue(String.class).toString();
                            n=value.length();
                            String s="Token";
                            Toast.makeText(send.this, value, Toast.LENGTH_SHORT).show();
                            if (key.equals(s) & value.length() >=100) {
                            // Process the details as per your requirements
                            //System.out.println("Key: " + key + ", Value: " + value);
                                Toast.makeText(send.this, value, Toast.LENGTH_SHORT).show();
                            }

                        }*/
                        FCMSend.pushNotification(
                                send.this,
                                "key="+"dgFpvCF3S1CAi-SWhfSfyB:APA91bHZhubraIVmoZYhNS9f9dWoFYJ16bNGS2XukORkix4LLVjHpcVG3t4JfU95Tn7Npff31lozKSbeTnBGQmiV9syMIFJbLYGxbD8ilymJrld3o2ftkvD3fGevsmxLwuQ3NaS-RhAX",
                                "From:" + regds,
                                //"From:" + regds + "\n" + "Title:" + title,
                                "Message:" + message
                        );
                        Toast.makeText(send.this, "Send Successful", Toast.LENGTH_SHORT).show();
                        mmessage1.setText(" ");
                    }}}
            }



                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

}


