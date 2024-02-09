public class Main
{
    public static void main(String[] args)
    {
        TrafficLight trafficLight = new TrafficLight();
        Car car1 = new Car(1);
        Car car2 = new Car(2);
        Car car3 = new Car(3);

        trafficLight.addCar(car1);
        trafficLight.addCar(car2);
        trafficLight.addCar(car3);

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
