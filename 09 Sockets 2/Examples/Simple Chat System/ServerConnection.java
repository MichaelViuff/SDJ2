package ex9_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection implements Runnable
{
    private final ObjectInputStream inFromClient;
    private final ObjectOutputStream outToClient;
    private final ConnectionPool connectionPool;

    public ServerConnection(Socket connectionSocket, ConnectionPool connectionPool) throws IOException
    {
        inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
        outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
        this.connectionPool = connectionPool;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                String message = (String) inFromClient.readObject();
                System.out.println("Received: " + message);
                connectionPool.broadcast(message);
            }
            catch (IOException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void send(String message) throws IOException
    {
        outToClient.writeObject(message);
    }
}