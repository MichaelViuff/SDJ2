# 16 Exercises: Readers-Writers Problem

## 16.1 Public toilet cleaning

People using a cabin in the public toilet.

A cleaning person cleaning while alone in the building.

<img src="Images/Exercise_01.png" width="50%">

```java
public interface PublicToilet
{
    void stepIntoStall();
    void leaveStall();
    void startCleaning();
    void endCleaning();
}
```

Which solution strategy?
(What is most important?)

<blockquote>
  <details>
    <summary>Display solution...</summary>
    
  </details>
</blockquote>

## 16.2 Single lane bridge

Multiple cars allowed in the same direction.

In opposite direction, wait until the bridge is cleared.

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

<blockquote>
  <details>
    <summary>Display solution...</summary>
    
  </details>
</blockquote>

## 16.3 Single lane bridge

Customers want to search for a flight.

Airport want to update the flight plan.

<img src="Images/Exercise_03a.png" width="100%">


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

Make the `FlightPlan` editable through interfaces.

<img src="Images/Exercise_03b.png" width="50%">





Make the `FlightPlan` thread-safe

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
<img src="Images/Exercise_03c.png" width="100%">

<blockquote>
  <details>
    <summary>Display solution...</summary>
    
  </details>
</blockquote>