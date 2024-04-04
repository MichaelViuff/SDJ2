import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//Implements the 'StringConverter' interface, but delegates the logic to the Java Sockets Server.
public class StringConverterSocketsImplementation implements StringConverter
{

    private ObjectOutputStream outToServer; //Stream used for communication with Server
    private ObjectInputStream InFromServer; //Stream used for communication with Server

    public StringConverterSocketsImplementation()
    {
        try
        {
            Socket serverSocket = new Socket("localhost", 2910); //Connects to the specified address. In this case, on the same machine and at the port specified in the 'ServerSockets' class

            //Initializes streams
            outToServer = new ObjectOutputStream(serverSocket.getOutputStream());
            outToServer.flush();
            InFromServer = new ObjectInputStream(serverSocket.getInputStream());
        }
        catch (IOException e)
        {
            //Poor error handling...
            throw new RuntimeException(e);
        }

    }

    //The methods from the 'StringConverter' interface
    @Override
    public String toUpperCase(String stringToConvert)
    {
        //Delegates the logic of converting strings to the Server instance
        try
        {
            Request request = new Request("UPPER", stringToConvert); //Creates a 'Request' Object...
            outToServer.writeObject(request); //...and sends it to Server
            String response = (String) InFromServer.readObject(); //Receives a response from Server. As per protocol, the response is a 'String'
            return response;
        }
        catch (IOException | ClassNotFoundException e)
        {
            //Poor error handling...
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toLowerCase(String stringToConvert)
    {
        //Delegates the logic of converting strings to the Server instance
        try
        {
            Request request = new Request("LOWER", stringToConvert); //Creates a 'Request' Object...
            outToServer.writeObject(request); //...and sends it to Server
            String response = (String) InFromServer.readObject(); //Receives a response from Server. As per protocol, the response is a 'String'
            return response;
        }
        catch (IOException | ClassNotFoundException e)
        {
            //Poor error handling...
            throw new RuntimeException(e);
        }
    }
}
