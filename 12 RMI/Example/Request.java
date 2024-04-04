import java.io.Serializable;

//Data structure to send requests through Sockets
public class Request implements Serializable
{
    private String type;
    private Object data;


    public Request(String type, Object data)
    {
        this.type = type;
        this.data = data;
    }


    public String getType()
    {
        return type;
    }

    public Object getData()
    {
        return data;
    }
}
