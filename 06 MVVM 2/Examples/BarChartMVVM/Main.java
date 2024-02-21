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