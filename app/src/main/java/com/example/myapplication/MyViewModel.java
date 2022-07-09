package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> categorysLiveData = new MutableLiveData<>();
    public ArrayList<String> categorys = new ArrayList<>();
    public int eventPos = -1;

    public MyViewModel() {
        // 생성될 때 카테고리 3개 추가
        categorys.add("과일");
        categorys.add("채소");
        categorys.add("육류");
        categorysLiveData.setValue(categorys);
    }

    public String getCategory(int pos) {
        return categorys.get(pos);
    }

    public void addCategory(String category) {
        Log.e("addCategory", "추가");
        eventPos = getItemSize();
        categorys.add(category);
        categorysLiveData.setValue(categorys);
    }

    public int getItemSize() {
        return categorys.size();
    }
}
