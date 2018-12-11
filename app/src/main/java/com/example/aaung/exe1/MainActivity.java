package com.example.aaung.exe1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.example.aaung.exe1.model.DataItem;
import com.example.aaung.exe1.sample.SampleDataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        DataItemAdapterRecyclerView adapter = new DataItemAdapterRecyclerView(getApplicationContext(), dataItemList);
        RecyclerView recyclerView = findViewById(R.id.rv_list_item);
        recyclerView.setAdapter(adapter);
    }
}
