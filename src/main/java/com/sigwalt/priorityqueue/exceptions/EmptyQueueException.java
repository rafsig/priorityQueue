package com.sigwalt.priorityqueue.exceptions;

public class EmptyQueueException extends RuntimeException{

    public EmptyQueueException() {
        super("Queue is empty");
    }
}
