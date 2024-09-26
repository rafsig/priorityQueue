package com.sigwalt.priorityqueue.models;

import com.sigwalt.priorityqueue.exceptions.EmptyQueueException;
import com.sigwalt.priorityqueue.exceptions.InvalidPriorityException;

import java.time.LocalDateTime;
import java.util.ArrayDeque;

public class PriorityQueueBase<T> implements PriorityQueue<T> {

    private PriorityQueueData<T> queue;

    /** Creates a default Priority Queue with default throttle = 2 and SLA = {minute:0, hour:1, day:0, priorityIncrease:1 }.
     */
    public PriorityQueueBase() {
        queue = new PriorityQueueData<T>();
    }

    /**
     * Creates a default Priority Queue with custom throttle and default SLA = {minute:0, hour:1, day:0, priorityIncrease:1 }
     *
     * @param throttle
     */
    public PriorityQueueBase(int throttle) {
        queue = new PriorityQueueData<T>(throttle);
    }

    /**
     * Creates a default Priority Queue with custom throttle and SLA
     *
     * @param throttle
     * @param sla
     */
    public PriorityQueueBase(int throttle , SLA sla) {
        queue = new PriorityQueueData<T>(throttle);
    }

    /**
     * Creates a default Priority Queue with default throttle = 2 and custom SLA
     *
     * @param sla
     */
    public PriorityQueueBase(SLA sla) {
        queue = new PriorityQueueData<T>();
    }

    /**
     * Enqueues and item on the priority queue. Allows priorties starting to one up to integer max limit.
     * If priority lower than one is entered throws InvalidPriorityException.
     *
     * @param priority of the item being enqueued
     * @param value of the item being enqueued
     *
     * @throws InvalidPriorityException
     */
    public void enqueue(int priority, T value) {
        validatePriority(priority);

        QueueItem<T> item =  new QueueItem<>(value);
        ArrayDeque<QueueItem<T>> priorityList;
        if(!queue.containsPriority(priority)) {
            priorityList = queue.createPriorityList(priority);
        }
        else {
            priorityList = queue.getPriorityList(priority);
        }
        priorityList.add(item);
    }

    private void validatePriority(int priority) {
        if(priority < 1) {
            throw new InvalidPriorityException("Priority must be greater or equal to one");
        }
    }

    /**
     * Dequeues the next item based on throttle setting.
     *
     *
     * @return next item on queue
     *
     * @throws EmptyQueueException if queue is empty.
     */
    public QueueItem<T> dequeue() {
        if(queue.getSize() == 0) {
            throw new EmptyQueueException();
        }

        int currentPriority = updateCurrentPriority();

        ArrayDeque<QueueItem<T>> priorityItems = queue.getPriorityList(currentPriority);
        QueueItem<T> item = priorityItems.pop();
        if(!queue.removePriorityIfEmpty(currentPriority)) {
           queue.incrementPriorityCount(currentPriority);
        }

        return item ;
    }

    private int updateCurrentPriority(){
        Integer currentPriority = queue.getCurrentPriority();
        Integer throttle = queue.getThrottle();
        if(currentPriority == null || queue.getPriorityCount(currentPriority) == null) {
            currentPriority = getHighestPriority();
        }

        if(queue.getPriorityCount(currentPriority) < throttle) {
            currentPriority = getHighestPriority();
        }

        if(queue.getPriorityCount(currentPriority) >= throttle) {
            queue.updatePriorityCount(currentPriority, 0);
            currentPriority = getNextLowerPriority();
        }

        queue.setCurrentPriority(currentPriority);
        return currentPriority;
    }

    /**
     * Gets the added size of all priorities of the queue
     *
     * @return added size of all priorities
     */
    public int getSize() {
        return queue.getSize();
    }


    /**
     * Updates priorities of items based on the SLA setting and item last priority increase.
     * This method is meant to be used within a scheduled task, verifying items that are due and increasing their priorities.
     */
    public void updatePriorityForPastDueItems() {
        for(Integer priority : queue.getPriorities()) {
            if(priority == 1) {
                break;
            }
            int newPriority = calculateNewPriority(priority);
            ArrayDeque<QueueItem<T>> items = queue.getPriorityList(priority);
            for(QueueItem<T> item : items) {
                if(verifyIfSLAPastDue(item.getLastPriorityIncrease())) {
                    enqueue(newPriority, item.getItem());
                    items.remove(item);
                }
                else{
                    break;
                }
            }
            queue.removePriorityIfEmpty(priority);
        }
    }

    private int calculateNewPriority(int priority) {
        int newPriority = priority - queue.getSla().getPriorityIncrease();
        if(newPriority < 1){
            newPriority = 1;
        }
        return newPriority;
    }

    private boolean verifyIfSLAPastDue(LocalDateTime lastPriorityIncrease) {
        SLA sla = queue.getSla();
        return lastPriorityIncrease.plusMinutes(sla.getMinutes()).plusHours(sla.getHours()).plusDays(sla.getDays()).isAfter(LocalDateTime.now());
    }

    private Integer getHighestPriority() {
        return queue.getPriorities().stream().findFirst().orElse(null);
    }

    private Integer getNextLowerPriority(){
        return  queue.getPriorities().stream()
                .filter(priority -> priority > queue.getCurrentPriority())
                .findFirst()
                .orElse(getHighestPriority());
    }

    /**
     *
     * Returns the size of a priority list
     *
     * @param priority to be queried
     * @return size of the priority list or 0 if priority is not existent
     *
     * @throws InvalidPriorityException if priority is smaller than 1
     */
    public int getPrioritySize(int priority) {
        validatePriority(priority);

        ArrayDeque<QueueItem<T>> arrayDeque = queue.getPriorityList(priority);
        if(arrayDeque != null) {
            return arrayDeque.size();
        }
        return 0;
    }
}
