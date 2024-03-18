public class Main {


    public static void main(String[] args) {

       // Log logger = new Log();

        LoginSystem loginSystem = new LoginSystem();
        CDLibrary cdLibrary = new CDLibrary();

        //Simulates events in the system, do not change
        String username = "Michael";
        String password = "12345678";
        loginSystem.login(username, password);
        cdLibrary.onPressedRemoveCD();
        cdLibrary.inputTitleToBeRemoved();
    }

}
