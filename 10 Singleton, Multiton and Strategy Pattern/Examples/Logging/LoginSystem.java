public class LoginSystem {

    /*
    This class only contains dummy methods to simulate a library system.
    */

    private Log logger;

    public LoginSystem(Log logger) {
        this.logger = logger;
    }

    public LoginSystem() {
        this.logger = new Log();
    }

    //dummy method to simulate logging in
    public void login(String username, String password)
    {
        logger.add("user logged in with username: " + username + " and password: " + password);
    }

}
