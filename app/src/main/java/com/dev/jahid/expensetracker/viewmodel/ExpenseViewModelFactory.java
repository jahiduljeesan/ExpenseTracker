package com.dev.jahid.expensetracker.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ExpenseViewModelFactory implements ViewModelProvider.Factory {


   private Context context;

   public ExpenseViewModelFactory(Context context) {
       this.context = context;
   }

   @NonNull
   @Override
   public <T extends ViewModel> T create(@NonNull Class<T> viewModelClass) {
       if (viewModelClass.isAssignableFrom(ExpenseViewModel.class)) {
           return (T) ExpenseViewModel.getInstance(context);
       }
       throw new IllegalArgumentException("Unknown viewmodel class");
   }

}
