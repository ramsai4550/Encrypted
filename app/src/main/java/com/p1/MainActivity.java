
package com.p1;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.os.Bundle;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.messaging.FirebaseMessaging;

        import java.text.BreakIterator;
        import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
     private static EditText usernameEditText;
        private EditText passwordEditText;
        private Button loginButton;
        private Button registerButton;
        private Button se;
        private DatabaseReference ref;
        private int flag=0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("users"); // "users" is the name of your database node
            ref = FirebaseDatabase.getInstance().getReference("users");
            usernameEditText = findViewById(R.id.activity_main_usernameEditText);
            passwordEditText = findViewById(R.id.activity_main_passwordEditText);
            loginButton = findViewById(R.id.activity_main_loginButton);
            registerButton=findViewById(R.id.activity_main_register_Button);
             String name = usernameEditText.getText().toString();
             String p = passwordEditText.getText().toString();

            //Intent intent = new Intent(MainActivity.this, log.class);
            //intent.putExtra("variableName", name);

                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Register();
                    }
                });
            final String[] token =new String[1];
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                return;
                            }
                            token[0] = task.getResult();
                        }
                    });
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name = usernameEditText.getText().toString();
                    final String p = passwordEditText.getText().toString();
                    System.out.println(name+" "+p);
                    if(name.isEmpty() || p.isEmpty()){
                        Toast.makeText(MainActivity.this,"Enter username, password", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Iterate through each child node of the "users" node
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    // Retrieve the username and password values from each child node
                                    String username = userSnapshot.child("U_name").getValue(String.class);
                                    String password = userSnapshot.child("password").getValue(String.class);
                                    // Do something with the username and password values
                                    if(password.equals(p) && username.equals(name)){
                                        Toast.makeText(MainActivity.this,"Login successful", Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(MainActivity.this,token[0], Toast.LENGTH_SHORT).show();
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("U_name", name);
                                        hashMap.put("password", p);
                                        hashMap.put("Token",token[0]);
                                        ref.child(name).setValue(hashMap);
                                        Intent intent1 = new Intent(MainActivity.this, send.class);
                                        startActivity(intent1);
                                        intent1.putExtra("myVariable", name);
                                        //Toast.makeText(MainActivity.this,"hello", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    else{
                                        flag=1;

                                    }
                                    if(flag==1){
                                        Toast.makeText(MainActivity.this,"Invalid Data", Toast.LENGTH_SHORT).show();

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle any errors that occur
                            }
                        });

                    }
                }
            });


        }
    public void Register(){
        Intent intent = new Intent(this, reg.class);
        startActivity(intent);
    }
    public static String getText(){
        String data = (String) usernameEditText.getText().toString();
        return data;
    }
   /* public  void Login(){
            Intent intent1 = new Intent(this, log.class);
            startActivity(intent1);
    }*/
    }

