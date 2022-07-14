package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> categorysLiveData = new MutableLiveData<>();
    public ArrayList<String> categorys = new ArrayList<>();

    public void addCategory(String category) {
        categorys.add(category);
        categorysLiveData.setValue(categorys); // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }

    public int getCategorySize() {
        return categorys.size();
    }
}
