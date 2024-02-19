# 05 Model-View-ViewModel Pattern

## 5.1 My first MVVM program

In this exercise we will implement the Uppercase example from the presentation.

### Model
For now, our Model will be kept very simple. It simply takes a string, converts it to uppercase, and returns it.

Create a class `Model` and give a `public String uppercase(String textToConvert)` method.
The method should convert the string to uppercase and return it.

### ViewModel
The ViewModel will serve as the connection between our Model and our View. It contains a reference to `Model` and calls the methods as necessary.<br>
It doesn't know anything about the View, it simply exposes some Properties that any View can bind to.

Create a class `ViewModel` and give it three attributes, one for the `Model` and two of type `StringProperty`,  called `inputTextProperty` and `outputTextProperty`.<br>
The constructor receives the `Model` and initializes the two `StringProperty` attributes. <br>
Add a method `public void convert()` that calls the `uppercase()` method on the `Model`, and uses the content of `inputTextProperty` as argument (use `inputTextProperty.get()` to get the content).<br>
The result from `uppercase()` should be set in the `outputTextProperty` (using `outputTextProperty.set(text)`)<br>

### View
The View is what the user will interact with. We want to allow the user to input new text that we will convert in the Model.<br>
To do so, we need 2 `TextField` components and a `Button` the user can press when he wants to convert new text.

Start by creating the `View` class. Make `@FXML` annotations and declare two `TextField` attributes, `inputTextField` and `outputTextField`.<br>
Create a constructor that takes a single argument, the `ViewModel` and sets it (`View` should have an attribute of type `ViewModel`).<br>
Set up the binding between the two `TextField` and `StringProperty` in the `initialize()` method that JavaFX uses when creating the `View` instance.<br>
Define a method that your `Button` calls when clicked. It should call the method `convert()` in the `ViewModel`.

### FXML file
The `.fxml` file is a part of our View. It simply defines the containers used and their layout.<br>
Additionally, JavaFX uses this file and searches for the `fx:controller` attribute in the root element. <br>
It then creates an instance of this class, and injects all components with an `fx:id` attribute, searching for `@FXML` annotated attributes with the same name (ie. reflection)

Create a `.fxml` file, and create a layout with two `TextField` components, and a single `Button`. <br>
Ensure that you assign proper `fx:controller` and `fx:id` attributes.<br>
The button should call the method `convert` in the `ViewModel`

### Main
We launch everything from a main method. Here we create the `Model` that we use when constructing the `ViewModel` that we in turn use when we construct the `View`.<br>

Now start the application, and see what happens when you input text in the input field and click the button.

<blockquote>
<details>
<summary>
Display solution...
</summary>

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
    public void start(Stage primaryStage) throws Exception
    {
        Model model = new Model();
        ViewModel viewModel = new ViewModel(model);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new View(viewModel));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

```java
public class Model
{
    public String uppercase(String textToConvert)
    {
        return textToConvert.toUpperCase();
    }
}
```

```java
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel
{
    private Model model;
    private StringProperty inputTextProperty, outputTextProperty;

    public ViewModel(Model model)
    {
        this.model = model;
        inputTextProperty = new SimpleStringProperty();
        outputTextProperty = new SimpleStringProperty();
    }

    public void convert()
    {
        String result = model.uppercase(inputTextProperty.get());
        outputTextProperty.set(result);
    }

    public StringProperty inputTextProperty()
    {
        return inputTextProperty;
    }

    public StringProperty outputTextProperty()
    {
        return outputTextProperty;
    }
}
```

```java
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class View
{
    @FXML
    TextField outputTextField;
    @FXML
    TextField inputTextField;

    private ViewModel viewModel;

    public View(ViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    public void initialize()
    {
        outputTextField.textProperty().bindBidirectional(viewModel.outputTextProperty());
        inputTextField.textProperty().bindBidirectional(viewModel.inputTextProperty());
    }

    public void onConvertButtonPressed()
    {
        viewModel.convert();
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>


<VBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="100.0" prefWidth="319.0" xmlns="http://javafx.com/javafx/11.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="View">
   <children>
      <HBox prefHeight="36.0" prefWidth="600.0">
         <children>
            <Label prefHeight="17.0" prefWidth="106.0" text="Input" />
            <TextField fx:id="inputTextField" />
         </children>
      </HBox>
      <HBox layoutX="10.0" layoutY="10.0" prefHeight="36.0" prefWidth="600.0">
         <children>
            <Label prefHeight="17.0" prefWidth="107.0" text="Output" />
            <TextField fx:id="outputTextField" prefHeight="25.0" prefWidth="208.0" />
         </children>
      </HBox>
      <Button mnemonicParsing="false" onAction="#onConvertButtonPressed" text="Button" />
   </children>
</VBox>
```

</details>
</blockquote>

## 5.2 Bi-directional binding

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
Display solution...
</summary>

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

```java
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
```

```java
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

## 5.3 Distributing responsibilities

With the Model-View-ViewModel design pattern, one challenge is to decide where everything goes.

An argument can be made, that input validation should happen at the Model level, to ensure that regardless of the upper layers, we will never have invalid data in the Model.

On the other hand, if the operations are expensive, why waste time trying to process data that could have already been rejected? Surely we should validate our data in the ViewModel?

But why even bother the ViewModel? If we design our View appropriately, perhaps we can prevent the user from creating malformed data in the first place?

In this exercise we will explore how the seemingly simple responsibility of input validation can be applied in the different parts of the Model-View-ViewModel architecture.

### A simple example

We will use a Model that stores information about `User` objects (a simple class with a `userName`, `password` and `age`), and a single View where we can register new users.

Download the [example](/05%20MVVM%201/Examples/Input%20Validation), or create your own.

Ensure that everything works by creating some `User` objects in your `Model`.

### Input validation rules

The rules for users are kept unrealistically simple for the purpose of this exercise:

 - A user name must consist of at most 20 characters
 - A password must consist of at least 8 characters
 - When registering a new user, the password must be typed twice, and both instances must be identical
 - A user must have an age consisting only of whole numbers

### Input validation in View

Let us start by adding some rules in our View.

The `age` attribute can only be an integer, so it would make sense that our input field for age does not allow the user to input anything other than whole numbers.

The following snippet can be used to set a `TextFormatter` for the field, that rejects everything but numbers:

```java
ageField.setTextFormatter(new TextFormatter<Integer>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
```

Similarly, we can limit the user name to only 20 characters, by simply rejecting any input beyond that, with this snippet:

```java
usernameField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 20) {
                return change;
            }
            return null;
        }));
```

What about the rule for passwords matching? 
Or the rule that a password must be at least 8 characters?
Should that be the responsibility of the View?

It seems reasonable to make the claim, that enforcing these sort of rules is outside of the responsibilities of the View.
Therefore, we limit ourselves to only implement rules that involve the View-elements.

For now, that means that the View is responsible for providing an input field that only accepts integers, and an input field that only accepts strings with a max length of 20 characters.

### Input validation in ViewModel

Those rules that we didn't feel belonged in the View are perfect candidates for the type of rules we want to implement in the ViewModel.

Let us start with the one about passwords having to match. It's easy enough to check if the two properties have the same value when the button is pressed:

```java
public void addUser()
{
    if(!password.get().equals(passwordRepeat.get()))
    {
        System.out.println("Passwords do not match");
        return;
    }
    model.addUser(username.get(), password.get(), age.get());
}
```

But since we are dealing with Properties, we can get notified every time the value of either field changes. So we could disable the "Submit" button until the rules are satisfied.

This requires us to create a new binding, between a new `BooleanProperty` in our `ViewModel` and our `Button` in the `View`:

```java
@FXML
private Button addButton

addButton.disableProperty().bind(viewModel.shouldSubmitBeDisabledProperty());
```

Now we add all the rules to check, by adding a new listener to the `passwordRepeatField` Property:

```java
passwordRepeat.addListener((observable, oldValue, newValue) -> {
    if(!password.get().equals(passwordRepeat.get()) 
    || password.get().length() < 8 
    || passwordRepeat.get().length() < 8)
    {
        shouldSubmitBeDisabled.set(true);
    }
    else
    {
        shouldSubmitBeDisabled.set(false);
    }
});
```

This should be refactored, moved into a private method, and have some null-pointer checks added.

### Input validation in Model

Finally, everything is checked, all rules are validated and we can never end up with illegal User objects. Or can we?

What happens if we decide to ditch the Model-View-ViewModel architecture, and create a brand new GUI? All our rules are currently enforced in the View and ViewModel.

For this reason, it could be argued, that input validation (data validation) should also happen at the Model level. In our case it will result in the same checks being performed (potentially) twice, but the trade-off is that we future-proof our Model.

We can implement all the rules directly in the `addUser` method for now:

```java
public void addUser(String username, String password, int age)
{
    if(username.length() <= 20 && password.length() >= 8)
    {
        users.add(new User(username, password, age));
        System.out.println("User added: " + username + ", " + password + ", " + age);
    }
}
```

Notice how the rules regarding matching passwords can not (and should not) be implemented at the Model level. <br>
This is not a rule that affects the data in User, rather it is a rule for how we construct User objects.

## 5.4 Multiple View and ViewModel classes

We often find ourselves in the situation, where a single Model can be used by many different classes.

For example, one class might be interested in the state of the model and visually represent this, while another class might want to modify the contents of the Model without caring about the output.

In the MVVM design pattern, we do this by creating multiple sets of View and ViewModel classes and connect them to the same Model.

We will expand on our previous exercise.
The input validation is not important for this exercise, so if your solution has become overly complicated, just reset with the [example](/05%20MVVM%201/Examples/Input%20Validation) as your starting point.

We want to have another windows, where we display these users in a list. 

To do so, we create a new set of View and ViewModel classes.

To distinguish between the different View and ViewModels, we will rename our old View to `CreateUserView`.

We also rename our ViewModel to `CreateUserViewModel` and our `.fxml` file to `CreateUserView.fxml` and ensure that our `fx:controller` attribute is updated.

### User List ViewModel

We will need a new ViewModel for our list display.

Create a new ViewModel class and call it `UserListViewModel`. In the constructor it receivess the `Model` - notice how we are using the same `Model` as before, no new Model is introduced.

The `UserListViewModel` should have another attribute, of type `ObservableList<User>` where we keep a collection of the users we want to display in the list.

Initialize this list in the constructor, using the JavaFX utility class:

```java
private ObservableList<User> users;

users = FXCollections.observableArrayList();
```

Create a getter for the list, and a `refresh()` method that we can call from the View. It should just get the newest data from the model:

```java
public void refresh()
{
    users.clear();
    users.addAll(model.getUsers());
}
```

<blockquote>
<details>
<summary>
Display solution...
</summary>

```java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserListViewModel
{
    private Model model;
    private ObservableList<User> users;

    public UserListViewModel(Model model)
    {
        this.model = model;
        users = FXCollections.observableArrayList();
        refresh();
    }

    public ObservableList<User> getUsers()
    {
        return users;
    }

    public void refresh()
    {
        users.clear();
        users.addAll(model.getUsers());
    }
}
```

</details>
</blockquote>

### User List View

We call our new View `UserListView`, and create a `.fxml` file for it with the name `UserListView.fxml`

Our `UserListView` should have an attribute of type `UserListViewModel` that we receive in the constructor.

The layout of the new View should have a component of type `ListView` and a single button that refreshes the data in the corresponding ViewModel.

Remember to set the `fx:id` attribute of the `ListView` component, and the `onAction` attribute of the button correctly.

In the `initialize()` method, set the items of the `ListView` to the `ObservableList<User>` from the `UserListViewModel`.

<blockquote>
<details>
<summary>
Display solution...
</summary>

```java
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class UserListView
{
    @FXML
    private ListView<User> userListView;

    private UserListViewModel userListViewModel;

    public UserListView(UserListViewModel createUserViewModel)
    {
        this.userListViewModel = createUserViewModel;
    }

    public void initialize()
    {
        userListView.setItems(userListViewModel.getUsers());
    }

    public void onRefreshButtonPressed()
    {
        userListViewModel.refresh();
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox prefHeight="221.0" prefWidth="422.0" xmlns="http://javafx.com/javafx/11.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="UserListView">
   <children>
      <ListView fx:id="userListView" prefHeight="200.0" prefWidth="200.0" />
      <Button mnemonicParsing="false" onAction="#onRefreshButtonPressed" text="Refresh" />
   </children>
</VBox>
```

</details>
</blockquote>

### Starting everything from Main

Now we just need to update our main method so it can start everything.

We create two ViewModels now, one for each View, and we use the `FXMLLoader` twice, to `load()` both Views from our `.fxml` files.

We want two separate windows, so we also need another `Stage` for our new View.

<blockquote>
<details>
<summary>
Display solution...
</summary>

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
    public void start(Stage primaryStage) throws Exception
    {
        Model model = new Model();
        CreateUserViewModel createUserViewModel = new CreateUserViewModel(model);
        UserListViewModel userListViewModel = new UserListViewModel(model);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateUserView.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new CreateUserView(createUserViewModel));

        Scene createUserScene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Create User");
        primaryStage.setScene(createUserScene);
        primaryStage.show();

        fxmlLoader = new FXMLLoader(getClass().getResource("UserListView.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new UserListView(userListViewModel));

        Scene userListScene = new Scene(fxmlLoader.load());
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("View Users");
        secondaryStage.setScene(userListScene);
        secondaryStage.show();
    }
}
```

</details>
</blockquote>

## 5.5 Organizing everything

Everything is working, so surely we must be done by now?

Look at your solution to the previous exercise. We have a total of 9 files in our directory now:

 - `Main`
 - `Model`
 - `User`
 - `CreateUserView`
 - `CreateUserView.fxml`
 - `CreateUserViewModel`
 - `UserListView`
 - `UserListViewModel`
 - `UserListView.fxml`
 
Maybe now would be a good time to start organizing everything a bit better...

Excatly how to organize everything will be up to you - there is no one solution that will always work.

We could separate our files by their components:

 - **Model**
   - `Model`
   - `User`
 - **View**
   - `CreateUserView`
   - `CreateUserView.fxml`
   - `UserListView`
   - `UserListView.fxml`
 - **ViewModel**
   - `CreateUserViewModel`
   - `UserListViewModel`
 - **Application**
   - `Main`
   
Or we could separate them by their feature:

 - **Model**
   - `Model`
   - `User`
 - **Create User**
   - `CreateUserView`
   - `CreateUserView.fxml`
   - `CreateUserViewModel`
 - **User List**
   - `UserListView`
   - `UserListView.fxml`
   - `UserListViewModel`
 - **Application**
   - `Main`
   
Regardless of what we choose, we will put everything into packages, to create a better separation and to maintain an overview of all the files.

> [!CAUTION]
> When moving everything into packages, make sure that you update your `fx:controller` attributes accordingly, as well as your paths when loading .fxml files with the `FXMLLoader`

### Factories

We notice that the `Main` class grew quite a lot when we added another window to our application. As we continue, this problem is only going to get worse, and we could potentially end up with a `Main` class that does a lot more than just start our application (which is the only thing we want it to do).

To avoid this, we can use an approximation of the Factory Method Pattern (this isn't strictly the Factory Method Pattern, but it is close enough for our need).

We create 3 new classes:

 - `ModelFactory`
 - `ViewModelFactory`
 - `ViewFactory`

The purpose of these classes, is to handle all the constructing of objects that right now happens in the `Main` class.

We start with the `ModelFactory` - it should simply create our `Model` when needed:

```java
public class ModelFactory
{
    private Model model;

    public Model getModel() 
    {
        if(model == null) 
        {
            model = new Model();
        }
        return model;
    }
}
```

We use lazy instantiation to delay the creating until needed (it won't matter much for this exercise, but it's good practice to do so).

Now we can create the `ViewModelFactory`. This class has an attribute of type `ModelFactory` that it uses to create instances of ViewModels.

For now we have two different ViewModels, `UserListViewModel` and `CreateUserViewModel`. Our `ViewModelFactory` should construct these when needed, just like how the `ModelFactory` did.

When creating the ViewModels, they need a reference to the `Model` - luckily our `ViewModelFactory` can provide this, by getting it from the `ModelFactory`.

```java
public class ViewModelFactory
{
    private UserListViewModel userListViewModel;
    private CreateUserViewModel createUserViewModel;

    private ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory modelFactory)
    {
        this.modelFactory = modelFactory;
    }

    public UserListViewModel getUserListViewModel()
    {
        if(userListViewModel == null)
        {
            userListViewModel = new UserListViewModel(modelFactory.getModel());
        }
        return userListViewModel;
    }

    public CreateUserViewModel getCreateUserViewModel()
    {
        if(createUserViewModel == null)
        {
            createUserViewModel = new CreateUserViewModel(modelFactory.getModel());
        }
        return createUserViewModel;
    }
}
```

Lastly, we can create the `ViewFactory` class.

The idea is the same as for the two previous factories, but this factory is going to implement all the logic that is currently happening in the `Main` class with regards to JavaFX `Scene` and `Stage` creation.

We can start by creating the constructor. We know that it is going to need a `ViewModelFactory` attribute, that we receive in the constructor.

```java
private ViewModelFactory viewModelFactory;

    public ViewFactory(ViewModelFactory viewModelFactory)
    {
        this.viewModelFactory = viewModelFactory;
    }
```

We also know the general idea, namely that the `ViewFactory` should create Views when needed, and that we are going to need two Views, `CreateUserView` and `UserListView`.

So the straightforward approach would be to just have the `ViewFactory` create them:

```java
    public UserListView getUserListView()
    {
        return new UserListView(viewModelFactory.getUserListViewModel());
    }

    public CreateUserView getCreateUserView()
    {
        return new CreateUserView(viewModelFactory.getCreateUserViewModel());
    }
```

The problem is, that we want JavaFX to handle the creation of these classes. This is what currently happens in the `Main` class:

```java
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateUserView.fxml"));
fxmlLoader.setControllerFactory(controllerClass -> new CreateUserView(createUserViewModel));

Scene createUserScene = new Scene(fxmlLoader.load());
primaryStage.setTitle("Create User");
primaryStage.setScene(createUserScene);
primaryStage.show();

fxmlLoader = new FXMLLoader(getClass().getResource("UserListView.fxml"));
fxmlLoader.setControllerFactory(controllerClass -> new UserListView(userListViewModel));

Scene userListScene = new Scene(fxmlLoader.load());
Stage secondaryStage = new Stage();
secondaryStage.setTitle("View Users");
secondaryStage.setScene(userListScene);
secondaryStage.show();
```

All we need to do is move that logic into the `ViewFactory` class, update some references, add some attributes and we are done:

```java
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory
{
    private ViewModelFactory viewModelFactory;
    private UserListView userListView;
    private CreateUserView createUserView;

    private final Stage primaryStage;
    private FXMLLoader fxmlLoader;


    public ViewFactory(ViewModelFactory viewModelFactory, Stage primaryStage)
    {
        this.viewModelFactory = viewModelFactory;
        this.primaryStage = primaryStage;
    }

    public UserListView getUserListView() throws IOException
    {
        if(userListView == null)
        {
            fxmlLoader = new FXMLLoader(getClass().getResource("UserListView.fxml"));
            fxmlLoader.setControllerFactory(controllerClass -> new UserListView(viewModelFactory.getUserListViewModel()));

            Scene createUserScene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("View Users");
            primaryStage.setScene(createUserScene);
            primaryStage.show();
            userListView = fxmlLoader.getController();
        }
        return userListView;
    }

    public CreateUserView getCreateUserView() throws IOException
    {
        if(createUserView == null)
        {
            fxmlLoader = new FXMLLoader(getClass().getResource("CreateUserView.fxml"));
            fxmlLoader.setControllerFactory(controllerClass -> new CreateUserView(viewModelFactory.getCreateUserViewModel()));

            Scene userListScene = new Scene(fxmlLoader.load());
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Create User");
            secondaryStage.setScene(userListScene);
            secondaryStage.show();
            createUserView = fxmlLoader.getController();
        }
        return createUserView;
    }
}
```

Now we can rewrite our `Main` class to simply create the Factories and then launch the Views we want:

```java
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ModelFactory modelFactory = new ModelFactory();
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewFactory viewFactory = new ViewFactory(viewModelFactory, primaryStage);

        viewFactory.getCreateUserView();
        viewFactory.getUserListView();
    }
}
```

