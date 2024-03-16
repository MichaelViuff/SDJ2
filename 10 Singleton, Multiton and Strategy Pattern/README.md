
# 10 Exercises: Singleton, Multiton and Strategy Pattern

## 10.1 Robot Behavior

### Objective
Create a Robot that will take part in a game with other robots. The robot can be configured with different behavior strategies for acting in the game. We will keep it simple with three simple strategies.

![Robot Behaviour](/10%20Singleton,%20Multiton%20and%20Strategy%2C%20Pattern/Images/Compression%20Exercise%20UML%20Class%20Diagram.png)

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

## 10.2 Compression Strategy

### Objective
Create a class `Compressor` that can compress a list of files and print out the names of the compressed files.

#### Tasks
1. **Implement Compression Strategies:**
   - Each concrete strategy should print that it is compressing the file and return the name of the compressed file like `filename + "-zip"`.

2. **Implement the `Compressor` Class:**
   - Loop through the files, 'compress' each using the configured compression method, and print out the list of names of compressed files.

## 10.3 Sorting a TreeSet

### Objective
Configure a `java.util.TreeSet` class with a comparator object to make the TreeSet sorted.

#### Tasks
1. **Implement Comparators:**
   - `LastnameComparator` should use `compareTo` on the last names.
   - `AgeComparator` should subtract ages.
   - Check the documentation on the compare method.

2. **Test:**
   - Create three persons, one `TreeSet` configured with a `LastnameComparator`, and a `TreeSet` with an `AgeComparator`. Add the persons to both TreeSets and print them out.

## 10.4 Sorting a List

### Objective
Create a `MyIntegerList` class that can store integers and sort them using different strategies.

#### Tasks
1. **Implement `SortingStrategy` Interface:**
   - Must have a single method `sort(Collection of integers)` that returns a collection of sorted integers.

2. **Implement `MyIntegerList` Class:**
   - Should have a `SortingStrategy` reference that can be set.
   - Implement different sorting strategies and test them on your `MyIntegerList` class.
