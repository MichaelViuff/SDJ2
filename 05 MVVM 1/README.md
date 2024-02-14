# 05 Exercises: Model-View-ViewModel Pattern

## 5.1 MVVM in JavaFX

Use your solution to exercise [04.06](https://github.com/MichaelViuff/SDJ2/tree/main/03%20Observer%20Pattern#36-data-representation) or download the [BarChart] example and make a project containing the source files (if you use the example, you may need to add JavaFX library to your project, and some paths may need to be changed to match your project structure).

In the model, make a private int numberOfUpdates and two methods increaseNumberOfUpdates and getNumberOfUpdates.
In the view create a new label numberOfUpdates. This is intended to show the number of updates (i.e. the number of times the button is clicked) whenever the button is clicked. 
In the viewmodel make a StringProperty numberOfUpdates. In the method that displays the updateTimeStamp also increase the number of updates in the model and assign the number of updates from the model to the StringProperty numberOfUpdates.
In the PieChartController bind the numberOfUpdates label to the numberOfUpdates property (you need a method to return the property in the viewModel).
Now the label should be updated each time the button is clicked.
