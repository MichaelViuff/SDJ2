package Adapter;

public record Person(String name)
{

  @Override
  public String toString()
  {
    return "Person{" + "name='" + name + '\'' + '}';
  }
}
