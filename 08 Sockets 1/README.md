
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

## 8.2 Multiple Clients

Modify your run configuration for your Client, so you can run multiple instances.

Start the Server, and then start at least two Clients. 

Send a message to the Server from either Client, and then from another Client.

This will probably work - the Server processes the request almost instantaneously, so even though the Server can't handle concurrent Clients, it can serve them in quick succession.

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

[Protocol](/08%20Sockets%201/Images/Echo%20Server%20Protocol.png)


In order to do so, we need to create the `ObjectInputStream` and `ObjectOutputStream` early, and reuse those.

The Server currently waits with the creation of the streams until necessary. Change this, so both streams are created immediately after the Client has connected.


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