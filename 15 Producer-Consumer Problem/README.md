# 15 Exercises: Producer-Consumer Problem

## 15.01 Basic Producer-Consumer Implementation

**Objective:** Implement a simple Producer-Consumer system using `wait()` and `notify()`.

**Description:**
- Create two classes, `Producer` and `Consumer`, that share a common buffer of fixed size.
- The `Producer` should generate an item (e.g., a random integer) and place it in the buffer if there is space; otherwise, it should wait until there is space.
- The `Consumer` should retrieve items from the buffer; if the buffer is empty, it should wait until an item is available.
- Use `wait()` and `notifyAll()` to manage the synchronization.

**Requirements:**
- Ensure proper handling of the buffer with mutual exclusion.
- Handle potential deadlocks and avoid busy waiting.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
class Buffer {
    private int item;
    private boolean available = false;

    public synchronized void put(int item) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.item = item;
        available = true;
        notifyAll();
    }

    public synchronized int get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1; // Indicate error or interruption.
            }
        }
        int item = this.item;
        available = false;
        notifyAll();
        return item;
    }
}

```
```java
class Producer extends Thread {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            buffer.put(i);
            System.out.println("Produced: " + i);
        }
    }
}

```
```java
class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Consumed: " + buffer.get());
        }
    }
}

```
```java
public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        producer.start();
        consumer.start();
    }
}
```

  </details>
</blockquote>

## 15.02 Handling Multiple Producers and Consumers

**Objective:** Extend the basic Producer-Consumer problem to handle multiple producers and consumers.

**Description:**
- Modify the previous exercise to support multiple `Producer` and `Consumer` threads operating on the same shared buffer.
- Ensure that the program works correctly with more than one producer and consumer.

**Requirements:**
- Implement proper synchronization to handle multiple threads correctly.
- Test for race conditions and ensure no data corruption occurs.

<blockquote>
  <details>
    <summary>Display solution...</summary>
    
```java
    class Buffer {
    private final LinkedList<Integer> list = new LinkedList<>();
    private final int capacity = 10;

    public synchronized void put(int item) throws InterruptedException {
        while (list.size() == capacity) {
            wait();
        }
        list.add(item);
        System.out.println("Produced: " + item);
        notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (list.isEmpty()) {
            wait();
        }
        int item = list.removeFirst();
        System.out.println("Consumed: " + item);
        notifyAll();
        return item;
    }
}
```
```java
class Producer extends Thread {
    private Buffer buffer;
    private int start;

    public Producer(Buffer buffer, int start) {
        this.buffer = buffer;
        this.start = start;
    }

    public void run() {
        try {
            for (int i = start; i < start + 50; i++) {
                buffer.put(i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```
```java
class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            for (int i = 0; i < 50; i++) {
                buffer.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer producer1 = new Producer(buffer, 0);
        Producer producer2 = new Producer(buffer, 50);
        Consumer consumer1 = new Consumer(buffer);
        Consumer consumer2 = new Consumer(buffer);
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
    }
}
```

  </details>
</blockquote>

## 15.03 Producer-Consumer with Priority

**Objective:** Implement a priority system where certain producers or consumers have higher priority over others.

**Description:**
- Extend the multi-threaded Producer-Consumer model to include priority levels for producers and consumers.
- High-priority producers can jump the queue to add items to the buffer, and high-priority consumers can similarly retrieve items first.

**Requirements:**
- Modify the synchronization objects to handle priority.
- Ensure that the system is fair and does not starve any producers or consumers indefinitely.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
import java.util.PriorityQueue;
import java.util.Comparator;

class Buffer {
    private PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
    private final int capacity = 10;

    public synchronized void put(int item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.offer(item);
        System.out.println("Produced (priority): " + item);
        notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int item = queue.poll();
        System.out.println("Consumed (priority): " + item);
        notifyAll();
        return item;
    }
}
```
```java
class Producer extends Thread {
    private Buffer buffer;
    private int priority;

    public Producer(Buffer buffer, int priority) {
        this.buffer = buffer;
        this.priority = priority;
    }

    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                buffer.put(priority * 10); // Multiplying to illustrate priority effect
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```
```java
class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("Consumed: " + buffer.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer highPriorityProducer = new Producer(buffer, 2);
        Producer lowPriorityProducer = new Producer(buffer, 1);
        Consumer consumer = new Consumer(buffer);
        highPriorityProducer.start();
        lowPriorityProducer.start();
        consumer.start();
    }
}
```
  </details>
</blockquote>

## 15.04 Implementing Producer-Consumer Using Different Mechanisms

**Objective:** Implement the Producer-Consumer problem using different synchronization tools available in Java.

**Description:**
- Create several versions of the Producer-Consumer problem using different synchronization constructs such as `ReentrantLock` and `Condition`, `Semaphore`, and the `BlockingQueue` interface from the `java.util.concurrent` package.

**Requirements:**
- Each implementation should correctly synchronize access to the shared resource.
- Discuss the pros and cons of each synchronization mechanism in terms of complexity, performance, and readability.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// Using ReentrantLock and Condition
class BufferLockCondition {
    private final int[] buffer = new int[10];
    private int count = 0, putIndex = 0, getIndex = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void put(int value) throws InterruptedException {
        lock.lock();
        try {
            while (count == buffer.length) {
                notFull.await();
            }
            buffer[putIndex] = value;
            putIndex = (putIndex + 1) % buffer.length;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int get() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            int value = buffer[getIndex];
            getIndex = (getIndex + 1) % buffer.length;
            count--;
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }
}
```
```java
// Using Semaphore
class BufferSemaphore {
    private final int[] buffer = new int[10];
    private int count = 0, putIndex = 0, getIndex = 0;
    private final Semaphore full = new Semaphore(0);
    private final Semaphore empty = new Semaphore(10);

    public void put(int value) throws InterruptedException {
        empty.acquire();
        buffer[putIndex] = value;
        putIndex = (putIndex + 1) % buffer.length;
        count++;
        full.release();
    }

    public int get() throws InterruptedException {
        full.acquire();
        int value = buffer[getIndex];
        getIndex = (getIndex + 1) % buffer.length;
        count--;
        empty.release();
        return value;
    }
}
```
```java
// Using BlockingQueue
class BufferBlockingQueue {
    private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public void put(int value) throws InterruptedException {
        queue.put(value);
    }

    public int get() throws InterruptedException {
        return queue.take();
    }
}
```
```java
public class Main {
    public static void main(String[] args) throws Exception {
        BufferLockCondition bufferLC = new BufferLockCondition();
        BufferSemaphore bufferS = new BufferSemaphore();
        BufferBlockingQueue bufferBQ = new BufferBlockingQueue();

        // Example usage of BufferLockCondition
        // Example usage of BufferSemaphore
        // Example usage of BufferBlockingQueue
    }
}
```

  </details>
</blockquote>

## 15.05 Producer-Consumer with Logging and Monitoring

**Objective:** Add logging and monitoring to the Producer-Consumer system to track system performance and behavior.

**Description:**
- Enhance any previous Producer-Consumer implementation to include logging of key actions (item produced, item consumed, waiting for space, etc.).
- Add functionality to monitor the buffer's state over time and report statistics like average wait time, production rate, and consumption rate.

**Requirements:**
- Implement a simple logging mechanism using `System.out.println` or any logging framework.
- Collect and display statistics about the system's operation, potentially using a simple GUI or console output.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity = 10;
    private int totalItemsProduced = 0;
    private int totalItemsConsumed = 0;
    private long startTime;

    public Buffer() {
        startTime = System.currentTimeMillis();
    }

    public synchronized void put(int item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(item);
        totalItemsProduced++;
        System.out.println("Produced: " + item + ", Total Produced: " + totalItemsProduced);
        notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int item = queue.remove();
        totalItemsConsumed++;
        System.out.println("Consumed: " + item + ", Total Consumed: " + totalItemsConsumed);
        notifyAll();
        return item;
    }

    public void printStatistics() {
        long endTime = System.currentTimeMillis();
        double elapsedTime = (endTime - startTime) / 1000.0;
        System.out.println("Elapsed Time: " + elapsedTime + "s");
        System.out.println("Items Produced: " + totalItemsProduced + ", Items Consumed: " + totalItemsConsumed);
    }
}
```
```java
class Producer extends Thread {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                buffer.put(i);
                Thread.sleep(10); // Simulate time-consuming production process
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```
```java
class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                buffer.get();
                Thread.sleep(10); // Simulate time-consuming consumption process
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        buffer.printStatistics();
    }
}
```
  </details>
</blockquote>