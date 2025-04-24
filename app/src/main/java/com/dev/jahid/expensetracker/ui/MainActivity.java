package com.dev.jahid.expensetracker.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dev.jahid.expensetracker.adapter.DataViewPagerAdapter;
import com.dev.jahid.expensetracker.R;
import com.dev.jahid.expensetracker.databinding.ActivityMainBinding;
import com.dev.jahid.expensetracker.entity_model.ExpenseModel;
import com.dev.jahid.expensetracker.repository.ExpenseRepository;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String category = "",type = "Expense",remark = "";
    private long amount = 0;
    private ExpenseRepository expenseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewpager();

        expenseRepo = ExpenseRepository.getInstance(this);

        binding.btnExp.setOnClickListener(v -> showBottomSheet());

        //Handelling collapsing;
        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                if (Math.abs(verticalOffset) == totalScrollRange) {
                    binding.toolbar.setVisibility(View.VISIBLE); // collapsed
                } else if (verticalOffset == 0) {
                    binding.toolbar.setVisibility(View.INVISIBLE); // expended
                } else {
                    binding.toolbar.setVisibility(View.INVISIBLE); // somewhere in between
                }
            }
        });

    }

    private void showBottomSheet() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.data_add_bottomsheet,null);
        bottomSheet.setContentView(view);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        TextInputLayout etlCategory = view.findViewById(R.id.etlCategory),
        etlAmount = view.findViewById(R.id.etlAmount);

        TextInputEditText etAmount = view.findViewById(R.id.etAmount),
                etRemark = view.findViewById(R.id.etRemark);

        AutoCompleteTextView etCategory = view.findViewById(R.id.etCategory);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        String [] categoryList = getResources().getStringArray(R.array.category_list);

        ArrayAdapter categoryAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,categoryList);
        etCategory.setAdapter(categoryAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getText().toString();
                Log.d("SelectedTab",type);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnSubmit.setOnClickListener(v -> {
            category = etCategory.getText().toString();
            Log.d("SetDataCheck",category);
            if (category.equals("Category")) {
                etlCategory.setHelperText("Please select any category!");
                return;
            }
            amount = Long.parseLong(etAmount.getText().toString().trim());
            Log.d("SetDataCheck",String.valueOf(amount));
            if (amount == 0) {
                etlAmount.setHelperText("Please enter any amount!");
                return;
            }

            remark = etRemark.getText().toString().trim();
            Log.d("SetDataCheck",String.valueOf(amount));

            String dateTime = new SimpleDateFormat("dd MMM yyy hh:mm a",Locale.ENGLISH)
                    .format(new Date());

            ExpenseModel expenseModel = new ExpenseModel(category,dateTime,remark,type,amount);
            expenseRepo.setExpense(expenseModel);

            Toast.makeText(this, "Data is added successfully", Toast.LENGTH_SHORT).show();

        });

        Log.d("AdapterCount", String.valueOf(categoryAdapter.getCount()));

        Log.d("CategoryListLength",categoryList.length+""); // categtory lengh is showing

        bottomSheet.show();
    }



    private void initViewpager() {

        ArrayList<DataFragment> dataFragments = new ArrayList<>();

        // Creating multiple instance of Data Fragment for multiple uses of this fragment
        DataFragment all = new DataFragment("All");
        DataFragment expense = new DataFragment("Expense");
        DataFragment income = new DataFragment("Income");

        // adding all instance to to this array
        dataFragments.add(all);
        dataFragments.add(expense);
        dataFragments.add(income);

        DataViewPagerAdapter dataViewPagerAdapter = new DataViewPagerAdapter(getSupportFragmentManager(),dataFragments);
        binding.listViewPager.setAdapter(dataViewPagerAdapter);

        binding.listViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.homeTabLayout.selectTab(binding.homeTabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.homeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.listViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}