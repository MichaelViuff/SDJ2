public class PokingMan implements Runnable
{

  private Thread bearToPoke;
  private int timeToSleep;

  public PokingMan(Thread bearToPoke, int timeToSleep)
  {
    this.bearToPoke = bearToPoke;
    this.timeToSleep = timeToSleep;
  }

  @Override public void run()
  {
    try
    {
      Thread.sleep(timeToSleep);
      bearToPoke.interrupt();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
