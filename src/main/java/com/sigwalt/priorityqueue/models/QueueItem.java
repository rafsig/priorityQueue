package com.sigwalt.priorityqueue.models;

import java.time.LocalDateTime;

public class QueueItem <T>{

    public QueueItem(T item) {
        this.item = item;
        lastPriorityIncrease = LocalDateTime.now();
    }

    T item;

    LocalDateTime lastPriorityIncrease;

    public T getItem() {
        return item;
    }

    public LocalDateTime getLastPriorityIncrease() {
        return lastPriorityIncrease;
    }
}
