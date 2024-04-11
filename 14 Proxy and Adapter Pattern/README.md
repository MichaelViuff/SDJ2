# 14 Exercises: Proxy and Adapter Pattern

# Proxy Pattern

## 10.1 Caching Proxy

Assume we have a database that is slow to query. We want to optimize it, without changing anything in the existing solution. This is an ideal situation for the Proxy Pattern.

We will make a simple setup for this exercise.

<img src="Images/Cache%20Proxy%20UML.png" width="100%">

<blockquote>
  <details>
    <summary>Proxy Pattern UML</summary>
    Compare the above UML class diagram with the generic diagram for the Proxy Pattern:
    <img src="Images/Proxy Pattern UML.png" width="100%">
  </details>
</blockquote>

First, create the`Person` class with appropriate attributes.

Then, make a class `PersonDatabase` (implementing the interface), that simulates a database of `Person` objects. Make the class contain a collection of `Person` objects (in an ArrayList probably). Have at least one method in `PersonDatabase` that can search for a specific `Person` object, given a (set of) criteria (for instance, search by name).

Create the `Tester` class, and create an instance of the `PersonDatabase` class and populate it with `Person` objects. Have the user read a name (or other attribute(s)) from keyboard, and use it to search in the `PersonDatabase` (through a `PersonCollection` reference) for a `Person` with that name.

Ensure that everything works.

This represents our current, slow and ineffective version, that we want to improve. But this code has already been deployed, and we are not allowed to change the `PersonDatabase` class directly. This is where the Proxy Pattern comes in handy

### Proxy Database

Now, create the `CachedPersonCollection`, and associate it with the `PersonDatabase` object (possibly in the constructor).  
The `CachedPersonCollection` should simply forward the method call `getPersonWithName` to the `PersonDatabase` object, and return the result to the caller. But, it should also save the result in a private variable, and make sure, that when the method is called, it first checks to see if the currently stored `Person` object (`lastPersonFound`) matches the criteria being searched for. If so, return that object instead.

Update the `Tester` class, so that instead of realizing the `PersonCollection` with a `PersonDatabase`, you create a `CachedPersonCollection`.

Ensure that everything (still) works.

In order to validate that you are using the cached result, you might want to print out some lines in the appropriate methods, to see which ones are being used.

<blockquote>
  <details>
    <summary>Display solution...</summary>
    <a href="Examples/Proxy/">Solution</a>
  </details>
</blockquote>

## 10.2 Logger

Assume you have a simple `Logger` class:

```java
public class Logger
{
    public void log(String textToLog)
    {
        System.out.println(textToLog);
    }
}
```

We might want to specify log-levels, in order to control how much is output to the log. We can create this filtering using the Proxy Pattern, and leave the `Logger` class unchanged.

Say you have two log-levels:

- Sparse
- Verbose

When the level is set to _sparse_ only messages containing the word "error" should be put in the log.

When the level is set to _verbose_, everything will be put in the log.

If you need any other filters, you are welcome to expand the exercise.

Use the Proxy Pattern to change your solution, so that the proxy class handles filtering.  
This means, you should have a setup similar to this:

<img src="Images/Logger%20UML.png" width="300px">

## 10.3 Sea Bear Exhibit

Let’s assume a species of animal called ‘Sea Bear’ lives in a zoo. The animal is a bit shy, so to not spook it, access to it is limited.  
Only children will be allowed to pet the bear.  
Only zookeepers will be allowed to feed the bear.  
Everyone will be allowed to view the bear.

To control the access of this rare species the park has appointed a ‘Sea Bear Guard’. He will assess the details of the visitors and controls the sea bear visits as appropriate.

We interact with the `SeaBear` through the following interface:

```java
public interface VisitSeaBear
{
    void view(String personType);
    void feed(String personType);
    void pet(String personType);
}
```

Implement a `SeaBear` class, which implements the interface. The methods in `SeaBear` just prints out something appropriate like “Bear is being viewed by Adult”, “Bear is being fed by zookeeper”, etc.

Create a `SeaBearGuard` class, which implements the above interface. Through this class, control access to the `SeaBea`.  
Create a class with a main method to test it.

## 10.4 Thread safe queue

Implement the proxy design pattern shown below. Use the uploaded interface, `StringQueue`, and class `BoundedArrayQueue`.  
Class `ThreadsafeQueue` is a monitor class (all methods are synchronized). The idea is that the `ThreadsafeQueue` provides thread safety to the otherwise not threadsafe `BoundedArrayQueue`.  
The `ThreadSafeBoundedQueue` will have the same methods, from the interface, and call relevant methods on the `BoundedArrayQueue`. But the methods will use synchronization.

<img src="Images/Thread%20Safe%20Queue%20UML.png" width="400px">

# Adapter Pattern

## 10.5 Queue containing Strings based on a List

The purpose for this exercise is to implement the adapter design pattern shown below. It is a Queue which can only contain Strings. We use the adapter design pattern to base our Queue on the functionality of an ArrayList.  
You can use Java’s `ArrayList`. No need to implement your own.  
The interface has been uploaded.  
The Queue specification has been uploaded.

<img src="Images/Queue%20Adapter%20UML.png" width="300px">

Consider: Which class is the Target, Adapter, and Adaptee?  
Make unit tests for your Queue. It might be easier to modify your test cases from previous (exercise 16.3) to test your new `StringQueue`. It should just be changing the field variable and the setup method.

In the upcoming assignment, we’re going to create what is called a Blocking Queue, basing the functionality on that of an ArrayList. There are strong similarities to this exercise.

## 10.6 File Writer

The purpose of this exercise is to create a data storage, using the adapter design pattern. Your system, which in this case could just be main method, will just use the `PersonStorage` interface.  
You’re going to create functionality to store information about Persons. See diagram below:

<img src="Images/Person%20Storage%20UML.png" width="300px">

You are allowed to add methods as needed. E.g. the `Person` object might need some get-methods and a constructor.

Think of it as creating an Adapter around your system of storage.

`DBPersonStorage` is just one possible implementation of the interface (that we might create later).

In this exercise you should create an adapter implementing `PersonStorage` to either an arraylist or a file.

You may start out with a class, `InMemoryPersonStorage`, and just store `Person`’s in an ArrayList.  
Or you may create a `FilePersonStorage`. This is more difficult.

The `FilePersonStorage` should either create a new file, or use the existing.  
When adding a `Person`, unwrap the information and add a new line to the File.  
When retrieving a `Person`, go through the file, line by line, and find the line containing the correct social security number. Retrieve the information from this line, put it into a new `Person` object, and return it.

Test your system:  
Create a simple class to read input from the console, where you can add and retrieve a person.

## 10.7 Database storage

This exercise assumes you know about JDBC and connecting Java and a Database.

Modify your previous exercise to use a database instead.

Test that your system still works, by adding and retrieving person objects.
