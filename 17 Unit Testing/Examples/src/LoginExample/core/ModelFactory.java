package LoginExample.core;

import LoginExample.model.LoginModel;
import LoginExample.modelimpls.LoginModelInMemory;

public class ModelFactory {

    // class for creating and providing Model implementations. I just have one model in this case

    private LoginModelInMemory loginModel;

    public LoginModel getLoginModel() {
        // using lazy instantiation, meaning I only create the LoginModel, when it is asked for.
        // it is stored in a field variable, so the same instance can be reused again, and by multiple view models
        // This ensure all view models use the same instance of the model
        if(loginModel == null) {
            loginModel = new LoginModelInMemory();
        }
        return loginModel;
    }
}
