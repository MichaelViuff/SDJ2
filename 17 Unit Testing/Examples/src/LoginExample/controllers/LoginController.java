package LoginExample.controllers;

import LoginExample.core.ViewHandler;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import LoginExample.viewmodels.LoginVM;

public class LoginController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label loginResultLabel;

    private LoginVM loginViewModel;
    private ViewHandler viewHandler;

    public void init(LoginVM lvm, ViewHandler viewHandler) {
        this.loginViewModel = lvm;
        this.viewHandler = viewHandler;

        // Binding the label to the property which will be updated with the login result
        // Using bidirectional, so the textfield can be cleared from the VM
        loginResultLabel.textProperty().bindBidirectional(lvm.loginResultProperty());

        // adding modelimpls listener to the login result property. Whenever the property in the VM is updated
        // the onLoginResult method is called
        loginResultLabel.textProperty().addListener(this::onLoginResult);

        usernameTextField.textProperty().bindBidirectional(lvm.userNameProperty());
        passwordTextField.textProperty().bindBidirectional(lvm.passwordProperty());
    }

    private void onLoginResult(Observable observable, String old, String newVal) {
        // if the result of modelimpls login attempt is OK, then I change views. Otherwise do nothing
        // the label will contain the login error message, because the VM updates it.
        if("OK".equals(newVal)) {
            viewHandler.openLoggedInSuccesfulView();
        }
    }

    // called when Login Button is pressed
    public void onLoginButton(ActionEvent actionEvent) {
        loginViewModel.validateLogin();
    }

    // Called when Exit Button is pressed. Just terminating the application
    public void onExitButton(ActionEvent actionEvent) {
        System.exit(0);
    }

    // Opening Create New User view.
    public void onCreateUserButton(ActionEvent actionEvent) {
        loginViewModel.clearFields();
        viewHandler.openCreateUserView();
    }

    // Opening Change Password view.
    public void onChangePassword(ActionEvent actionEvent) {
        loginViewModel.clearFields();
        viewHandler.openChangePasswordView();
    }
}
