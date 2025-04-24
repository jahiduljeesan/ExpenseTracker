package com.dev.jahid.expensetracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.jahid.expensetracker.R;
import com.dev.jahid.expensetracker.entity_model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseVH> {

    private List<ExpenseModel> expenseModelList = new ArrayList<>();
    private Context context;
    private String tag="";

    public void setExpenseModelList(List<ExpenseModel> expenseModelList,String tag) {
        this.expenseModelList = expenseModelList;
        this.tag = tag;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseListAdapter.ExpenseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_style,parent,false);
        return new ExpenseVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapter.ExpenseVH holder, int position) {
        setData(holder,position);
    }

    private void setData(ExpenseVH holder, int position) {
        ExpenseModel expenseModel = expenseModelList.get(position);
        // setting category images according to category name;
        switch (expenseModel.getCategory()) {
            case "Meal": holder.imgCategory.setImageResource(R.drawable.ic_meal);
                break;

            case "Food": holder.imgCategory.setImageResource(R.drawable.ic_food);
                break;

            case "Bills": holder.imgCategory.setImageResource(R.drawable.ic_bill);
                break;

            case "Rent": holder.imgCategory.setImageResource(R.drawable.ic_rent);
                break;

            case "Medicine": holder.imgCategory.setImageResource(R.drawable.ic_medicine);
                break;

            case "Education": holder.imgCategory.setImageResource(R.drawable.ic_educaion);
                break;

            case "Travel": holder.imgCategory.setImageResource(R.drawable.ic_travel);
                break;

            case "Shopping": holder.imgCategory.setImageResource(R.drawable.ic_shopping);
                break;

            case "Beauty": holder.imgCategory.setImageResource(R.drawable.ic_beauty);
                break;

            case "Others": holder.imgCategory.setImageResource(R.drawable.ic_others);
                break;

            default: holder.imgCategory.setImageResource(R.drawable.ic_category);
                break;
        } // setting category images

        holder.tvCategory.setText(expenseModel.getCategory());
        holder.tvRemark.setText(expenseModel.getRemark());
        holder.tvDateTime.setText(expenseModel.getDateTime());
        // Setting amount color as per type
        if (expenseModel.getType().equals("Income")) {
            holder.tvAmount.setText("+"+String.valueOf(expenseModel.getAmount())+" BDT");
            holder.tvAmount.setTextColor(
                    ContextCompat.getColor(context,R.color.color_primary_dark));
        } else {
            holder.tvAmount.setText("-"+String.valueOf(expenseModel.getAmount())+" BDT");
            holder.tvAmount.setTextColor(
                    ContextCompat.getColor(context,R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    public class ExpenseVH extends RecyclerView.ViewHolder {
        TextView tvDateTime,tvCategory,tvRemark,tvAmount;
        ImageView imgCategory;
        public ExpenseVH(@NonNull View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            imgCategory = itemView.findViewById(R.id.imgCategory);
        }
    }
}
