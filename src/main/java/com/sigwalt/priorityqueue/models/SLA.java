package com.sigwalt.priorityqueue.models;

public class SLA {
    private Integer minutes;
    private Integer hours;
    private Integer days;
    private Integer priorityIncrease;


    public SLA(Integer minutes, Integer hours, Integer days, Integer priorityIncrease) {
        this.minutes = minutes;
        this.hours = hours;
        this.days = days;
        this.priorityIncrease = priorityIncrease;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Integer getHours() {
        return hours;
    }

    public Integer getDays() {
        return days;
    }

    public Integer getPriorityIncrease() {
        return priorityIncrease;
    }
}
