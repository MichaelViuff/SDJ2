import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

//Server class for RMI implementation. Remember to run this before 'Main'
public class ServerRMI implements RMIInterface //Implements the RMIInterface interface - this is the type that the Client will work with.
{

    public static void main(String[] args) //Would be cleaner to have a separate 'main'-method somewhere else. Only here to keep things simple.
    {
        try
        {
            //These are the steps necessary to create an RMI Server and put it in the Registry.
            ServerRMI server = new ServerRMI(); //Can't use 'this' in a static context, so we create an instance (side-effect of having 'main'-method in this class...
            Registry registry = LocateRegistry.createRegistry(1099); //Arbitrary port number, feel free to change.
            registry.bind("StringConverter", server); //Putting the server in the Registry. The only protocol necessary for RMI is to know the interface-type behind the name. In this case "StringConverter" points to a 'RMIInterface' type.
        }
        catch (RemoteException | AlreadyBoundException e)
        {
            //Should be replaced with proper error handling...
            throw new RuntimeException(e);
        }
    }

    public ServerRMI()
    {
        try
        {
            //Black magic that makes RMI work (for details: https://docs.oracle.com/javase/1.5.0/docs/guide/rmi/hello/hello-world.html)
            UnicastRemoteObject.exportObject(this, 0); // 'port: 0' actually uses a random, open port for the underlying communication (not to be confused with the port used for the Registry).
        }
        catch (RemoteException e)
        {
            //Should be replaced with proper error handling...
            throw new RuntimeException(e);
        }
    }

    //The methods from the 'RMIInterface' interface. These are the methods that the client can use.
    @Override
    public String toUpperCase(String stringToConvert) throws RemoteException
    {
        return stringToConvert.toUpperCase();
    }

    @Override
    public String toLowerCase(String stringToConvert) throws RemoteException
    {
        return stringToConvert.toLowerCase();
    }
}
