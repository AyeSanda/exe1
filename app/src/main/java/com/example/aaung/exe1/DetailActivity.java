package com.example.aaung.exe1;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aaung.exe1.model.DataItem;
import com.example.aaung.exe1.sample.SampleDataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //String itemId = getIntent().getStringExtra(DataItemAdapterRecyclerView.ITEM_ID_KEY);
        //DataItem dataItem = SampleDataProvider.dataItemMap.get(itemId);

        DataItem dataItem = getIntent().getParcelableExtra(DataItemAdapterRecyclerView.ITEM_OBJECT);

        TextView itemName = findViewById(R.id.tvItemName);
        TextView itemPrice = findViewById(R.id.tvPrice);
        TextView itemDescription = findViewById(R.id.tvDescription);
        ImageView imageView = findViewById(R.id.itemImage);

        if(dataItem != null){

            itemName.setText(dataItem.getItemName());
            itemDescription.setText(dataItem.getDescription());

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            itemPrice.setText(currencyFormat.format(dataItem.getPrice()));

            InputStream inputStream = null;
            try {
                String imageFile = dataItem.getImage();
                inputStream = getApplicationContext().getAssets().open(imageFile);
                Drawable d = Drawable.createFromStream(inputStream,null);
                imageView.setImageDrawable(d);
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

        }else {
            Log.e(DataItemAdapterRecyclerView.ITEM_OBJECT, "no data");
        }


    }
}
