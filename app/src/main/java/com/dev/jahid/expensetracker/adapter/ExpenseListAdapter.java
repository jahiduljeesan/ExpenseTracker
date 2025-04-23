package com.dev.jahid.expensetracker.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseVH> {

    
    @NonNull
    @Override
    public ExpenseListAdapter.ExpenseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapter.ExpenseVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ExpenseVH extends RecyclerView.ViewHolder {
        public ExpenseVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
