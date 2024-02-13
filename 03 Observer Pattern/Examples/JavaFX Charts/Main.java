import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application
{
  public static void main(String[] args)
  {
    launch();
  }

  @Override public void start(Stage stage) throws IOException
  {
    DataModel model = new DataModel();

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BarChartView.fxml"));
    fxmlLoader.setControllerFactory(controllerClass -> new BarChartViewController(model));
    Scene scene = new Scene(fxmlLoader.load(), 800, 600);
    stage.setTitle("Data Representation");
    stage.setScene(scene);
    stage.show();

    Thread dataThread = new Thread(model);
    dataThread.setDaemon(true);
    dataThread.start();
  }
}