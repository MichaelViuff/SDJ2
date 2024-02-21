package ViewModel;

import Model.Model;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class CreateUserViewModel
{
    private Model model;
    private StringProperty username, password, passwordRepeat;
    private IntegerProperty age;
    private BooleanProperty shouldSubmitBeDisabled;

    public CreateUserViewModel(Model model)
    {
        this.model = model;

        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        shouldSubmitBeDisabled = new SimpleBooleanProperty();
        passwordRepeat = new SimpleStringProperty();
        age = new SimpleIntegerProperty();

        shouldSubmitBeDisabled.set(true);
        passwordRepeat.addListener(this::validatePassword);
    }

    private void validatePassword(Observable observable)
    {
        if(!password.get().equals(passwordRepeat.get()) || password.get().length() < 8 || passwordRepeat.get().length() < 8)
        {
            shouldSubmitBeDisabled.set(true);
        }
        else
        {
            shouldSubmitBeDisabled.set(false);
        }
    }

    public void addUser()
    {
        model.addUser(username.get(), password.get(), age.get());
    }

    public StringProperty usernameProperty()
    {
        return username;
    }

    public StringProperty passwordProperty()
    {
        return password;
    }

    public IntegerProperty ageProperty()
    {
        return age;
    }

    public StringProperty passwordRepeatProperty()
    {
        return passwordRepeat;
    }

    public ObservableValue<Boolean> shouldSubmitBeDisabledProperty()
    {
        return shouldSubmitBeDisabled;
    }

}