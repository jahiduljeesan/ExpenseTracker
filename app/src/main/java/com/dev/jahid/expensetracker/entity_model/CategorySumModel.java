package com.dev.jahid.expensetracker.entity_model;

public class CategorySumModel {
    public String category;
    public long amount;

    public CategorySumModel(String category, long amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
