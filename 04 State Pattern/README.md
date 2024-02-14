# 04 Exercises: State Pattern

## 4.1 Phone

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

## 4.2 Radiator

This exercise will simulate a radiator with buttons to increase or decrease power level.

Here is the UML class diagram for the exercise:

![Radiator UML class diagram](https://github.com/MichaelViuff/SDJ2/blob/main/04%20State%20Pattern/Images/RadiatorUML.png)

Implement the class diagram as shown.

The different states have the following behaviour:

### `Offstate`
 - The `power` attribute is a `private static final int`, set to 0
 - Turning down doesn't do anything
 - Turning up changes to state 1
 - Use `getPower()` to return the value of `power`

### `Power1State`
 - `power` value is 1
 - `turnUp()` or `turnDown()` switches the state to `Power2State` or `OffState` respectively

### `Power2State`
 - `power` value is 2
 - `turnUp()` or `turnDown()` switches the state to `Power3State` or `Power2State` respectively

### `Power3State`
 - `power` value is 3
 - After 10 seconds, this state automatically changes to `Power2State`

Create a main method to test your radiator. Use a `Thread.sleep(20000)` or infinite while-loop in the main thread to verify that the radiator will automatically go down from `Power3State` to `Power2State` after 10 seconds.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
Let the states create new instances every time they change, instead of storing and reusing states in the <code>Radiator</code>.
</p>
<p>
In the <code>Power3State</code> constructor, you must start a new thread, which will sleep 10 seconds, and then switch down to <code>Power2State</code>. 
 
This can be done with an anonymous inner class inside <code>Power3State</code> to handle the thread. 
You should make the thread a daemon thread, before calling the <code>start()</code> method (using <code>thread.setDaemon(true)</code>). This will make sure the thread is terminated, if the program is shut down.

In the <code>urnDown()</code> method of <code>Power3State</code>, you must interrupt the thread that was started from the constructor to prevent it from automatically switching state later. Otherwise, you will run into the scenario where you turn the power down, and then the sleeping thread will later wake up and change the power again.
</p>

 

  
</p>
<details>
<summary>Display solution...</summary>

```java

```
</details>
</details>
</blockquote>

## 4.3 Sliding door

This exercise is about the sliding door example from the presentation.

Create a UML class diagram for the sliding door, applying the State Pattern. You're going to need the four states: closed, open, closing, opening.

<blockquote>
<details>
<summary>Display solution...</summary>
 <img  src="https://github.com/MichaelViuff/SDJ2/blob/main/04%20State%20Pattern/Images/PhoneStateMachine.PNG" />
</details>
</blockquote>

Implement the classes from your diagram.

In the opening and closing state, you need to simulate that it takes some time to close/open the doors. Use `Thread.sleep()` for a number of seconds.
When the doors are closing, you can click the button to make them open again. This means that you will probably need to interrupt a sleeping thread.

<blockquote>
<details>
<summary>Display solution...</summary>
<p>
 
</p>
</details>
</blockquote>

