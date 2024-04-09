package Proxy;

import java.util.ArrayList;

public class PersonDatabase implements PersonCollection
{

  private ArrayList<Person> persons;

  public PersonDatabase()
  {
    persons = new ArrayList<>();
  }

  public void add(Person person)
  {
    persons.add(person);
  }

  public Person getPersonWithName(String name)
  {
    try
    {
      Thread.sleep(5000);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    for (Person person : persons)
    {
      if(person.name().equals(name))
      {
        System.out.println("PERSON IN REALSUBJECT WAS FOUND");
        return person;
      }
    }
    return null;
  }

}
