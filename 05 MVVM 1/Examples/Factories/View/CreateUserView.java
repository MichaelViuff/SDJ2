package View;

import ViewModel.CreateUserViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

public class CreateUserView
{
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField passwordRepeatField;
    @FXML
    TextField ageField;
    @FXML
    Button addButton;

    private CreateUserViewModel createUserViewModel;

    public CreateUserView(CreateUserViewModel createUserViewModel)
    {
        this.createUserViewModel = createUserViewModel;
    }

    public void initialize()
    {
        usernameField.textProperty().bindBidirectional(createUserViewModel.usernameProperty());
        passwordField.textProperty().bindBidirectional(createUserViewModel.passwordProperty());
        passwordRepeatField.textProperty().bindBidirectional(createUserViewModel.passwordRepeatProperty());
        ageField.textProperty().bindBidirectional(createUserViewModel.ageProperty(), new NumberStringConverter());
        addButton.disableProperty().bind(createUserViewModel.shouldSubmitBeDisabledProperty());

        usernameField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 20) {
                return change;
            }
            return null;
        }));

        ageField.setTextFormatter(new TextFormatter<Integer>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    public void onAddButtonPressed()
    {
        createUserViewModel.addUser();
    }
}