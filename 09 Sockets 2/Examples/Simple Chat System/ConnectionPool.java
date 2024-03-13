package ex9_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool
{
    private final List<ServerConnection> connections;

    public ConnectionPool()
    {
        this.connections = new ArrayList<>();
    }

    public void add(ServerConnection serverConnection)
    {
        connections.add(serverConnection);
    }

    public void broadcast(String message) throws IOException
    {
        for (ServerConnection connection : connections)
        {
            connection.send(message);
        }
    }
}