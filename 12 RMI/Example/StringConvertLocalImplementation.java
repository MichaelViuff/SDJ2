//Implements the 'StringConverter' interface, and handles the logic locally.
public class StringConvertLocalImplementation implements StringConverter
{

    //The methods from the 'StringConverter' interface
    @Override
    public String toUpperCase(String stringToConvert)
    {
        return stringToConvert.toUpperCase();
    }

    @Override
    public String toLowerCase(String stringToConvert)
    {
        return stringToConvert.toLowerCase();
    }
}
