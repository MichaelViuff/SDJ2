//Simple interface to allow us to easily swap implementation
public interface StringConverter
{
    String toUpperCase(String stringToConvert);
    String toLowerCase(String stringToConvert);
}
