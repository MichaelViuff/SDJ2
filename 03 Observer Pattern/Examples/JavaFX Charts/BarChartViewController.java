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
    private XYChart.Data<String, Integer> redData;
    private XYChart.Data<String, Integer> greenData;
    private XYChart.Data<String, Integer> blueData;


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
            redData.setYValue(newRed);
            greenData.setYValue(newGreen);
            blueData.setYValue(newBlue);
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

        redData = new XYChart.Data("Red", 0);
        greenData = new XYChart.Data("Green", 0);
        blueData = new XYChart.Data("Blue", 0);

        dataSeries = new XYChart.Series();
        dataSeries.getData().addAll(redData, greenData, blueData);

        barChart.getData().add(dataSeries);

        Node node = barChart.lookup(".data0.chart-bar");
        node.setStyle("-fx-bar-fill: red");
        node = barChart.lookup(".data1.chart-bar");
        node.setStyle("-fx-bar-fill: green");
        node = barChart.lookup(".data2.chart-bar");
        node.setStyle("-fx-bar-fill: blue");
    }
}
