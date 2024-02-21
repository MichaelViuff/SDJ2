package View;

import Model.User;
import ViewModel.UserListViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class UserListView
{
    @FXML
    private ListView<User> userListView;

    private UserListViewModel userListViewModel;

    public UserListView(UserListViewModel createUserViewModel)
    {
        this.userListViewModel = createUserViewModel;
    }

    public void initialize()
    {
        userListView.setItems(userListViewModel.getUsers());
    }

    public void onRefreshButtonPressed()
    {
        userListViewModel.refresh();
    }
}
