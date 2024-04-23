package LoginExample.controllers;

import LoginExample.core.ViewHandler;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import LoginExample.viewmodels.ChangePasswordVM;

public class ChangePasswordController {

    // GUI Objects set by JavaFX. The variable names case-match the names from the fx:id in the fxml file
    // In intellij, just to the left, you should see small icons indicating that intellij knows about the
    // connection to the fxml file
    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField newPasswordTextField;
    public TextField newPasswordAgainTextField;
    public Label changePasswordResultLabel;

    private ChangePasswordVM changePasswordVM;
    private ViewHandler viewHandler;

    // method called to setup everything, from the ViewHandler
    public void init(ChangePasswordVM cpvm, ViewHandler vh) {
        changePasswordVM = cpvm;
        viewHandler = vh;

        // making the bindings, so data can flow to the View Model, and back again, automatically.
        usernameTextField.textProperty().bindBidirectional(changePasswordVM.usernameProperty());
        passwordTextField.textProperty().bindBidirectional(changePasswordVM.passwordProperty());
        newPasswordTextField.textProperty().bindBidirectional(changePasswordVM.newPasswordProperty());
        newPasswordAgainTextField.textProperty().bindBidirectional(changePasswordVM.newPasswordAgainProperty());

        // Here I use bind, i.e. one-directional. This is because, it's a label, and the user cannot insert anything.
        // There is no data to flow to the View Model. Only the View Model can update the content of this label
        changePasswordResultLabel.textProperty().bind(changePasswordVM.changePasswordResultProperty());

        // adding a listener. Whenever the value of the label changes, the method "onChangePasswordResult" is called
        changePasswordResultLabel.textProperty().addListener(this::onChangePasswordResult);
    }

    private void onChangePasswordResult(Observable observable, String old, String newVal) {
        // if the result is now "OK", that means the password change was accepted, and we can clear the fields, and
        // open the login view again
        if("OK".equals(newVal)) {
            changePasswordVM.clearFields();
            viewHandler.openLoginView();
        }
    }

    // method called when the Update Password button is pressed
    public void onUpdatePasswordButton(ActionEvent actionEvent) {
        changePasswordVM.updatePassword();
    }

    // Method called when the Cancel button is pressed
    public void onCancelButton(ActionEvent actionEvent) {
        changePasswordVM.clearFields();
        viewHandler.openLoginView();
    }
}
