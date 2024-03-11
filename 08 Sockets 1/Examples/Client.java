import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            Socket socket = new Socket("localhost", 2910);

            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            outToServer.writeObject("Hello");

            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
            String o = (String) inFromServer.readObject();

            System.out.println(o);
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
