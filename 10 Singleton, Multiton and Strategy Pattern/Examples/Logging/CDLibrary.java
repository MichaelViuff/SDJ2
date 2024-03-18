public class CDLibrary {

    /*
    This class only contains dummy methods to simulate a library system.
     */

    private Log logger;

    public CDLibrary(Log logger) {
        this.logger = logger;
    }

    public CDLibrary() {
        this.logger = new Log();
    }

    public void onPressedRemoveCD()
    {
        logger.add("removing a cd has been pressed");
    }

    public void inputTitleToBeRemoved()
    {
        logger.add("title for cd to remove has been entered");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        removeCD();
    }

    private void removeCD() {
        logger.add("cd found and has been removed in the model");
    }

}
