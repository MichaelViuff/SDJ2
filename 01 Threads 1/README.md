# 01 Exercises: Threads 1

## 1.1 Print letters and numbers

Copy the code from the slides with the classes `Numbers`, `Letters` and `Start` (the three classes which prints letters, numbers, and starts the two threads).

Run the program from the `Start` class, and check the console to see that numbers and letters are alternatingly printed.

You may see that a lot of numbers are printed, and then letters are printed, and then numbers again, and so forth. Can you explain why this would happen?

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

Now, change the code in the main method to just instantiate your two Runnable classes, and call the run() method on both. Something like:

```java
Runnable hiPrinter = new HiPrinter();
Runnable helloPrinter = new HelloPrinter();
hiPrinter.run();
helloPrinter.run();
```

Do the printouts ever alternate now? Can you explain what is happening?

<blockquote>
<details>
<summary>Explanation</summary>
  <p>Remember that using <code>.run()</code> directly is no different than calling any other method.
  Therefore, no multithreading happens, and the code runs sequentially, just as expected.
  In order to use threads, remember to create a <code>Thread</code> object first</p>
</details>
</blockquote>
