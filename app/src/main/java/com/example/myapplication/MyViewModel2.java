package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyViewModel2 extends ViewModel {
    public MutableLiveData<ArrayList<String>> itemsLiveData = new MutableLiveData<>();
    public ArrayList<String> items = new ArrayList<>();

    public void addItem(String item) {
        items.add(item);
        itemsLiveData.setValue(items);
    }

    public int getItemSize() { return items.size(); }
}
