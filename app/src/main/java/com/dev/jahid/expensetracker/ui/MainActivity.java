package com.dev.jahid.expensetracker.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.dev.jahid.expensetracker.adapter.DataViewPagerAdapter;
import com.dev.jahid.expensetracker.R;
import com.dev.jahid.expensetracker.databinding.ActivityMainBinding;
import com.dev.jahid.expensetracker.entity_model.CategorySumModel;
import com.dev.jahid.expensetracker.entity_model.ExpenseModel;
import com.dev.jahid.expensetracker.repository.ExpenseRepository;
import com.dev.jahid.expensetracker.viewmodel.ExpenseViewModel;
import com.dev.jahid.expensetracker.viewmodel.ExpenseViewModelFactory;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String category = "",type = "Expense",remark = "";
    private long amount = 0;
    private ExpenseRepository expenseRepo;
    private ExpenseViewModel expenseViewModel;
    private String[] categoryTypeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initializing category type list;
        categoryTypeList = getResources().getStringArray(R.array.category_list);

        //initializing multiple instance of DataModel fragment
        initViewpager();

        expenseViewModel = new ViewModelProvider(this,new ExpenseViewModelFactory(this))
                .get(ExpenseViewModel.class);
        expenseRepo = ExpenseRepository.getInstance(this);
        // initializing pi chart
        expenseViewModel.getRemainingBalance().observe(this,balance -> {
            initPiChart(balance);
            if (isAbsulate(balance)) {
                binding.tvRemainingBalance.setTextColor(ContextCompat.getColor(this,R.color.color_primary_dark));
            } else {
                binding.tvRemainingBalance.setTextColor(ContextCompat.getColor(this,R.color.red));
            }
            binding.tvRemainingBalance.setText(balance.toString()+" /=");
        });

        expenseViewModel.getTotalExpense().observe(this,expense -> {
            binding.tvTotalExp.setText(expense.toString()+"/=");
        });

        expenseViewModel.getTotalIncome().observe(this,income -> {
            binding.tvTotalInc.setText(income.toString()+"/=");
        });

        binding.btnExp.setOnClickListener(v -> showBottomSheet(0));
        binding.btnInc.setOnClickListener(v -> showBottomSheet(1));

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

    private boolean isAbsulate(Long balance) {
        return balance == Math.abs(balance);
    }

    private void initPiChart(long balance) {
        BarChart barChart = binding.barChart;

        expenseViewModel.getExpenseSumByCategory().observe(this, remainingBalanceList -> {
            // Define unique colors for each category
            int[] categoryColors = new int[]{
                    Color.parseColor("#FF6B6B"), // red
                    Color.parseColor("#FFD93D"), // yellow
                    Color.parseColor("#6BCB77"), // green
                    Color.parseColor("#4D96FF"), // blue
                    Color.parseColor("#9D4EDD"), // purple
                    Color.parseColor("#FF9671"), // orange
                    Color.parseColor("#00C9A7")  // teal
            };

            // Prepare data for the chart
            List<BarEntry> entries = new ArrayList<>();
            List<String> labels = new ArrayList<>();

            // Loop to fill entries and labels with data from the database
            for (int i = 0; i < remainingBalanceList.size(); i++) {
                CategorySumModel cm = remainingBalanceList.get(i);
                entries.add(new BarEntry(i, cm.amount)); // Add data for the bar
                labels.add(cm.category); // Add label for each bar
            }

            // Create a BarDataSet and apply the category colors
            BarDataSet dataSet = new BarDataSet(entries, ""); // Empty string to remove the title
            dataSet.setColors(categoryColors); // Set the bar colors here
            dataSet.setValueTextColor(Color.BLACK); // Set text color on top of the bars
            dataSet.setValueTextSize(10f); // Set text size for the values on top of bars

            // Set the BarData for the chart
            BarData barData = new BarData(dataSet);
            barData.setBarWidth(0.7f); // Set bar width
            barChart.setData(barData); // Set data to the bar chart

            // Configure X Axis
            XAxis xAxis = barChart.getXAxis();
            xAxis.setYOffset(10f); // Move labels a little upward
            xAxis.setTextSize(10f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(true);
            xAxis.setGridColor(Color.parseColor("#DDDDDD"));
            xAxis.setGranularity(1f); // To ensure each bar is correctly labeled
            xAxis.setLabelCount(labels.size());
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels)); // Set labels to X Axis

            // Configure Y Axis
            YAxis yAxisLeft = barChart.getAxisLeft();
            YAxis yAxisRight = barChart.getAxisRight();
            yAxisLeft.setTextColor(Color.BLACK);
            yAxisLeft.setGridColor(Color.parseColor("#EEEEEE"));
            yAxisRight.setEnabled(false); // Disable the right Y Axis

            // Configure Legend
            Legend legend = barChart.getLegend();
            legend.setEnabled(false); // Disable the legend (if not needed)

            // Final chart settings
            barChart.setDrawGridBackground(false); // No grid in the background
            barChart.setDescription(null); // Remove description text
            barChart.animateY(1200); // Animate the chart with Y-axis animation
            barChart.setDoubleTapToZoomEnabled(false); // Disable double-tap zoom
            barChart.setScaleEnabled(false);
            barChart.setExtraBottomOffset(12f); // or maybe 12f, adjust as needed
            barChart.invalidate(); // Refresh the chart
        });
    }


    private void showBottomSheet(int tabPosition) {

        if (tabPosition == 1) {
            categoryTypeList = getResources().getStringArray(R.array.income_category_list);
        } else {
            categoryTypeList = getResources().getStringArray(R.array.category_list);
        }

        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.data_add_bottomsheet,null);
        bottomSheet.setContentView(view);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.selectTab(tabLayout.getTabAt(tabPosition));
        TextInputLayout etlCategory = view.findViewById(R.id.etlCategory),
        etlAmount = view.findViewById(R.id.etlAmount);

        TextInputEditText etAmount = view.findViewById(R.id.etAmount),
                etRemark = view.findViewById(R.id.etRemark);

        AutoCompleteTextView etCategory = view.findViewById(R.id.etCategory);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getText().toString();
                Log.d("Type",type);
                etCategory.setAdapter(new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,getList(type)));

            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        etCategory.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,categoryTypeList));

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

     // categtory lengh is showing

        bottomSheet.show();
    }

    private String[] getList(String type) {
        if (type.equals("Expense")) {
            return  getResources().getStringArray(R.array.category_list);
        }
        else {
            return getResources().getStringArray(R.array.income_category_list);
        }
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

