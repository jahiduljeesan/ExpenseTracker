package com.dev.jahid.expensetracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.jahid.expensetracker.adapter.ExpenseListAdapter;
import com.dev.jahid.expensetracker.databinding.FragmentDataBinding;
import com.dev.jahid.expensetracker.viewmodel.ExpenseViewModel;
import com.dev.jahid.expensetracker.viewmodel.ExpenseViewModelFactory;

public class DataFragment extends Fragment {

    private FragmentDataBinding binding;
    private String listTag;
    private ExpenseListAdapter adapter;

    public DataFragment(String listTag) {
        this.listTag = listTag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDataBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ListCheck","hi we are in data fragment");
        binding.dataList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ExpenseListAdapter();
        binding.dataList.setAdapter(adapter);
        initArrays();
    }

    private void initArrays() {
        ExpenseViewModel viewModel = new ViewModelProvider(requireActivity(),
                new ExpenseViewModelFactory(requireContext())).get(ExpenseViewModel.class);
        if (listTag.equals("All")) {
            viewModel.getAllData().observe(getViewLifecycleOwner(),allList -> {
                adapter.setExpenseModelList(allList,"All");
            });
        } else if (listTag.equals("Expense")) {
            viewModel.getAllExpenses().observe(getViewLifecycleOwner(),expenseList -> {
                adapter.setExpenseModelList(expenseList,"Expense");
            });
        } else {
            viewModel.getAllIncome().observe(getViewLifecycleOwner(),incomeList -> {
                adapter.setExpenseModelList(incomeList,"Income");
            });
        }
    }
}