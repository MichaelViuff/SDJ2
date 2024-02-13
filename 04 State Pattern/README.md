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
 <img  src="https://github.com/MichaelViuff/SDJ2/blob/main/04%20State%20Pattern/Images/PhoneStateMachine.PNG" />
</details>
</blockquote>

Implement the following classes according to the State Machine Diagram you drew:
 - Create the missing State classes, `VibrateState` and `SilentState`, and implement their logic
 - Test that everything works in a main method

<blockquote>
<details>
<summary>Display solution...</summary>

```java
public class VibrateState implements PhoneState
{
    @Override
    public void onReceiveMessage(String message, Phone phone)
    {
        phone.vibrate();
        System.out.println(message);
    }

    @Override
    public void onReceiveCall(Phone phone)
    {
        phone.vibrate();
    }

    @Override
    public void onVolumeButtonUp(Phone phone)
    {
        phone.changeToSoundState();
    }

    @Override
    public void onVolumeButtonDown(Phone phone)
    {
        phone.changeToSilentState();
    }
}

public class SilentState implements PhoneState
{
    @Override
    public void onReceiveMessage(String message, Phone phone)
    {
        System.out.println(message);
    }

    @Override
    public void onReceiveCall(Phone phone)
    {
        //Do nothing
    }

    @Override
    public void onVolumeButtonUp(Phone phone)
    {
        phone.changeToVibrateState();
    }

    @Override
    public void onVolumeButtonDown(Phone phone)
    {
        //Do nothing
    }
}

public class Main
{
    public static void main(String[] args)
    {
        Phone phone = new Phone();
        phone.receiveMessage("This message was delivered while phone should be in Sound State");
        for (int i = 0; i <= 100; i++)
        {
            phone.volumeDownButton();
        }
        phone.receiveMessage("This message was delivered while phone should be in Silent State");
        phone.volumeUpButton();
        phone.receiveMessage("This message was delivered while phone should be in Vibrate State");
    }
}
```
</details>
</blockquote>


## 4.1 Automatic door example

</details>
</details>
</blockquote>
