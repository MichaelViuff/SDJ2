public class Test
{
  public static void main(String[] args)
  {
    TrafficLight trafficLight = new TrafficLight();
    
    Car car1 = new Car(1, trafficLight);
    Car car2 = new Car(2, trafficLight);
    
    trafficLight.addPropertyChangeListener(car1);
    trafficLight.addPropertyChangeListener(car2);

    trafficLight.setColor("GREEN");
    trafficLight.setColor("YELLOW");
    trafficLight.setColor("RED");
    trafficLight.setColor("YELLOW");
    trafficLight.setColor("GREEN");
  }
}
