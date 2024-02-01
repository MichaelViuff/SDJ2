public class Bear implements Runnable
{
  @Override public void run()
  {
    try
    {
      Thread.sleep(3000);
      System.out.println("NU ER JEG UDHVILET");
    }
    catch (InterruptedException e)
    {
      System.out.println("WRAH JEG ER SUR!");
    }
  }
}
