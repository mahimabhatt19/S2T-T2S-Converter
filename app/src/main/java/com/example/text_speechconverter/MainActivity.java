package com.example.text_speechconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button S2Tbtn , T2Sbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        S2Tbtn=(Button) findViewById(R.id.S2Tbtn);
        T2Sbtn=(Button) findViewById(R.id.T2Sbtn);

        S2Tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new  Intent(getApplicationContext(),SpeechToTextActivity.class);
                startActivity(i);
            }
        });

        T2Sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j =new  Intent(getApplicationContext(),TextToSpeechActivity.class);
                startActivity(j);

            }
        });



    }
}