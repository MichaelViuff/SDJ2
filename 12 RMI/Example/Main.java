public class Main
{
    public static void main(String[] args)
    {
        //StringConverter interface hides the concrete implementation. Change the right hand side of = to swap to another implementation.
        StringConverter stringConverter = new StringConverterSocketsImplementation(); //When using a networked implementation, make sure you run the corresponding Server first.

        //Calling the methods from StringConverter interface to test functionality.
        String result1 = stringConverter.toUpperCase("stringToUpper");
        System.out.println(result1); //Should display 'STRINGTOUPPER'

        String result2 = stringConverter.toLowerCase("stringToLower");
        System.out.println(result2); //Should display 'stringtolower'
    }
}
