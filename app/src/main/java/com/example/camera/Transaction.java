package com.example.camera;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {
    private int id;
    private String type;
    private String category;
    private double amount;
    private String date;

    // Constructor
    public Transaction(int id, String type, String category, double amount, String date) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Getters
    public int getId() { return id; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }

    // toString() for debugging
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                '}';
    }

    // Parcelable implementation
    protected Transaction(Parcel in) {
        id = in.readInt();
        type = in.readString();
        category = in.readString();
        amount = in.readDouble();
        date = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeString(category);
        parcel.writeDouble(amount);
        parcel.writeString(date);
    }
}
