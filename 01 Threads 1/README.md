# 01 Exercises: Threads 1

## 1.1 Print letters and numbers

Run the code from the presentation with the classes `Numbers`, `Letters` and `Start` (the three classes which prints letters, numbers, and starts the two threads).

Run the program from the `Start` class, and check the console to see that numbers and letters are printed alternating.

You may see that a lot of numbers are printed, and then letters are printed, and then numbers again, and so forth. Can you explain why this would happen?

<blockquote>
<details>
<summary>Explanation</summary>
  <p>The time scheduler assigns time to a single thread for an amount of time. We have no control over this, and the amount of time might vary for each execution of the program. As such, sometimes we might see many numbers in a row before letter, or vice versa. And other times only a few numbers before letters are printed, or vice versa.</p>
</details>
</blockquote>

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

## 1.3 Constructors

The purpose of this exercise is to see how a thread can receive information through its constructor.
Make a copy of exercise 1.1, the one which prints numbers and letters. Modify the code so that the constructor each for `Runnable` class takes a parameter, which determines how many numbers/letters to print out. 
Currently it’s hardcoded to 1000. You should be able to instantiate the threads similar to this:
```java
Thread numPrint = new Thread ( new Numbers ( 57 ) ) ;
Thread letPrint = new Thread ( new Letters ( 33 ) ) ;
```

<blockquote>
<details>
<summary>Display hints...</summary>
<p>Introduce an integer attribute in the <code>Numbers</code> and <code>Letters</code> class and initialize it in the constructor.</p>
<details>
<summary>Display solution...</summary>

```java
public class Letters implements Runnable
{
    
    private int iterations;

    public Letters(int iterations) 
    {
        this.iterations = iterations;
    }

    @Override public void run()
    {
        while(true)
        {
            for (int i = 0; i < iterations; i++)
            {
                System.out.println((char) (i+97));
            }
        }
    }
}

public class Numbers implements Runnable
{
    
    private int iterations;

    public Numbers(int iterations) 
    {
        this.iterations = iterations;
    }

    @Override public void run()
    {
        while(true)
        {
            for (int i = 0; i < iterations; i++)
            {
                System.out.println(i);
            }
        }
    }
}

public class Test
{
  public static void main(String[] args)
  {
    Thread numPrint = new Thread ( new Numbers ( 57 ) ) ;
    Thread letPrint = new Thread ( new Letters ( 33 ) ) ;
    numPrint.start();
    letPrint.start();
  }
}
```

</details>
</details>

</blockquote>

## 1.4 Waiting

Create a `Runnable` class which prints out a number each second. The constructor takes an integer x as input to determine how many numbers to print out.

Then print out numbers from 0 to x (the constructor argument), waiting one second between each number.

Try to do both by creating a class implementing the `Runnable` interface and the lambda expression approach (when using lambda you won't be able to use a parameter for x, so just use a set value).

<blockquote>
<details>
<summary>Display hints...</summary>
<p>In order to wait, use the <code>Thread.sleep()</code>. This method waits a number of milliseconds given as argument.
Be aware that the method has <code>Exceptions</code> that need to be handled with <code>try/catch</code> or passed on. 

</p>
<p>To use lambda expressions, remember that it is equivalent to using an anonymous class for the <code>Thread</code> constructor. 
The constructor expects a class that implements the <code>Runnable</code> interface, and as such, you can create an implementation on the spot.</p>
<details>
<summary>Display solution...</summary>

```java
public class WaitPrinter implements Runnable
{

    private int x;

    public WaitPrinter(int x)
    {
        this.x = x;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < x; i++)
        {
            System.out.println(i);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

public class Test
{
    public static void main(String[] args)
    {
        Thread waitPrinter = new Thread(new WaitPrinter(10));
        waitPrinter.start();

        // Lambda expression implementation that does the same, except it uses a set value '10' in the for loop
        Thread waitPrinterLambda = new Thread(() -> {
            for (int i = 0; i < 10; i++)
            {
                System.out.println(i);
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        waitPrinterLambda.start();
    }
}
```

</details>
</details>

</blockquote>

## 1.5 Thread serialization using `join()`

Create a `Runnable` class, the constructor for which should take two integer parameters, x and y. 

The class should then print out the numbers from x to y. 

In a main method create four instances of your `Runnable` class, and start `Threads` for each. 

One instance should print numbers from 0-25000, the next 25000 -50000, the next 50000-75000 and so on.

Are the numbers printed in ascending sequence? Why not?

<blockquote>
<details>
<summary>Explanation</summary>
  <p>You are starting the 4 <code>Threads</code> at the same time. As such, they will run interchangeably instead of waiting for each other.</p>
</details>
</blockquote>

Modify your code to use the `join()` method to make sure that the numbers are printed in ascending sequence.

Make one thread wait for for the other to finish before starting.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>In order to wait for one thread to finish, you need to stop the execution of the <code>main</code> method, so it doesn't start the next thread until the first one finishes.

This is exactly what the <code>join()</code> method does. The method must be invoked from somewhere, and that somewhere will then wait until the thread upon which it is called finishes.

In other words, you must start the first thread from the main method, and then immediately put the main method into wait mode, using <code>join()</code> before it starts the next thread, and so on. </p>
<details>
<summary>Display solution...</summary>

```java

public class Range implements Runnable
{

    private int x, y;

    public Range(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run()
    {
        for (int i = x; i < y; i++)
        {
            System.out.println(i);
        }
    }
}


public class Test
{
    public static void main(String[] args) throws InterruptedException {
        Thread range1 = new Thread(new Range(0, 25000));
        Thread range2 = new Thread(new Range(25000, 50000));
        Thread range3 = new Thread(new Range(50000, 75000));
        Thread range4 = new Thread(new Range(75000, 100000));
        range1.start();
        range1.join();
        range2.start();
        range2.join();
        range3.start();
        range3.join();
        range4.start();
        range4.join();
    }
}

```

</details>
</details>

</blockquote>

## 1.6 Waiting
Program the bear and the poking man example from the presentation. The UML could look like this:

![Bear and Poking Man UML Class Diagram](https://github.com/MichaelViuff/SDJ2/blob/main/01%20Threads%201/Images/BearPokingManUML.png)

Notice that the `PokingMan` class takes a `Thread`, not a `Bear`, as input for the constructor.

The bear goes to sleep. If it wakes by itself, print out it is well-rested. If another source wakes it, print out it is angry.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>Start by making the <code>Bear</code> class. The <code>run()</code> method must sleep for an amount of time, and when finished with sleeping, show that it is well-rested (print out "I am a well-rested bear" or similiar). If it isn't allowed to finish waiting (i.e. an <code>InterruptedException</code>code> is thrown</code>), show that it is angry (print out "I am an angry bear!" or similiar).

The <code>PokingMan </code></p> constructor must get the <code>Bear</code> thread as an argument. The <code>run()</code> method should sleep an amount of time, and then wake up (interrupt) the <code>Bear</code> thread.

Use different timers for the sleep of both threads and see what happens.</p>
<details>
<summary>Display solution...</summary>

```java
public class Bear implements Runnable
{
  @Override public void run()
  {
    try
    {
      Thread.sleep(3000);
      System.out.println("I am a well-rested bear");
    }
    catch (InterruptedException e)
    {
      System.out.println("I am an angry bear!");
    }
  }
}

public class PokingMan implements Runnable
{

  private Thread bearToPoke;

  public PokingMan(Thread bearToPoke)
  {
    this.bearToPoke = bearToPoke;
  }


  @Override public void run()
  {
    try
    {
      Thread.sleep(2000); //Change this value to allow the bear to finish resting before getting poked.
      bearToPoke.interrupt();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}

```

</details>
</details>

</blockquote>

## 1.7 A sleuth of bears

We will expand on the previous exercise, by adding more bears for our man to poke!

This time you have 5 bears (or some other arbitrary number), and when one bear is woken up unexpectedly, it roars loudly, waking up all other bears sleeping in the same cave!

<blockquote>
<details>
<summary>Display hints...</summary>
<p>In order for one bear to wake all other bears, it must have a reference to their threads. This can be done in many ways, but a simple way for now is to have a class <code>Cave</code> that contains a list of bear threads. Create a <code>wakeAllBears</code> method in that class, that interrupts all sleeping threads and removes them from the list.

When a bear is awoken by being poked, let it call the <code>WakeAllBears</code> method.

Try letting the <code>PokingMan</code> wake up different bears and confirm that everyone wakes up!

</p>
<details>
<summary>Display solution...</summary>

```java

import java.util.ArrayList;
import java.util.List;

public class Cave {

    private List<Thread> sleepingBears;

    public Cave()
    {
        sleepingBears = new ArrayList<>();
    }

    public void addBear(Thread bear)
    {
        sleepingBears.add(bear);
    }

    public void wakeAllBears()
    {
        for (Thread bear : sleepingBears)
        {
            bear.interrupt();
        }
        sleepingBears.clear();
    }
}


public class Bear implements Runnable
{

  private Cave cave;

  public Bear(Cave cave)
  {
    this.cave = cave;
  }

  @Override public void run()
  {
    try
    {
      Thread.sleep(3000);
      System.out.println("I am a well-rested bear");
    }
    catch (InterruptedException e)
    {
      System.out.println("I am an angry bear!");
      cave.wakeAllBears();
    }
  }
}

public class PokingMan implements Runnable
{

  private Thread bearToPoke;
  private int timeToSleep;

  public PokingMan(Thread bearToPoke, int timeToSleep)
  {
    this.bearToPoke = bearToPoke;
    this.timeToSleep = timeToSleep;
  }

  @Override public void run()
  {
    try
    {
      Thread.sleep(timeToSleep);
      bearToPoke.interrupt();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}



```

</details>
</details>

</blockquote>



## 1.9 Updating a shared resource

The result of this exercise may cause some confusion. It is an appetizer for next session, where we will look at the problem, which arises in this exercise.

Below is a UML of the classes needed. Please note the UML diagram may not be complete, and you're welcome to add to it as is needed.

![CounterIncrementer UML Class Diagram](https://github.com/MichaelViuff/SDJ2/blob/main/01%20Threads%201/Images/CounterIncrementerUML.png)

Create a class, call it Counter, with a single private field variable of type int. You can call the int field “count”. It should be initialized to 0 in the constructor of Counter.
Create a method, which when called will increment the int with 1, i.e. “count++;”.
Create a method to get the count variable, i.e. “return count;”
Create a Runnable class (a class implementing the Runnable interface), you can e.g. call it CountIncrementer, which takes a reference to your class with the counter (an argument for the constructor of CountIncrementer). In your main method, you should have something like:

CounterIncrementer ci = new CounterIncrementer(counter);

In the run() method of CounterIncrementer, you call the update method 1.000.000 times, in a for-loop, so that the ‘count’ variable in Counter is incremented. When the for-loop is done, i.e. after and outside of the for-loop, get the count variable, and print it out.
Now create a Start class with a main method. Instantiate the Counter class, instantiate one instance of CountIncrementer, and start the Thread, something like:

Counter c = new Counter();
Thread t = new Thread(new CountIncrementer(c) ) ;

Verify that the number printed out is 1.000.000.
Change the code in your main method so that two CounterIncrementer threads are created and started.
Two threads will now both increment the count variable 1.000.000 times. We would expect the printed result to be 2.000.000. Is that always so? Or does the number sometimes differ from 2.000.000?

This exercise is to peek your curiosity. We’ll discuss this next time. It has something to do with Time Slicing, and multiple threads interfering with each other.

