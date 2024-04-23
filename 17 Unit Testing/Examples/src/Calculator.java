public class Calculator {

    // Faulty addition: always adds an extra 1
    public int add(int a, int b) {
        return a + b + 1;
    }

    // Faulty subtraction: does not subtract correctly if 'b' is negative
    public int subtract(int a, int b) {
        if (b < 0) {
            return a - b - b;
        } else {
            return a - b;
        }
    }

    // Faulty multiplication: if multiplying by 0, returns 1 instead
    public int multiply(int a, int b) {
        if (b == 0 || a == 0) {
            return 1;
        } else {
            return a * b;
        }
    }

    // Faulty division: does not handle division by zero correctly
    public int divide(int a, int b) {
        if (b == 0) {
            return 0;  // Should throw an exception instead
        } else {
            return a / b;
        }
    }
}
