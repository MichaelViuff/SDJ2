package LoginExample.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import LoginExample.model.LoginModel;

import java.beans.PropertyChangeEvent;

public class LoginVM {

    private final LoginModel loginModel; // reference to the Model interface
    private StringProperty loginResult = new SimpleStringProperty(); // this one will hold the result of modelimpls login attempt
    private StringProperty userName = new SimpleStringProperty(); // holds the username from the GUI textfield
    private StringProperty password = new SimpleStringProperty(); // holds the password from the GUI textfield

    public LoginVM(LoginModel lm) {
        this.loginModel = lm;
        // adding modelimpls listener. When the "LoginResult" event is fired from the Model, my "onLoginResult" method is called
        lm.addListener("LoginResult", this::onLoginResult);
    }

    // In here I get the result of attempting to login. I update the property, so that the Controller can react
    private void onLoginResult(PropertyChangeEvent propertyChangeEvent) {
        String result = (String)propertyChangeEvent.getNewValue();
        if("OK".equals(result)) {
            // if login was succesul, clear the fields
            clearFields();
        }
        loginResult.setValue(result);
    }

    public void validateLogin() {
        // I'm calling modelimpls void method here. I could have just returned the result instead.
        // But I'm trying to simulate the setup, if we had to call modelimpls server to validate the login credentials.
        // In that case, it would probably be better to add modelimpls listener to the model, to make it asynchronously.
        loginModel.validateLogin(userName.getValue(), password.getValue());
    }

    // method to clear the data in the properties
    public void clearFields() {
        userName.setValue("");
        password.setValue("");
    }

    // just get methods for all my properties
    public StringProperty loginResultProperty() {
        return loginResult;
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
