package LoginExample.modelimpls;

import LoginExample.model.LoginModelBase;
import LoginExample.model.User;

// I have chosen to extend an abstract LoginModelBase. I'm going to have multiple versions of the LoginModelInMemory,
// so this way I can avoid duplicate code. The potential duplicate code is put into the LoginModelBase.
public class LoginModelInMemoryA extends LoginModelBase {

    // The implementation of the Model interface. This version is supposed to work
    // In here I do all the validation checking, when logging in, creating new users, updating passwords.
    // It also has access to all know user information, through the parent class, LoginModelBase. Usually
    // such information would be in a database, or on the server.

    public LoginModelInMemoryA() {
        populateModelWithDummyusers();
    }

    @Override
    public void validateLogin(String username, String password) {
        String result = checkLoginCredentials(username, password);
        support.firePropertyChange("LoginResult", "", result);
    }

    @Override
    public void changePassword(String username, String pw, String newPw, String newPwAgain) {
        String result = checkUpdateNewPW(username, pw, newPw, newPwAgain);

        support.firePropertyChange("ChangePasswordResult", "", result);
    }

    @Override
    public void createUser(String username, String pw, String pwAgain) {
        String result = attemptCreateUser(username, pw, pwAgain);

        if("OK".equals(result)) {
            // adding the new user
            users.add(new User(username, pw));
        }

        support.firePropertyChange("CreateUserResult", "", result);
    }

    private String checkLoginCredentials(String username, String password) {
        User user = findUser(username);
        if(user == null) {
            return "User not found";
        }
        if(!user.password.equals(password)) {
            return "Incorrect password";
        }

        return "OK";
    }

    private User findUser(String username) {
        User user = null;
        for (User u : users) {
            if(u.userName.equals(username)) {
                user = u;
                break;
            }
        }
        return user;
    }

    private String attemptCreateUser(String username, String pw, String pwAgain) {
        if(username == null) {
            return "Username cannot be empty";
        }
        if(username.contains("#")) {
            return "Username cannot contain #";
        }
        if(username.length() < 4) {
            return "Username must contain more than 3 characters";
        }
        if(username.length() > 14) {
            return "Username must contain less than 15 characters";
        }
        if(findUser(username) != null) {
            return "Username already exists";
        }

        // checking the passwords
        return validatePasswords(pw, pwAgain); // returning result of checking passwords
    }

    // method to check that two passwords are valid and matches
    private String validatePasswords(String pw, String pwAgain) {
        // I'm using an approach here called "guard clause", or "if guards"
        // Some people dislike it because there are many returns, i.e. exits out of the method, and they feel it
        // can cause confusion.
        // In lower level languages such as C++, it can cause problems, if you are not careful.
        // In java there are no problems with this, because we have a garbage collector.
        // It's a matter of preference.
        // Some people prefer to have a bunch of if-elseif-elseif-elseif, and only one return statement at the very end
        // The point is I'm checking and validating a bunch of stuff before at the end, allowing to do what was
        // intended. In this case just returning "OK", but it could also be putting things in the database, or deleting
        // data or starting a new Thread or whatever.

        if(pw == null) {
            return "Password cannot be empty";
        }
        if(pw.length() < 8) {
            return "Password length must be 8 or more";
        }
        if(pw.length() > 11) {
            return "Password length must be 14 or less";
        }
        if(!pw.equals(pwAgain)) {
            return "Passwords do not match";
        }

        // verifying that the password contains at least one upper case character
        if(pw.equals(pw.toLowerCase())) {
            return "Password must contain at least one upper case letter";
        }

        // password may not contain "#"
        if(pw.contains("#")) {
            return "Password cannot contain #";
        }

        // using regular expression to check that the password contains a number
        if(!pw.matches(".*\\d.*")) {
            return "Password must contain at least one number";
        }

        // if I reach this point, everything is okay.
        return "OK";
    }

    private boolean pwContainsLowerCase(String pw) {
        boolean foundLowerCase = false;
        for (int i = 0; i < pw.length(); i++) { // looping through all characters in the pw
            char c = pw.charAt(i);
            if(Character.isLowerCase(c)) { // checking if the character is lower case
                foundLowerCase = true;
                break;
            }
        }
        return foundLowerCase;
    }

    private String checkUpdateNewPW(String username, String pw, String newPw, String newPwAgain) {

        // check that username and pw is correct;
        if(!"OK".equals(checkLoginCredentials(username, pw))) {
            return "Incorrect login credentials";
        }

        // check that passwords are valid and matches
        return validatePasswords(newPw, newPwAgain);
    }
}
