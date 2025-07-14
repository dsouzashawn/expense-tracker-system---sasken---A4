package com.example.camera;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity {

    private Spinner typeSpinner, categorySpinner;
    private EditText amountInput, dateInput;
    private Button saveButton;
    private DatabaseHelper dbHelper;

    String[] incomeCategories = {"Salary", "Business", "Gift", "Rent"};
    String[] expenseCategories = {"Food", "Shopping", "Entertainment", "Utilities"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        dbHelper = new DatabaseHelper(this);
        initViews();
        setupTypeSpinner();
        setupDatePicker();
        setupSaveButton();
    }

    private void initViews() {
        typeSpinner = findViewById(R.id.typeSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        amountInput = findViewById(R.id.amountInput);
        dateInput = findViewById(R.id.dateInput);
        saveButton = findViewById(R.id.saveButton);

        // Disable keyboard on date input
        dateInput.setFocusable(false);
        dateInput.setCursorVisible(false);
    }

    private void setupTypeSpinner() {
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Income", "Expense"});
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = typeSpinner.getSelectedItem().toString();
                ArrayAdapter<String> catAdapter = new ArrayAdapter<>(AddTransactionActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        selected.equals("Income") ? incomeCategories : expenseCategories);
                categorySpinner.setAdapter(catAdapter);
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupDatePicker() {
        dateInput.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this,
                    (view, year, month, day) -> {
                        // Format to yyyy-MM-dd
                        String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
                        dateInput.setText(formattedDate);
                    }, y, m, d);
            dialog.show();
        });
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> {
            String type = typeSpinner.getSelectedItem().toString();
            String category = categorySpinner.getSelectedItem().toString();
            String amountStr = amountInput.getText().toString();
            String date = dateInput.getText().toString();

            if (amountStr.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                dbHelper.insertTransaction(type, category, amount, date);
                Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show();
                finish(); // Return to MainActivity
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
