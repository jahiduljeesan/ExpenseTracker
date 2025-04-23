package com.dev.jahid.expensetracker.entity_model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class ExpenseModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String category,dateTime,remark,type;
    private long amount;

    public ExpenseModel(String category, String dateTime, String remark, String type, long amount) {
        this.category = category;
        this.dateTime = dateTime;
        this.remark = remark;
        this.type = type;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
