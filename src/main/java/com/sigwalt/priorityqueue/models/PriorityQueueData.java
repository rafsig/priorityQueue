package com.sigwalt.priorityqueue.models;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriorityQueueData<T> {
    private Map<Integer, ArrayDeque<QueueItem<T>>> queue = new HashMap<>();

    private Map<Integer, Integer> priorityCount = new HashMap<>();

    private Integer currentPriority;

    private Integer throttle = 2;

    private SLA sla = new SLA(0, 1, 0, 1);

    public PriorityQueueData() {
    }

    public PriorityQueueData(Integer throttle) {
        this.throttle = throttle;
    }

   public boolean containsPriority(int priority) {
        return queue.containsKey(priority);
   }

   public ArrayDeque<QueueItem<T>> createPriorityList(int priority) {
       ArrayDeque<QueueItem<T>> priorityList = new ArrayDeque<>();
       queue.put(priority, priorityList);
       priorityCount.put(priority, 0);
       return priorityList;
   }

   public ArrayDeque<QueueItem<T>> getPriorityList(int priority) {
        return queue.get(priority);
   }

    public Integer getCurrentPriority() {
        return currentPriority;
    }

    public void setCurrentPriority(Integer currentPriority) {
        this.currentPriority = currentPriority;
    }

    public Integer getPriorityCount(int priority) {
        return this.priorityCount.get(priority);
    }

    public Integer getThrottle() {
        return throttle;
    }

    public void setThrottle(Integer throttle) {
        this.throttle = throttle;
    }

    public int getSize() {
        int size = 0;
        for(Integer key : queue.keySet()){
            size += queue.get(key).size();
        }
        return size;
    }

    public boolean removePriorityIfEmpty(int priority) {
        if(queue.containsKey(priority) && queue.get(priority).size() == 0) {
            queue.remove(priority);
            priorityCount.remove(priority);
            return true;
        }
        return false;
    }

    public void updatePriorityCount(int priority, int count) {
        priorityCount.put(priority, count);
    }

    public List<Integer> getPriorities() {
        return queue.keySet().stream().toList();
    }

    public SLA getSla() {
        return sla;
    }

    public void incrementPriorityCount(int priority) {
        priorityCount.put(priority, priorityCount.get(priority) + 1);
    }

}
