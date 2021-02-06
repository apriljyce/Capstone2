package com.example.sample1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText txtFullName, txtEmailAddress, RegisterPassword, txtPhoneNumber;
    private Button btnRegister, btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtFullName = (EditText) findViewById(R.id.txtFullName);
        txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);

        RegisterPassword = (EditText) findViewById(R.id.RegisterPassword);


        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateAccount();
            }
        }) ;
    }
            private void CreateAccount() {
                //extract data from the form

                String fullName = txtFullName.getText().toString();
                String phone = txtPhoneNumber.getText().toString();
                String email = txtEmailAddress.getText().toString();
                String password = RegisterPassword.getText().toString();

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(this, "Full name required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "Phone required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
                }
                else {
             Toast.makeText(MainActivity.this, "DATA VALIDATED", Toast.LENGTH_SHORT).show();
                    ValidatephoneNumber(fullName, phone, email, password);
                }
}

    private void ValidatephoneNumber(final String fullName, final String phone, final String email, final String password)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Full name: ", txtFullName);
                    userdataMap.put("Phone Number: ", txtPhoneNumber);
                    userdataMap.put("Email Address: ", txtEmailAddress);
                    userdataMap.put("Password: ", RegisterPassword);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Your account is created",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Login.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Network Error. Please try again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(MainActivity.this, "This"
                            + phone + " already exists!", Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(MainActivity.this, Login.class);

            startActivity(intent);
        }
    }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
