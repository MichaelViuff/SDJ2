import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainViewController
{
    @FXML
    private TextField inputTextField;
    @FXML
    private TextField outputTextField;
    private UppercaseViewModel uppercaseViewModel;

    public MainViewController(UppercaseViewModel uppercaseViewModel)
    {
        this.uppercaseViewModel = uppercaseViewModel;
    }

    public void initialize()
    {
        inputTextField.textProperty().bindBidirectional(uppercaseViewModel.inputTextProperty());
        outputTextField.textProperty().bindBidirectional(uppercaseViewModel.outputTextProperty());
    }

    public void onConvertButtonPressed()
    {
        uppercaseViewModel.convert();
    }
}
