import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

public class DataModel implements PropertyChangeSubject, Runnable
{
    private int red, green, blue;

    private Random random = new Random();

    private PropertyChangeSupport propertyChangeSupport;

    public DataModel()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(name, listener);
    }

    @Override
    public void run()
    {
        while (true)
        {
            recalculateData();
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void recalculateData()
    {
        int first = random.nextInt(100) + 1;
        int second = random.nextInt(100) + 1;
        int bottom = Math.min(first, second);
        int top = Math.max(first, second);

        red = bottom;
        green = top - bottom;
        blue = 100 - top;

        System.out.println("red: " + red);
        System.out.println("green: " + green);
        System.out.println("blue: " + blue);
        System.out.println("Sum: " + (red + green + blue));

        propertyChangeSupport.firePropertyChange("DataChange", null, new int[]{red, green, blue});
    }
}
