package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Observable;

public class MainActivity extends AppCompatActivity {
    private MyViewModel viewModel;
    private ActivityMainBinding binding;
    private RecyclerView rv;
    private MyRecyclerAdapter ra;

    private ArrayList<String> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        rv = (RecyclerView)binding.recyclerView;
        ra = new MyRecyclerAdapter(viewModel); // 어댑터 생성

        rv.setAdapter(ra);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true); // View마다 크기 똑같게

        final Observer<ArrayList<String>> myObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                String str = "데이터 개수 : " + viewModel.getItemSize();
                Log.e("Observer", str);
                ra.notifyDataSetChanged();
                registerForContextMenu(binding.recyclerView);
            }
        };
        viewModel.categorysLiveData.observe(this, myObserver);

        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 카테고리 추가 (리사이클러뷰에 항목 추가)
                Log.e("OnClick", "추가 버튼 클릭");
                viewModel.addCategory("추가");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calender: // '달력' 버튼 선택
                // 달력 Activity로 이동
                break;
            case R.id.setting: // '설정' 버튼 선택
                // 설정 Activity로 이동
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}