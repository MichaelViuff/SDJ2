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