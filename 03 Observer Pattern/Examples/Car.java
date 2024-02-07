import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Car implements PropertyChangeListener
{

  private int id;
  private PropertyChangeSubject subject;

  public Car(int id, TrafficLight trafficLight)
  {
    this.id = id;
    this.subject = trafficLight;
  }

  private void reactToChange(PropertyChangeEvent evt)
  {
    String color = evt.getNewValue().toString();
    if(color.equals("GREEN"))
      move();
    if(color.equals("YELLOW"))
      stop();
  }

  private void stop()
  {
    System.out.println("STOPPING" + id);
  }

  public void move()
  {
    System.out.println("VROOM VROOM!" + id);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    reactToChange(evt);
  }
}
