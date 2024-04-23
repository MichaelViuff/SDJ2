package LoginExample.core;

import LoginExample.viewmodels.ChangePasswordVM;
import LoginExample.viewmodels.CreateUserVM;
import LoginExample.viewmodels.LoginVM;

public class ViewModelFactory {

    private final ModelFactory modelFactory;
    private LoginVM loginVM;
    private CreateUserVM createUserVM;
    private ChangePasswordVM changePasswordVM;

    public ViewModelFactory(ModelFactory mf) {
        this.modelFactory = mf;
    }

    // Here I have removed the access modifyer: private/protected/public.
    // This means this method is only available to other classes in the same package.
    LoginVM getLoginVM() {
        // using lazy instantiation, to ensure only one LoginVM is created, and it can subsequently be reused
        // I could also have instantiated them in modelimpls constructor
        if(loginVM == null) {
            loginVM = new LoginVM(modelFactory.getLoginModel());
        }
        return loginVM;
    }

    CreateUserVM getCreateUserVM() {
        if(createUserVM == null) {
            createUserVM = new CreateUserVM(modelFactory.getLoginModel());
        }
        return createUserVM;
    }

    ChangePasswordVM getChangePasswordVM() {
        if(changePasswordVM == null) {
            changePasswordVM = new ChangePasswordVM(modelFactory.getLoginModel());
        }
        return changePasswordVM;
    }
}
