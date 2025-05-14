package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class geography extends AppCompatActivity {


        TextView descriptionText;
        Button btnPetal, btnStem, btnLeaf, btnRoot;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_geography);
            ImageView backButton = findViewById(R.id.Back); // Use ImageView if it's an ImageView
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openGame = new Intent(geography.this, GameCentre.class);
                    startActivity(openGame);
                }
            });

            descriptionText = findViewById(R.id.descriptionText);
            btnPetal = findViewById(R.id.btnPetal);
            btnStem = findViewById(R.id.btnStem);
            btnLeaf = findViewById(R.id.btnLeaf);
            btnRoot = findViewById(R.id.btnRoot);

            btnPetal.setOnClickListener(v ->
                    descriptionText.setText("Petals are often colorful and fragrant to attract pollinators.")
            );

            btnStem.setOnClickListener(v ->
                    descriptionText.setText("The stem supports the plant and transports water and nutrients.")
            );

            btnLeaf.setOnClickListener(v ->
                    descriptionText.setText("Leaves carry out photosynthesis to produce food for the plant.")
            );

            btnRoot.setOnClickListener(v ->
                    descriptionText.setText("Roots anchor the plant and absorb water and minerals from the soil.")
            );
        }
    }
