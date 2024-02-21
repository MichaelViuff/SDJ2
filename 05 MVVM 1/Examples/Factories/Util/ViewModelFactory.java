package Util;

import ViewModel.CreateUserViewModel;
import ViewModel.UserListViewModel;

public class ViewModelFactory
{
    private UserListViewModel userListViewModel;
    private CreateUserViewModel createUserViewModel;

    private ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory modelFactory)
    {
        this.modelFactory = modelFactory;
    }

    public UserListViewModel getUserListViewModel()
    {
        if(userListViewModel == null)
        {
            userListViewModel = new UserListViewModel(modelFactory.getModel());
        }
        return userListViewModel;
    }

    public CreateUserViewModel getCreateUserViewModel()
    {
        if(createUserViewModel == null)
        {
            createUserViewModel = new CreateUserViewModel(modelFactory.getModel());
        }
        return createUserViewModel;
    }
}
