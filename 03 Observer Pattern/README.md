# 03 Exercises: Observer Pattern

## 3.1 Traffic light

Download the Traffic light example that was shown in the presentation, with the classes [`Car`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/Car.java), [`TrafficLight`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/TrafficLight.java) and [`Main`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/Main.java). 

Run the example and verify that the 3 cars react to the traffic light changing.

Implement a new class `Taxi`: 
 - It ignores yellow lights, stops when red, and drives when green.

Implement a new class `SleepyDriver`: 
 - If the light is red, and changes to yellow, it doesn’t do anything
 - When the light changes to green, it start its engine and drives.
 - If the light is green, and changes to yellow, it slows down

Implement a new class `Pedestrian`. When the cars are waiting for red light, he can cross the road:
 - When the light turns green he waits (red means red for the cars, we haven't modelled a traffic light for pedestrians)
 - When the light turns from green to yellow, he runs fast across the road.
 - When the light turns red he crosses the road.
 - When the light turns from red to yellow, he gets ready to cross.

## 3.2 Traffic light

Modify your solution from 3.1 to use the `PropertyChangeSupport`, `PropertyChangeListener` and optionally use the [`PropertyChangeSubject`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/PropertyChangeSubject.java) interface.

## 3.3 Traffic light

You’re going to simulate a Doctor’s waiting room, initially it looks something like this:

![WaitingRoomUMLClassDiagram](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Images/WaitingRoomUML.png)

The PropertyChangeListener interface is the one from Java. The PropertyChangeSubject is the one from the slides.
In the main class, create the WaitingRoom and run it in a separate thread. In the run method, it should increment a counter from 0, fire an event, and then sleep for e.g. 1 second. This is to simulate that each patient takes a ticket number when entering the waiting room. Occasionally the doctor is ready for the next patient, so the patients are informed by the ticket system in the waiting room, and the patient with the correct ticket number enters the doctor’s office.

In the main class, create a bunch of patients, give them each a ticket number (for-loop?), and add them as listeners to the waiting room. 
Upon notification the patient must look up, if it’s their number go to the doctor’s office, otherwise go back to play with phone.
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

## 3.4 Soccer match

The target of this exercise is to implement a soccer match, and a number of other classes observing this soccer match.

Download the [`SoccerMatch`]() class and add it to your project.

Create a different class, with a main method. Create an instance of SoccerMatch call the `startMatch` method.

Verify that two things are printed out, with 9 seconds in between: 

"the match has started" and "the match has ended".

### 3.4.1

Change the `SoccerMatch` class so that it becomes a Subject:

 - Implement an interface similar to the one shown in the Traffic Light example
 -	The SoccerMatch must have a private field of type PropertyChangeSupport, which is used similar to the Traffic Light example.
 -	Find the four TODOs - implement them so you fire events, using the PropertyChangeSupport. Give meaningful names for the events, like “DreamTeamScore”, “OldBoysRoughTackle”.

### 3.4.2

Implement two fan classes: DreamTeamFan, OldBoysFan. These should be Listeners. When an event is fired, so that the team they’re rooting for has scored, print out something like “Dream team fans: YEEEAAH!”.
When the opposite team scores, print out some buuhing.
In the main method, create an instance of each, and add them as Listeners to the SoccerMatch. Run the example and verify it is working as expected.

### 3.4.3

Implement a Referee class a Listener. He should react to rough tackles, print out something like “Referee gives Old Boys a yellow card for a rough tackle”. 

### 3.4.4

Create the class ‘AngryCoach’. 
When his team scores, he should cheer. When the other team makes a rough tackle, he should yell at the judge.

### 3.4.5

Create a class ScoreBoard, it should print out the current score, every time it changes.

### 3.4.6

Create a Medic class. They should react to rough tackle updates.

## 3.5 Bird watching

Write a small program to represent the bird watcher example from the slides.
Create a bird class, have it flap its wings or sing a song. Just simulate it with print outs:
“Peacock flashes its wings”
“Peacock whistles”
Create a BirdWatcher class, which will react to the birds behaviour with exclamations like: “Ooh”, “How nice”, “Would you look at that”.
Create a BlindBirdWatcher class, which can’t see anything, so he only reacts to the bird’s singing.

## 3.6 Data representation

This is for the adventurous ones, it assumes some knowledge about JavaFX.
You’re going to implement the example from the slides about visualizing data in different ways.
In JavaFX there are several components to show data, you’ll have to investigate how to use them. In the SceneBuilder they’re found under the ‘charts’ section.

![SceneBuilderChartsScreenshot](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Images/SceneBuilderChartsScreenshot.png)
 
The uploaded class DataModel has a method, which will calculate three values: red, green, yellow. The sum will always be 100. The numbers will be recalculated whenever the recalculateData method is called.
Modify the DataModel class so that is becomes a Subject: Implement the interface, create a field variable of type PropertyChangeSupport, and fire events from the recalculateData method.
Instantiate the DataModel, e.g. in a main method. Create a while(true) loop, which calls the recalculateData method, then sleeps for a short while.
Run the example, and verify the printed output is as expected.

### 3.7.1	

Create multiple Listener classes. Each class should listen for changes in the DataModel. Each class should present the data in a different way, play around with the chart components.

<blockquote>
<details>
<summary>Explanation</summary>
  <p>
   
  </p>
</details>
</blockquote>



