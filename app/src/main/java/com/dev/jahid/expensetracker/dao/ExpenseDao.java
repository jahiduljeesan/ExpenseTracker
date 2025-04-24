package com.dev.jahid.expensetracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dev.jahid.expensetracker.entity_model.ExpenseModel;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    public void addExpense(ExpenseModel expenseModel);

    @Query("SELECT * FROM expense_table where type = 'Expense'")
    LiveData<List<ExpenseModel>> getAllExpense();

    @Query("SELECT * FROM expense_table")
    LiveData<List<ExpenseModel>> getAllData();
}
