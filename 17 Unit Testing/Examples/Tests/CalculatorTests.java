import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorTests {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testAdd() {
        // Expecting a fault: result should be 5, but faulty logic adds an extra 1
        assertEquals(5, calculator.add(2, 3), "Result is 5");
    }

    @Test
    public void testSubtract() {
        // Expecting a fault when b is negative: result should be 5, not 8
        assertEquals(5, calculator.subtract(2, -3), "Result is -1");
        // Correct subtraction when b is positive
        assertEquals(2, calculator.subtract(5, 3), "Result is 2");
    }

    @Test
    public void testMultiply() {
        // Expecting a fault when a or b is zero: should return 0, not 1
        assertEquals(0, calculator.multiply(5, 0), "Result is 0");
        assertEquals(0, calculator.multiply(0, 5), "Result is 0");
        // Correct multiplication otherwise
        assertEquals(15, calculator.multiply(3, 5), "Result is 15");
    }

    @Test
    public void testDivide() {
        // Expecting a fault when dividing by zero: should throw an ArithmeticException
        assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0), "Division by zero throws an exception");
        // Correct division otherwise
        assertEquals(2, calculator.divide(6, 3), "Result is 2");
    }
}
