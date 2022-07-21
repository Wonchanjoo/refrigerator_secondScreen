package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ItemViewModel extends ViewModel implements Parcelable {
    public MutableLiveData<ArrayList<String>> itemsLiveData = new MutableLiveData<>();
    public ArrayList<String> items = new ArrayList<>();
    public int longClickPosition;

    public ItemViewModel() {}
    protected ItemViewModel(Parcel in) {
        items = in.createStringArrayList();
        longClickPosition = in.readInt();
    }

    public static final Creator<ItemViewModel> CREATOR = new Creator<ItemViewModel>() {
        @Override
        public ItemViewModel createFromParcel(Parcel in) {
            return new ItemViewModel(in);
        }

        @Override
        public ItemViewModel[] newArray(int size) {
            return new ItemViewModel[size];
        }
    };

    public void addItem(String item) {
        if(items.contains(item))
            return;
        items.add(item);
        itemsLiveData.setValue(items);
    }

    public int getItemSize() { return items.size(); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(items);
        parcel.writeInt(longClickPosition);
    }
}
