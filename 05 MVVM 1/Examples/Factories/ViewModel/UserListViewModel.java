package ViewModel;

import Model.Model;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserListViewModel
{
    private Model model;
    private ObservableList<User> users;

    public UserListViewModel(Model model)
    {
        this.model = model;
        users = FXCollections.observableArrayList();
        refresh();
    }

    public ObservableList<User> getUsers()
    {
        return users;
    }

    public void refresh()
    {
        users.clear();
        users.addAll(model.getUsers());
    }
}