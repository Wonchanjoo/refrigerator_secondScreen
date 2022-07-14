package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerAdapter2 extends RecyclerView.Adapter<MyRecyclerAdapter2.ViewHolder2> {

    private MyViewModel2 viewModel;

    public MyRecyclerAdapter2(MyViewModel2 viewModel) {
        this.viewModel = viewModel;
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView item;

        public ViewHolder2(View itemView) {
            super(itemView);
            this.item = itemView.findViewById(R.id.itemTextView);
        }

        public void setContents(int pos) {
            String text = viewModel.items.get(pos);
            item.setText(text);
        }
    }


    @NonNull
    @Override
    public MyRecyclerAdapter2.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHolder(View를 담는 상자) 생성
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_recyclerview2, parent, false);
        ViewHolder2 viewHolder = new ViewHolder2(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
        // ViewHolder에 데이터 연결
        String text = viewModel.items.get(position);
        holder.setContents(position);
    }

    @Override
    public int getItemCount() {
        return viewModel.getItemSize();
    }
}
