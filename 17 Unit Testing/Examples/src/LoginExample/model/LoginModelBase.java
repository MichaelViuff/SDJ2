package LoginExample.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class LoginModelBase implements LoginModel {

    // this class is introduced for two purposes.
    // I'm going to have multiple LoginModel implementations for various testing purposes.
    // This means all listener-subject code is not duplicated. I have my PropertyChangeSupport once, instead of in each
    // implementing class
    // I also have extra methods, mainly for testing purposes. It can setup a few dummy users, and remove all users.
    // usually a class like this is not relevant for MVVM.

    protected PropertyChangeSupport support = new PropertyChangeSupport(this);
    protected List<User> users = new LinkedList<>();

    public void addListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    // this method is used to pupulate my dummy database with modelimpls few hardcoded users.
    public void populateModelWithDummyusers() {
        User[] u = {
                new User("Troels", "Troels1"),
                new User("Steffen", "Steffen1"),
                new User("Michael", "Michael1"),
                new User("Kasper", "Kasper1"),
                new User("Jakob", "Jakob1"),
        };
        Collections.addAll(users, u);
    }

    // used to clear the list of users. Mainly available for testing purposes
    public void clearUsers() {
        users.clear();
    }
}
