package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> categorysLiveData = new MutableLiveData<>();
    public ArrayList<String> categorys = new ArrayList<>();
    public int eventPos = -1;

    public String getCategory(int pos) {
        return categorys.get(pos);
    }

    public void addCategory(String category) {
        eventPos = getItemSize();
        categorys.add(category);
        categorysLiveData.setValue(categorys); // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
        Log.e("ViewModel", category + " 추가");
    }

    public int getItemSize() {
        return categorys.size();
    }
}
