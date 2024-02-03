public class Start
{

  public static void main(String[] args)
  {
    Thread numbersThread = new Thread(new Numbers());
    Thread lettersThread = new Thread(new Letters());

    numbersThread.start();
    lettersThread.start();
  }
}
