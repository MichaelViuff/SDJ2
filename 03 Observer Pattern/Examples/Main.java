public class Main
{
    public static void main(String[] args)
    {
        TrafficLight trafficLight = new TrafficLight();
        Car car1 = new Car(1);

        trafficLight.addCar(car1);

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
