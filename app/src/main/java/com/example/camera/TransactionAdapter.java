package com.example.camera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<Transaction> transactionList;

    public TransactionAdapter() {
        this.transactionList = new ArrayList<>();
    }

    // Setter to update the list from MainActivity
    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.typeText.setText(transaction.getType());
        holder.categoryText.setText(transaction.getCategory());
        holder.amountText.setText("₹" + String.format("%.2f", transaction.getAmount()));
        holder.dateText.setText(transaction.getDate());

        // Optional: Color coding income/expense
        if ("Income".equalsIgnoreCase(transaction.getType())) {
            holder.amountText.setTextColor(0xFF2E7D32); // green
        } else {
            holder.amountText.setTextColor(0xFFC62828); // red
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView typeText, categoryText, amountText, dateText;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.typeText);
            categoryText = itemView.findViewById(R.id.categoryText);
            amountText = itemView.findViewById(R.id.amountText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }

}