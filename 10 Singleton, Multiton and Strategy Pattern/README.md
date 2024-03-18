
# 10 Exercises: Singleton, Multiton and Strategy Pattern

# Singleton and Multiton Pattern

## 10.1 Database Connector as Singleton

Assume we had a class that handled connection to a database.

This class would be shared across many other classes, and we would want to ensure that all classes use the same instance.

Such a scenario is an ideal situation for the Singleton Design Pattern.

Our `DatabaseConnector` class could look like this:

```java
public class DatabaseConnector 
{

    private DatabaseConnector(String IP)
    {
        //Initializing the database connection would happen here.
    }

    public void connect()
    {
        System.out.println("Database connection established");
    }
}
```

Change the class, so it becomes a Singleton. Remember to ensure proper synchronization.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseConnector
{

    private static DatabaseConnector instance;
    private static final Lock lock = new ReentrantLock();

    private DatabaseConnector()
    {
        //Initializing the database connection would happen here.
    }

    public static DatabaseConnector getInstance()
    {
        if (instance == null)
        {
            synchronized (lock)
            {
                if (instance == null)
                {
                    instance = new DatabaseConnector(); //Not a valid IP, just for demonstration purposes.
                }
            }
        }
        return instance;
    }

    public void connect()
    {
        System.out.println("Database connection established");
    }
}
```

</details>
</blockquote>

## 10.2 Database Connector as Multiton

Assume that we had multiple different databases that we wanted to ensure a single instance of each.

This would be a typical use case for the Multiton pattern.

Each connector should be referenced by a name, and a previous instance should be returned if it exists.

```java
public class MultitonTest
{
    public static void main(String[] args)
    {
        DatabaseConnector mysqlConnector = DatabaseConnector.getInstance("MySQL");
        DatabaseConnector postgresConnector = DatabaseConnector.getInstance("PostgreSQL");
        DatabaseConnector anotherMysqlConnector = DatabaseConnector.getInstance("MySQL");

        System.out.println(mysqlConnector == postgresConnector); // Should print false
        System.out.println(mysqlConnector == anotherMysqlConnector); // Should print true
    }
}
```

Change the `DatabaseConnector` class so it becomes a Multiton. 

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseConnector
{
    private static Map<String, DatabaseConnector> instances = new HashMap<>();
    private static final Lock lock = new ReentrantLock();

    private DatabaseConnector()
    {
        //Initializing the database connection would happen here.
    }

    public static DatabaseConnector getInstance(String key)
    {
        if (!instances.containsKey(key))
        {
            synchronized (lock)
            {
                if (!instances.containsKey(key))
                {
                    instances.put(key, new DatabaseConnector());
                }
            }
        }
        return instances.get(key);
    }

    public void connect()
    {
        System.out.println("Database connection established");
    }
}
```

</details>
</blockquote>

## 10.3 Logging with a Singleton

In the [Logging example](/10%20Singleton,%20Multiton%20and%20Strategy%20Pattern/Examples/Logging/), two classes, `CDLibrary` and `LoginSystem`, are using a `Log` class to log everything that happens.

The output is logged to console and to a file (this is the role of the `Log` class, ignore the implementation details).

For now, the `Log` instance is created in the constructor of `CDLibrary` and `LoginSystem`, and it uses the current time to create a text file. This causes 2 separate files to be created, one for `CDLibrary` logging and one for `LoginSystem` logging. 

If both classes shared the same instance, they would not create separate files.

We can achieve this, by turning the `Log` class into a Singleton, and update the constructor of `CDLibrary` and `LoginSystem` to get the Singleton instance.

<blockquote>
  <details>
    <summary>Hints</summary>
There are 3 steps necessary to turn `Log` into a Singleton:

1. Declare a private static instance of the Log class within the class itself.
```java
private static Log instance = new Log(); // Singleton instance
```
2. Make the constructor private to prevent instantiation from outside the class.
```java
private Log()
{
   //All setup has been moved to separate method to make it easier to refactor to Singleton
   initialize();
}
```

3. Provide a public static method that returns the singleton instance.
```java
public static Log getInstance()
{
   return instance;
}
```

Afterwards, update the constructor in `CDLibrary` and `LoginSystem` so they use `Log.getInstance()` instead of `new Log()`

<blockquote>
  <details>
    <summary>Display solution...</summary>
```java

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Log
{
    private static Log instance = new Log(); // Singleton instance

    private Queue<LogLine> logQueue;
    private File logFile;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd  hh:mm:ss");

    private Log()
    {
        //All setup has been moved to separate method to make it easier to refactor to Singleton
        initialize();
    }

    public static Log getInstance()
    {
        return instance;
    }

    private void initialize()
    {
        logQueue = new LinkedList<>();
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh-mm-ss");
        String filename = "log-" + dateFormat.format(currentTime) + ".txt";
        logFile = new File(filename);
        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void add(String log)
    {
        if (log == null || log.equals("")) //Don't log empty lines
        {
            return;
        }
        // add to the queue
        LogLine logLine = new LogLine(log, dateFormat.format(Calendar.getInstance().getTime()));
        logQueue.add(logLine);
        addToFile(logLine.toString());          // add to the file
        System.out.println(logLine); // add to the console
    }

    public Queue<LogLine> getAll()
    {
        return logQueue;
    }

    //Method to write logEntries to a file
    private void addToFile(String log)
    {
        if (log == null)
        {
            return;
        }
        BufferedWriter out = null;
        try
        {
            out = new BufferedWriter(new FileWriter(logFile, true));
            out.write(log + "\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
```
  </details>
</blockquote>

  </details>
</blockquote>

![Logging](/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Logging%20Exercise%20UML%20Class%20Diagram.png)

### Objective
Implement a `Log` class to log all actions and demonstrate the Singleton design pattern.

#### Tasks
1. **Singleton Logging:**
   - Change the `Log` class to a Singleton.
   - Update `CDLibrary` and `LoginSystem` classes to use the Singleton instance of the `Log` class.
   - Run the main method to ensure only one text file is created.

2. **Thread Safety:**
   - Make the Singleton implementation thread-safe.

3. **Using the Logger:**
   - Create threads that use methods of `CDLibrary` and `LoginSystem` which will use the Logger.

## 10.x Factories as Singletons

### Objective
Convert multiple classes in an MVVM project to singletons to avoid passing them around to different controllers and factories.

#### Tasks
1. **Singleton Factories:**
   - Convert `ViewHandler`, `ViewModelFactory`, `ModelFactory`, and possibly `ClientFactory` into singletons.

## 10.x Project Glossary

<img src="/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Project%20Glossary%20Exercise%20UML%20Class%20Diagram.png" alt="Project Glossary" width="558"/>

### Objective
Implement a project glossary system using Singleton and Multiton patterns.

### 10.x Singleton Glossary

#### Tasks
1. **Implement Singleton Glossary:**
   - Convert `ProjectGlossary` into a Singleton.
   - Consider changes to the class diagram.

### 10.x Multiton Glossary

#### Tasks
1. **Implement Multiton Glossary:**
   - Convert `ProjectGlossary` into a Multiton with a language key.
   - Implement double-checked locking of the `getInstance` method.

## 10.x Singleton Fake Database (Optional)

### Objective
Create a dummy database for a car renting company using the Singleton pattern.

#### Tasks
1. **Implement Singleton Database:**
   - Create `Customer` and `Car` classes.
   - Implement a `Database` class with collections to hold `Customer` and `Car` objects.
   - Make the `Database` class into a singleton and thread-safe.


# Strategy Pattern

## 10.x Robot Behavior

### Objective
Create a Robot that will take part in a game with other robots. The robot can be configured with different behavior strategies for acting in the game. We will keep it simple with three simple strategies.

![Robot Behaviour](/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Robot%20Behaviour%20Exercise%20UML%20Class%20Diagram.png)

#### Tasks
1. **Create the `Behaviour` Interface:**
   - With one method.

2. **Implement Strategies:**
   - `AggressiveBehaviour` implementing `Behaviour`. `moveCommand()` should return `1` and print to `System.out` that it is aggressive.
   - Implement two other concrete strategies returning `0` and `-1`.

3. **Create the `Robot` Class:**
   - In its `move` method, call the `behaviourStrategy.moveCommand` and print out the result.

4. **Main Method:**
   - Create a new `GameBoard`, a new `Robot`, set a behavior on the robot, and calls `move` on the robot.

## 10.x Compression Strategy

![Compression](/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Compression%20Exercise%20UML%20Class%20Diagram.png)

### Objective
Create a class `Compressor` that can compress a list of files and print out the names of the compressed files.

#### Tasks
1. **Implement Compression Strategies:**
   - Each concrete strategy should print that it is compressing the file and return the name of the compressed file like `filename + "-zip"`.

2. **Implement the `Compressor` Class:**
   - Loop through the files, 'compress' each using the configured compression method, and print out the list of names of compressed files.

## 10.x Sorting a TreeSet

![Comparator](/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Comparator%20Exercise%20UML%20Class%20Diagram.png)

### Objective
Configure a `java.util.TreeSet` class with a comparator object to make the TreeSet sorted.

#### Tasks
1. **Implement Comparators:**
   - `LastnameComparator` should use `compareTo` on the last names.
   - `AgeComparator` should subtract ages.
   - Check the documentation on the compare method.

2. **Test:**
   - Create three persons, one `TreeSet` configured with a `LastnameComparator`, and a `TreeSet` with an `AgeComparator`. Add the persons to both TreeSets and print them out.

## 10.x Sorting a List

### Objective
Create a `MyIntegerList` class that can store integers and sort them using different strategies.

#### Tasks
1. **Implement `SortingStrategy` Interface:**
   - Must have a single method `sort(Collection of integers)` that returns a collection of sorted integers.

2. **Implement `MyIntegerList` Class:**
   - Should have a `SortingStrategy` reference that can be set.
   - Implement different sorting strategies and test them on your `MyIntegerList` class.