package com.dev.jahid.expensetracker.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dev.jahid.expensetracker.entity_model.ExpenseModel;
import com.dev.jahid.expensetracker.repository.ExpenseRepository;

import java.util.List;

public class ExpenseViewModel extends ViewModel {
    ExpenseRepository expenseRepo;
    public static volatile ExpenseViewModel INSTANCE;

    public ExpenseViewModel (Context context) {
        expenseRepo = ExpenseRepository.getInstance(context);
    }

    public static ExpenseViewModel getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new ExpenseViewModel(context);
        return INSTANCE;
    }

    public LiveData<List<ExpenseModel>> getAllExpenses() {
        return expenseRepo.getAllExpenses();
    }
    public LiveData<List<ExpenseModel>> getAllData() {
        return expenseRepo.getAllData();
    }
}
