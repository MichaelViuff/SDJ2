
# 08 Exercises: Sockets Part 1 

## 8.1 Uppercase Echo Server

[Download](/08%20Sockets%201/Examples/Server.java) and run the Echo Server example from the presentation.

[Download](/08%20Sockets%201/Examples/Client.java) the Client and run it. 

Look at the output from both the Server and the Client.

Your Client terminates as soon as it has received the response from the Server.

Modify your Client, so that it takes an input from the user, and sends this input to the Server (instead of always sending `"Hello"`).

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        try
        {
            Socket socket = new Socket("localhost", 2910);

            System.out.println("Enter a string to send to the server");
            String stringToSend = scanner.nextLine();

            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            outToServer.writeObject(stringToSend);

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
```
</details>
</blockquote>

## 8.2 "Multiple" Clients

Modify your run configuration for your Client, so you can run multiple instances.

Start the Server, and then start at least two Clients. 

Send a message to the Server from either Client, and then from another Client.

This will probably work - the Server processes the request almost instantaneously, so even though the Server can't currently handle concurrent Clients, it can serve them in quick succession.

Modify your Client, so it doesn't terminate after sending a single message, but instead keeps asking for and sending messages until the user inputs `"EXIT"`.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        try
        {
            Socket socket = null;
            while (true)
            {
                System.out.println("Enter a string to send to the server");
                String stringToSend = scanner.nextLine();
                if (stringToSend.equals("EXIT"))
                {
                    break;
                }
                socket = new Socket("localhost", 2910);

                ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
                outToServer.writeObject(stringToSend);

                ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
                String o = (String) inFromServer.readObject();

                System.out.println(o);
            }
            socket.close();
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

## 8.3 Keeping a connection

In the previous exercise, the Server was able to handle multiple Clients - not at the same time, but one after another.

However, the solution was not pretty. Each Client made a new connetion each time it wanted to send a message. This is highly inefficient.

Instead, we want to establish a connection through the `Socket` and keep using this connection.

We need to modify both the Server and Client, so they can do this. To ensure the correct flow of actions on both the Server and Client, we must agree on a specific protocol.

![Protocol](/08%20Sockets%201/Images/Echo%20Server%20Protocol.png)

In order to do so, we need to create the `ObjectInputStream` and `ObjectOutputStream` early, and reuse those.

The Server currently waits with the creation of the streams until necessary. Change this, so both streams are created immediately after the Client has connected.

Do the same in the Client, so that immediately after the connection has been accepted, both streams are created.

>[!CAUTION]
>When creating `ObjectInputStream` and `ObjectOutputStream` the order actually matters.

As seen in the [Java API](https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html#ObjectInputStream%28java.io.InputStream%29), the constructor for `ObjectInputStream` "...will block until the corresponding ObjectOutputStream has written and flushed the header".

Here's the correct order for initializing these streams:

**Server-Side**

First, create `ObjectOutputStream`:

```java
ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
outToClient.flush(); // Important to flush to send the serialization stream header
```

Then, create `ObjectInputStream`:

```java
ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());
```

**Client-Side**

Similarly, create `ObjectOutputStream` first:

```java
ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
outToServer.flush(); // Flush to send the serialization stream header
```
Then, create `ObjectInputStream`:

```java
ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
```

Create an inner loop in the Server, that continues to receive the message from the Client, and returns the message in uppercase back to the Client. This loop should stop when the Client sends the message `"STOP"`.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            while (true)
            {
                Socket socket = welcomeSocket.accept();
                System.out.println("Client connected");

                ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
                outToClient.flush(); // Important to flush to send the serialization stream header
                ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());

                String o = (String) inFromClient.readObject();

                while(!"STOP".equals(o))
                {
                    System.out.println("Received: " + o);

                    String answer = o.toUpperCase();

                    outToClient.writeObject(answer);

                    o = (String) inFromClient.readObject();
                }
            }
        }
        catch (IOException | ClassNotFoundException e)
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
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        try
        {
            Socket socket = new Socket("localhost", 2910);

            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            outToServer.flush(); // Flush to send the serialization stream header
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());


            while (true)
            {
                System.out.println("Enter a string to send to the server");
                String stringToSend = scanner.nextLine();
                if (stringToSend.equals("EXIT"))
                {
                    break;
                }

                outToServer.writeObject(stringToSend);

                String o = (String) inFromServer.readObject();

                System.out.println(o);
            }
            socket.close();
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

## 8.4 Calling "methods" on the Server

Right now, our Server always does the same thing - returns the message in uppercase.

We want to modify our Server, so that the Client can request to either have a message converted to uppercase or lowercase.

First, design the protocol for this. It could look like this.

![Protocol](/08%20Sockets%201/Images/Echo%20Upper%20or%20Lower%20Server%20Protocol.png)

Then, modify both your Server and Client so they follow the protocol.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            while (true)
            {
                Socket socket = welcomeSocket.accept();
                System.out.println("Client connected");

                ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
                outToClient.flush(); // Important to flush to send the serialization stream header
                ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());

                while(true)
                {
                    outToClient.writeObject("Select upper- or lowercase. Type EXIT to end connection.");

                    String o = (String) inFromClient.readObject();
                    if ("EXIT".equals(o))
                    {
                        socket.close();
                        break;
                    }

                    System.out.println("Received: " + o);

                    if("uppercase".equals(o))
                    {
                        String message = (String) inFromClient.readObject();
                        String answer = message.toUpperCase();
                        outToClient.writeObject(answer);
                    }
                    else if ("lowercase".equals(o))
                    {
                        String message = (String) inFromClient.readObject();
                        String answer = message.toLowerCase();
                        outToClient.writeObject(answer);
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException e)
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
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        try
        {
            Socket socket = new Socket("localhost", 2910);

            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            outToServer.flush(); // Flush to send the serialization stream header
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            while (true)
            {
                String o = (String) inFromServer.readObject();
                System.out.println(o);

                String choice = scanner.nextLine();
                if (choice.equals("EXIT"))
                {
                    outToServer.writeObject(choice);
                    break;
                }

                outToServer.writeObject(choice);

                System.out.println("Enter a string to send to the server");
                String stringToSend = scanner.nextLine();

                outToServer.writeObject(stringToSend);
                String response = (String) inFromServer.readObject();
                System.out.println(response);
            }
            socket.close();
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

## 8.5 Protocol Design

In this exercise we will create a Server, that can perform basic mathematics.

The Server must be able to receive two numbers, and perform an operation on them. The operations are limited to addition, subtraction and multiplication.

The Client must be able to send the two numbers, as well as specify which operation to perform.

### Protocol

Start by designing the protocol. There are many ways this can be done.

Consider how to send the arguments. There are many ways to do this.

-	First send the first operand (number), then send the operator (+, -, *), then send the second operand (number)
-	First send the operator, then send both operands separated with `,`
-	Send all three, like "3 + 5" splitting after `[Space]` using `String.split(" ")`
-	Send all three, like "+,3,5" splitting after `,`
-	Etc...

Write down your protocol, you will need it for later.

### Client/Server

Implement the Server and Client according to your protocol.

Run your program and ensure everything is working as expected.

### Agreeing on Protocols

Team up with a classmate for this next part. 

Swap protocols with your classmate - you now have your own Client and Server, implemented according to your own protocol, and you have a new protocol that your classmate has a working system for.

Implement the Client according to their protocol. You might have to implement your own version of their Server while you are testing everyhing.

When you think you have a working Client, ask for the source code for their Server, and run their Server and your Client. If everything has been implemented according to the protocol (and the protocol has been correctly documented!) everything should work. Ensure that it does.

Now try to design the Server according to their protocol, if you didn't already.

When done, ask for the source code for their Client, and run your Server and their Client. Ensure everything is working.

### Running on separate machines

So far everything has been tested locally, i.e. on your own machine.

It would be much more fun, if you could run a Server instance on your computer, and have your classmate connect to it from their computer.

Start by finding the IP address of the machine you will run the Server on.

On Windows, this can be done in the Command Prompt by typing `ipconfig` and looking for an entry for an IPv4 address.

On the machine that will run the Client, use this IP instead of `"localhost"` when creating the `Socket`

```java
Socket socket = new Socket([IP ADDRESS HERE], 2910);
```

Start the Server on one machine, and then start the Client on the other machine. If there are no firewalls in place, everything should work.
If something isn't working, don't worry. Just go back to running everything locally. Or create your own LAN using your smart phone to create a WiFi-hotspot, and have both machines connect to this. Or try another WiFi connection.
