import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.beans.PropertyChangeEvent;

public class BarChartViewController
{
  @FXML
  private BarChart<String, Integer> barChart;
  @FXML
  private CategoryAxis xAxis = new CategoryAxis();
  @FXML
  private NumberAxis yAxis = new NumberAxis();

  private XYChart.Series<String, Integer> dataSeries;

  public BarChartViewController(DataModel model)
  {
    model.addPropertyChangeListener("DataChange", this::updateData);
  }

  public void initialize()
  {
    barChart.setTitle("Data Representation");
    xAxis.setLabel("Colour");
    yAxis.setLabel("Value");
    yAxis.setAutoRanging(false);
    yAxis.setLowerBound(0);
    yAxis.setUpperBound(100);

    dataSeries = new XYChart.Series();
    dataSeries.setName("Colour Values");
    dataSeries.getData().add(new XYChart.Data("Red", 0));
    dataSeries.getData().add(new XYChart.Data("Green", 0));
    dataSeries.getData().add(new XYChart.Data("Yellow", 0));
    barChart.getData().addAll(dataSeries);
  }

  private void updateData(PropertyChangeEvent evt)
  {
    Platform.runLater(() ->
    {
      int[] data = (int[]) evt.getNewValue();
      dataSeries.getData().get(0).setYValue(data[0]);
      dataSeries.getData().get(1).setYValue(data[1]);
      dataSeries.getData().get(2).setYValue(data[2]);
    });
  }
}
