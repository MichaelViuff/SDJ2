import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.beans.PropertyChangeEvent;

public class BarChartViewController {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();

    private XYChart.Series<String, Integer> dataSeries;

    public BarChartViewController(DataModel model)
    {
        model.addPropertyChangeListener("DataChange", this::onColourValueChange);
    }

    private void onColourValueChange(PropertyChangeEvent event)
    {
        Platform.runLater(() ->
        {
            int[] newValues = (int[]) event.getNewValue();
            int newRed = newValues[0];
            int newGreen = newValues[1];
            int newBlue = newValues[2];
            dataSeries.getData().get(0).setYValue(newRed);
            dataSeries.getData().get(1).setYValue(newGreen);
            dataSeries.getData().get(2).setYValue(newBlue);
        });
    }

    public void initialize()
    {
        barChart.setTitle("Data Representation");
        barChart.setLegendVisible(false);
        xAxis.setLabel("Colours");
        yAxis.setLabel("Value");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        dataSeries = new XYChart.Series();
        dataSeries.getData().add(new XYChart.Data("Red", 0));
        dataSeries.getData().add(new XYChart.Data("Green", 0));
        dataSeries.getData().add(new XYChart.Data("Blue", 0));
        barChart.getData().add(dataSeries);

        Node node = barChart.lookup(".data0.chart-bar");
        node.setStyle("-fx-bar-fill: red");
        node = barChart.lookup(".data1.chart-bar");
        node.setStyle("-fx-bar-fill: green");
        node = barChart.lookup(".data2.chart-bar");
        node.setStyle("-fx-bar-fill: blue");
    }
}
