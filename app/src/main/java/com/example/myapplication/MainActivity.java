package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyViewModel viewModel;
    private ActivityMainBinding binding;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        mRecyclerView = (RecyclerView)binding.recyclerView;
        mAdapter = new MyRecyclerAdapter(viewModel); // 어댑터 생성

        mRecyclerView.setAdapter(mAdapter); // 리사이클러뷰에 MyRecyclerAdapter 객체 지정
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // 리사이클러뷰에 LinearLayoutManager 객체 지정
        mRecyclerView.setHasFixedSize(true); // View마다 크기 똑같게

        final Observer<ArrayList<String>> myObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                String str = "데이터 개수 : " + viewModel.getItemSize();
                Log.e("Observer 실행", str);
                mAdapter.notifyDataSetChanged(); // 어댑터에게 데이터가 변경되었다는 것을 알림
            }
        };
        viewModel.categorysLiveData.observe(this, myObserver);

        viewModel.addCategory("과일");
        viewModel.addCategory("채소");
        viewModel.addCategory("생선");
        viewModel.addCategory("육류");
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
                Intent intent = new Intent(this, CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.setting: // '설정' 버튼 선택
                // 설정 Activity로 이동
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void categoryAddButtonClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_category_add, null);
        builder.setView(layout);

        EditText editCategory = (EditText) layout.findViewById(R.id.editCategory);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String text = editCategory.getText().toString();
                viewModel.addCategory(text);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
        Log.e("OnClick", "추가 버튼 클릭");
    }
}