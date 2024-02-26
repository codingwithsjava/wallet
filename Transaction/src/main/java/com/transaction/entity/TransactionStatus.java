package com.transaction.entity;

public enum TransactionStatus {
    SUCCESS("Success"),
    FAILED("Failed"),
    PENDING("Pending"),
    REFUND("Refund");

    private String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}