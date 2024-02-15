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

Implement a new class `SleepyDriver`: 
 - If the light is red, and changes to yellow, it doesn’t do anything.
 - When the light changes to green, it start its engine and drives.
 - If the light is green, and changes to yellow, it slows down.
 - It stops for red light.

<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  Just like with the previous class, you can reuse everything from <code>Car</code> and modify the behaviour as necessary. You will also need to make a few changes in the <code>TrafficLight</code> and <code>Main</code> class.
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
  Once again, reuse everything from <code>Car</code> and make necessary changes. Your <code>TrafficLight</code> and <code>Main</code> classes will have been updated quite a lot by now, so their implementation is also shown in the solution below.
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
  Start by reworking the <code>TrafficLight</code> class. Turn it into a subject using the Observer pattern. Listeners should attach themselves as <code>PropertyChangeListener</code>, and be stored in a <code>PropertyChangeSupport</code>. To do so, combine and modify the <code>add()</code> methods. It should also notify all listeners whenever the light changes, in the <code>lightChanged()</code> method, calling <code>firePropertyChange()</code> on the <code>PropertyChangeSupport</code>. 
</p>

 <p>
  Everyone looking at the light should be turned into listeners using the Observer pattern. To get notifications about light changes, they need to attach themselves to the subject, the <code>TrafficLight</code> class. The method to attach takes a <code>PropertyChangeListener</code> as argument, so they need to implement that interface. That interface has a single method <code>propertyChange(PropertyChangeEvent evt)</code> which they need to implement. Have that method call the <code>setLight()</code> method with the value received.
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

## 3.3 Bird watching

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
  Start by creating the <code>Bird</code> class as a subject.
</p>
<p>
  The <code>BirdWatcher</code> and <code>BlindBirdWatcher</code> classes are listeners, and should react to the events fired by the <code>Bird</code> class. When attaching them as listeners, do it with lambda expressions instead of implementing the <code>PropertyChangeListener</code> interface.
</p>
 
<details>
<summary>Display solution...</summary>

```java
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

public class Bird implements PropertyChangeSubject
{
    private PropertyChangeSupport support;

    public Bird()
    {
        support = new PropertyChangeSupport(this);
    }

    public void start()
    {
        Random random = new Random();
        while(true)
        {
            random.nextInt(100);
            if(random.nextInt(100) < 50)
            {
                System.out.println("Bird is flapping wings");
                support.firePropertyChange("Flapping", null, null);
            }
            else
            {
                System.out.println("Bird is singing a song");
                support.firePropertyChange("Singing", null, null);
            }
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(name, listener);
    }
}

import java.util.Random;

public class BirdWatcher
{
    public BirdWatcher(Bird birdToWatch)
    {
        birdToWatch.addPropertyChangeListener("Flapping", evt -> reactToFlapping());
        birdToWatch.addPropertyChangeListener("Singing", evt -> reactToSinging());
    }

    private void reactToFlapping()
    {
        Random random = new Random();
        int reaction = random.nextInt(3);
        if(reaction == 0)
        {
            System.out.println("Bird Watcher: Ooh!");
        }
        else if(reaction == 1)
        {
            System.out.println("Bird Watcher: So beautiful!");
        }
        else
        {
            System.out.println("Bird Watcher: Would you look at that");
        }
    }

    private void reactToSinging()
    {
        Random random = new Random();
        int reaction = random.nextInt(3);
        if(reaction == 0)
        {
            System.out.println("Bird Watcher: Wow!");
        }
        else if(reaction == 1)
        {
            System.out.println("Bird Watcher: How nice");
        }
        else
        {
            System.out.println("Bird Watcher: What a lovely voice!");
        }
    }
}

import java.util.Random;

public class BlindBirdWatcher
{
    public BlindBirdWatcher(Bird birdToWatch)
    {
        birdToWatch.addPropertyChangeListener("Singing", evt -> reactToSinging());
    }

    private void reactToSinging()
    {
        Random random = new Random();
        int reaction = random.nextInt(3);
        if(reaction == 0)
        {
            System.out.println("Blind Bird Watcher: Wow!");
        }
        else if(reaction == 1)
        {
            System.out.println("Blind Bird Watcher: How nice");
        }
        else
        {
            System.out.println("Blind Bird Watcher: What a lovely voice!");
        }
    }
}

public class Main
{
    public static void main(String[] args)
    {
        Bird bird = new Bird();
        new BirdWatcher(bird);
        new BlindBirdWatcher(bird);

        bird.start();
    }
}
```
</details>
</details>
</blockquote>

## 3.4 Waiting room

Implement the UML class diagram shown below:

![WaitingRoomUMLClassDiagram](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Images/WaitingRoomUML.png)

The `run()` method of the `WaitingRoom` start a counter at 0 (to simulate ticket number for the patients), run indefinitely while it increments the counter by 1, fire an event each time it does, and then sleep for 1 second. 

While waiting the patients in waiting room will receive a notification each time the ticket number increases.

Upon notification the patient must look up, check the current number, and if it’s their number, go to the doctor’s office. Otherwise they go back to looking at their phone.

When a patient enters the doctor’s office, they are no longer interested in the ticket numbers and should stop receiving notifications.

Simulate this with print outs. So, running the program could produce an output like this (shown here with only 2 patients for clarity):

```
Patient 0 enters the waiting room
Patient 1 enters the waiting room
Diing!
Patient 0 looks up
Patient 0 goes to the doctor's room
Patient 1 looks up
Patient 1 goes back to looking at phone
Diing!
Patient 1 looks up
Patient 1 goes to the doctor's room
Diing!
Diing!
Diing!
Diing!
```


<blockquote>
<details>
<summary>Display hints...</summary>
<p>
  Start with the <code>WaitingRoom</code> since it will act as the subject. The <code>run()</code> method can be implemented with a <code>while(true)</code> loop where you increment the ticket number and sleep for 1 second with <code>Thread.sleep(1000)</code>
</p>
<p>
  The <code>Patient</code> class can either be implemented as a <code>PropertyChangeListener</code> or it could use anonymous classes (using lambda expressions or other syntactic sugary goodness if desired). If you use lambda expressions you might find it difficult to detach the listener again. This can be fixed by storing your <code>PropertyChangeListener</code> reference.
</p>
 <p>
  In the UML class diagram, the listener, <code>Patient</code>, has an association to the subject, <code>WaitingRoom</code>. Feel free to set this attribute in the constructor, or create a setter in <code>Patient</code>. Alternatively, ignore the association and link the listener and subject manually in the main method.
</p>
<details>
<summary>Display solution...</summary>

```java
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WaitingRoom implements PropertyChangeSubject, Runnable
{
    private PropertyChangeSupport support;

    public WaitingRoom()
    {
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(name, listener);
    }

    @Override
    public void run()
    {
        int i = 0;
        while(true)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }System.out.println("Diing!");
            support.firePropertyChange("NextPatient", null, i);
            i++;

        }
    }
}

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Patient
{
    private int ticketNumber;
    private WaitingRoom waitingRoom;
    private PropertyChangeListener listenForNextPatient;

    public Patient(int ticketNumber, WaitingRoom waitingRoom)
    {
        this.ticketNumber = ticketNumber;
        this.waitingRoom = waitingRoom;
        listenForNextPatient = this::reactToDing; // This could also be written as 'evt -> reactToNextPatient(evt)'
        waitingRoom.addPropertyChangeListener(listenForNextPatient);
        System.out.println("Patient " + ticketNumber + " enters the waiting room");
    }

    private void reactToDing(PropertyChangeEvent evt)
    {
        System.out.println("Patient " + ticketNumber + " looks up");
        if(ticketNumber == (int) evt.getNewValue())
        {
            System.out.println("Patient " + ticketNumber + " goes to the doctor's room");
            waitingRoom.removePropertyChangeListener(listenForNextPatient); // This is a method reference
        }
        else
        {
            System.out.println("Patient " + ticketNumber + " goes back to looking at phone");
        }
    }
}

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        WaitingRoom waitingRoom = new WaitingRoom();
        Thread waitingRoomThread = new Thread(waitingRoom);
        waitingRoomThread.start();

        Patient patient0 = new Patient(0, waitingRoom);
        Patient patient1 = new Patient(1, waitingRoom);
        Patient patient2 = new Patient(2, waitingRoom);
        Patient patient3 = new Patient(3, waitingRoom);
        Patient patient4 = new Patient(4, waitingRoom);
    }
}
```
</details>
</details>
</blockquote>

## 3.5 Soccer match

The target of this exercise is to implement a soccer match, and a number of other classes observing this soccer match.

Download the [`SoccerMatch`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/SoccerMatch.java) class and add it to your project.

Create a class with a main method. Create an instance of `SoccerMatch` and call the `startMatch()` method.

Verify that two things are printed out, with 9 seconds in between: 

"Match starting" and "Match ended".

### 3.5.1

Change the `SoccerMatch` class so that it becomes a subject:

 - Implement the [`PropertyChangeSubject`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/PropertyChangeSubject.java) or create your own.
 -	The `SoccerMatch` must have an attribute of type `PropertyChangeSupport`, similar to the `TrafficLight` example.
 -	Find the four TODOs - implement them so you fire events, using the `PropertyChangeSupport`. Give meaningful names for the events, like “Goal” and “RoughTackle”.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

public class SoccerMatch implements PropertyChangeSubject
{

    private String team0 = "Dream Team";
    private String team1 = "Old Boys";

    private PropertyChangeSupport support;

    public SoccerMatch()
    {
        support = new PropertyChangeSupport(this);
    }

    public void startMatch()
    {
        System.out.println("Match starting \n\n");
        Random random = new Random();
        for (int i = 0; i < 90; i++)
        {

            int rand = random.nextInt(100);
            int whichTeam = random.nextInt(2);

            if (rand < 8)
            {
                // score goal
                scoreGoal(whichTeam);
            }
            else if (rand < 12)
            {
                // penalty
                roughTackle(whichTeam);
            }

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                break;
            }
        }

        System.out.println("\n\nMatch ended");
    }

    private void roughTackle(int whichTeam)
    {
        if (whichTeam == 0)
        {
            support.firePropertyChange("RoughTackle", null, team0);
        }
        else
        {
            support.firePropertyChange("RoughTackle", null, team1);
        }
    }

    private void scoreGoal(int whichTeam)
    {
        if (whichTeam == 0)
        {
            support.firePropertyChange("Goal", null, team0);
        }
        else
        {
            support.firePropertyChange("Goal", null, team1);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(name, listener);
    }
}

```
</details>
</blockquote>

### 3.5.2

Create two fan classes: `DreamTeamFans` and `OldBoysFans`. 

 - When the team they’re rooting for has scored, print out something like “Dream team fans: YEEEAAH!”
 - When the opposite team scores, print out something like "Old boys fans: BOOOOOOOH!"
 - In the main method, create an instance of each, and add them as listeners to the `SoccerMatch`.
 
<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.beans.PropertyChangeEvent;

public class DreamTeamFans
{
    public DreamTeamFans(SoccerMatch soccerMatch)
    {
        soccerMatch.addPropertyChangeListener("Goal", this::teamScored);
    }

    private void teamScored(PropertyChangeEvent evt)
    {
        if(("Dream Team").equals(evt.getNewValue()))
        {
            System.out.println("Dream team fans: YEEEAAH!");
        }
        else
        {
            System.out.println("Dream team fans: BOOOOOOOH!");
        }
    }
}

import java.beans.PropertyChangeEvent;

public class OldBoysFans
{
    public OldBoysFans(SoccerMatch soccerMatch)
    {
        soccerMatch.addPropertyChangeListener("Goal", this::teamScored);
    }

    private void teamScored(PropertyChangeEvent evt)
    {
        if("Old Boys".equals(evt.getNewValue()))
        {
            System.out.println("Old Boys fans: YEEEAAH!");
        }
        else
        {
            System.out.println("Old Boys fans: BOOOOOOOH!");
        }
    }
}
```
</details>
</blockquote>

### 3.5.3

Create a class `Referee`. He should react to rough tackles - print out something like “Referee gives Old Boys a yellow card for a rough tackle”. 

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.beans.PropertyChangeEvent;

public class Referee
{
    public Referee(SoccerMatch soccerMatch)
    {
        soccerMatch.addPropertyChangeListener("RoughTackle", this::roughTackle);
    }

    private void roughTackle(PropertyChangeEvent evt)
    {
        String team = (String) evt.getNewValue();
        System.out.println("Referee gives " + team + " a yellow card for a rough tackle");
    }
}
```
</details>
</blockquote>

### 3.5.4

Create a class `AngryCoach`. When his team scores, he should cheer. When the other team makes a rough tackle, he should yell at the referee.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.beans.PropertyChangeEvent;

public class AngryCoach
{
    private String team;

    public AngryCoach(String team, SoccerMatch soccerMatch)
    {
        this.team = team;
        soccerMatch.addPropertyChangeListener("RoughTackle", this::roughTackle);
        soccerMatch.addPropertyChangeListener("Goal", this::teamScored);
    }

    private void roughTackle(PropertyChangeEvent evt)
    {
        String teamThatRoughTackled = (String) evt.getNewValue();
        if(!team.equals(teamThatRoughTackled))
        {
            System.out.println("Angry "+team+" coach: " + teamThatRoughTackled + " player, you are out of the game! Referee, send him off!");
        }
    }

    private void teamScored(PropertyChangeEvent evt)
    {
        if (team.equals(evt.getNewValue()))
        {
            System.out.println("Angry "+team+" coach: " + this.team + " scored! Good job guys!");
        }
    }
}
```
</details>
</blockquote>

### 3.5.5

Create a class `ScoreBoard`. It should print out the current score, every time it changes.

<blockquote>
<details>
<summary>Display solution...</summary>

```java
import java.beans.PropertyChangeEvent;

public class ScoreBoard
{

    private int teamDreamTeamScore = 0;
    private int teamOldBoysScore = 0;

    public ScoreBoard(SoccerMatch soccerMatch)
    {
        soccerMatch.addPropertyChangeListener("Goal", this::teamScored);
    }

    private void teamScored(PropertyChangeEvent evt)
    {
        String teamThatScored = (String) evt.getNewValue();
        if("Dream Team".equals(teamThatScored))
        {
            teamDreamTeamScore++;
        }
        else
        {
            teamOldBoysScore++;
        }
        System.out.println("Score: Dream Team: " + teamDreamTeamScore + " - Old Boys: " + teamOldBoysScore);
    }
}
```
</details>
</blockquote>

## 3.6 Data representation

<u>This exercise assumes that you are already familiar with JavaFX.</u>

You’re going to implement the example from the presentation about visualizing data in different ways.

In JavaFX there are several different components that can be used to show data. You will have to investigate how to use them on your own. In SceneBuilder they can be found under the "Charts" section.

![SceneBuilderChartsScreenshot](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Images/SceneBuilderChartsScreenshot.png)
 
The uploaded class [`DataModel`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/DataModel.java) will calculate three values: red, green, blue. The sum will always be 100. The numbers will be recalculated every second.

Start by creating a `Main` class and create a new `Thread` for the `DataModel` and `start()` it. Verify that it prints a new set of values to the console every second.

### Creating a data representation chart

You may have notified that the `DataModel` class fires an event called "DataChange". This is the event we want to subscribe to in our representation classes. Select one of the chart types in JavaFX, and research on your own how they work. For now, we will just create a single `Controller` class and implement everything there.

You should be able to get everything running with a total of 5 files in your workspace:
 - `DataModel`, the one you've already downloaded
 - `PropertyChangeSubject` the interface used by `DataModel`
 - An appropriately named `Controller` for your JavaFX GUI
 - A `Main` class that extends `Application` with a main method that calls `launch()` to start everything
 - An appropriately named `.fxml` file containing your layout, using the chosen chart type.

In the following section a list of the most common issues has been covered. Start by reading through this first.

You will probably also need to inject your model into your `FXMLController` class. There are many ways to do this, one is to use the `ControllerFactory` in JavaFX, like this:

```java
DataModel model = new DataModel();
FXMLLoader fxmlLoader = new FXMLLoader([name-of-your-class-here].class.getResource("[name-of-your-FXML-file-here].fxml"));
fxmlLoader.setControllerFactory(controllerClass -> new [name-of-your-controller-class-here](model));
```

JavaFX runs on its own thread. If we start a new thread for the `DataModel`, our main thread persists while this thread keep running. To avoid this, we can set the `DataModel` thread as a daemon thread, so it dies when the JavaFX thread dies, like this:

```java
Thread dataThread = new Thread(model);
dataThread.setDaemon(true);
dataThread.start();
```

Due to the way JavaFX works, you are not able to modify JavaFX properties from another thread. To deal with this, we can defer the modification until a time that JavaFX deems suitable, by wrapping the call in a `Platform.runLater()` call, like this:

```java
Platform.runLater(() ->
{
  //Insert the code that modifies JavaFX properties here...
});
```

JavaFX will initialize all your `@FXML` annotated attributes in the `initialize()` method, so make sure you don't try to use them in your constructor (they aren't initialized at that point).

You should be able to get a small example running now. Good luck :)

If you are stuck, take a look at the [Bar Chart Example](https://github.com/MichaelViuff/SDJ2/tree/main/03%20Observer%20Pattern/Examples/JavaFX%20Charts)
