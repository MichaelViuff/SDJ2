public class User
{
    private String username;
    private String password;
    private int age;

    public User(String username, String password, int age)
    {

        this.username = username;
        this.password = password;
        this.age = age;
    }

    public String toString()
    {
        return username + ", " + password + ", " + age;
    }
}