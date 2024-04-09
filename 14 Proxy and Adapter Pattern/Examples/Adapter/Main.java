package Adapter;

public class Main
{
  public static void main(String[] args)
  {
    Person person = new Person("bob");

    Persistence persistence;

    persistence = new Adapter();
    persistence.save(person);
  }
}