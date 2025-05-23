package com.dev.jahid.expensetracker.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.dev.jahid.expensetracker.dao.ExpenseDao;
import com.dev.jahid.expensetracker.database.ExpenseDatabase;
import com.dev.jahid.expensetracker.entity_model.CategorySumModel;
import com.dev.jahid.expensetracker.entity_model.ExpenseModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseRepository {

    public static ExpenseDao expenseDao;
    public static ExecutorService executorService;
    public static volatile ExpenseRepository INSTANCE;

    public static ExpenseRepository getInstance(Context context) {
        if (INSTANCE == null) {
            expenseDao = ExpenseDatabase.getInstance(context).expenseDao();
            executorService = Executors.newSingleThreadExecutor();
            INSTANCE = new ExpenseRepository();
        }
        return INSTANCE;
    }

    public void setExpense(ExpenseModel expenseModel) {
        executorService.execute(() -> {
            expenseDao.addExpense(expenseModel);
        } );
    }

    public LiveData<List<ExpenseModel>> getAllData() {
        return expenseDao.getAllData();
    }

    public LiveData<List<ExpenseModel>> getAllExpenses() {
        return expenseDao.getAllExpense();
    }

    public LiveData<List<ExpenseModel>> getAllIncome() { return expenseDao.getAllIncome(); };

    public LiveData<Long> getTotalIncome() {
        return expenseDao.getTotalIncome();
    }

    public LiveData<Long> getTotalExpense() {
        return expenseDao.getTotalExpense();
    }

    public LiveData<List<CategorySumModel>> getExpenseSumByCategory() {
        return expenseDao.getExpenseSumByCategory();
    }

    public LiveData<Long> getRemainingBalance() {
        return expenseDao.getRemainingBalance();
    }
}
