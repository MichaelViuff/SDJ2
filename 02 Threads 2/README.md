# 02 Exercises: Threads 2

## 2.1 Counter exercises

### 2.1.1 Updating a shared resource, revisited

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

### 2.1.2 Two counts

Modify the solution to exercise 2.1 The `Count` class should now have two attributes for count - A and B (similar to the example shown in the presentation).

Use Lock objects to synchronize the critical code.

Create a couple of threads to update the two counters. Verify the output is as expected.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  Instead of using the intrinsic lock of the object, we should define our own locks, so the two counts can be updated without blocking each other.

  We can use the <code>ReentrantLock</code> class, or any other <code>Object</code> for that matter, to define the two locks.

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

### 2.1.3 `try` lock

Change the first counter example, so this time it looks like the example on slide 30, with `try` lock, and you use the `tryLock()` method. Initially, just do it like the slide, so whenever the lock could not be acquired, it's printed out.

You should see that once again, we don’t reach 2,000,000, because not all the time can the lock be acquired. You should also see a bunch of print outs.

- Try to fix it, so if the lock cannot be obtained, you wait 1 ms and try again.
- How do you wait 1 ms?
- What if you cannot acquire the lock the second time? What about the third time?
- How to do something multiple times?


## 2.2 Synchronized list


**2.2 Synchronized list**
Create a class, ListContainer. It has a private field of type List<Integer>, instantiate it as ArrayList<Integer>.

Create a method, add(int i), which adds the integer to the List.

Create a method to get the length of the List.

Ignore synchronization for now.

Create a Runnable class, which has a reference to the ListContainer. It should insert the numbers from 0 to 100000 into the ListContainer. Then print out the count.

In a main method, create 2 threads to run two instances of your Runnable class.

Do you get the printed count you expect?

Implement synchronization.


