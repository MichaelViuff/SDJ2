import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class QueueTests
{
private Queue<Integer> queue;

    @BeforeEach
    public void setUp()
    {
        queue = new Queue<>(10);
    }

    @Test
    public void testEnqueue()
    {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(3, queue.size(), "Queue size is 3"); //Notice dependency on size() method
    }

    @Test
    public void testEnqueueThrowsIllegalArgumentExceptionIfElementIsNull()
    {
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null), "Enqueueing null element should throw IllegalArgumentException");
    }

    @Test
    public void testEnqueueThrowsIllegalStateExceptionIfCapacityIsReached()
    {
        for(int i = 0; i < 10; i++)
        {
            queue.enqueue(i);
        }
        assertThrows(IllegalStateException.class, () -> queue.enqueue(10), "Enqueueing when capacity is reached should throw IllegalStateException");
    }

}
