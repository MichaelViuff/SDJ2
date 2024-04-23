package LoginExample.controllers;

import LoginExample.core.ViewHandler;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import LoginExample.viewmodels.CreateUserVM;

public class CreateUserController {

    // setting up all the connections to various GUI elements: Text fields and labels.
    // These are automatically set by the JavaFX framework.
    // Notice the small icon to the left, indicating IntelliJ knows about the connection.
    // If the Icon is not there, something is wrong
    @FXML
    private Label createResultLabel;
    @FXML
    private TextField passwordAgainTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField usernameTextField;


    private CreateUserVM createUserVM;
    private ViewHandler viewHandler;

    public void init(CreateUserVM createUserVM, ViewHandler viewHandler) {
        this.createUserVM = createUserVM;
        this.viewHandler = viewHandler;

        // setting up bindings
        createResultLabel.textProperty().bind(createUserVM.createUserResultProperty());
        // adding a listener, so when the value of the label changes, the method "onCreateUser" is called
        createResultLabel.textProperty().addListener(this::onCreateUser);

        // setting up bidirectional bindings, so data can flow automatically between controller and view model
        usernameTextField.textProperty().bindBidirectional(createUserVM.usernameProperty());
        passwordTextField.textProperty().bindBidirectional(createUserVM.passwordProperty());
        passwordAgainTextField.textProperty().bindBidirectional(createUserVM.passwordAgainProperty());
    }

    // method called, when the content of the result label changes. This is changed from the View Model, based on the
    // result of the Model.
    // If all is okay, then I clear the fields, and open the login view, assuming the user has been created.
    private void onCreateUser(Observable observable, String old, String newVal) {
        if("OK".equals(newVal)) {
            createUserVM.clearFields();
            viewHandler.openLoginView();
        }
    }

    // method called, when the create user button is pressed.
    // make the View Model handle the request
    public void onCreateUserButton(ActionEvent actionEvent) {
        createUserVM.attemptCreateUser();
    }

    public void onCancelButton(ActionEvent actionEvent) {
        createUserVM.clearFields();
        viewHandler.openLoginView();
    }
}
