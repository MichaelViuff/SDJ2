# 02 Exercises: Threads 2

## 2.1 Updating a shared resource, revisited

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

## 1.2 `run()` instead of `start()`

Create two classes, which implement the `Runnable` interface. One should print out “hi” 1000 times. The other should print out “hello” 1000 times. Start two threads using these classes from a main method, and check the output. Do this a few times. You should see, that sometimes “hi” and “hello” are printed alternatingly, similar to the previous exercise.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>Printing out "hi" and "hello" 1000 times should be trivial (use any type of loop). To create a class that implements <code>Runnable</code> simply ensure that you put your logic for printing 1000 times into the <code>.run()</code> method of that class.</p>
<details>
<summary>Display solution...</summary>

```java
public class HiPrinter implements Runnable
{
  @Override
  public void run()
  {
    for(int i = 0; i < 1000; i++)
    {
      System.out.println("hi");
    }
  }
}

public class HelloPrinter implements Runnable
{
  @Override
  public void run()
  {
    for(int i = 0; i < 1000; i++)
    {
      System.out.println("hello");
    }
  }
}

public class Test
{
  public static void main(String[] args)
  {
    Runnable hiPrinter = new HiPrinter();
    Runnable helloPrinter = new HelloPrinter();
    Thread thread1 = new Thread(hiPrinter);
    Thread thread2 = new Thread(helloPrinter);
    thread1.start();
    thread2.start();
  }
}
```

</details>
</details>

</blockquote>
