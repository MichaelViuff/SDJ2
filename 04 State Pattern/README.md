# 04 Exercises: State Pattern

## 4.1 Phone example

Start by downloading the incomplete Phone example, consisting of the classes [`Phone`](https://github.com/MichaelViuff/SDJ2/blob/main/04%20State%20Pattern/Examples/Phone.java), [`PhoneState`](https://github.com/MichaelViuff/SDJ2/blob/main/04%20State%20Pattern/Examples/PhoneState.java), and [`SoundState`](https://github.com/MichaelViuff/SDJ2/blob/main/04%20State%20Pattern/Examples/SoundState.java)

The Phone example is incomplete, and needs to be finished.
We will introduce two new states, one for when the phone is on silent, and one for when the phone is on vibrate mode.

Draw a State Machine Diagram for the follwoing behaviour:
 - The phone starts in SoundState
 - When the volume button down is pressed, if the volume would reach the value 0, instead change state to the VibrateState
 - When the phone is in VibrateState, pressing the volume up button will change the state to SoundState
 - When the phone is in VibrateState, pressing the volume down button will change the state to SilentState
 - When the phone is in SilentState, pressing the volume up button will change the state to VibrateState

<blockquote>
<details>
<summary>Display solution...</summary>
 ![Phone State Machine](https://github.com/MichaelViuff/SDJ2/blob/main/04%20State%20Pattern/Images/PhoneStateMachine.PNG)
</details>
</blockquote>

Implement the following classes according to the State Machine Diagram you drew:
 - Create the missing State classes, `VibrateState` and `SilentState`, and implement their logic
 - Test that everything works in a main method

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
