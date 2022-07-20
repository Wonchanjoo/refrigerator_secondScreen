package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ItemViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> itemsLiveData = new MutableLiveData<>();
    public ArrayList<String> items = new ArrayList<>();
    public int longClickPosition;

    public void addItem(String item) {
        if(items.contains(item))
            return;
        items.add(item);
        itemsLiveData.setValue(items);
    }

    public int getItemSize() { return items.size(); }
}
