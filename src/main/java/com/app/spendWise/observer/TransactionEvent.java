package com.app.spendWise.observer;

import com.app.spendWise.entity.Transaction;
import lombok.Getter;

@Getter
public class TransactionEvent {
    private final Transaction transaction;
    private final TransactionEventType eventType;

    public TransactionEvent(Transaction transaction, TransactionEventType eventType) {
        this.transaction = transaction;
        this.eventType = eventType;
    }

}
