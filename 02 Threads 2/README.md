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

Modify the solution to exercise 2.1.1 The `Count` class should now have two attributes for count - A and B (similar to the example shown in the presentation).

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

### 2.1.3 try lock

Modify your solution from the previous exercise, with `try` lock, and the `tryLock()` method. You can use the example from the presentation:

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


## 2.2 Synchronized list

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

</p>
<details>
<summary>Display solution...</summary>

```java

```

</details>
</details>

</blockquote>

## 2.3 Simulating the temperature in a room

The purpose for this exercise is to simulate a thermometer (transducer) measuring indoor temperatures. The following method may be used to simulate the temperature in a room (with or without a heater):

```java
/**
   * Calculating the temperature measured in one of two locations.
   * This includes a term from a heater (depending on location and
   * heaters power), and a term from an outdoor heat loss.
   * Values are only valid in the outdoor temperature range [-20; 20]
   * and when s, the number of seconds between each measurements are
   * between 4 and 8 seconds.
   *
   * @param t  the last measured temperature
   * @param p  the heaters power {0, 1, 2 or 3} where 0 is turned off,
   *    1 is low, 2 is medium and 3 is high
   * @param d  the distance between heater and measurements {1 or 7}
   *    where 1 is close to the heater and 7 is in the opposite corner
   * @param t0 the outdoor temperature (valid in the range [-20; 20])
   * @param s the number of seconds since last measurement [4; 8]
   * @return the temperature
   */
  public double temperature(double t, int p, int d, double t0, int s)
  {
    double tMax = Math.min(11 * p + 10, 11 * p + 10 + t0);
    tMax = Math.max(Math.max(t, tMax), t0);
    double heaterTerm = 0;
    if (p > 0)
    {
      double den = Math.max((tMax * (20 - 5 * p) * (d + 5)), 0.1);
      heaterTerm = 30 * s * Math.abs(tMax - t) / den;
    }
    double outdoorTerm = (t - t0) * s / 250.0;
    t = Math.min(Math.max(t - outdoorTerm + heaterTerm, t0), tMax);
    return t;
  }
```
We will use the method in a class `Thermometer` as shown in the UML diagram:


![Thermometer UML Class Diagram](https://github.com/MichaelViuff/SDJ2/blob/main/02%20Threads%202/Images/ThermometerUML.png)

Implement a `Runnable` class `Thermometer` exactly as shown in the class diagram - with the following notes:
- Copy/paste method `temperature` as shown and change the visibility to private.
- Instance variables `id` representing the name of the thermometer (e.g., "t1"), and `t` representing the current temperature.
- A constructor initializing both attributes.
- A `run` method (from interface `Runnable`) with an infinite loop, in which you:
  - Update temperature `t` calling method `temperature`. Use the last measured temperature `t` and `p=0`, `d=1`, `t0=0`, and `s=6` (i.e., distance to a heater is 1, heater power is 0, i.e., turned off, outdoor temperature is 0, and the number of seconds between each measurement is 6).
  - Print out the temperature `t` (and the `id`).
  - Sleep for 6 seconds (6000 milliseconds).

Implement another class with a `main` method, in which you
- Create a `Thermometer` object. Use "t1" for `id` and 15 for the initial temperature.
- Create a thread with the `Thermometer` as an argument, and start the thread.

Run the application and observe that the temperature slowly drops from 15 towards 0 (over time, the indoor temperature drops to the outdoor temperature when there is no heater).

Change the second argument calling method `temperature` (in the `run` method) to `p=2` (i.e., a heater turned on to power position 2) and observe that the temperature now increases from 15.

## 2.4	Incrementer/Decrementer

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

### Main Class

Implement a class with a `main` method in which you:

- Create a `Counter` object with a `max` value of 100, and pass it to two `CounterIncrementer` objects and two `CounterDecrementer` objects (all with the second argument set to 200, i.e. 200 number of updates).
- Create 4 threads with each of the 4 `Runnable` objects and start the 4 threads.

Insert a few print-statements in class `Counter` to see when it is being updated (and by which thread), e.g., insert something similar to the following when `value` is updated and when a thread is blocked:

```java
System.out.println(value + ": " + Thread.currentThread().getName());
```

You can give a `Thread` object a name with the `setName()` method.

Run the program a few times and inspect the output.

Now create a thread that prints out the result when the above threads are finished. You’re going to need the join() method.

## 2.5	Computer simulation

Implement the UML class diagram below:

![Incrementer/Decrementer UML Class Diagram](https://github.com/MichaelViuff/SDJ2/blob/main/02%20Threads%202/Images/ComputerUML.png)

### Runnable Class: `Program`

Implement the two `Runnable` classes: `Program` and `Mailbox` to be used in the class `Computer` to simulate a computer with independent notifications from programs and mailbox.

In the `Runnable` class `Program` (in the `run` method), the action is printed approximately `count` number of times in the `RUNTIME` length.

Example: `program1` shown in the `main` method below prints “Windows wants to update,” then sleeps for approximately `RUNTIME/30` milliseconds (could be a random number in a range you specify), print and sleep again, and so forth, a total of 30 times.

```java
public class RunComputer {
    public static void main(String[] args) {        
        Thread mailbox = new Thread(new Mailbox(20));        
        Thread program1 = new Thread(new Program("Windows", "update", 30));        
        Thread program2 = new Thread(new Program("AVG", "update virus database", 5));        
        Thread program3 = new Thread(new Program("FBackup", "perform a scheduled backup", 3));        
        Thread program4 = new Thread(new Program("Skype", "notify you about a person logging in", 17));       
        System.out.println("---->Turning on the computer");        
        program1.start();        
        program2.start();        
        program3.start();        
        program4.start();        
        mailbox.start();    
    }
}

Runnable Class: Mailbox
In the Runnable class Mailbox (in the run method), the string “New mail in your mailbox...” is printed approximately 20 times during the RUNTIME.

// First part of the output
---->Turning on the computer
Windows wants to update
New mail in your mailbox...
Skype wants to notify you about a person logging in
Windows wants to update
Windows wants to update
New mail in your mailbox...
Skype wants to notify you about a person logging in



