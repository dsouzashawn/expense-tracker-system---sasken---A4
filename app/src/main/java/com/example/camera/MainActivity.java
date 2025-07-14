package com.example.camera;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private TextView incomeText, expenseText, balanceText;
    private Button addButton, chartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        initViews();
        setupRecyclerView();
        setupButtons();
        loadTransactions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransactions(); // Refresh summary and list when returning
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        incomeText = findViewById(R.id.incomeText);
        expenseText = findViewById(R.id.expenseText);
        balanceText = findViewById(R.id.balanceText);
        addButton = findViewById(R.id.addButton);
        chartButton = findViewById(R.id.chartButton);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter();  // Set empty initially
        recyclerView.setAdapter(adapter);
    }

    private void setupButtons() {
        addButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddTransactionActivity.class))
        );

        chartButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ChartActivity.class))
        );
    }

    private void loadTransactions() {
        List<Transaction> transactionList = dbHelper.getAllTransactions();
        adapter.setTransactionList(transactionList);  // More efficient
        adapter.notifyDataSetChanged();

        double totalIncome = dbHelper.getTotalAmount("Income");
        double totalExpense = dbHelper.getTotalAmount("Expense");
        double balance = totalIncome - totalExpense;

        incomeText.setText("Income: ₹" + String.format("%.2f", totalIncome));
        expenseText.setText("Expense: ₹" + String.format("%.2f", totalExpense));
        balanceText.setText("Balance: ₹" + String.format("%.2f", balance));
    }
}
