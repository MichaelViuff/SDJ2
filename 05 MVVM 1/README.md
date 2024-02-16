# 05 Model-View-ViewModel Pattern

## 5.1 MVVM in Java

testfdsafdaf
fdsafsd
fdsafgg

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
