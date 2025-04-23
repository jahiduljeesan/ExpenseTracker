package com.dev.jahid.expensetracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.jahid.expensetracker.adapter.ExpenseListAdapter;
import com.dev.jahid.expensetracker.databinding.FragmentDataBinding;

public class DataFragment extends Fragment {

    FragmentDataBinding binding;
    private String listTag;

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

        binding.dataList.setAdapter(new ExpenseListAdapter());
    }
}