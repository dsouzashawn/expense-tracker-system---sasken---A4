package com.example.camera;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    private DatabaseHelper dbHelper;
    private String startDate = "", endDate = "";

    private TextView txtSelectedStartDate;
    private TextView txtSelectedEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        pieChart = findViewById(R.id.pieChart);
        dbHelper = new DatabaseHelper(this);

        Button btnStart = findViewById(R.id.btnStartDate);
        Button btnEnd = findViewById(R.id.btnEndDate);
        Button btnApply = findViewById(R.id.btnApplyFilter);

        txtSelectedStartDate = findViewById(R.id.txtSelectedStartDate);
        txtSelectedEndDate = findViewById(R.id.txtSelectedEndDate);

        btnStart.setOnClickListener(v -> showDatePicker(true));
        btnEnd.setOnClickListener(v -> showDatePicker(false));

        btnApply.setOnClickListener(v -> {
            if (!startDate.isEmpty() && !endDate.isEmpty()) {
                loadChartWithDates(startDate, endDate);
            }
        });

        loadChart(); // default full chart
    }

    private void showDatePicker(boolean isStart) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog picker = new DatePickerDialog(this, (view, year, month, day) -> {
            String date = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day);

            if (isStart) {
                startDate = date;
                txtSelectedStartDate.setText("Start: " + date);
            } else {
                endDate = date;
                txtSelectedEndDate.setText("End: " + date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        picker.show();
    }

    private void loadChartWithDates(String startDate, String endDate) {
        Map<String, Float> data = dbHelper.getCategoryWiseExpenseSumBetween(startDate, endDate);
        drawChart(data, "Expenses\n" + startDate + " to " + endDate);
    }

    private void loadChart() {
        Map<String, Float> data = dbHelper.getCategoryWiseExpenseSum();
        drawChart(data, "Expense Breakdown");
    }

    private void drawChart(Map<String, Float> data, String centerText) {
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : data.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{
                Color.parseColor("#FF6F61"),
                Color.parseColor("#6B5B95"),
                Color.parseColor("#88B04B"),
                Color.parseColor("#F7CAC9"),
                Color.parseColor("#92A8D1")
        });

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(14f);

        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText(centerText);
        pieChart.setCenterTextSize(18f);

        Description description = new Description();
        description.setText("Expenses");
        pieChart.setDescription(description);

        pieChart.animateY(1000);
        pieChart.invalidate();
    }
}
