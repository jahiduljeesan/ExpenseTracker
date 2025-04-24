package com.dev.jahid.expensetracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dev.jahid.expensetracker.entity_model.CategorySumModel;
import com.dev.jahid.expensetracker.entity_model.ExpenseModel;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    public void addExpense(ExpenseModel expenseModel);

    @Query("SELECT * FROM expense_table WHERE type = 'Expense'")
    LiveData<List<ExpenseModel>> getAllExpense();

    @Query("SELECT * FROM expense_table")
    LiveData<List<ExpenseModel>> getAllData();

    @Query("SELECT " +
            "IFNULL((SELECT SUM(amount) FROM expense_table WHERE type = 'Income'), 0) - " +
            "IFNULL((SELECT SUM(amount) FROM expense_table WHERE type = 'Expense'), 0)")
    LiveData<Long> getRemainingBalance();

    @Query("SELECT SUM(amount) FROM expense_table WHERE type = 'Expense'")
    LiveData<Long> getTotalExpense();

    @Query("SELECT SUM(amount) FROM expense_table WHERE type = 'Income'")
    LiveData<Long> getTotalIncome();

    @Query("SELECT category, SUM(amount) AS amount FROM expense_table WHERE type = 'Expense' GROUP BY category")
    LiveData<List<CategorySumModel>> getExpenseSumByCategory();



}
