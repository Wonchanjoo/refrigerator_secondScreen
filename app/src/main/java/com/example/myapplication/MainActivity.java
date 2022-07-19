package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String refrigeratorName;
    private boolean init = false;

    private MyViewModel viewModel;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;

    private String editCategoryText;

    private DatabaseReference mPostReference;
    static ArrayList<String> arrayIndex = new ArrayList<>();
    static ArrayList<String> arrayData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refrigeratorName = "냉장고1"; // 냉장고 이름
        setTitle(refrigeratorName + "의 냉장고");

        viewModel = new ViewModelProvider(this).get(MyViewModel.class); // 뷰모델 생성

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new MyRecyclerAdapter(viewModel); // 어댑터 생성

        mRecyclerView.setAdapter(mAdapter); // 리사이클러뷰에 MyRecyclerAdapter 객체 지정
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // 리사이클러뷰에 LinearLayoutManager 객체 지정
        mRecyclerView.setHasFixedSize(true); // View마다 크기 똑같게

        registerForContextMenu(mRecyclerView);

        final Observer<ArrayList<String>> myObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                mAdapter.notifyDataSetChanged(); // 어댑터에게 데이터가 변경되었다는 것을 알림
            }
        };
        viewModel.categorysLiveData.observe(this, myObserver);

        // DB에 있는 카테고리들 가져오기
        getFirebaseDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 상단의 달력, 설정 메뉴 만들어질 때
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.information: // 정보 버튼 - 카테고리에는 필요 없으므로 삭제해야댐!!
                Log.e("onContextItemSelected", "정보 버튼 클릭");
                break;
            case R.id.delete: // 카테고리 삭제하기
                Log.e("delete", "longClickPosition = " + viewModel.longClickPosition);
                // 데이터베이스에도 삭제해야됨
                String deleteName = viewModel.categorys.get(viewModel.longClickPosition);
                Log.e("delete", "delete name = " + deleteName);
                deleteCategory(viewModel.categorys.get(viewModel.longClickPosition));
                viewModel.deleteItem(viewModel.longClickPosition);
                break;
        }
        return true;
    }

    public void createCategoryInDataBase(String category) {
        mPostReference = FirebaseDatabase.getInstance().getReference();
        mPostReference.child("냉장고").child(refrigeratorName).child(category).setValue("");
    }

    public void deleteCategory(String category) {
        mPostReference = FirebaseDatabase.getInstance().getReference();
        mPostReference.child("냉장고").child(refrigeratorName).child(category).removeValue();
    }

    public void postFirebaseDatabase(boolean add) {
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add) {
            FirebasePost post = new FirebasePost(editCategoryText);
            postValues = post.toMap();
        }
        String str = "/냉장고/" + "냉장고2" + "/";
        //childUpdates.put(str, "추가", ""); // 데이터베이스에 카테고리 추가
        //mPostReference.updateChildren(childUpdates);
        mPostReference.child("냉장고").child("냉장고2").child("추가").setValue("");
    }

    public void getFirebaseDatabase() {
        Log.e("getFirebaseDatabase", "함수 실행");
        FirebaseDatabase.getInstance().getReference().child("냉장고").child(refrigeratorName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("getFirebaseDatabase", "onDataChange");
                for(DataSnapshot data : snapshot.getChildren()) {
                    String key = data.getKey();
                    Log.e("getFirebaseDatabase", "key = " + key);
                    viewModel.addCategory(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // 카테고리 추가
    public void categoryAddButtonClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_category_add, null);
        builder.setView(layout);

        EditText editCategory = (EditText) layout.findViewById(R.id.editCategory);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editCategoryText = editCategory.getText().toString(); // 입력한 카테고리 받아오고
                viewModel.addCategory(editCategoryText); // viewModel에 카테고리 추가
                createCategoryInDataBase(editCategoryText); // 데이터베이스에 카테고리 추가
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
        Log.e("OnClick", "추가 버튼 클릭");
    }

    // 카테고리 클릭 -> Activity 이동
    public void categoryClick(View v) {
        Intent intent = new Intent(this, MainActivity2.class);
        TextView t = (TextView) v;
        Log.e("categoryClick", (String)t.getText() + " Click");
        intent.putExtra("category", (String)t.getText()); // intent에 클릭한 카테고리 이름 넣어주기
        startActivity(intent); // 액티비티 이동
    }
}