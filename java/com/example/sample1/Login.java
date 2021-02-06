package com.example.sample1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Users;

public class Login extends AppCompatActivity {
    private EditText txtEmailAddress,txtPhoneNumber, RegisterPassword;
    Button btnCreateAccount, btnConfirm;
    private TextView adminPanelLink,notAdminPanelLink;


    private String parentDbName = "Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtEmailAddress = (EditText)findViewById(R.id.txtEmailAddress);
        RegisterPassword = (EditText)findViewById(R.id.RegisterPassword);

        notAdminPanelLink = (TextView) findViewById(R.id.notAdminPanelLink);
        adminPanelLink = (TextView) findViewById(R.id.adminPanelLink);

        btnConfirm =  (Button) findViewById(R.id.btnConfirm);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        adminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConfirm.setText("Login Admin");
                adminPanelLink.setVisibility(View.INVISIBLE);
                notAdminPanelLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });
        notAdminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConfirm.setText("Login");
                adminPanelLink.setVisibility(View.VISIBLE);
                notAdminPanelLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void LoginUser() {
        String phone = txtPhoneNumber.getText().toString();
        String email = txtEmailAddress.getText().toString();
        String password = RegisterPassword.getText().toString();

         if (email.isEmpty()) {
            txtEmailAddress.setError("Email Address is required");
            return;
        }
        else if (phone.isEmpty()) {
            txtPhoneNumber.setError("Phone number is required");
            return;
        }
        else if (password.isEmpty()) {
            RegisterPassword.setError("Password is required");
            return;
        }
        else
         {
             AllowAccessToAccount(phone, password);
         }
    }

    private void AllowAccessToAccount(String phone, String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users userData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(userData.getPhone().equals(phone)){
                        if(userData.getPassword().equals(password)){
                            if(parentDbName.equals("Admins")){
                                Toast.makeText(Login.this,"Welcome Admin! Logged in successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, AdminAddNewProduct.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users")){
                                Toast.makeText(Login.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Dashboard.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(Login.this, "Account do not exist",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

