import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UppercaseModel implements PropertyChangeSubject
{
    private PropertyChangeSupport support;

    public UppercaseModel()
    {
        support = new PropertyChangeSupport(this);
    }

    public void uppercase(String text)
    {
        String result = text.toUpperCase();
        support.firePropertyChange("Uppercase", null, result);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(name, listener);
    }
}