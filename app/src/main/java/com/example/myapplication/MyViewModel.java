package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class MyViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> categorysLiveData = new MutableLiveData<>();
    public ArrayList<String> categorys = new ArrayList<>();
    public int longClickPosition;

    public void addCategory(String category) {
        if(categorys.contains(category))
            return;
        categorys.add(category);
        Collections.sort(categorys);
        categorysLiveData.setValue(categorys); // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }

    public void deleteItem(int position) {
        categorys.remove(position);
        categorysLiveData.setValue(categorys);
    }

    public int getCategorySize() {
        return categorys.size();
    }
}
