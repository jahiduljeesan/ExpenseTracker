package com.dev.jahid.expensetracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dev.jahid.expensetracker.dao.ExpenseDao;
import com.dev.jahid.expensetracker.entity_model.ExpenseModel;

@Database(entities = {ExpenseModel.class},version = 1)
public abstract class ExpenseDatabase extends RoomDatabase {

    public abstract ExpenseDao expenseDao();
    public static volatile ExpenseDatabase INSTANCE;

    public static ExpenseDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    ExpenseDatabase.class,"ExpenseDB")
                    .fallbackToDestructiveMigrationOnDowngrade(true)
                    .build();
        }
        return INSTANCE;
    }
}
