package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemRecyclerviewBinding;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private MyViewModel viewModel;
    private ArrayList<String> categoryList;

    public MyRecyclerAdapter(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /* ViewHolder inner Class */
    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRecyclerviewBinding binding;

        public ViewHolder(@NonNull View itemView, ItemRecyclerviewBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void setContents(int pos) {
            Log.e("setContents", "실행");
            String item = viewModel.getCategory(pos);
            Log.e("setContents", item);
            TextView textView = this.binding.category;
            textView.setText(item);
            Log.e("setContents", (String) textView.getText());
        }
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHolder(View를 담는 상자) 생성
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecyclerviewBinding binding = ItemRecyclerviewBinding.inflate(layoutInflater, parent, false);
        View view = layoutInflater.inflate(R.layout.item_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // ViewHolder에 데이터 연결
        Log.e("onBindViewHolder", "실행");
        holder.setContents(position);
    }

    public void setCategoryList(ArrayList<String> list) {
        this.categoryList = list;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return viewModel.getItemSize();
    }
}