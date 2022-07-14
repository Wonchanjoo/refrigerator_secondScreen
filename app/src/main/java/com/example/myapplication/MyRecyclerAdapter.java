package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private MyViewModel viewModel;

    public MyRecyclerAdapter(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /*
    ViewHolder inner Class - 아이템 뷰를 저장
    */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.category = itemView.findViewById(R.id.category);
        }

        public void setContents(int pos) {
            String text = viewModel.categorys.get(pos);
            category.setText(text);
        }
    }



    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHolder(View를 담는 상자) 생성
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // ViewHolder에 데이터 연결
        String text = viewModel.categorys.get(position);
        holder.setContents(position);
    }

    @Override
    public int getItemCount() {
        return viewModel.getCategorySize();
    }
}