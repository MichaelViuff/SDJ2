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
        UppercaseModel uppercaseModel = new UppercaseModel();
        UppercaseViewModel uppercaseViewModel = new UppercaseViewModel(uppercaseModel);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new MainViewController(uppercaseViewModel));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Uppercase Observer Demonstration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
