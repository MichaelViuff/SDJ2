package Adapter;

import parser.ParserException;
import parser.XmlJsonParser;

public class Adapter implements Persistence
{

  private XmlJsonParser adaptee; //We pretend this is an "incompatible service"

  public Adapter()
  {
    adaptee = new XmlJsonParser(); //The thing that the Adapter will delegate work to
  }

  //Implementing the existing interface
  @Override public void save(Object o)
  {
    try
    {
      adaptee.toXml(o, "testFile"); //Delegating work to the "incompatible" service
    }
    catch (ParserException e)
    {
      e.printStackTrace();
    }
  }
}
