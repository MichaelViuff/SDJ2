import static org.junit.jupiter.api.Assertions.*;

import LoginExample.model.LoginModel;
import LoginExample.modelimpls.LoginModelInMemory;
import LoginExample.viewmodels.CreateUserVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTests
{

    private CreateUserVM createUserVM;

    @BeforeEach
    public void setUp()
    {
        LoginModel loginModel = new LoginModelInMemory();
        createUserVM = new CreateUserVM(loginModel);
    }

    @Test
    public void testCreateNewUserUserNameCannotBeEmpty()
    {
        createUserVM.attemptCreateUser();
        assertEquals("Username cannot be empty", createUserVM.createUserResultProperty().getValue());
    }

    @Test
    public void testCreateNewUserWithValidUserNameAndPassword()
    {
        createUserVM.usernameProperty().setValue("user");
        createUserVM.passwordProperty().setValue("12341234Aa");
        createUserVM.passwordAgainProperty().setValue("12341234Aa");
        createUserVM.attemptCreateUser();
        assertEquals("OK", createUserVM.createUserResultProperty().getValue());
    }

}
