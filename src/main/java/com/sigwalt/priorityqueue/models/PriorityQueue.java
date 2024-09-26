package com.sigwalt.priorityqueue.models;

import java.util.ArrayDeque;

public interface PriorityQueue<T> {

    public void enqueue(int priority, T value);

    public QueueItem<T> dequeue();

    public int getSize();

    public int getPrioritySize(int priority);

    public void updatePriorityForPastDueItems();

}
