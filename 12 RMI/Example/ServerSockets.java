import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//Server class for Java Sockets implementation. Remember to run this before 'Main'
public class ServerSockets
{
    public static void main(String[] args) //Would be cleaner to have a separate 'main'-method somewhere else. Only here to keep things simple.
    {
        //Uses Java Sockets for network communication.
        ServerSocket welcomeSocket = null; //Arbitrary port number. Feel free to change.
        try
        {
            welcomeSocket = new ServerSocket(2910);
            System.out.println("Server started.");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        while(true)
        {
            try
            {
                //Does not currently handle multiple clients
                Socket socket = welcomeSocket.accept(); //Waiting for connection
                System.out.println("Client connected...");

                //Creating streams
                ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
                outToClient.flush();
                ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());

                //Continously listens for requests.
                while (true)
                {
                    Request request = (Request) inFromClient.readObject(); //From protocol, we know that clients only ever send 'Request' Objects, so we cast it.

                    //Extract type to decide what to do
                    String requestType = request.getType();

                    //Data is only extracted if expected (as per protocol)
                    String requestData;
                    String result;

                    switch (requestType)
                    {
                        case "UPPER":
                            requestData = (String) request.getData();
                            result = toUpperCase(requestData);
                            break;
                        case "LOWER":
                            requestData = (String) request.getData();
                            result = toLowerCase(requestData);
                            break;
                        case "GET_LOG":
                            //TODO: Implement
                            //Here we wouldn't try to extract data as there is none
                            System.out.println("not yet implemented");
                        default:
                            //Poor error handling...
                            System.out.println("Invalid request type");
                            result = "SOMETHING WENT WRONG";
                            break;
                    }

                    //Response is sent as a String, as per protocol
                    outToClient.writeObject(result);
                }
            }
            catch (IOException e)
            {
                //Poor error handling...
                System.out.println("Client disconnected");
            }
            catch (ClassNotFoundException e)
            {
                //Poor error handling...
                System.out.println("Something went wrong");
            }
        }
    }

    //Helper methods to handle conversion
    public static String toUpperCase(String toUpper)
    {
        return toUpper.toUpperCase();
    }

    public static String toLowerCase(String toLower)
    {
        return toLower.toLowerCase();
    }
}