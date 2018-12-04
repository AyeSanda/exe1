package com.example.aaung.exe1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.aaung.exe1.model.DataItem;

import java.util.List;

public class DataItemAdapter extends ArrayAdapter<DataItem> {


    public DataItemAdapter(@NonNull Context context,@NonNull List<DataItem> objects) {
        super(context, R.layout.list_item, objects);
    }
}
