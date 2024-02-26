package com.transaction.entity;

public enum TransactionType {
    DEBIT_CARD("Debit Card"),
    CREDIT_CARD("Credit Card"),
    UPI("UPI"),
	WALLET("WALLET");

    private String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}