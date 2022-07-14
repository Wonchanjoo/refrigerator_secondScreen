package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/*
MainActivity에서 카테고리를 클릭하면 해당 카테고리의 이름을 받아와야됨
받아온 이름을 사용해서 카테고리 안에 들어있는 아이템들을 출력 (Recycler View에 넣기)
 */

public class MainActivity2 extends AppCompatActivity {
    private String category;

    private MyViewModel2 viewModel;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter2 mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        viewModel = new ViewModelProvider(this).get(MyViewModel2.class);

        mRecyclerView = (RecyclerView)findViewById(R.id.itemRecyclerView);
        mAdapter = new MyRecyclerAdapter2(viewModel);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);

        final Observer<ArrayList<String>> myObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                mAdapter.notifyDataSetChanged();
            }
        };
        viewModel.itemsLiveData.observe(this, myObserver);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        getFirebaseDatabase();
    }

    public void addItemBtnClick(View v) {
        // item 추가하는 액티비티로 이동
    }

    public void getFirebaseDatabase() {
        Log.e("getFirebaseDatabase2", "함수 실행");
        FirebaseDatabase.getInstance().getReference().child("category").child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("getFirebaseDatabase", "onDataChange");
                for(DataSnapshot data : snapshot.getChildren()) {
                    FirebasePost2 get = data.getValue(FirebasePost2.class);
                    String key = data.getKey();
                    Log.e("getFirebaseDatabase", "key = " + key);
                    viewModel.addItem(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}