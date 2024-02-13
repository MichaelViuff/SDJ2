# 04 Exercises: State Pattern


## 4.2 Phone example

Start by downloading the incomplete `Phone`

For this exercise, we will implement everything without using the Observer pattern, just to see how it quickly becomes hard to maintain.

Create the Traffic light example that was shown in the presentation, with the classes [`Car`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/Car.java), [`TrafficLight`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/TrafficLight.java) and [`Main`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/Main.java). 

Run the example and verify that the `Car` reacts to the traffic light changing.

Implement a new class `Taxi`: 
 - It ignores yellow lights, stops when red, and drives when green.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  You can duplicate most of the code from the <code>Car</code> class. You will need to make changes in the <code>setLight()</code> method, so that the <code>Taxi</code> acts like instructed. You will also need to make a few changes in the <code>TrafficLight</code> and <code>Main</code> class.
</p>
<details>
<summary>Display solution...</summary>

```java
public class Taxi
{
    private String previousLight;
    private int id;

    public Taxi(int id)
    {
        this.id = id;
    }

    public void setLight(String currentLight)
    {
        if("GREEN".equals(currentLight))
        {
            System.out.println("Taxi " + id + " drives");
        }
        else if("YELLOW".equals(currentLight))
        {
            if("RED".equals(previousLight))
            {
                System.out.println("Taxi " + id + " turns engine on");
            }
            else
            {
                System.out.println("Taxi " + id + " drives");
            }
        }
        else if("RED".equals(currentLight))
        {
            System.out.println("Taxi " + id + " stops");
        }
        previousLight = currentLight;
    }
}
```
</details>
</details>
</blockquote>


## 4.1 Automatic door example

</details>
</details>
</blockquote>
