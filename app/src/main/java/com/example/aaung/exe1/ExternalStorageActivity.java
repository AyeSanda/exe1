package com.example.aaung.exe1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PERMISSION_WRITE = 1001 ;
    private EditText output;
    private EditText input;
    public static final String FILE_NAME = "externalStorage_file.txt";
    private boolean permissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storeage);
        output = (EditText) findViewById(R.id.txt_write_file);
        input = (EditText) findViewById(R.id.txt_read_file);
        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    private File getFile(){
        return new File(Environment.getExternalStorageDirectory(),FILE_NAME);
    }

    public void onCreateButtonClick(View view){
        if(!permissionGranted){
            checkPermissions();
        }
        FileOutputStream fileOutputStream = null;
        File file = getFile();
        try {
            fileOutputStream = new FileOutputStream(file);
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
        if(!permissionGranted){
            checkPermissions();
        }
        try {
            File file = getFile();
            FileInputStream fileInputStream = new FileInputStream(file);
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
        if(!permissionGranted){
            checkPermissions();
            return;
        }
        File file  = getFile();
        if(file.exists()) {
            file.delete();
            input.setText("");
            output.setText("");
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

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            permissionGranted = false;
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            permissionGranted = false;
            return false;
        } else {
            permissionGranted = true;
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
