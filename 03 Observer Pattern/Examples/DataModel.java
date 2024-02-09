package datavisualization;

import java.util.Random;

public class DataModel {

    private int red, green, yellow;

    private Random random = new Random();

    public DataModel() {
    }

    public void recalculateData() {
        int first = random.nextInt(100)+1;
        int second = random.nextInt(100)+1;
        int bottom = Math.min(first, second);
        int top = Math.max(first, second);

        red = bottom;
        green = top - bottom;
        yellow = 100 - top;
        System.out.println("red: " + red);
        System.out.println("green: " + green);
        System.out.println("yellow: " + yellow);
        System.out.println("Sum: " + (red + green + yellow));
    }
}
