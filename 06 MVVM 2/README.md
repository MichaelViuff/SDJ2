# 06 Exercises: MVVM and Observer Pattern

## 6.1 Observer in MVVM

Use your solution to exercise [03.06](https://github.com/MichaelViuff/SDJ2/tree/main/03%20Observer%20Pattern#36-data-representation) or download the [Bar Chart Example](https://github.com/MichaelViuff/SDJ2/tree/main/03%20Observer%20Pattern/Examples/JavaFX%20Charts) and make a project containing the source files (if you use the example, you may need to add a JavaFX library to your project, and some paths may need to be changed to match your project structure).

In the `DataModel`, add two new methods `startCalculating()` and `stopCalculating()` that starts and stops the recalculation in the `run()` method (wrap the `recalculateData()` call in an if-statement, and change the value of the boolean used in the start/stop methods for instance).

Create a new .fxml file that contains a view with 3 input fields and 3 buttons, or download this [Input Fields View]() and add it to your project.

In the controller for your new view, add methods to start/stop the model, and have two of your buttons call these methods.

Create a Main method that starts everything - have it extend `Application`, use an `FXMLLoader` to load your view into a `Scene`, create a new `Stage`, add your scene to it and show it. Remember to start the `DataModel` as well.

Verify that your buttons start/stops calculations (you should be able to see the output start/stop in the console).

Create a...
