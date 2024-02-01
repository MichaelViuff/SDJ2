public class Letters implements Runnable
{

  private Thread threadToJoin;

  public Letters(Thread threadToJoin)
  {
    this.threadToJoin = threadToJoin;
  }

  @Override public void run()
  {

    for (int i = 2000; i < 3000; i++)
    {
      System.out.println(i);
    }
    try
    {
      threadToJoin.join();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    for (int i = 3000; i < 4000; i++)
    {
      System.out.println(i);
    }
  }
}
