import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.beans.PropertyChangeEvent;

public class BarChartViewModel
{
    private DataModel model;
    private Property redProperty;
    private Property greenProperty;
    private Property blueProperty;

    public BarChartViewModel(DataModel model)
    {
        this.model = model;
        model.addPropertyChangeListener("DataChange", this::onColourValueChange);
        redProperty = new SimpleIntegerProperty();
        greenProperty = new SimpleIntegerProperty();
        blueProperty = new SimpleIntegerProperty();
    }

    public Property redProperty()
    {
        return redProperty;
    }

    public Property greenProperty()
    {
        return greenProperty;
    }

    public Property blueProperty()
    {
        return blueProperty;
    }

    private void onColourValueChange(PropertyChangeEvent event)
    {
        int[] newValues = (int[]) event.getNewValue();
        redProperty.setValue(newValues[0]);
        greenProperty.setValue(newValues[1]);
        blueProperty.setValue(newValues[2]);
    }
}
