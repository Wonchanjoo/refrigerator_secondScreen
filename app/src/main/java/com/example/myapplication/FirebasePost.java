package com.example.myapplication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FirebasePost {
    public String category;

    public FirebasePost() {

    }
    public FirebasePost(String category) {
        this.category = category;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("category", category);
        return result;
    }
}
