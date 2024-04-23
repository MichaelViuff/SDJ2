import java.util.ArrayList;

public class Queue<T> implements QueueADT<T>
{
    private final int size;
    private final ArrayList<T> queue;

    public Queue(int size)
    {
        queue = new ArrayList<>(size);
        this.size = size;
    }

    @Override
    public void enqueue(T element)
    {
        if(element == null)
        {
            throw new IllegalArgumentException("Element cannot be null");
        }
        if(queue.size() == size)
        {
            throw new IllegalStateException("Queue is full");
        }
        queue.add(element);
    }

    @Override
    public T dequeue()
    {
        return null;
    }

    @Override
    public T first()
    {
        return null;
    }

    @Override
    public int indexOf(T element)
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public int size()
    {
        return queue.size();
    }

    @Override
    public boolean contains(T element)
    {
        return false;
    }
}
