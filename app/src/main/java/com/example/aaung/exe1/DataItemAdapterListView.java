package com.example.aaung.exe1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aaung.exe1.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataItemAdapterListView extends ArrayAdapter<DataItem> {

    List<DataItem> itemList;
    LayoutInflater layoutInflater;

    public DataItemAdapterListView(@NonNull Context context, @NonNull List<DataItem> objects) {
        super(context, R.layout.list_item, objects);
        itemList = objects;
        layoutInflater = LayoutInflater.from(getContext());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item,parent,false);
        }
        TextView itemName = convertView.findViewById(R.id.item_name);
        ImageView itemiImage = convertView.findViewById(R.id.item_image);

        DataItem dataItem = itemList.get(position);
        itemName.setText(dataItem.getItemName());

        InputStream inputStream = null;
        try {
            String imageFile = dataItem.getImage();
            inputStream = getContext().getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream,null);
            itemiImage.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return convertView;
    }
}
