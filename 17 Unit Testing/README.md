# 17 Exercises: Unit testing

## 17.1 Basic Assertions

**Objective**: Write a JUnit test class for a simple Java class that calculates the sum of two integers.

- **Task 1**: Create a class `Calculator` with a method `add(int a, int b)`.
- **Task 2**: Write tests using `assertEquals` to check if the `add` method returns the correct sum for at least three different pairs of integers.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3), "2 + 3 should equal 5");
        assertEquals(-1, calculator.add(-2, 1), "-2 + 1 should equal -1");
        assertEquals(0, calculator.add(0, 0), "0 + 0 should equal 0");
    }
}
```

  </details>
</blockquote>

## 17.2 Testing for Exceptions

**Objective**: Write tests that check if your method correctly throws an exception under certain conditions.

- **Task**: Modify the `Calculator` class to throw an `IllegalArgumentException` if either argument is negative. Write tests using `assertThrows` to verify this behavior.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
public class Calculator {
    public int add(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Both numbers must be non-negative");
        }
        return a + b;
    }
}
```

```java
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void testAddNegativeNumbers() {
        assertThrows(IllegalArgumentException.class, () -> calculator.add(-1, 5), "Should throw exception if first argument is negative");
        assertThrows(IllegalArgumentException.class, () -> calculator.add(5, -1), "Should throw exception if second argument is negative");
        assertThrows(IllegalArgumentException.class, () -> calculator.add(-5, -5), "Should throw exception if both arguments are negative");
    }
}
```

  </details>
</blockquote>

## 17.3 Setup and Teardown

**Objective**: Learn to use `@BeforeEach` and `@AfterEach` annotations.

- **Task**: Suppose you have a `Database` class that needs to connect to a database at the start of each test and disconnect at the end. Write dummy `connect` and `disconnect` methods and use `@BeforeEach` and `@AfterEach` to call these methods.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
public class Database {
    public void connect() {
        // Simulate database connection
        System.out.println("Database connected");
    }

    public void disconnect() {
        // Simulate disconnecting from a database
        System.out.println("Database disconnected");
    }
}
```

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
        database.connect();
    }

    @AfterEach
    public void tearDown() {
        database.disconnect();
    }

    @Test
    public void testConnection() {
        System.out.println("Testing database connection...");
        // Here you would have logic to verify the connection is active
        // This could be simulated by a boolean flag or similar mechanism
    }
}
```

  </details>
</blockquote>

## 17.4 Parameterized Tests

**Objective**: Use JUnit's parameterized test feature to test the `Calculator` method with multiple parameters.

- **Task**: Refactor your `add` method tests to use `@ParameterizedTest` with `@ValueSource` or `@CsvSource` to supply multiple sets of numbers and expected results.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private Calculator calculator = new Calculator();

    @ParameterizedTest
    @CsvSource({
        "0, 0, 0",
        "1, 2, 3",
        "-1, -2, -3",
        "Integer.MAX_VALUE, 0, Integer.MAX_VALUE",
        "Integer.MIN_VALUE, 0, Integer.MIN_VALUE"
    })
    public void testAdd(int a, int b, int expectedResult) {
        assertEquals(expectedResult, calculator.add(a, b), "Sum should be correct");
    }
}
```

  </details>
</blockquote>

## 17.5 Mocking Dependencies

**Objective**: Write a test for a method that depends on an external service by using mocking.

- **Task**: Assume you have a `WeatherService` interface that retrieves temperature. Create a `WeatherReporter` class that uses `WeatherService` to report whether it's freezing. Use Mockito to mock `WeatherService` and write tests to check `WeatherReporter` behavior in various conditions.

For this exercise, we'll look at how to write a unit test using mocking for a class that depends on an external service. We'll use [Mockito](https://site.mockito.org/) to handle the mocking.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
public interface WeatherService {
    int getCurrentTemperature();
}

public class WeatherReporter {
    private WeatherService weatherService;

    public WeatherReporter(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public String reportWeather() {
        int temperature = weatherService.getCurrentTemperature();
        if (temperature <= 0) {
            return "It's freezing!";
        } else {
            return "It's warm!";
        }
    }
}
```

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

public class WeatherReporterTest {
    @Test
    public void testReportWeatherFreezing() {
        WeatherService mockService = mock(WeatherService.class);
        when(mockService.getCurrentTemperature()).thenReturn(0);

        WeatherReporter reporter = new WeatherReporter(mockService);
        assertEquals("It's freezing!", reporter.reportWeather(), "Should report freezing at 0 degrees");
    }

    @Test
    public void testReportWeatherWarm() {
        WeatherService mockService = mock(WeatherService.class);
        when(mockService.getCurrentTemperature()).thenReturn(25);

        WeatherReporter reporter = new WeatherReporter(mockService);
        assertEquals("It's warm!", reporter.reportWeather(), "Should report warm at 25 degrees");
    }
}
```

  </details>
</blockquote>

## 17.6 Integration Test

**Objective**: Write a simple integration test that involves more than one class interacting.

- **Task**: Create a small application that involves a `UserRepository` and a `UserService` class. `UserService` should have a method to add a user if not already present. Write a test that checks the integration between `UserService` and `UserRepository`.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
import java.util.HashSet;
import java.util.Set;

public class UserRepository {
    private Set<String> users = new HashSet<>();

    public boolean addUser(String username) {
        return users.add(username);
    }

    public boolean exists(String username) {
        return users.contains(username);
    }
}
```

```java
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUserIfNotPresent(String username) {
        if (!userRepository.exists(username)) {
            return userRepository.addUser(username);
        }
        return false;
    }
}
```

```java
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    @Test
    public void testAddUser() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        // Test adding a new user
        boolean resultAdd = userService.addUserIfNotPresent("john_doe");
        assertTrue(resultAdd, "User should be successfully added.");

        // Test adding the same user again
        boolean resultAddAgain = userService.addUserIfNotPresent("john_doe");
        assertFalse(resultAddAgain, "User should not be added again.");
    }
}
```

  </details>
</blockquote>

## 17.7 Advanced Mocking Techniques

**Objective**: Explore advanced features of mocking, like spying.

- **Task**: Using the same `WeatherReporter` and `WeatherService`, write a test where you use a spy to partially mock `WeatherService`, allowing you to control the output of one method while keeping the original behavior of others.

<blockquote>
  <details>
    <summary>Display solution...</summary>

```java
public interface WeatherService {
    int getCurrentTemperature();
}

public class WeatherReporter {
    private WeatherService weatherService;

    public WeatherReporter(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public String reportWeather() {
        int temperature = weatherService.getCurrentTemperature();
        if (temperature <= 0) {
            return "It's freezing!";
        } else {
            return "It's warm!";
        }
    }
}
```

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

public class WeatherReporterTest {

    @Test
    public void testReportWeatherFreezingWithSpy() {
        WeatherService realService = new WeatherService() {
            @Override
            public int getCurrentTemperature() {
                return 10;  // Default real implementation
            }
        };
        WeatherService spiedService = spy(realService);

        // Forcing the spy to return 0 when getCurrentTemperature is called
        when(spiedService.getCurrentTemperature()).thenReturn(0);

        WeatherReporter reporter = new WeatherReporter(spiedService);
        assertEquals("It's freezing!", reporter.reportWeather(), "Should report freezing at 0 degrees, despite real implementation");
    }
}
```

  </details>
</blockquote>
