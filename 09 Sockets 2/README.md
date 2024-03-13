# 09 Exercises: Sockets Part 2

## 9.1 Excluding from broadcast

Start by implementing the Simple Chat System from the presentation (or download it [here](/09%20Sockets%202/Examples/Simple%20Chat%20System/)).

Run it and ensure everything is working as expected.

Notice that whenever a Client sends a message, they receive that same message from the Server.

This is not necessarily the best behaviour. We want to exclude the sender from the broadcast, so only other Clients receive that message.

To do so, we need to keep track of who sends the message, and send the message to everyone else.

One way to do this is to have the `broadcast` method take an additional argument, the `ServerConnection` to ignore, when calling it from the `ServerConnection` main loop. Remember to update the call from `ServerConnection` as well, sending itself as an argument.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
//In ConnectionPool
public void broadcast(String message, ServerConnection connectionToIgnore) throws IOException
{
    for (ServerConnection connection : connections)
    {
        if(connection != connectionToIgnore)
        {
            connection.send(message);
        }
    }
}
```

```java
//In ServerConnection
@Override
public void run()
{
    while(true)
    {
        try
        {
            String message = (String) inFromClient.readObject();
            System.out.println("Received: " + message);
            connectionPool.broadcast(message, this);
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
```
</details>
</blockquote>

## 9.2 A man needs a name

It would be great if we could identify the sender of each message.

To do so, our Clients need to supply a name to the Server.

A naive approach would be to do so before creating the `ServerConnection` and storing it there. But this would halt the server, while it waits for a message (with the name) from the Client.

Instead, we will update our protocol, so that the first thing a Client must do, once connected, is to send a name.

The `ServerConnection` must handle this before beginning the main loop. Remember to update the call to `connectionPool.broadcast(...)` as well. Your Client could also be updated, so it first asks for a name.

```java
@Override
public void run()
{
    //Get a name from the client and store it.
    /*Your code goes here*/
    while(true)
    {
        try
        {
            String message = (String) inFromClient.readObject();
            System.out.println("Received: " + message);
            connectionPool.broadcast(message, this); //Remember to update this
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
```

<blockquote>
<details>
<summary>Display solution...</summary>

```java
//ServerConnection
 @Override
public void run()
{
    //Get a name from the client and store it.
    /*Your code goes here*/
    String name;
    try
    {
        name = (String) inFromClient.readObject();
        System.out.println("Name received: " + name);
    }
    catch (IOException | ClassNotFoundException e)
    {
        throw new RuntimeException(e);
    }

    while(true)
    {
        try
        {
            String message = (String) inFromClient.readObject();
            System.out.println("Received: " + message);
            connectionPool.broadcast(message, this, name);
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
```
```java
//ConnectionPool
public void broadcast(String message, ServerConnection connectionToIgnore, String name) throws IOException
{
    for (ServerConnection connection : connections)
    {
        if(connection != connectionToIgnore)
        {
            connection.send("[" + name + "] - " + message);
        }
    }
}
```
```java
//Client
public static void main(String[] args)
{
    Scanner scanner = new Scanner(System.in);

    try
    {
        Socket socket = new Socket("localhost", 2910);
        ClientConnection clientConnection = new ClientConnection(socket);
        new Thread(clientConnection).start();

        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        clientConnection.send(name);

        while(true)
        {
            System.out.println("Enter a message: ");
            String message = scanner.nextLine();
            clientConnection.send(message);
        }
    }
    catch (IOException e)
    {
        throw new RuntimeException(e);
    }
}
```
</details>
</blockquote>

## 9.3 Sending Objects

So far, our communication has only consisted of `String` Objects.

We can send anything that can be [serialized](https://www.baeldung.com/cs/serialization-deserialization) through our Sockets.

For now, let us stick to just sending Java Objects.

In the previous exercise, when a Client connected, they had to supply their name as the first message to the Server.

We will change this behaviour, so that whenever a Client sends a message, the message contains their name as well as the actual message.

This could be done with some clever appending of `String` Objects, and parsing them on the Server. We won't do that though; instead we will create our own Object type to send - `Message`.

```java
import java.io.Serializable;

public class Message implements Serializable
{
    private String name;
    private String content;

    public Message(String name, String content)
    {
        this.name = name;
        this.content = content;
    }

    public String getName()
    {
        return name;
    }

    public String getContent()
    {
        return content;
    }
}
```

Update your classes, so they use the new `Message` class instead of just sending `String` Objects

<blockquote>
<details>
<summary>Display solution...</summary>

```java
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
```

```java
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
                Message message = (Message) inFromClient.readObject();
                System.out.println("Received message from: " + message.getName());
                System.out.println("Message content: " + message.getContent());
                connectionPool.broadcast(message, this);
            }
            catch (IOException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void send(Message message) throws IOException
    {
        outToClient.writeObject(message);
    }
}
```

```java
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

    public void broadcast(Message message, ServerConnection connectionToIgnore) throws IOException
    {
        for (ServerConnection connection : connections)
        {
            if(connection != connectionToIgnore)
            {
                connection.send(message);
            }
        }
    }
}
```

```java
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        try
        {
            System.out.println("Enter your name: ");
            String name = scanner.nextLine();

            Socket socket = new Socket("localhost", 2910);
            ClientConnection clientConnection = new ClientConnection(socket);
            new Thread(clientConnection).start();

            while(true)
            {
                System.out.println("Enter a message: ");
                String messageContent = scanner.nextLine();
                Message message = new Message(name, messageContent);
                clientConnection.send(message);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
```

```java
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable
{

    private final ObjectOutputStream outToServer;
    private final ObjectInputStream inFromServer;

    public ClientConnection(Socket socket) throws IOException
    {
        outToServer = new ObjectOutputStream(socket.getOutputStream());
        inFromServer = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Message message = (Message) inFromServer.readObject();
                System.out.println(message.getName() + ": " + message.getContent());
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) throws IOException
    {
        outToServer.writeObject(message);
    }
}

```

```java
import java.io.Serializable;

public class Message implements Serializable
{
    private String name;
    private String content;

    public Message(String name, String content)
    {
        this.name = name;
        this.content = content;
    }

    public String getName()
    {
        return name;
    }

    public String getContent()
    {
        return content;
    }
}

```

</details>
</blockquote>

## 9.4 Adding a GUI

The console is not the best at representing our Simple Chat System.

We need to add a GUI, so our system is easier to work with.

This can be done in several different ways. One approach would be to use the MVVM Design Pattern introduced earlier.

<blockquote>
<details>
<summary>Display solution...</summary>

No solution for now. Keep up the good work ;)

</details>
</blockquote>
<!--

## 9.4 Authentication

Security in our Simple Chat System is non-existent. We should probably look into that...

Right now, a Client can easily send messages disguised as another user.

To prevent this, we will add authentication to our 

-->

