import java.util.ArrayList;
import java.util.List;

public class TrafficLight
{
    private List<Car> cars;
    private String[] lights = {"GREEN", "YELLOW", "RED", "YELLOW"};
    private int count = 2;
    private String currentLight;

    public TrafficLight()
    {
        currentLight = lights[2];
        cars = new ArrayList<>();
    }

    public void addCar(Car car)
    {
        cars.add(car);
        car.setLight(currentLight);
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
    }
}
