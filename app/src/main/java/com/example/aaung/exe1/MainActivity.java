package com.example.aaung.exe1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aaung.exe1.model.DataItem;
import com.example.aaung.exe1.sample.SampleDataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<DataItem> dataItemList = SampleDataProvider.dataItemList;
    List<String> itemNameList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (DataItem dataItem:
             dataItemList) {
            itemNameList.add(dataItem.getItemName());
        }

        Collections.sort(itemNameList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNameList);
        ListView listView = findViewById(R.id.list_item);
        listView.setAdapter(adapter);
    }
}
