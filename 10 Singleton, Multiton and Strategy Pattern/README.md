
# 10 Exercises: Singleton, Multiton and Strategy Pattern

# Singleton and Multiton Pattern

## 10.x Logging Feature

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

<img src="/10%20Singleton%2C%20Multiton%20and%20Strategy%20Pattern/Images/Project%20Glossary%20Exercise%20UML%20Class%20Diagram.png" alt="Project Glossary" width="100"/>

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