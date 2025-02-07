# 02 Exercises: Threads 2

## 2.1 Synchronized list

Create a class, `ListContainer`. Give it an attribute of type `List<Integer>`. Instantiate it as `ArrayList<Integer>` in the constructor.

Create a method, `add(int i)`, which adds the integer to the list.

Create a method that returns the length of the list.

Ignore synchronization for now.

Create a `Runnable` class, which has a reference to the `ListContainer`. In the `run()` method it should insert the numbers from 0 to 100.000 into the `ListContainer`. Then print out the length of the list.

In a `main` method, create 2 threads to run two instances of your `Runnable` class, referencing the same `ListContainer`.

Do you get the printed count you expect? Probably not. Fix it using synchronization.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  The implementation of the <code>ArrayList<Integer></code> uses an array with indexing, and time slicing is causing issues with the indexes. To solve this, we need to ensure that only one thread can attempt to add something at a time (using a lock on the <code>add(int i)</code> method
</p>
<details>
<summary>Display solution...</summary>

```java
import java.util.ArrayList;
import java.util.List;

public class ListContainer
{
    private List<Integer> list;

    private ListContainer()
    {
        list = new ArrayList<Integer>();
    }

    public synchronized void add(int i)
    {
        list.add(i);
    }

    public int getLength()
    {
        return list.size();
    }
}
```

```java
public class Inserter implements Runnable
{
    private ListContainer listContainer;
    
    public Inserter(ListContainer listContainer)
    {
        this.listContainer = listContainer;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 100000; i++)
        {
            listContainer.add(i);
        }
        System.out.println(listContainer.getLength());
    }
}
```

```java
public class Test
{
    public static void main(String[] args)
    {
        //create 2 threads to run two instances of your Runnable class, referencing the same ListContainer.
        ListContainer listContainer = new ListContainer();
        Inserter inserter1 = new Inserter(listContainer);
        Inserter inserter2 = new Inserter(listContainer);

        Thread inserterThread1 = new Thread(inserter1);
        Thread inserterThread2 = new Thread(inserter2);
        inserterThread1.start();
        inserterThread2.start();
    }
}
```
</details>
</details>
</blockquote>


## 2.2 Counter exercises

### 2.2.1 Updating a shared resource, revisited

Implement exercise 1.9 from last time if you haven't already. 

Run it, and confirm that output is not (always) 2.000.000. We are losing increment operations as discussed in the presentation.

Use synchronization to fix this, so the code will always produce 2.000.000 as the output.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  We want to synchronize the <code>Counter</code> so only a single thread can access the <code>incrementCount</code> method at a time.

  To do so, we can synchronize only that method. It's easiest to just modify the <code>Counter</code> class directly, but you could also create a new class for the synchronized version (or maybe extend the old version).
</p>
<details>
<summary>Display solution...</summary>

```java
public class Counter
{
  private int count;

  public synchronized void incrementCount()
  {
    count++;
  }

  public int getCount()
  {
    return count;
  }
}
```

```java
public class CounterIncrementer implements Runnable
{
  private Counter counter;

  public CounterIncrementer(Counter counter)
  {
    this.counter = counter;
  }

  @Override public void run()
  {
    for (int i = 0; i < 1000000; i++)
    {
      counter.incrementCount();
    }
    System.out.println(counter.getCount());
  }
}
```

```java
public class Start
{
  public static void main(String[] args)
  {
    Counter counter = new Counter();

    CounterIncrementer counterIncrementer1 = new CounterIncrementer(counter);
    CounterIncrementer counterIncrementer2 = new CounterIncrementer(counter);

    Thread counterIncrementerThread1 = new Thread(counterIncrementer1);
    Thread counterIncrementerThread2 = new Thread(counterIncrementer2);
    counterIncrementerThread1.start();
    counterIncrementerThread2.start();
  }
}
```

</details>
</details>

</blockquote>

Run it a couple of times, to be sure you weren't just lucky.

Try both with synchronizing the method and using the synchronized block approach.

### 2.2.2 Two counts

Modify the solution to exercise 2.2.1 The `Count` class should now have two attributes for count - A and B (similar to the example shown in the presentation).

Use Lock objects to synchronize the critical code.

Create a couple of threads to update the two counters. Verify the output is as expected.

<blockquote>
<details>
<summary>Display hints...</summary>
  <p>
    Instead of using the intrinsic lock of the object, we should define our own locks, so the two counts can be updated without blocking each other.
  </p>
  <p>
    We can use the <code>ReentrantLock</code> class, or any other <code>Object</code> for that matter, to define the two locks.
  </p>

```java
  private Lock lockA = new ReentrantLock();
  private Lock lockB = new ReentrantLock();
```

  Synchronize the body of the increment methods for A and B using these locks.
</p>
<details>
<summary>Display solution...</summary>

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DoubleCounter
{
  private int countA;
  private int countB;

  private Lock lockA = new ReentrantLock();
  private Lock lockB = new ReentrantLock();

  public void incrementCountA()
  {
    synchronized (lockA)
    {
      countA++;
    }
  }

  public void incrementCountB()
  {
    synchronized (lockB)
    {
      countB++;
    }
  }

  public int getCountA()
  {
    synchronized (lockA)
    {
      return  countA;
    }
  }

  public int getCountB()
  {
    synchronized (lockB)
    {
      return  countB;
    }  
  }
}
```

</details>
</details>

</blockquote>

### 2.2.3 try lock

Modify your solution to exercise 2.2.2, with `try` lock, and the `tryLock()` method. You can use the example from the presentation:

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockCounter
{
  private int count;
  private Lock lock = new ReentrantLock();

  public void incrementCount()
  {
    if(lock.tryLock())
    {
      count++;
      lock.unlock();
    }
    else
    {
      System.out.println("Lock was in use");
    }
  }

  public synchronized int getCount()
  {
    return count;
  }
}
```

You should see that once again, we don’t reach 2.000.000. You should also see a bunch of "Lock was in use". Why?

<blockquote>
<details>
<summary>Explanation</summary>
  <p>
    Whenever a thread is assigned time on the CPU, but the lock is in use, it will spend time doing nothing (printing out "Lock was in use").
  </p>
</details>
</blockquote>

We could wait if the lock is use, and then try again. Let us wait 1 ms for now.

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockCounter
{
  private int count;
  private Lock lock = new ReentrantLock();

  public void incrementCount()
  {
    if(lock.tryLock())
    {
      count++;
      lock.unlock();
    }
    else
    {
      try
      {
        Thread.sleep(1);
        if(lock.tryLock())
        {
          count++;
          lock.unlock();
        }
      }
      catch(InterruptedException e)
      {
        throw new RuntimeException(e);
      } 
      System.out.println("Lock was in use");
    }
  }

  public synchronized int getCount()
  {
    return count;
  }
}
```

Did that fix it? What happens if you change the wait time to 10 ms?

You might start seeing the correct values, but the underlying issue still persists (try changing the count to 10.000.000 to see that it is still in fact a problem)

We need to keep trying until we actually get the lock.

Change your solution, so it doesn't only attempt to get the lock once, but instead keeps trying untill it succeeds.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  <code>lock.tryLock()</code> returns a boolean value. We can construct a while loop that runs as long as that value is false and keeps trying to acquire the lock.
</p>
<details>
<summary>Display solution...</summary>

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockCounter
{
  private int count;
  private Lock lock = new ReentrantLock();

  public void incrementCount()
  {
    while(!lock.tryLock())
    {
      try
      {
        System.out.println("Lock was in use, going to sleep");
        Thread.sleep(1);
      }
      catch (InterruptedException e)
      {
        throw new RuntimeException(e);
      }
    }
      count++;
      lock.unlock();
  }

  public synchronized int getCount()
  {
    return count;
  }
}
```
</details>
</details>
</blockquote>

## 2.3	Incrementer/Decrementer

Implement the UML class diagram below:

![Incrementer/Decrementer UML Class Diagram](https://github.com/MichaelViuff/SDJ2/blob/main/02%20Threads%202/Images/IncrementerDecrementerUML.png)

### Monitor Class: `Counter`

Make sure you implement the class `Counter` as a Monitor class (i.e. it has private attributes and all methods are synchronized).

Give it the following characteristics:

- A constructor setting `value` to 0 and `min` and `max` to the values of the two arguments.
- A method `increment()` incrementing the value by 1. If the value >= `max`, then the calling thread must wait, so here you need the guarded block approach. Remember to do the `notifyAll()` call too.
- A method `decrement()` decrementing the value by 1 (and let the calling thread wait if `value` <= `min`).
- A method `getValue()` returning the value.

### `Runnable` Classes: `CounterIncrementer` and `CounterDecrementer`

The `CounterIncrementer` and `CounterDecrementer` are the `Runnable` classes that will increment and decrement the value respectively.

Give them the following characteristics:

 - The classes `CounterIncrementer` and `CounterDecrementer` must implement the`Runnable` interface.
 - In their `run` method, create a loop that runs a number of times equal to the value of the `updates` attribute.
 - In the loops, call the `Counter` method `increment()` or `increment()` accordingly.
 - After the loop, print out the value of the counter.

### Main method

Implement a class with a `main` method in which you:

- Create a `Counter` object with a `max` value of 100, and pass it to two `CounterIncrementer` objects and two `CounterDecrementer` objects (all with the second argument set to 200, i.e. 200 number of updates).
- Create 4 threads with each of the 4 `Runnable` objects and start the 4 threads.

Insert a few print-statements in class `Counter` to see when it is being updated (and by which thread), e.g., insert something similar to the following when `value` is updated and when a thread is blocked:

```java
System.out.println(value + ": " + Thread.currentThread().getName());
```

You can give a `Thread` object a name with the `setName()` method.

Run the program a few times and inspect the output. Does the value ever go above or below the limits?

<blockquote>
<details>
<summary>Explanation</summary>
  <p>
    If you used an <code>if-statement</code> instead of a loop when checking to see if a thread should be set to wait, you will see this issue. This is because the thread checks the condition, and if the condition is true (i.e. it is not allowed to update value) it is set to wait. But, when it awakes, it doesn't check again, and instead just continues execution, updating the value regardless of the condition.
  </p>

  <p>
    To fix this, use a loop so that the thread checks again when it wakes up.
  </p>
</details>
</blockquote>

<blockquote>
<details>
<summary>Display solution...</summary>

```java
public class Counter
{
    private long value;
    private long max;
    private long min;

    public Counter(long min, long max)
    {
        value = 0;
        this.min = min;
        this.max = max;
    }

    public synchronized void increment()
    {
        while(value >= max)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        value++;
        System.out.println(value + ": " + Thread.currentThread().getName());
        notifyAll();
    }

    public synchronized void decrement()
    {
        while(value <= min)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        value--;
        System.out.println(value + ": " + Thread.currentThread().getName());
        notifyAll();
    }

    public synchronized long getValue()
    {
        return value;
    }
}
```

```java
public class CounterIncrementer implements Runnable
{
    private int updates;
    private Counter counter;

    public CounterIncrementer(Counter counter, int updates)
    {
        this.updates = updates;
        this.counter = counter;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < updates; i++)
        {
            counter.increment();
        }
        System.out.println(Thread.currentThread().getName() + " har finished, count is: " + counter.getValue());
    }
}
```

```java
public class CounterDecrementer implements Runnable
{
    private Counter counter;

    public CounterDecrementer(Counter counter, int updates)
    {
        this.updates = updates;
        this.counter = counter;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < updates; i++)
        {
            counter.decrement();
        }
        System.out.println(Thread.currentThread().getName() + " har finished, count is: " + counter.getValue());
    }
}
```

```java
public class Test
{
    public static void main(String[] args)
    {
        Counter counter = new Counter(0, 100);
        
        CounterIncrementer counterIncrementer1 = new CounterIncrementer(counter, 400);
        CounterIncrementer counterIncrementer2 = new CounterIncrementer(counter, 400);
        CounterDecrementer counterDecrementer1 = new CounterDecrementer(counter, 400);
        CounterDecrementer counterDecrementer2 = new CounterDecrementer(counter, 400);

        Thread counterIncrementerThread1 = new Thread(counterIncrementer1);
        Thread counterIncrementerThread2 = new Thread(counterIncrementer2);
        Thread counterDecrementerThread1 = new Thread(counterDecrementer1);
        Thread counterDecrementerThread2 = new Thread(counterDecrementer2);

        counterIncrementerThread1.setName("Incrementer 1");
        counterIncrementerThread2.setName("Incrementer 2");
        counterDecrementerThread1.setName("Decrementer 1");
        counterDecrementerThread2.setName("Decrementer 2");

        counterIncrementerThread1.start();
        counterIncrementerThread2.start();
        counterDecrementerThread1.start();
        counterDecrementerThread2.start();
    }
}
```
</details>
</blockquote>


