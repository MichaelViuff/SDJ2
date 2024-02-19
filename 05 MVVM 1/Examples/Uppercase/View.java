package demonstration;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class View
{
    @FXML
    private TextField inputTextField;
    @FXML
    private TextField outputTextField;
    private ViewModel viewModel;

    public View(ViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    public void initialize()
    {
        inputTextField.textProperty().bindBidirectional(viewModel.inputTextProperty());
        outputTextField.textProperty().bindBidirectional(viewModel.outputTextProperty());
    }

    public void onConvertButtonPressed()
    {
        viewModel.convert();
    }
}
