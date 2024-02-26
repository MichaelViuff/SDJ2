import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;

public class UppercaseViewModel
{
    private UppercaseModel uppercaseModel;
    private StringProperty inputTextProperty;
    private StringProperty outputTextProperty;

    public UppercaseViewModel(UppercaseModel uppercaseModel)
    {
        this.uppercaseModel = uppercaseModel;
        inputTextProperty = new SimpleStringProperty();
        outputTextProperty = new SimpleStringProperty();
        uppercaseModel.addPropertyChangeListener("Uppercase", this::onUpperCaseResult);
    }

    private void onUpperCaseResult(PropertyChangeEvent event)
    {
        String result = (String) event.getNewValue();
        outputTextProperty.set(result);
    }

    public StringProperty inputTextProperty()
    {
        return inputTextProperty;
    }

    public StringProperty outputTextProperty()
    {
        return outputTextProperty;
    }

    public void convert()
    {
        uppercaseModel.uppercase(inputTextProperty.get());
    }
}
