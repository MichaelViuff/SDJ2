# 16 Exercises: Readers-Writers Problem

## 16.1 Public restroom cleaning

In this exercise we will model a public restroom.

There are the the people using the restroom (*readers*) and the cleaning person (*writer*).

The methods for the restroom follows this interface:

<img src="Images/Exercise_01.png" width="50%">

```java
public interface PublicRestRoom
{
    void stepIntoStall();
    void leaveStall();
    void startCleaning();
    void endCleaning();
}
```

Implement a solution, with 1 cleaner and multiple restroom users (feel free to enforce a maximum capacity as well).

Give the cleaner priority, so when they are about to clean or are in the middle of cleaning, noone can enter the restroom, and when the restroom clears out, they start cleaning.

This is an example of the **Writer-Preference** solution strategy.

<blockquote>
  <details>
    <summary>Display solution...</summary>
    
```java
public class PublicRestRoomImplementation implements PublicRestRoom
{

    private int restRoomUsers = 0;
    private boolean cleanerIsWaiting = false;
    private boolean isCleaning = false;

    public synchronized void stepIntoStall()
    {
        // Wait while it's being cleaned or the cleaner is waiting
        while (isCleaning || cleanerIsWaiting)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        restRoomUsers++;
    }

    @Override
    public synchronized void leaveStall()
    {
        restRoomUsers--;
        if (restRoomUsers == 0 && cleanerIsWaiting)
        {
            notifyAll(); // Notify the cleaner that they can start cleaning if there are no more people in stalls
        }
    }

    @Override
    public synchronized void startCleaning()
    {
        cleanerIsWaiting = true;
        // Wait until the stalls are empty and it's not currently being cleaned
        while (restRoomUsers > 0)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        isCleaning = true;
        cleanerIsWaiting = false;
    }

    @Override
    public synchronized void endCleaning()
    {
        isCleaning = false;
        notifyAll(); // Notify all waiting restroom users that they can enter now
    }
}
```

  </details>
</blockquote>

## 16.2 Single lane bridge

In this exercise we will model a single lane bridge.

Cars can approach from either direction, but will have to stop and wait if there are cars coming from the other direction.

Multiple cars from the same direction are allowed on the bridge at the same time.

The interface for the single lane bridge is this:

<img src="Images/Exercise_02.png" width="60%">

```java
public interface Lane
{
    void enterFromTheLeft(); // Allowed only when no cars from the right side are on the lane
    void exitToTheRight(); // A car has exited the lane to the right side
    void enterFromTheRight(); // Allowed only when no cars from the left side are on the lane
    void exitToTheLeft(); // A car has exited the lane to the left side
}
```

Implement a solution that does not give priority to either side (this is neither of the 3 strategies presented)


<blockquote>
  <details>
    <summary>Display solution...</summary>
    
```java
public class SingleLaneBridge implements Lane
{

    private int carsOnLeftSide = 0;
    private int carsOnRightSide = 0;

    @Override
    public synchronized void enterFromTheLeft()
    {
        carsOnLeftSide++;
        while (carsOnRightSide > 0)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
        //start crossing bridge
    }

    @Override
    public synchronized void exitToTheRight()
    {
        carsOnLeftSide--;
        notifyAll();
        //finished crossing bridge
    }

    @Override
    public synchronized void enterFromTheRight()
    {
        carsOnRightSide++;
        while (carsOnLeftSide > 0)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
        //start crossing bridge
    }

    @Override
    public synchronized void exitToTheLeft()
    {
        carsOnRightSide--;
        notifyAll();
        //finished crossing bridge
    }
}
```

  </details>
</blockquote>

## 16.3 Single lane bridge

In this exercise we will model a system where customers can book flights, and administrators can update details on flights.

We begin with this UML Class diagram:

<img src="Images/Exercise_03a.png" width="100%">

Implement everything as shown and create some dummy data for testing purposes.

```java
public class FlightPlan
{
    private ArrayList<Flight> flights;
    
    public FlightPlan()
    {
        flights = new ArrayList<>();  
        createDummyData();
    }

    //...

}
```

Make the `FlightPlan` editable through interfaces as shown in this diagram:

<img src="Images/Exercise_03b.png" width="50%">

Finally, make the `FlightPlan` thread-safe using the [Proxy](https://viaucdk-my.sharepoint.com/:p:/g/personal/mivi_viauc_dk/EUGB7qts_9RLn2C35XmHCEYB4oIE6OLV06L99PMfDaZNWA?e=kD7tif) Pattern.

<img src="Images/Exercise_03c.png" width="100%">

Notice how the "acquire"-methods return an interface type.

Implement the Readers-Writers Solution using the "fair" strategy.

```java
public class ThreadSafeFlightPlan implements FlightPlanAccess
{
    private int readers;  
    private int writers;
    private FlightPlan flightPlan;

    public ThreadSafeFlightPlan(FlightPlan flightPlan)
    {
        this.readers = 0;
        this.writers = 0;  
        this.flightPlan = flightPlan;
    }

    //...

}
```
