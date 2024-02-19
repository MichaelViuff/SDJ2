package demonstration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel
{
    private Model model;
    private StringProperty inputTextProperty;
    private StringProperty outputTextProperty;

    public ViewModel(Model model)
    {
        this.model = model;
        inputTextProperty = new SimpleStringProperty();
        outputTextProperty = new SimpleStringProperty();
    }

    public String uppercase(String text)
    {
        return model.uppercase(text);
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
        String uppercase = model.uppercase(inputTextProperty.get());
        outputTextProperty.set(uppercase);
    }
}
