package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    private Button btnFood, btnPasabay, btnAboutUs, btnContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button btnFood= findViewById(R.id.btnFood);
        Button btnPasabay= findViewById(R.id.btnPasabay);
        Button btnAboutUs= findViewById(R.id.btnAboutUs);
        Button btnContact= findViewById(R.id.btnContact);

        btnFood.setOnClickListener(this);
        btnPasabay.setOnClickListener(this);
        btnAboutUs.setOnClickListener(this);
        btnContact.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnFood:
                Toast.makeText(this, "Food clicked", Toast.LENGTH_SHORT).show();
                Intent b1 = new Intent (getApplicationContext(),Food.class);
                startActivity(b1);
                break;
            case R.id.btnPasabay:
                Toast.makeText(this, "Pasabay clicked", Toast.LENGTH_SHORT).show();
                //Intent b2 = new Intent (getApplicationContext(),Pasabay.class);
                // startActivity(b2);
                break;
            case R.id.btnAboutUs:
                Toast.makeText(this, "About Us clicked", Toast.LENGTH_SHORT).show();
                Intent b3 = new Intent (getApplicationContext(),Aboutus.class);
                startActivity(b3);
                break;
            case R.id.btnContact:
                Toast.makeText(this, "Contact clicked", Toast.LENGTH_SHORT).show();
                Intent b4 = new Intent (getApplicationContext(),Contact.class);
                startActivity(b4);
                break;
        }

    }
}