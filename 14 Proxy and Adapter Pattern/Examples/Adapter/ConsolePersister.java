package Adapter;

public class ConsolePersister implements Persistence
{
  @Override public void save(Object o)
  {
    System.out.println(o.toString());
  }
}
