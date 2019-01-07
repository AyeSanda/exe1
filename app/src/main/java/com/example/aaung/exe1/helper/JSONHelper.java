package com.example.aaung.exe1.helper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.aaung.exe1.model.DataItem;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME = "menuitems.json";
    private static final String TAG = "JSONHelper";

    public static boolean exportToJSON(Context context, List<DataItem> items){

        //List<DataItem> itemList = items;
        JSONDataItem jsonDataItem = new JSONDataItem();
        jsonDataItem.setDataItems(items);
        Gson json = new Gson();
        String jsonString = json.toJson(jsonDataItem);
        Log.i(TAG,"exportToJSON : "+ jsonString);

        FileOutputStream outputStream = null;
        try {
            File file  = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
            outputStream = new FileOutputStream(file);
            outputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            Log.i(TAG,"exportToJSON Exception : "+ e.getLocalizedMessage());
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static List<DataItem> importFromJSON(Context context){
        FileReader reader = null;
        try{
            File file = new File(Environment.getExternalStorageDirectory(),FILE_NAME);
             reader = new FileReader(file);
            Gson jsondata  = new Gson();
            JSONDataItem dataItemObject = jsondata.fromJson(reader,JSONDataItem.class);
            return dataItemObject.getDataItems();

        }catch (IOException  | IllegalStateException ex){
            Log.i(TAG,"importToJSON Exception : "+ ex.getLocalizedMessage());
            ex.printStackTrace();

        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    static class JSONDataItem {
        List<DataItem> dataItems;

        public List<DataItem> getDataItems(){
            return dataItems;
        }

        public void setDataItems(List<DataItem> dataItems){
            this.dataItems = dataItems;
        }

    }
}
