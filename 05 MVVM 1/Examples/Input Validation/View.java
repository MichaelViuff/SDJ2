import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class View
{
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField passwordRepeatField;
    @FXML
    TextField ageField;

    private ViewModel viewModel;

    public View(ViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    public void initialize()
    {
        usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());
        passwordRepeatField.textProperty().bindBidirectional(viewModel.passwordRepeatProperty());
        ageField.textProperty().bindBidirectional(viewModel.ageProperty(), new NumberStringConverter());
    }

    public void onAddButtonPressed()
    {
        viewModel.addUser();
    }
}