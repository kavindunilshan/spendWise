package com.app.spendWise.observer;

// Subscriber
public interface TransactionObserver {
    void onTransactionEvent(TransactionEvent event);
}
