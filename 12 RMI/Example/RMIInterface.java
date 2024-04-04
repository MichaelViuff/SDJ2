import java.rmi.Remote;
import java.rmi.RemoteException;

//Interface for the RMI Server. Not to be confused with the StringConverter interface!
public interface RMIInterface extends Remote //Notice the 'extends Remote' - necessary for RMI
{
    //All methods must have the 'throws RemoteException' clause
    String toUpperCase(String stringToConvert) throws RemoteException;
    String toLowerCase(String stringToConvert) throws RemoteException;
}
