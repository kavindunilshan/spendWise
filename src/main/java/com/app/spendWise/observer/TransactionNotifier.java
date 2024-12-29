package com.app.spendWise.observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// publisher
@Component
public class TransactionNotifier {
    private final Map<TransactionEventType, List<TransactionObserver>> observersMap;

    public TransactionNotifier() {
        observersMap = new HashMap<>();
        for (TransactionEventType eventType : TransactionEventType.values()) {
            observersMap.put(eventType, new ArrayList<>());
        }
    }

    public void addObserver(TransactionEventType eventType, TransactionObserver observer) {
        observersMap.get(eventType).add(observer);
    }

    public void removeObserver(TransactionEventType eventType, TransactionObserver observer) {
        observersMap.get(eventType).remove(observer);
    }

    public void notifyObservers(TransactionEvent event) {
        System.out.println("Notifying observers");
        observersMap.get(event.getEventType()).forEach(observer -> observer.onTransactionEvent(event));
    }


}
