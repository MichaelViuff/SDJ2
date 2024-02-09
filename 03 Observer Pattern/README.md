# 03 Exercises: Observer Pattern

## 3.1 Traffic light

Run the Traffic light example that was shown in the presentation, with the classes [`Car`](https://github.com/MichaelViuff/SDJ2/blob/main/03%20Observer%20Pattern/Examples/Car.java), `TrafficLight` and `Main`. 

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

<blockquote>
<details>
<summary>Explanation</summary>
  <p>
   
  </p>
</details>
</blockquote>



