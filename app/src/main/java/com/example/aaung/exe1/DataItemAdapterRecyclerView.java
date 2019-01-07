package com.example.aaung.exe1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aaung.exe1.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataItemAdapterRecyclerView extends RecyclerView.Adapter<DataItemAdapterRecyclerView.ViewHolder>{
    public Context mContext;
    public List<DataItem> mDataItemList;
    public static String ITEM_ID_KEY = "item_id_key";
    public static String ITEM_OBJECT = "item_object";

    public DataItemAdapterRecyclerView(Context context, List<DataItem> dataItemList) {
        this.mContext = context;
        this.mDataItemList = dataItemList;
    }

    @Override
    public DataItemAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

//        boolean grid = preferences.getBoolean(mContext.getString(R.string.pref_display_grid), false);
//        Log.e("grid", grid + "");
//        int layoutId  = grid? R.layout.grid_item : R.layout.list_item;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(viewType,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataItemAdapterRecyclerView.ViewHolder holder, int position) {
        final DataItem dataItem = mDataItemList.get(position);
        holder.itemName.setText(dataItem.getItemName());
        InputStream inputStream = null;
        try {
            String imageFile = dataItem.getImage();
            inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream,null);
            holder.itemImage.setImageDrawable(d);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemId = dataItem.getItemId();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(ITEM_ID_KEY, itemId);
                intent.putExtra(ITEM_OBJECT,dataItem);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean grid = preferences.getBoolean(mContext.getString(R.string.pref_display_grid), false);
        Log.e("grid", grid + "");
        return grid? R.layout.grid_item : R.layout.list_item;
    }

    @Override
    public int getItemCount() {
        return mDataItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName;
        public ImageView itemImage;
        public View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            this.itemView = itemView;

        }
    }

 }
