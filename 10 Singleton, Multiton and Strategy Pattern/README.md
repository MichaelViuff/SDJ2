
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

For now, the <code>Log</code> instance is created in the constructor of <code>CDLibrary</code> and <code>LoginSystem</code>, and it uses the current time to create a text file. This causes 2 separate files to be created, one for <code>CDLibrary</code> logging and one for <code>LoginSystem</code> logging. 

If both classes shared the same instance, they would not create separate files.

We can achieve this, by turning the <code>Log</code> class into a Singleton, and update the constructor of <code>CDLibrary</code> and <code>LoginSystem</code> to get the Singleton instance.

<blockquote>
<details>
<summary>Hints</summary>
There are 3 steps necessary to turn <code>Log</code> into a Singleton:

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
Afterwards, update the constructor in <code>CDLibrary</code> and <code>LoginSystem</code> so they use <code>Log.getInstance()</code> instead of <code>new Log()</code>

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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Log
{
    private static Log instance = new Log(); // Singleton instance
    private static final Lock lock = new ReentrantLock();

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
        if (instance == null)
        {
            synchronized (lock)
            {
                if (instance == null)
                {
                    instance = new Log;
                }
            }
        }
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

```java
public class CDLibrary
{

    /*
    This class only contains dummy methods to simulate a library system.
    */

    private Log logger;

    public CDLibrary()
    {
        this.logger = Log.getInstance();
    }

    public void onPressedRemoveCD()
    {
        logger.add("removing a cd has been pressed");
    }

    public void inputTitleToBeRemoved()
    {
        logger.add("title for cd to remove has been entered");
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        removeCD();
    }

    private void removeCD()
    {
        logger.add("cd found and has been removed in the model");
    }

}
```

```java
public class LoginSystem
{

    /*
    This class only contains dummy methods to simulate a library system.
    */

    private Log logger;

    public LoginSystem()
    {
        this.logger = Log.getInstance();
    }

    //Dummy method to simulate logging in
    public void login(String username, String password)
    {
        logger.add("user logged in with username: " + username + " and password: " + password);
    }

}
```
</details>
</details>
</blockquote>

## 10.4 Factories as Singletons

In previous [MVVM](/05%20MVVM%201/README.md) exercises, we have used Factories to construct our Model, ViewModel and Views. 

Instead of passing these factories around, they could be turned into Singletons instead.

In one of your exercises, rewrite your factories to turn them into Singletons.

## 10.5 Project Glossary

The system presented in the UML class diagram (and in the [source code](/10%20Singleton,%20Multiton%20and%20Strategy%20Pattern/Examples/Project%20Glossary/)) represent an IT project with a project glossary. 

<img src="/10 Singleton, Multiton and Strategy Pattern/Images/Project Glossary Exercise UML Class Diagram.png" alt="Project Glossary" width="558"/>

A `Project` has a name and a glossary where the glossary contains a list of items with a word or phrase and the corresponding definition, e.g.

**Phrase:** *"User"*<br>
**Definition:** *"End user in form of a doctor or a nurse."*

Example:

```java

//In a main method
Project project1 = new Project("Project 1");
project1.addGlossaryItem("Client", "The client part of a client/server application.");
project1.addGlossaryItem("User", "End user in form of a doctor or a nurse.");
project1.addGlossaryItem("Account", "A location on the server application storing username, password and phone number.");
System.out.println("Project 1: Definition for Client: " + project1.getDefinition("Client"));
System.out.println(project1);

/* OUTPUT:
Project 1: Definition for Client: The client part of a client/server application.
Project: Project 1
- Client: "The client part of a client/server application."
- User: "End user in form of a doctor or a nurse."
- Account: "A location on the server application storing username, password and phone number."
*/
```

Convert the `ProjectGlossary` class into a Multiton with a String key representing the language, e.g. “uk” for a project using an English project glossary and “dk” for a project using a Danish one. 

It should be able to work with the following test:

```java
public class Test
{
    public static void main(String[] args)
    {
        //English
        Project project_uk = new Project("Project UK", "uk");

        project_uk.addGlossaryItem("Client", "The client part of a client/server application.");
        project_uk.addGlossaryItem("User", "End user in form of a doctor or a nurse.");
        project_uk.addGlossaryItem("Account", "A location on the server application storing username, password and phone number.");

        System.out.println(project_uk);

        // Danish:
        Project project_dk = new Project("Project DK", "dk");

        project_dk.addGlossaryItem("Client", "Det program der som en del af en Client/Server applikation bliver installeret på computere til læger og sygeplejesker.");
        project_dk.addGlossaryItem("Bruger", "Bruger af systemet - her en læge eller sygeplejeske.");
        project_dk.addGlossaryItem("Konto", "Et sted på en server med oplysninger om brugernavn, kodeord og telefonnummer.");

        System.out.println(project_dk);
    }
}
```

<blockquote>
  <details>
    <summary>Hints</summary>
Turning the <code>ProjectGlossary</code> into a Singleton is trivial at this point.

From there, we just need to create a <code>Map</code> of instances, and return instances from this map with the <code>getInstance(String key)</code> method.

Our constructor for <code>Project</code> will also need to be updated, since it needs to specify which language we want to use for our <code>ProjectGlossary</code>.

<details>
    <summary>Display solution...</summary>

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProjectGlossary
{
    private static Map<String, ProjectGlossary> instances = new HashMap<>();
    private static final Lock lock = new ReentrantLock();

    private List<GlossaryItem> items;

    private ProjectGlossary()
    {
        this.items = new ArrayList<>();
    }

    public static ProjectGlossary getInstance(String key)
    {
        if (!instances.containsKey(key))
        {
            synchronized (lock)
            {
                if (!instances.containsKey(key))
                {
                    instances.put(key, new ProjectGlossary());
                }
            }
        }
        return instances.get(key);
    }

    public int size()
    {
        return items.size();
    }

    public GlossaryItem[] getAll()
    {
        GlossaryItem[] array = new GlossaryItem[items.size()];
        return items.toArray(array);
    }

    public String getDefinition(String phrase)
    {
        for (GlossaryItem item : items)
        {
            if (item.getPhrase().equalsIgnoreCase(phrase))
            {
                return item.getDefinition();
            }
        }
        return null;
    }

    public void addItem(String phrase, String definition)
    {
        if (getDefinition(phrase) != null)
        {
            throw new IllegalStateException(
                    "Glossary phrase already exist: " + phrase);
        }
        items.add(new GlossaryItem(phrase, definition));
    }

    public void removeItem(String phrase)
    {
        items.remove(new GlossaryItem(phrase, getDefinition(phrase)));
    }

    public String toString()
    {
        String s = "";
        if (items.size() == 0)
        {
            s += "[Empty]";
        }
        for (int i = 0; i < items.size(); i++)
        {
            s += "- " + items.get(i);
            if (i < items.size() - 1)
            {
                s += "\n";
            }
        }
        return s;
    }
}
```

```java
public class Project
{
    private String name;
    private ProjectGlossary glossary;

    public Project(String name, String locale)
    {
        this.name = name;
        this.glossary = ProjectGlossary.getInstance(locale);
    }

    public String getName()
    {
        return name;
    }

    public ProjectGlossary getGlossary()
    {
        return glossary;
    }

    public String getDefinition(String phrase)
    {
        return glossary.getDefinition(phrase);
    }

    public void addGlossaryItem(String phrase, String definition)
    {
        glossary.addItem(phrase, definition);
    }

    public void removeGlossaryItem(String phrase)
    {
        glossary.removeItem(phrase);
    }

    @Override public String toString()
    {
        String s = "Project: " + name;
        if (glossary.size() > 0)
        {
            s += "\n" + glossary;
        }
        else
        {
            s += " [No glossary]";
        }
        return s;
    }
}
```
</details>
</details>
</blockquote>

# Strategy Pattern

## 10.6 Robot Behavior

In this exercise we will simulate a `Robot` that will take part in a game with other robots.

The robot can be configured with different behaviour strategies for acting in the game. We will keep it simple with just three strategies.

Our strategies will not actually do anything useful at this point, only show that the chosen strategy is used.

![Robot Behaviour](/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Robot%20Behaviour%20Exercise%20UML%20Class%20Diagram.png)

Create the `Behaviour` interface with one method.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
import javafx.geometry.Point2D;

public interface Behaviour
{
    int moveCommand(GameBoard board, Point2D robotLocation);
}
```
  </details>
</blockquote>

First, crate the class `AggressiveBehaviour` implementing `Behaviour`. The `moveCommand()` should return "1" and print to `System.out` that it is aggressive.

Then, create the other two concrete strategies. Return "0" and "-1" in their `moveCommand()`.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
import javafx.geometry.Point2D;

public class AggressiveBehaviour implements Behaviour
{
   @Override
   public int moveCommand(GameBoard board, Point2D robotLocation)
   {
      System.out.println("Aggressive Behaviour: if find another robot attack it");
      return 1;
   }
}
```

```java
import javafx.geometry.Point2D;

public class NeutralBehaviour implements Behaviour
{
   @Override
   public int moveCommand(GameBoard board, Point2D robotLocation)
   {
      System.out.println("Neutral Behaviour: if find another robot ignore it");
      return 0;
   }
}
```

```java
import javafx.geometry.Point2D;

public class DefensiveBehaviour implements Behaviour
{

   @Override
   public int moveCommand(GameBoard board, Point2D robotLocation)
   {
      System.out.println("Defensive Behaviour: if find another robot run from it");
      return -1;
   }
}
```

  </details>
</blockquote>

Create the `Robot` class.

The `Robot` class should call the `behaviourStrategy.moveCommand()` in the `move()` method, and print out the result. 

The location can just be instantiated to any arbitrary location, for instance `new Point2D(2,3)`, we are not goint to change it.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
import javafx.geometry.Point2D;

public class Robot
{
    private String name;
    private GameBoard board;
    private Behaviour behaviourStrategy;

    public Robot(String name, GameBoard board)
    {
        this.name = name;
        this.board = board;
    }

    public Behaviour getBehaviourStrategy()
    {
        return behaviourStrategy;
    }

    public void setBehaviourStrategy(Behaviour behaviourStrategy)
    {
        this.behaviourStrategy = behaviourStrategy;
    }

    public void move()
    {
        behaviourStrategy.moveCommand(board, new Point2D(0, 0));
    }
}
```

```java
public class GameBoard
{
    //Doesn't do anything for now
}
```

  </details>
</blockquote>

Ensure that everything works by running this test and looking at the output

```java
public class Test
{
    public static void main(String[] args)
    {
        GameBoard board = new GameBoard();
        Robot robot = new Robot("Botty", board);

        robot.setBehaviourStrategy(new NeutralBehaviour());
        robot.move();

        robot.setBehaviourStrategy(new AggressiveBehaviour());
        robot.move();

        robot.setBehaviourStrategy(new DefensiveBehaviour());
        robot.move();
    }
}
```

## 10.7 Compression Strategy

In this exercise you will create a class, `Compressor`, that can compress* a list of files and print out the names of the compressed files (*we will not implement actual compression).

Compression can be done in several different ways, so it is moved to a strategy object.

![Compression](/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Compression%20Exercise%20UML%20Class%20Diagram.png)

Each concrete strategy should print that it is "compressing" the file and return the name of the compressed file, like filename + “-zip”.

The `Compressor` should loop through the files in the list, "compress" each using the `compress()``, and print out the list names of compressed files.

Test everything with this test

```java
import java.util.ArrayList;
import java.util.List;

public class Test
{
    public static void main(String[] args)
    {
        Compressor compressor = new Compressor();
        compressor.setMethod(new SevenCCompression());

        List<String> files = new ArrayList<>();
        files.add("file1");
        files.add("file2");
        files.add("file3");

        //Using 7C compression
        compressor.compress(files);

        //Using ZIP compression
        compressor.setMethod(new ZipCompression());
        compressor.compress(files);
    }

    /* OUTPUT:
    Compressing file1 using 7C compression
    File file1 compressed, result: file1.7c
    Compressing file2 using 7C compression
    File file2 compressed, result: file2.7c
    Compressing file3 using 7C compression
    File file3 compressed, result: file3.7c
    Compressing file1 using ZIP compression
    File file1 compressed, result: file1.zip
    Compressing file2 using ZIP compression
    File file2 compressed, result: file2.zip
    Compressing file3 using ZIP compression
    File file3 compressed, result: file3.zip
     */
}
```

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
public interface CompressionMethod
{
   String compress(String filename);
}
```

```java
import java.util.List;

public class Compressor
{
    private CompressionMethod method;

    public void setMethod(CompressionMethod method)
    {
        this.method = method;
    }

    public void compress(List<String> files)
    {
        for (String file : files)
        {
            System.out.println("File " + file + " compressed, result: " + method.compress(file));
        }
    }
}
```

```java
public class ZipCompression implements CompressionMethod
{
    @Override
    public String compress(String filename)
    {
        System.out.println("Compressing " + filename + " using ZIP compression");
        return filename + ".zip";
    }
}
```

```java
public class SevenCCompression implements CompressionMethod
{
    @Override
    public String compress(String filename)
    {
        System.out.println("Compressing " + filename + " using 7C compression");
        return filename + ".7c";
    }
}
```

  </details>
</blockquote>


## 10.8 Sorting a TreeSet

Similiar to how we sorted an ArrayList in the presentation. we will look at how to sort a `java.util.TreeSet` class.

We can use a `Comparator` object to sort the `TreeSet`.


![Comparator](/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Comparator%20Exercise%20UML%20Class%20Diagram.png)

The `LastnameComparator` should use `compareTo()` on the lastnames, `AgeComparator` should subtract age. (Check the documentation on the `compare()` method in `Comparator`)

The comparator should be given to the `TreeSet` in its constructor.

Ensure that you are sorting correctly with this test

```java
import java.util.TreeSet;

public class Test
{
    public static void main(String[] args)
    {
        //A Treeset is a sorted set, so it will sort the elements based on the comparator. If two objects are equal, it will not add the second object.
        Person person1 = new Person("John", "Doe", 29);
        Person person2 = new Person("James", "Carlsen", 30);
        Person person3 = new Person("John", "Smith", 22);
        Person person4 = new Person("James", "Smyth", 23);

        TreeSet<Person> treeSet_lastnameSorting = new TreeSet<>(new LastnameComparator());
        treeSet_lastnameSorting.add(person1);
        treeSet_lastnameSorting.add(person2);
        treeSet_lastnameSorting.add(person3);
        treeSet_lastnameSorting.add(person4);

        System.out.println(treeSet_lastnameSorting);
        //OUTPUT:
        //[James Carlsen 30, John Doe 30, John Smith 22, James Smyth 23]

        TreeSet<Person> treeSet_ageSorting = new TreeSet<>(new AgeComparator());
        treeSet_ageSorting.add(person1);
        treeSet_ageSorting.add(person2);
        treeSet_ageSorting.add(person3);
        treeSet_ageSorting.add(person4);

        System.out.println(treeSet_ageSorting);
        //OUTPUT:
        //[John Smith 22, James Smyth 23, John Doe 29, James Carlsen 30]
    }
}
```

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
public class Person
{
    private String firstname;
    private String lastname;
    private int age;

    public Person(String firstname, String lastname, int age)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String toString()
    {
        return firstname + " " + lastname + " " + age;
    }
}
```

```java
import java.util.Comparator;

public class LastnameComparator implements Comparator<Person>
{
    @Override
    public int compare(Person o1, Person o2)
    {
        return o1.getLastname().compareTo(o2.getLastname());
    }
}
```

```java
import java.util.Comparator;

public class AgeComparator implements Comparator<Person>
{
    @Override
    public int compare(Person o1, Person o2)
    {
        return o1.getAge() - o2.getAge();
    }
}
```

## 10.9 Sorting a List

Create a `MyIntegerList` class that can store `Integer` objects in an `ArrayList`.

Create a `SortingStrategy` interface.  It must have a single method `ArrayList<Integer> sort(ArrayList<Integer> integers)` that returns an `ArrayList<Integer>` of sorted integers.

Use the Strategy Pattern, so that the `MyIntegerList` class has a `SortingStrategy` reference that can be set. 

Create a `sort()` method in your `MyIntegerList` that calls the `sort()` method on its `SortingStrategy` object.

Implement different sorting strategies and test them with this test.

```java
public class Test
{
    public static void main(String[] args)
    {
        MyIntegerList list = new MyIntegerList();
        list.add(5);
        list.add(3);
        list.add(7);
        list.add(1);
        list.add(9);
        list.add(2);
        list.add(6);
        list.add(4);
        list.add(8);
        list.add(0);

        list.setSortingStrategy(new QuickSort());
        list.sort();
        System.out.println(list); //OUTPUT: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        list.randomize();

        list.setSortingStrategy(new BubbleSort());
        list.sort();
        System.out.println(list); //OUTPUT: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        list.randomize();

        list.setSortingStrategy(new InsertionSort());
        list.sort();
        System.out.println(list); //OUTPUT: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        list.randomize();

        //... and so on. Feel free to add more sorting strategies
    }
}
```

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
import java.util.ArrayList;

public interface SortingStrategy
{
    ArrayList<Integer> sort(ArrayList<Integer> integers);
}

```

```java
import java.util.ArrayList;

public class MyIntegerList
{
    private ArrayList<Integer> list;
    private SortingStrategy sortingStrategy;

    public MyIntegerList()
    {
        list = new ArrayList<>();
        sortingStrategy = null; //No initial sorting strategy, will fail if not set
    }

    public void add(int integer)
    {
        list.add(integer);
    }

    public void setSortingStrategy(SortingStrategy sortingStrategy)
    {
        this.sortingStrategy = sortingStrategy;
    }

    public void sort()
    {
        list = sortingStrategy.sort(list);
    }

    public void randomize()
    {
        ArrayList<Integer> newList = new ArrayList<>();
        while (list.size() > 0)
        {
            int index = (int) (Math.random() * list.size());
            newList.add(list.remove(index));
        }
        list = newList;
    }

    @Override
    public String toString()
    {
        return list.toString();
    }
}
```

```java
/*
    Generated by GitHub Copilot, validity of the sorting algorithm is not guaranteed.
 */
import java.util.ArrayList;

public class BubbleSort implements SortingStrategy
{
    @Override
    public ArrayList<Integer> sort(ArrayList<Integer> integers)
    {
        for (int i = 0; i < integers.size(); i++)
        {
            for (int j = 0; j < integers.size() - 1; j++)
            {
                if (integers.get(j) > integers.get(j + 1))
                {
                    int temp = integers.get(j);
                    integers.set(j, integers.get(j + 1));
                    integers.set(j + 1, temp);
                }
            }
        }
        return integers;
    }
}
```

```java
/*
    Generated by GitHub Copilot, validity of the sorting algorithm is not guaranteed.
 */
import java.util.ArrayList;

public class QuickSort implements SortingStrategy
{
    @Override
    public ArrayList<Integer> sort(ArrayList<Integer> integers)
    {
        quickSort(integers, 0, integers.size() - 1);
        return integers;
    }

    private void quickSort(ArrayList<Integer> integers, int i, int i1)
    {
        if (i < i1)
        {
            int pivotIndex = partition(integers, i, i1);
            quickSort(integers, i, pivotIndex - 1);
            quickSort(integers, pivotIndex + 1, i1);
        }
    }

    private int partition(ArrayList<Integer> integers, int i, int i1)
    {
        int pivot = integers.get(i1);
        int low = i - 1;
        for (int j = i; j < i1; j++)
        {
            if (integers.get(j) < pivot)
            {
                low++;
                int temp = integers.get(low);
                integers.set(low, integers.get(j));
                integers.set(j, temp);
            }
        }
        int temp = integers.get(low + 1);
        integers.set(low + 1, integers.get(i1));
        integers.set(i1, temp);
        return low + 1;
    }
}
```

```java
/*
    Generated by GitHub Copilot, validity of the sorting algorithm is not guaranteed.
 */
import java.util.ArrayList;

public class InsertionSort implements SortingStrategy
{
    @Override
    public ArrayList<Integer> sort(ArrayList<Integer> integers)
    {
        for (int i = 1; i < integers.size(); i++)
        {
            int currentElement = integers.get(i);
            int k;
            for (k = i - 1; k >= 0 && integers.get(k) > currentElement; k--)
            {
                integers.set(k + 1, integers.get(k));
            }
            integers.set(k + 1, currentElement);
        }
        return integers;
    }
}
```
  </details>
</blockquote>