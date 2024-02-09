# 03 Exercises: Observer Pattern

## 3.1 Traffic light

For this exercise, we will implement everything without using the Observer pattern, just to see how it quickly becomes hard to maintain.

Create the Traffic light example that was shown in the presentation, with the classes [`Car`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/Car.java), [`TrafficLight`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/TrafficLight.java) and [`Main`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/Main.java). 

Run the example and verify that the `Car` reacts to the traffic light changing.

Implement a new class `Taxi`: 
 - It ignores yellow lights, stops when red, and drives when green.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  You can duplicate most of the code from the `Car` class. You will need to make changes in the `setLight()` method, so that the `Taxi` acts like instructed. You will also need to make a few changes in the `TrafficLight` and `Main` class.
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

Implement a new class `SleepyDriver`: 
 - If the light is red, and changes to yellow, it doesn’t do anything.
 - When the light changes to green, it start its engine and drives.
 - If the light is green, and changes to yellow, it slows down.
 - It stops for red light.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  Just like with the previous class, you can reuse everything from `Car` and modify the behaviour as necessary. You will also need to make a few changes in the `TrafficLight` and `Main` class.
</p>
<details>
<summary>Display solution...</summary>

```java
public class SleepyDriver
{
    private String previousLight;
    private int id;

    public SleepyDriver(int id)
    {
        this.id = id;
    }

    public void setLight(String currentLight)
    {
        if("GREEN".equals(currentLight))
        {
            System.out.println("SleepyDriver " + id + " turns engine on");
            System.out.println("SleepyDriver " + id + " drives");
        }
        else if("YELLOW".equals(currentLight))
        {
            if("RED".equals(previousLight))
            {
                //Do nothing
            }
            else
            {
                System.out.println("SleepyDriver " + id + " slows down");
            }
        }
        else if("RED".equals(currentLight))
        {
            System.out.println("SleepyDriver " + id + " stops");
        }
        previousLight = currentLight;
    }
}
```
</details>
</details>
</blockquote>

Implement a new class `Pedestrian`. When the cars are waiting for red light, he can cross the road:
 - When the light turns green he waits (green here means green for the cars, we haven't modelled a traffic light for pedestrians).
 - When the light turns from green to yellow, he runs fast across the road.
 - When the light turns red he crosses the road.
 - When the light turns from red to yellow, he gets ready to cross.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  Once again, reuse everything from `Car` and make necessary changes. Your `TrafficLight` and `Main` classes will have been updated quite a lot by now, so their implementation is also shown in the solution below.
</p>
<details>
<summary>Display solution...</summary>

```java
public class Pedestrian
{
    private int id;
    private String previousLight;

    public Pedestrian(int id)
    {
        this.id = id;
    }

    public void setLight(String currentLight)
    {
        if("GREEN".equals(currentLight))
        {
            System.out.println("Pedestrian " + id + " waits");
        }
        else if("YELLOW".equals(currentLight))
        {
            if("RED".equals(previousLight))
            {
                System.out.println("Pedestrian " + id + " gets ready to cross the road");
            }
            else
            {
                System.out.println("Pedestrian " + id + " walks fast accross the road");
            }
        }
        else if("RED".equals(currentLight))
        {
            System.out.println("Pedestrian " + id + " walks accross the road");
        }
        previousLight = currentLight;
    }
}

import java.util.ArrayList;
import java.util.List;

public class TrafficLight
{
    List<Car> cars;
    List<Taxi> taxis;
    List<SleepyDriver> sleepyDrivers;
    List<Pedestrian> pedestrians;

    private String[] lights = {"GREEN", "YELLOW", "RED", "YELLOW"};
    private int count = 2;
    private String currentLight;

    public TrafficLight()
    {
        currentLight = lights[2];
        cars = new ArrayList<>();
        taxis = new ArrayList<>();
        sleepyDrivers = new ArrayList<>();
        pedestrians = new ArrayList<>();
    }

    public void addCar(Car car)
    {
        cars.add(car);
        car.setLight(currentLight);
    }

    public void addTaxi(Taxi taxi)
    {
        taxis.add(taxi);
        taxi.setLight(currentLight);
    }

    public void addSleepyDriver(SleepyDriver sleepyDriver)
    {
        sleepyDrivers.add(sleepyDriver);
        sleepyDriver.setLight(currentLight);
    }

    public void addPedestrian(Pedestrian pedestrian)
    {
        pedestrians.add(pedestrian);
        pedestrian.setLight(currentLight);
    }

    public void start() throws InterruptedException
    {
        while(true)
        {
            Thread.sleep(2000);
            count = (count + 1) % 4;
            currentLight = lights[count];
            System.out.println("\nLight is " + currentLight);
            lightChanged();
        }
    }

    private void lightChanged()
    {
        for (Car car : cars)
        {
            car.setLight(currentLight);
        }
        for (Taxi taxi : taxis)
        {
            taxi.setLight(currentLight);
        }
        for (SleepyDriver sleepyDriver : sleepyDrivers)
        {
            sleepyDriver.setLight(currentLight);
        }
        for (Pedestrian pedestrian : pedestrians)
        {
            pedestrian.setLight(currentLight);
        }
    }
}

public class Main
{
    public static void main(String[] args)
    {
        TrafficLight trafficLight = new TrafficLight();
        Car car1 = new Car(1);
        Taxi taxi1 = new Taxi(1);
        SleepyDriver sleepyDriver1 = new SleepyDriver(1);
        Pedestrian pedestrian1 = new Pedestrian(1);

        trafficLight.addCar(car1);
        trafficLight.addTaxi(taxi1);
        trafficLight.addSleepyDriver(sleepyDriver1);
        trafficLight.addPedestrian(pedestrian1);

        try
        {
            trafficLight.start();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
```
</details>
</details>
</blockquote>

## 3.2 Traffic light using the Observer pattern

Modify your solution from 3.1 to use the `PropertyChangeSupport`, `PropertyChangeListener` and optionally use the [`PropertyChangeSubject`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/PropertyChangeSubject.java) interface.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  Start by reworking the `TrafficLight` class. Turn it into a subject using the Observer pattern. Listeners should attach themselves as `PropertyChangeListener`, and be stored in a `PropertyChangeSupport`. To do so, combine and modify the `add()` methods. It should also notify all listeners whenever the light changes, in the `lightChanged()` method, calling `firePropertyChange()` on the `PropertyChangeSupport`. 
</p>

 <p>
  Everyone looking at the light should be turned into listeners using the Observer pattern. To get notifications about light changes, they need to attach themselves to the subject, the `TrafficLight` class. The method to attach takes a `PropertyChangeListener` as argument, so they need to implement that interface. That interface has a single method `propertyChange(PropertyChangeEvent evt)` which they need to implement. Have that method call the `setLight()` method with the value received.
</p>

<details>
<summary>Display solution...</summary>

```java
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TrafficLight
{
    private PropertyChangeSupport support;
    private String[] lights = {"GREEN", "YELLOW", "RED", "YELLOW"};
    private int count = 2;
    private String currentLight;

    public TrafficLight()
    {
        currentLight = lights[2];
        support = new PropertyChangeSupport(this);
    }

    public void start() throws InterruptedException
    {
        while(true)
        {
            Thread.sleep(2000);
            count = (count + 1) % 4;
            currentLight = lights[count];
            System.out.println("\nLight is " + currentLight);
            lightChanged();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    private void lightChanged()
    {
        support.firePropertyChange("LightChanged", null, currentLight);
    }
}

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Car implements PropertyChangeListener
{
    private String previousLight;
    private int id;

    public Car(int id)
    {
        this.id = id;
    }

    public void setLight(String currentLight)
    {
        if("GREEN".equals(currentLight))
        {
            System.out.println("Car " + id + " drives");
        }
        else if("YELLOW".equals(currentLight))
        {
            if("RED".equals(previousLight))
            {
                System.out.println("Car " + id + " turns engine on");
            }
            else
            {
                System.out.println("Car " + id + " slows down");
            }
        }
        else if("RED".equals(currentLight))
        {
            System.out.println("Car " + id + " stops");
        }
        previousLight = currentLight;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        setLight((String) evt.getNewValue());
    }
}

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Taxi implements PropertyChangeListener
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

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        setLight((String) evt.getNewValue());
    }
}

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SleepyDriver implements PropertyChangeListener
{
    private String previousLight;
    private int id;

    public SleepyDriver(int id)
    {
        this.id = id;
    }

    public void setLight(String currentLight)
    {
        if("GREEN".equals(currentLight))
        {
            System.out.println("SleepyDriver " + id + " turns engine on");
            System.out.println("SleepyDriver " + id + " drives");
        }
        else if("YELLOW".equals(currentLight))
        {
            if("RED".equals(previousLight))
            {
                //Do nothing
            }
            else
            {
                System.out.println("SleepyDriver " + id + " slows down");
            }
        }
        else if("RED".equals(currentLight))
        {
            System.out.println("SleepyDriver " + id + " stops");
        }
        previousLight = currentLight;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        setLight((String) evt.getNewValue());
    }
}

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Pedestrian implements PropertyChangeListener
{
    private int id;
    private String previousLight;

    public Pedestrian(int id)
    {
        this.id = id;
    }

    public void setLight(String currentLight)
    {
        if("GREEN".equals(currentLight))
        {
            System.out.println("Pedestrian " + id + " waits");
        }
        else if("YELLOW".equals(currentLight))
        {
            if("RED".equals(previousLight))
            {
                System.out.println("Pedestrian " + id + " gets ready to cross the road");
            }
            else
            {
                System.out.println("Pedestrian " + id + " walks fast accross the road");
            }
        }
        else if("RED".equals(currentLight))
        {
            System.out.println("Pedestrian " + id + " walks accross the road");
        }
        previousLight = currentLight;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        setLight((String) evt.getNewValue());
    }
}

public class Main
{
    public static void main(String[] args)
    {
        TrafficLight trafficLight = new TrafficLight();
        Car car1 = new Car(1);
        Taxi taxi1 = new Taxi(1);
        SleepyDriver sleepyDriver1 = new SleepyDriver(1);
        Pedestrian pedestrian1 = new Pedestrian(1);

        trafficLight.addPropertyChangeListener(car1);
        trafficLight.addPropertyChangeListener(taxi1);
        trafficLight.addPropertyChangeListener(sleepyDriver1);
        trafficLight.addPropertyChangeListener(pedestrian1);

        try
        {
            trafficLight.start();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
```
</details>
</details>
</blockquote>

## 3.3 Waiting room

Implement the UML class diagram shown below:

![WaitingRoomUMLClassDiagram](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Images/WaitingRoomUML.png)

In the `Main` class, create a `WaitingRoom` thread and start it. In the `run()` method, it should increment a counter from 0, fire an event, and then sleep for 1 second. This is to simulate that each patient takes a ticket number when entering the waiting room. When the doctor is ready for the next patient, the patients are informed by the ticket system in the waiting room, and the patient with the correct ticket number enters the doctor’s office.

In the `Main` class, create a bunch of patients, give them each a ticket number, and add them as listeners to the waiting room. 

Upon notification the patient must look up, check the current number, and if it’s their number, go to the doctor’s office. Otherwise they go back to looking at their phone.

Simulate this with print outs. So, running the program could produce an output like this:

```
Patient 0 enters waiting room
Patient 1 enters waiting room
Diing!
Patient 0 looks up
Patient 0 goes to the doctor's room
Patient 1 looks up
Patient 1 goes back to looking at phone
Patient 2 enters waiting room
Diing!
Patient 1 looks up
Patient 1 goes to the doctor's room
Patient 2 looks up
Patient 2 goes back to looking at phone
Patient 3 enters waiting room
Patient 4 enters waiting room
Diing!
Patient 2 looks up
Patient 2 goes to the doctor's room
Patient 3 looks up
```

When a patient enters the doctor’s office, remove them as listeners. They are no longer interested in the ticket numbers.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

## 3.4 Soccer match

The target of this exercise is to implement a soccer match, and a number of other classes observing this soccer match.

Download the [`SoccerMatch`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/SoccerMatch.java) class and add it to your project.

Create a class with a main method. Create an instance of `SoccerMatch` and call the `startMatch()` method.

Verify that two things are printed out, with 9 seconds in between: 

"Match starting" and "Match ended".

### 3.4.1

Change the `SoccerMatch` class so that it becomes a subject:

 - Implement the [`PropertyChangeSubject`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/PropertyChangeSubject.java) or create your own.
 -	The `SoccerMatch` must have an attribute of type `PropertyChangeSupport`, similar to the `TrafficLight` example.
 -	Find the four TODOs - implement them so you fire events, using the `PropertyChangeSupport`. Give meaningful names for the events, like “DreamTeamScore”, “OldBoysRoughTackle”.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

### 3.4.2

Create two fan classes: `DreamTeamFan` and `OldBoysFan`. 

 - When the team they’re rooting for has scored, print out something like “Dream team fans: YEEEAAH!”
 - When the opposite team scores, print out something like "Old boys fans: BOOOOOOOH!"
 - In the main method, create an instance of each, and add them as listeners to the `SoccerMatch`.
 
<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

### 3.4.3

Create a class `Referee`. He should react to rough tackles - print out something like “Referee gives Old Boys a yellow card for a rough tackle”. 

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

### 3.4.4

Create a class `AngryCoach`. When his team scores, he should cheer. When the other team makes a rough tackle, he should yell at the judge.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

### 3.4.5

Create a class `ScoreBoard`. It should print out the current score, every time it changes.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

### 3.4.6

Create a `Medic` class. They should react to rough tackles. 

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

## 3.5 Bird watching

Write a small program to represent the bird watcher example from the presentations-

Create a `Bird` class. It must either flap its wings or sing a song. Simulate it with print outs:

```
Peacock flashes its wings
Peacock whistles
```

Create a `BirdWatcher` class, which will react to the birds behaviour with exclamations like: “Ooh!”, “How nice”, “Would you look at that”.

Create a `BlindBirdWatcher` class. They can’t see anything, so they only reacts to the bird’s singing.

Test everything in a main method where you create several birds and watchers.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

## 3.6 Data representation

This is for the adventurous ones, it assumes some knowledge about JavaFX.
You’re going to implement the example from the slides about visualizing data in different ways.
In JavaFX there are several components to show data, you’ll have to investigate how to use them. In the SceneBuilder they’re found under the ‘charts’ section.

![SceneBuilderChartsScreenshot](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Images/SceneBuilderChartsScreenshot.png)
 
The uploaded class DataModel has a method, which will calculate three values: red, green, yellow. The sum will always be 100. The numbers will be recalculated whenever the recalculateData method is called.
Modify the DataModel class so that is becomes a Subject: Implement the interface, create a field variable of type PropertyChangeSupport, and fire events from the recalculateData method.
Instantiate the DataModel, e.g. in a main method. Create a while(true) loop, which calls the recalculateData method, then sleeps for a short while.
Run the example, and verify the printed output is as expected.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>

### 3.7.1	

Create multiple Listener classes. Each class should listen for changes in the DataModel. Each class should present the data in a different way, play around with the chart components.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  
</p>
<details>
<summary>Display solution...</summary>

```java
```
</details>
</details>
</blockquote>



