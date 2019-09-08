package com.example.spyros.farmakeia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAvailablePharamcies();

            }
        });


        Button AllPharmacies = findViewById(R.id.button3);
        AllPharmacies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllPharmacies();
            }
        });
    }

    public void openAvailablePharamcies() {

        Intent intent = new Intent(this, AvailablePharmacies.class);
        startActivity(intent);

    }

    public void openAllPharmacies() {

        Intent intent2 = new Intent(this, All_Pharmacies.class);
        startActivity(intent2);

    }

}

