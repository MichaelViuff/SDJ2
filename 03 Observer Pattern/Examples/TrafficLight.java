import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class TrafficLight implements PropertyChangeSubject
{

  private String lightColor;
  private PropertyChangeSupport propertyChangeSupport;

  public TrafficLight()
  {
    propertyChangeSupport = new PropertyChangeSupport(this);
  }

  public void setColor(String color)
  {
    String oldColor = lightColor;
    lightColor = color;
    propertyChangeSupport.firePropertyChange("Lightchanged", oldColor, color);
  }

  @Override public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  @Override public void addPropertyChangeListener(String name, PropertyChangeListener listener)
  {
    propertyChangeSupport.addPropertyChangeListener(name, listener);
  }

  @Override public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(String name, PropertyChangeListener listener)
  {
    propertyChangeSupport.removePropertyChangeListener(name, listener);
  }
}
