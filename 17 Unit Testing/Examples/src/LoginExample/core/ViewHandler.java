package LoginExample.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import LoginExample.controllers.ChangePasswordController;
import LoginExample.controllers.CreateUserController;
import LoginExample.controllers.LoginController;

import java.io.IOException;

public class ViewHandler {

    private final ViewModelFactory viewModelFactory;
    private Stage mainStage;

    public ViewHandler(ViewModelFactory lvm) {
        this.viewModelFactory = lvm;
        mainStage = new Stage();
    }

    // I could do this in the constructor. It's just personal preference to make the constructor only create things
    // and not start all kinds of stuff.
    public void start() {
        // opening first view
        openLoginView();
        mainStage.show();
    }

    private Scene loginScene;
    public void openLoginView() {
        try {
            // I check if the scene has already been created.
            // storing the scene for future use, so I do not have to load it and initialize the controller
            // multiple times.
            if(loginScene == null) {
                FXMLLoader loader = new FXMLLoader(); // creating fx loader, which can load fxml and create controller

                loader.setLocation(getClass().getResource("../views/Login.fxml"));

                // loading scene root. This contains every object in the scene
                // It's a tree structure similar to XML or HTML
                Parent root = loader.load();

                // getting the controller. JavaFX creates this and makes the connection between fxml file and controller
                LoginController view = loader.getController();

                // initializing my controller. I need this method, because we do not have access to the constructor
                view.init(viewModelFactory.getLoginVM(), this);

                // storing the created scene for future use
                loginScene = new Scene(root);
            }
            // setting title of the window
            mainStage.setTitle("Log in");

            // putting my scene into the window
            mainStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openLoggedInSuccesfulView() {
        // no need to store for future use, I only get to this view once
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("../views/LoginResult.fxml"));
            Parent root = loader.load();

            mainStage.setTitle("Logged in");

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene createUserScene;
    public void openCreateUserView() {
        try {
            // no need to load the same scene more than once. I can just reuse it
            if(createUserScene == null) {
                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(getClass().getResource("../views/CreateUser.fxml"));
                Parent root = loader.load();

                CreateUserController view = loader.getController();
                view.init(viewModelFactory.getCreateUserVM(), this);

                // storing scene in field variable for future use
                createUserScene = new Scene(root);
            }
            mainStage.setTitle("Create User");
            mainStage.setScene(createUserScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene changePWScene;
    public void openChangePasswordView() {
        try {
            // no need to load the same scene more than once. I can just reuse it
            if(changePWScene == null) {
                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(getClass().getResource("../views/ChangePassword.fxml"));
                Parent root = loader.load();

                ChangePasswordController view = loader.getController();
                view.init(viewModelFactory.getChangePasswordVM(), this);

                // storing scene in field variable for future use
                changePWScene = new Scene(root);
            }
            mainStage.setTitle("Change password");
            mainStage.setScene(changePWScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
