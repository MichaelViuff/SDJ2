package LoginExample.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import LoginExample.model.LoginModel;

import java.beans.PropertyChangeEvent;

public class ChangePasswordVM {

    // View model class for the change password view


    private final LoginModel loginModel; // reference to the model interface. I don't know about the actual implementation

    // String properties to hold the data shown/created in the view
    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty newPassword = new SimpleStringProperty();
    private StringProperty newPasswordAgain = new SimpleStringProperty();
    private StringProperty changePasswordResult = new SimpleStringProperty();

    // constructor
    public ChangePasswordVM(LoginModel model) {
        loginModel = model;
        model.addListener("ChangePasswordResult", this::onChangePasswordResult);
    }

    // method called, when the model has been requested to update a password.
    // I receive the result here, and update the string property, which will automatically
    // push the data to the controller, because of the bindings. The Controller can then react to the result
    private void onChangePasswordResult(PropertyChangeEvent propertyChangeEvent) {
        changePasswordResult.set((String)propertyChangeEvent.getNewValue());
    }

    // method called by the controller, when the user requests to update the password.
    // releveant information is retrieved from the properties, and forwarded to the model
    public void updatePassword() {
        loginModel.changePassword(username.getValue(), password.getValue(), newPassword.get(), newPasswordAgain.get());
    }

    // method to clear all data
    public void clearFields() {
        username.set("");
        password.set("");
        newPassword.set("");
        newPasswordAgain.set("");
        changePasswordResult.set("");
    }

    // methods to get the properties, so the controller can bind to them
    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty newPasswordProperty() {
        return newPassword;
    }

    public StringProperty newPasswordAgainProperty() {
        return newPasswordAgain;
    }

    public StringProperty changePasswordResultProperty() {
        return changePasswordResult;
    }
}
