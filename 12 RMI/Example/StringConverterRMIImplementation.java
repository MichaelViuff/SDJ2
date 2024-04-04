import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//Implements the 'StringConverter' interface, but delegates the logic to the RMI Server.
public class StringConverterRMIImplementation implements StringConverter
{
    private RMIInterface server; //Stores a global instance of the Server, initialized in the constructor.

    public StringConverterRMIImplementation()
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); //Locates the Registry on the network. In this case on the same machine and at the port defined in the 'ServerRMI' class
            server = (RMIInterface) registry.lookup("StringConverter"); //Finds the Server stub. According to protocol, there is a 'RMIInterface' instance at the name "StringConverter"
        }
        catch (RemoteException | NotBoundException e)
        {
            //Poor error handling...
            throw new RuntimeException(e);
        }

    }

    //The methods from the 'StringConverter' interface
    @Override
    public String toUpperCase(String stringToConvert)
    {
        try
        {
            return server.toUpperCase(stringToConvert); //Delegates the logic of converting strings to the Server instance
        }
        catch (RemoteException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toLowerCase(String stringToConvert)
    {
        try
        {
            return server.toLowerCase(stringToConvert); //Delegates the logic of converting strings to the Server instance
        }
        catch (RemoteException e)
        {
            throw new RuntimeException(e);
        }
    }
}
