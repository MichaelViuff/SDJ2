# 05 Model-View-ViewModel Pattern

## 5.1 Bi-directional binding

In this exercise we will see how JavaFX can create bi-directional binding of Properties.

Start by creating a `ViewModel` class. It should have a single `StringProperty` attribute that is initialized as a `SimpleStringProperty` in the constructor and a getter for it.

Create a `View` class, and give it two `TextField` attributes.<br>
Remember to import the correct `TextField` - the `javafx.scene.control.TextField` and not the `java.awt` one.<br>
Make sure you annotate the attributes with `@FXML` so the components can be injected by JavaFX.<br>
The `View` should have one more attribute of type `ViewModel` that you assign in the constructor.<br>
Add a `public void initialize()` method, where you bind the `textProperty` of the text fields to the `StringProperty` from your `ViewModel` - use the `.bindBidirectional()` method.

Create a `.fxml` file for the view, and insert two `TextField` components. Link the `View` and attributes using `fx:controller` and `fx:id` where appropriate.

We will launch everything from a main method. Create a class `Main` that extends `Application` (so we can launch a JavaFX application), and add the following code, updating paths and names as necessary:

```java
public class Main extends Application
{
    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ViewModel viewModel = new ViewModel();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new View(viewModel));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

Launch everything from a main method and see what happens when you input something in one text field. 

<blockquote>
<details>
<summary>
...display solution
</summary>

```java
public class Main extends Application
{
    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ViewModel viewModel = new ViewModel();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new View(viewModel));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class View
{
    @FXML
    TextField textFieldA;
    @FXML
    TextField textFieldB;

    ViewModel viewModel;

    public View(ViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    public void initialize()
    {
        textFieldA.textProperty().bindBidirectional(viewModel.simpleTextPropertyProperty());
        textFieldB.textProperty().bindBidirectional(viewModel.simpleTextPropertyProperty());
    }
}

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel
{
    private StringProperty simpleTextProperty;

    public ViewModel()
    {
        simpleTextProperty = new SimpleStringProperty();
    }

    public StringProperty simpleTextPropertyProperty()
    {
        return simpleTextProperty;
    }
}
```

```xml

<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="82.0" prefWidth="274.0" xmlns="http://javafx.com/javafx/11.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="View">
   <children>
      <HBox prefHeight="42.0" prefWidth="600.0">
         <children>
            <Label prefHeight="17.0" prefWidth="93.0" text="Text Field A" />
            <TextField fx:id="textFieldA" />
         </children>
      </HBox>
      <HBox prefHeight="43.0" prefWidth="274.0">
         <children>
            <Label prefHeight="17.0" prefWidth="94.0" text="Text Field B" />
            <TextField fx:id="textFieldB" />
         </children>
      </HBox>
   </children>
</VBox>

```


</details>
</blockquote>

## 5.2 My first MVVM program

In this exercise we will add a model, so the Model-View-ViewModel pattern is complete.

![MVVM Diagram](/Images/MVVMh%20Diagram.png)

### Model
For now, our Model will be kept very simple. It simply prints text to the console, and can add new text to the stored text.

Create a class `Model` and give a `String` attribute called `text`. Initialize it in the constructor with the value "This is some text". <br>
Add a getter for `text` and a method `public void addText(String moreText)` that appends `moreText` to `text`. <br>
Add a method `public void printText()` that prints the value of `text` to the console.

### ViewModel
The ViewModel will serve as the connection between our Model and our View. It contains a reference to `Model` and calls the methods as necessary.<br>
It doesn't know anything about the View, it simply exposes some Properties that any View can bind to.

Create a class `ViewModel` and give it two attributes, one for the `Model` and one for a `StringProperty`.<br>
The constructor receives the `Model` and initializes the `StringProperty`.

ggggg...
ggg

<blockquote>
<details>
<summary>
...display solution
</summary>
```java


public class Main extends Application
{
    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ViewModel viewModel = new ViewModel();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new View(viewModel));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

```

</details>
</blockquote>