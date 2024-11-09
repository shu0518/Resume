package com.example.a0731;

public class HistoryRecord {
    private String recordType;
    private String date;
    private String customerName;

    public HistoryRecord(String recordType, String customerName, String date) {
        this.recordType = recordType;
        this.date = date;
        this.customerName = customerName;
    }

    public String getRecordType() {
        return recordType;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getDate() {
        return date;
    }
}
