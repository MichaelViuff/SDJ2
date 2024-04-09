package Proxy;

public class PersonDatabaseCachedProxy implements PersonCollection
{
  //The Proxy pattern
  private PersonDatabase realSubject;

  //The thing(s) that the Proxy adds
  private Person lastPersonFound;

  public PersonDatabaseCachedProxy(PersonDatabase realSubject)
  {
    this.realSubject = realSubject; //The class that the Proxy will delegate work to
  }

  @Override public Person getPersonWithName(String name)
  {
    //Proxy doing proxy stuff, in this case optimizing by caching
    if(lastPersonFound != null && lastPersonFound.name().equals(name))
    {
      System.out.println("PERSON IN PROXY WAS FOUND");
      return lastPersonFound;
    }

    //When it can't optimize, default to just delegating
    lastPersonFound = realSubject.getPersonWithName(name);
    return lastPersonFound;
  }
}
