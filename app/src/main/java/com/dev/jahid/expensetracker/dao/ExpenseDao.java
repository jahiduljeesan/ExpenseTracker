package com.dev.jahid.expensetracker.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.dev.jahid.expensetracker.entity_model.ExpenseModel;

@Dao
public interface ExpenseDao {

    @Insert
    public void addExpense(ExpenseModel expenseModel);
}
