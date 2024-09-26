package com.sigwalt.priorityqueue;

import com.sigwalt.priorityqueue.exceptions.EmptyQueueException;
import com.sigwalt.priorityqueue.exceptions.InvalidPriorityException;
import com.sigwalt.priorityqueue.models.PriorityQueue;
import com.sigwalt.priorityqueue.models.PriorityQueueBase;
import com.sigwalt.priorityqueue.models.QueueItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PriorityQueueServiceTest {

    PriorityQueue<Integer> priorityQueue;

    @BeforeEach
    public void setup() {
        priorityQueue = new PriorityQueueBase<>();
    }


    @Test
    public void canAddItemToQueue() {
        priorityQueue.enqueue(1,1);

        Assertions.assertEquals(1, priorityQueue.getSize());
    }

    @Test
    public void throwsInvalidPriorityExceptionWhenAddingPriorityLessThanOne() {
        Assertions.assertThrows(InvalidPriorityException.class, () -> priorityQueue.enqueue(0, 1));
    }

    @Test
    public void throwsEmptyQueueExceptionWhenNoDequeueingFromEmptyQueue() {
        Assertions.assertThrows(EmptyQueueException.class, () -> priorityQueue.dequeue());
    }

    @Test
    public void dequeueOneItem() {
        priorityQueue.enqueue(1,1);

        QueueItem<Integer> result = priorityQueue.dequeue();

        Assertions.assertEquals(1, result.getItem());
    }

    @Test
    public void throttlesEqualsTwoByDefaultOnDequeue() {
        priorityQueue.enqueue(1,1);
        priorityQueue.enqueue(1,2);
        priorityQueue.enqueue(1,3);
        priorityQueue.enqueue(2,4);

        QueueItem<Integer> result = priorityQueue.dequeue();
        Assertions.assertEquals(1, result.getItem());

        result = priorityQueue.dequeue();
        Assertions.assertEquals(2, result.getItem());

        result = priorityQueue.dequeue();
        Assertions.assertEquals(4, result.getItem());
    }

    @Test
    public void returnsToPreviousPriorityOnFourthExecution() {
        priorityQueue.enqueue(1,1);
        priorityQueue.enqueue(1,2);
        priorityQueue.enqueue(1,3);
        priorityQueue.enqueue(2,4);
        priorityQueue.enqueue(2,5);

        QueueItem<Integer> result = priorityQueue.dequeue();
        Assertions.assertEquals(1, result.getItem());

        result = priorityQueue.dequeue();
        Assertions.assertEquals(2, result.getItem());

        result = priorityQueue.dequeue();
        Assertions.assertEquals(4, result.getItem());

        result = priorityQueue.dequeue();
        Assertions.assertEquals(3, result.getItem());
    }

    @Test
    public void returnItemsOnCorrectOrder() {
        priorityQueue.enqueue(1,1);
        priorityQueue.enqueue(1,2);
        priorityQueue.enqueue(2,3);
        priorityQueue.enqueue(1,4);
        priorityQueue.enqueue(1,5);
        priorityQueue.enqueue(2,6);
        priorityQueue.enqueue(3,7);
        priorityQueue.enqueue(1,8);
        priorityQueue.enqueue(2,9);
        priorityQueue.enqueue(2,10);
        priorityQueue.enqueue(2,11);

        for( int i = 1; i <= 11; i++) {
            QueueItem<Integer> result = priorityQueue.dequeue();
            Assertions.assertEquals(i, result.getItem());
        }
    }

    @Test
    public void returnItemsOnCorrectOrderWithCustomThrottle() {
        priorityQueue = new PriorityQueueBase<>(3);

        priorityQueue.enqueue(1,1);
        priorityQueue.enqueue(1,2);
        priorityQueue.enqueue(1,3);
        priorityQueue.enqueue(2,4);
        priorityQueue.enqueue(1,5);
        priorityQueue.enqueue(1,6);
        priorityQueue.enqueue(1,7);
        priorityQueue.enqueue(2,8);
        priorityQueue.enqueue(1,9);
        priorityQueue.enqueue(3,10);
        priorityQueue.enqueue(4,11);


        for( int i = 1; i <= 11; i++) {
            QueueItem<Integer> result = priorityQueue.dequeue();
            Assertions.assertEquals(i, result.getItem());
        }
    }
    
    @Test
    public void  testWithRandomlyGeneratedQueue() {
        priorityQueue.enqueue(1,1);
        priorityQueue.enqueue(1,2);
        priorityQueue.enqueue(2,3);
        priorityQueue.enqueue(1,4);
        priorityQueue.enqueue(1,5);
        priorityQueue.enqueue(2,6);
        priorityQueue.enqueue(3,7);
        priorityQueue.enqueue(2,8);
        priorityQueue.enqueue(2,9);
        priorityQueue.enqueue(3,10);
        priorityQueue.enqueue(4,11);
        priorityQueue.enqueue(2,12);
        priorityQueue.enqueue(2,13);
        priorityQueue.enqueue(3,14);
        priorityQueue.enqueue(2,15);
        priorityQueue.enqueue(2,16);
        priorityQueue.enqueue(3,17);
        priorityQueue.enqueue(4,18);
        priorityQueue.enqueue(5,19);
        priorityQueue.enqueue(3,20);
        priorityQueue.enqueue(3,21);
        priorityQueue.enqueue(4,22);
        priorityQueue.enqueue(3,23);
        priorityQueue.enqueue(3,24);
        priorityQueue.enqueue(4,25);
        priorityQueue.enqueue(5,26);
        priorityQueue.enqueue(6,27);
        priorityQueue.enqueue(3,28);
        priorityQueue.enqueue(3,29);
        priorityQueue.enqueue(4,30);
        priorityQueue.enqueue(3,31);
        priorityQueue.enqueue(3,32);
        priorityQueue.enqueue(4,33);
        priorityQueue.enqueue(5,34);
        priorityQueue.enqueue(3,35);
        priorityQueue.enqueue(4,36);
        priorityQueue.enqueue(4,37);
        priorityQueue.enqueue(5,38);
        priorityQueue.enqueue(6,39);
        priorityQueue.enqueue(7,40);
        priorityQueue.enqueue(4,41);
        priorityQueue.enqueue(4,42);
        priorityQueue.enqueue(5,43);
        priorityQueue.enqueue(4,44);
        priorityQueue.enqueue(5,45);
        priorityQueue.enqueue(6,46);
        priorityQueue.enqueue(5,47);
        priorityQueue.enqueue(5,48);
        priorityQueue.enqueue(6,49);
        priorityQueue.enqueue(7,50);
        priorityQueue.enqueue(5,51);
        priorityQueue.enqueue(5,52);
        priorityQueue.enqueue(6,53);
        priorityQueue.enqueue(5,54);
        priorityQueue.enqueue(6,55);
        priorityQueue.enqueue(7,56);
        priorityQueue.enqueue(6,57);
        priorityQueue.enqueue(6,58);
        priorityQueue.enqueue(7,59);
        priorityQueue.enqueue(7,60);
        priorityQueue.enqueue(7,61);
        priorityQueue.enqueue(7,62);

        for( int i = 1; i <= 62; i++) {
            QueueItem<Integer> result = priorityQueue.dequeue();
            Assertions.assertEquals(i, result.getItem());
        }
    }
}
