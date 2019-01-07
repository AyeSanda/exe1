package com.example.aaung.exe1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class InternalStoreageActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText output;
    private EditText input;
    public static final String FILE_NAME = "internal_storage.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_internal_storeage);

        output = (EditText) findViewById(R.id.txt_write_file);
        input = (EditText) findViewById(R.id.txt_read_file);
        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);

    }

    public void onCreateButtonClick(View view){
        FileOutputStream fileOutputStream = null;
        File file = new File(FILE_NAME);
        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(output.getText().toString().getBytes());
            Toast.makeText(this,"FILE WRITTEN : " + output.getText().toString(),Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }catch (IOException ex){
            ex.printStackTrace();
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void onReadButtonClick(View view){

        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader InputFilereader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(InputFilereader);
            StringBuilder stringReader = new StringBuilder();
            stringReader.append(bufferedReader.readLine());
            input.setText(stringReader.toString());
        } catch (IOException e) {
             e.printStackTrace();
        }

    }
    public void onDeleteButtonClick(View view){
        File file  = new File(getFilesDir(), FILE_NAME);
        if(file.exists()) {
            deleteFile(FILE_NAME);
            Toast.makeText(this, "File is deleted", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"File is not existed",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create:
                onCreateButtonClick(v);
                break;
            case R.id.btn_read:
                onReadButtonClick(v);
                break;
            case R.id.btn_delete:
                onDeleteButtonClick(v);
                break;

        }
    }
}
