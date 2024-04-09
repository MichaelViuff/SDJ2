package Proxy;

public class Main
{

  public static void main(String[] args)
  {
    Person p1 = new Person("bob");

    PersonDatabase personDatabase = new PersonDatabase();
    personDatabase.add(p1);

    PersonDatabaseCachedProxy personDatabaseCachedProxy = new PersonDatabaseCachedProxy(personDatabase);

    PersonCollection personCollection = personDatabase;
    //personCollection = personDatabaseCachedProxy; //Uncomment to see the effect of using the Proxy
    System.out.println(personCollection.getPersonWithName("bob"));
    System.out.println(personCollection.getPersonWithName("bob"));
  }
}
