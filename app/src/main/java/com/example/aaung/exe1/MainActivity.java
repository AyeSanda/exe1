package com.example.aaung.exe1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aaung.exe1.database.DataSource;
import com.example.aaung.exe1.database.DatabaseHelper;
import com.example.aaung.exe1.helper.JSONHelper;
import com.example.aaung.exe1.model.DataItem;
import com.example.aaung.exe1.sample.SampleDataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.prefs.Preferences;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    List<DataItem> dataItemList = SampleDataProvider.dataItemList;
    List<String> itemNameList = new ArrayList<>();
    private DataItemAdapterRecyclerView adapter;

    @Inject OkHttpClient mOkHttpClient;

    @Inject SharedPreferences sharedPreferences;

DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDataSource = new DataSource(this);
        mDataSource.Open();
        for (DataItem dataItem:
             dataItemList) {
            itemNameList.add(dataItem.getItemName());
        }

        /* //using with build in arrayadapter//
        Collections.sort(itemNameList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNameList); */

        Collections.sort(dataItemList, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getItemName().compareToIgnoreCase(o2.getItemName());
            }
        });

        /*  using with listview */
//        DataItemAdapterListView adapter = new DataItemAdapterListView(getApplicationContext(), dataItemList);
//        ListView listView = findViewById(R.id.list_item);
//        listView.setAdapter(adapter);

        /**using with Recycler View**/
        adapter = new DataItemAdapterRecyclerView(getApplicationContext(), dataItemList);
        initView();
    }

    private void initView(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean grid = preferences.getBoolean(getString(R.string.pref_display_grid), false);

        RecyclerView recyclerView = findViewById(R.id.rv_list_item);
        recyclerView.setAdapter(adapter);
        if(grid){
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_signin:
//                Intent intent = new Intent(this, SigninActivity.class);
//                startActivityForResult(intent,SIGNIN_REQUEST);
//                return true;

            case R.id.exe_list_and_grid:
                Intent settingIntent = new Intent(this, PrefsActivity.class);
                startActivity(settingIntent);
                return true;

            case R.id.exe_internal_storage:
                Intent internalStorageIntent = new Intent(this, InternalStoreageActivity.class);
                startActivity(internalStorageIntent);
                return true;

            case R.id.exe_external_storage:
                Intent externalStorageIntent = new Intent(this, ExternalStorageActivity.class);
                startActivity(externalStorageIntent);
                return true;
            case R.id.exe_import:
                List<DataItem> dataItems = JSONHelper.importFromJSON(this);
                if(dataItems != null && dataItems.size() > 0){
                    Toast.makeText(this,"JSON Imported " + dataItems.toString() ,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this,"JSON Imported Failed " ,Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.exe_export:
                boolean exported = JSONHelper.exportToJSON(this,dataItemList);
                if(exported){
                    Toast.makeText(this,"JSON Exported " ,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this,"JSON Exported Fail",Toast.LENGTH_LONG).show();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.Open();
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.Close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(resultCode == RESULT_OK && requestCode == SIGNIN_REQUEST){
//            String email = data.getStringExtra(SigninActivity.EMAIL_KEY);
//            SharedPreferences.Editor editor = getSharedPreferences()
//        }

    }
}
