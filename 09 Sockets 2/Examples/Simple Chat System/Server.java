package ex9_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args)
    {
        System.out.println("Starting server...");
        try
        {
            ServerSocket welcomeSocket = new ServerSocket(2910);
            ConnectionPool connectionPool = new ConnectionPool();

            while (true)
            {
                Socket socket = welcomeSocket.accept();
                ServerConnection serverConnection = new ServerConnection(socket, connectionPool);
                connectionPool.add(serverConnection);
                System.out.println("Client connected");
                new Thread(serverConnection).start();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}