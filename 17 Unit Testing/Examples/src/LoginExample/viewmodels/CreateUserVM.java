package LoginExample.viewmodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import LoginExample.model.LoginModel;

import java.beans.PropertyChangeEvent;

public class CreateUserVM {

    // properties to hold the data shown/created in the controller
    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty passwordAgain = new SimpleStringProperty();
    private StringProperty createUserResult = new SimpleStringProperty();

    private LoginModel model;

    // constructor
    public CreateUserVM(LoginModel loginModel) {
        model = loginModel;
        // adding a listener to the model. When the event called "CreateuserResult" is fired, a method in this class
        // is called
        model.addListener("CreateUserResult", this::onCreateUser);
    }

    // called when the model has handled the request to create a user
    // A property is updated, so the controller can either show the error, or change the view if everything went okay
    private void onCreateUser(PropertyChangeEvent propertyChangeEvent) {
        createUserResult.setValue((String) propertyChangeEvent.getNewValue());
    }

    // method to clear the data from the properties
    public void clearFields() {
        username.setValue("");
        password.setValue("");
        passwordAgain.setValue("");
        createUserResult.setValue("");
    }

    // method called by the controller, when the user wants to create a new user
    // relevant data is retrieved from the properties, and forwarded to the model
    public void attemptCreateUser() {
        model.createUser(username.getValue(), password.getValue(), passwordAgain.getValue());
    }

    // methods to get access to the properties, so the controller can bind to them
    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty passwordAgainProperty() {
        return passwordAgain;
    }

    public StringProperty createUserResultProperty() {
        return createUserResult;
    }
}
