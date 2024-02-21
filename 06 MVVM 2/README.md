# 06 Exercises: Completing the MVVM Design Pattern 

## 6.1 Converting User List example to Observer

In the previous exercises, we implemented MVVM in JavaFX. Almost. Our ViewModel was responsible for digging down into the Model to find the data it needed. This is not in line with how MVVM should be implemented. Instead, we want our ViewModel to register itself as a listener to the Model, and for the Model to broadcast any changes to interested listeners.

We will use our solution to [exercise 5.5](https://github.com/MichaelViuff/SDJ2/tree/main/05%20MVVM%201#55-organizing-everything) as a starting point, and update the Model and ViewModel accordingly. Notice how we won't be changing anything at all in the View.

Start by opening your solution or download this [example](05%20MVVM%201/Examples/Factories) (everything is sorted into packages, so update your paths if necessary when importing it into your project).

### Model

Modify the `Model` so it has a `PropertyChangeSupport` that is initialized in the constructor. Define methods for listeners to attach/detach to the `Model` such as `addPropertChangeListener(String name, PropertyChangeListener listener)`. You can also use the [`PropertyChangeSubject`](/03%20Observer%20Pattern/Examples/PropertyChangeSubject.java) interface that we have used previously.

Now update the `addUser()` method so it fires an event "UserAdded" after a new `User` has been added to the list. For now just have it send the entire `users` List as the `newValue`.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
public void addUser(String username, String password, int age)
{
    if(username.length() <= 20 && password.length() >= 8)
    {
        users.add(new User(username, password, age));
        support.firePropertyChange("UserAdded", null, users);
    }
}

@Override
public void addPropertyChangeListener(PropertyChangeListener listener)
{
    support.addPropertyChangeListener(listener);
}

@Override
public void addPropertyChangeListener(String name, PropertyChangeListener listener)
{
    support.addPropertyChangeListener(name, listener);
}

@Override
public void removePropertyChangeListener(PropertyChangeListener listener)
{
    support.removePropertyChangeListener(listener);
}

@Override
public void removePropertyChangeListener(String name, PropertyChangeListener listener)
{
    support.removePropertyChangeListener(name, listener);
}
}
```
</details>
</blockquote>

### ViewModel

Now modify the `UserListViewModel`, by changing defining a new method that should be called when the event is fired from the `Model`. Call it `private void update(PropertyChangeEvent propertyChangeEvent)`. Inside this method, update the `users` list by clearing the old values and adding the new list that is stored as `newValue` in the `PropertyChangeEvent`.

Register this method to the `Model` by calling `model.addPropertyChangeListener("UserAdded", this::update);` in the constructor.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
public UserListViewModel(Model model)
{
    this.model = model;
    users = FXCollections.observableArrayList();
    refresh();
    model.addPropertyChangeListener("UserAdded", this::update);
}

private void update(PropertyChangeEvent propertyChangeEvent)
{
    List<User> newUsers = (List<User>) propertyChangeEvent.getNewValue();
    users.clear();
    users.addAll(newUsers);
}

public void refresh()
{
    users.clear();
    users.addAll(model.getUsers());
}
```
</details>
</blockquote>




### Run everything

You shouldn't need to change anything else at this point. Try running your program, and verify that when you add a new user, it is shown in the list without the need to press the Refresh button.

### Cleaning up

Everything is working, so we are done. Yay!

But, we have some smelly code that we should probably clean up.

The `Model` is exposing the `List<User>` through the `getData()` method. In pure MVVM we shouldn't need this. How can we avoid it? The ViewModel needs this data when the program starts...

Luckily, we know exactly when this data should be sent to the ViewModels - when they register themselves to the Model. 

We can update the attach/detach methods (`addPropertyChangeListener()` and `removePropertyChangeListener()`) so they fire a `PropertyChangeEvent` whenever a listener attaches itself. This way, a new listener immediately gets the newest version of the data (ignoring concurrency issues for now...).

<blockquote>
<details>
<summary>Display solution...</summary>

```java
@Override
public void addPropertyChangeListener(PropertyChangeListener listener)
{
    support.addPropertyChangeListener(listener);
    listener.propertyChange(new PropertyChangeEvent(this, null, null, users));
}

@Override
public void addPropertyChangeListener(String name, PropertyChangeListener listener)
{
    support.addPropertyChangeListener(name, listener);
    listener.propertyChange(new PropertyChangeEvent(this, name, null, users));
}
```
</details>
</blockquote>

Now we can remove the `getData()` method from the `Model`.

We can also remove the button from our `UserListView.fxml` and the corresponding method in the controller; they are no longer needed.

## 6.2 Converting BarChart example to MVVM

In the previous exercise we adapted a previous MVVM solution to use the Observer pattern. In this exercise we want to adapt a previous Observer solution to use the MVVM pattern.

Use your solution to exercise [03.06](https://github.com/MichaelViuff/SDJ2/tree/main/03%20Observer%20Pattern#36-data-representation) or download the [Bar Chart Example](https://github.com/MichaelViuff/SDJ2/tree/main/03%20Observer%20Pattern/Examples/JavaFX%20Charts) (some paths may need to be changed to match your project structure).

Our `DataModel` doesn't need any changes, so we will start by creating a new ViewModel class.

### ViewModel

Create a new class and call it `BarChartViewModel`.

It has an attribute of type `DataModel`. Set it in the constructor. 

We also need 3 `Property` attributes to hold the values of the colours. Initialize them in the constructor, and create getters for them.

Create the method that should be called when data changes. It should extract the values from the `int[]` in `newValue` and set them in the 3 `Property` attributes.

Attach the method to the Model in the constructor.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.beans.PropertyChangeEvent;

public class BarChartViewModel
{
    private DataModel model;
    private Property redProperty;
    private Property greenProperty;
    private Property blueProperty;

    public BarChartViewModel(DataModel model)
    {
        this.model = model;
        model.addPropertyChangeListener("DataChange", this::onColourValueChange);
        redProperty = new SimpleIntegerProperty();
        greenProperty = new SimpleIntegerProperty();
        blueProperty = new SimpleIntegerProperty();
    }

    public Property getRedProperty()
    {
        return redProperty;
    }

    public Property getGreenProperty()
    {
        return greenProperty;
    }

    public Property getBlueProperty()
    {
        return blueProperty;
    }

    private void onColourValueChange(PropertyChangeEvent event)
    {
        int[] newValues = (int[]) event.getNewValue();
        redProperty.setValue(newValues[0]);
        greenProperty.setValue(newValues[1]);
        blueProperty.setValue(newValues[2]);
    }
}
```
</details>
</blockquote>

### View

We can start from scratch with a new View layer, or we can adapt the existing `BarChartViewController` class. 

Rename the class `BarChartView` and ensure that you also update the `fx:controller` attribute in the `.fxml` file. 

The `BarChartView` should have a new attribute of type `BarChartViewModel` that is set in the constructor. Remove the `DataModel` from the parameters for the constructor, and remove the `addPropertyChangeListener` call in the constructor.

In the constructor, bind the `YValueProperty` of the 3 `XYChart.Data` attributes to the 3 `Property` attributes from the `BarChartViewModel`.

Delete the `onColourValueChange()` method.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BarChartView
{

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();

    private BarChartViewModel viewModel;
    private XYChart.Series<String, Integer> dataSeries;
    private XYChart.Data<String, Integer> redData;
    private XYChart.Data<String, Integer> greenData;
    private XYChart.Data<String, Integer> blueData;

    public BarChartView(BarChartViewModel viewModel)
    {
        this.viewModel = viewModel;

        redData = new XYChart.Data("Red", 0);
        greenData = new XYChart.Data("Green", 0);
        blueData = new XYChart.Data("Blue", 0);

        redData.YValueProperty().bindBidirectional(viewModel.redProperty());
        greenData.YValueProperty().bindBidirectional(viewModel.greenProperty());
        blueData.YValueProperty().bindBidirectional(viewModel.blueProperty());

        dataSeries = new XYChart.Series();
        dataSeries.getData().addAll(redData, greenData, blueData);
    }

    public void initialize()
    {
        xAxis.setLabel("Colours");
        yAxis.setLabel("Value");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        barChart.setTitle("Data Representation");
        barChart.setLegendVisible(false);
        barChart.getData().add(dataSeries);

        Node node = barChart.lookup(".data0.chart-bar");
        node.setStyle("-fx-bar-fill: red");
        node = barChart.lookup(".data1.chart-bar");
        node.setStyle("-fx-bar-fill: green");
        node = barChart.lookup(".data2.chart-bar");
        node.setStyle("-fx-bar-fill: blue");
    }
}
```
</details>
</blockquote>

### Main

We need to make a slight change in the `Main` class, since the View no longer takes the Model in the constructor, but instead takes the ViewModel.

Create an instance of `BarChartViewModel` and pass it to the constructor for the `BarChartView`.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataModel model = new DataModel();
        BarChartViewModel viewModel = new BarChartViewModel(model);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BarChartView.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new BarChartView(viewModel));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setTitle("Data Representation");
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread dataModelThread = new Thread(model);
        dataModelThread.setDaemon(true);
        dataModelThread.start();
    }
}
```
</details>
</blockquote>


## 6.3 Multiple views for BarChart example

We have succesfully adapted our BarChart example to use the MVVM Design Pattern. If not, download the [solution](/06%20MVVM%202/Examples/BarChartMVVM).

Create a new .fxml file that contains a view with 3 input fields and 3 buttons, or download this [Input Fields View]() and add it to your project.

In the controller for your new view, add methods to start/stop the model, and have two of your buttons call these methods.

Create a Main method that starts everything - have it extend `Application`, use an `FXMLLoader` to load your view into a `Scene`, create a new `Stage`, add your scene to it and show it. Remember to start the `DataModel` as well.

Verify that your buttons start/stops calculations (you should be able to see the output start/stop in the console).

Create a...



We want to be able to start/stop the calculations, and be able to input our own values as well.

### Model

In the `DataModel`, add two new methods `startCalculating()` and `stopCalculating()` that starts and stops the recalculation in the `run()` method (wrap the `recalculateData()` call in an if-statement, and change the value of the boolean in the start/stop methods for instance).

<blockquote>
<details>
<summary>Display solution...</summary>

```java
private boolean shouldCalculateData;

public DataModel()
{
    propertyChangeSupport = new PropertyChangeSupport(this);
    startCalculating();
}

public void startCalculating()
{
    this.shouldCalculateData = true;
}

public void stopCalculating()
{
    this.shouldCalculateData = false;
}

@Override
public void run()
{
    while (true)
    {
        if(shouldCalculateData)
        {
            recalculateData();
        }
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
```
</details>
</blockquote>