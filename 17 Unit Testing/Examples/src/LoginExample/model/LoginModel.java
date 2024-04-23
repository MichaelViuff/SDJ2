package LoginExample.model;

import java.beans.PropertyChangeListener;

public interface LoginModel {

    void addListener(String name, PropertyChangeListener listener);

    void validateLogin(String username, String password);

    void createUser(String username, String pw, String pwAgain);

    void changePassword(String username, String pw, String newPw, String newPwAgain);
}
