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
    private String refrigeratorName;
    private String category;

    private MyViewModel2 viewModel;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter2 mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        viewModel = new ViewModelProvider(this).get(MyViewModel2.class); // 뷰모델 생성

        mRecyclerView = (RecyclerView)findViewById(R.id.itemRecyclerView);
        mAdapter = new MyRecyclerAdapter2(viewModel); // 어댑터 생성

        mRecyclerView.setAdapter(mAdapter); // 리사이클러뷰에 MyRecyclerAdapter2 객체 지정
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);

        // item에 변화가 생긴 경우 어댑터에게 알려서 갱신하도록
        final Observer<ArrayList<String>> myObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                mAdapter.notifyDataSetChanged();
            }
        };
        viewModel.itemsLiveData.observe(this, myObserver);

        // MainActivity에서 넘어온 Intent, 카테고리 이름이 담겨있음
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        refrigeratorName = "냉장고1";
        setTitle(category);
        getFirebaseDatabase(); // 카테고리 안에 있는 아이템들을 가져와서 보여줌
    }

    public void addItemBtnClick(View v) {
        // item 추가하는 액티비티로 이동
    }

    public void getFirebaseDatabase() {
        Log.e("getFirebaseDatabase2", "함수 실행");
        FirebaseDatabase.getInstance().getReference().child("냉장고").child(refrigeratorName).child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("getFirebaseDatabase", "onDataChange");
                for(DataSnapshot data : snapshot.getChildren()) {
                    //FirebasePost2 get = data.getValue(FirebasePost2.class);
                    String key = data.getKey();
                    Log.e("getFirebaseDatabase", "key = " + key);
                    //viewModel.addItem(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}