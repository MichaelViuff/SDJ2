# 14 Exercises: Proxy and Adaper Pattern

# Proxy Pattern

## 10.1 Database Connector as Singleton

The purpose of this exercise is to create a class that caches a result and returns the cached result when available.

![Cache Proxy UML](Images/Cache%20Proxy%20UML.png)

We will make a simple setup for this exercise.
Create a class Person with appropriate attributes.

Then, make a class PersonDatabase (implementing the interface), that simulates a database of Person objects. Make the class contain a collection of Person objects (in an ArrayList probably). Have at least one method in PersonDatabase that can search for a specific Person object, given a (set of) criteria (for instance, search by name).

Make your test class, and create an instance of the PersonDatabase object and populate it with Person objects. Have the user read a name (or other attribute(s)) from keyboard, and use it to search in the PersonDatabase (through a PersonCollection reference) for a Person with that name.
Test that everything works.
Now, create the CachedPersonCollection, and associate it with the PersonDatabase object (possibly in the constructor)
The CachedPersonCollection should simply forward the method call getPersonWithName to the PersonDatabase object, and return the result to the caller. But, it should also save the result in a private variable, and make sure, that when the method is called, it first checks to see if the currently stored Person object (lastPersonFound) matches the criteria being searched for. If so, return that object instead.

Update the test class, so that instead of realizing the PersonCollection with a PersonDatabase, you create a CachedPersonCollection.
Test that everything (still) works.

In order to see that you are using the cached result, you might want to print out some lines in the appropriate methods, to see which ones are being used.

# Adapter Pattern
