
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
Turning the `ProjectGlossary` into a Singleton is straightforward.

From there, we just need to create a `Map` of instances, and return instances from this map with the `getInstance(String key)` method.

Our constructor for `Project` will also need to be updated, since it needs to specify which language we want to use for our `ProjectGlossary`.

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