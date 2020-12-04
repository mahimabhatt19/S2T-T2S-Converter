package com.example.text_speechconverter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SpeechToTextActivity extends AppCompatActivity {

    private EditText txvResult;
    Button mSaveBtn;
    String mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speechtotext);
        txvResult = (EditText) findViewById(R.id.txvResult);
        mSaveBtn=(Button) findViewById(R.id.mSaveBtn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mText=txvResult.getText().toString().trim();

                if (mText.isEmpty()){
                    Toast.makeText(SpeechToTextActivity.this, "Please enter something...", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                            String[] permissions= {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions,200);
                        }
                        else{
                            saveToTxtFile(mText);
                        }
                    }
                    else{
                        saveToTxtFile(mText);
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 200:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    saveToTxtFile(mText);
                else
                    Toast.makeText(this, "Storage Permission is required to store data", Toast.LENGTH_LONG).show();
        }
    }

    private void saveToTxtFile(String mText) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(System.currentTimeMillis());

        try{
            File path = Environment.getExternalStorageDirectory();

            File dir = new File(path+"/Speech to Text Recognized Files/");
            //File dir = new File(getExternalFilesDir());
            dir.mkdirs();

            String fileName = "MyFile_"+timeStamp+".txt";

            File file = new File(dir,fileName);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(mText);
            bw.close();

            Toast.makeText(this,fileName+" saved successfully ", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){

            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));
                }
                break;
        }
    }

    //String mText = txvResult.getText().toString().trim();



}
