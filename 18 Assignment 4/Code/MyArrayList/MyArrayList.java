package MyArrayList;

/**
 * ArrayList is an implementation of interface ListADT (the interface to a list
 * collection - the abstract data type List). Elements are referenced by
 * contiguous numeric indices. The list allow duplicate elements and
 * <code>null</code> elements.
 *
 * @param <T>
 *           the data type of elements in the collection
 * @author Steffen Vissing Andersen
 * @version 0.1, date: 21/03/2019
 */
public class MyArrayList<T> implements ListADT<T>
{
   private int DEFAULT_CAPACITY = 100;
   private T[] list;
   private int size;

   /**
    * A constructor creating an expandable list with an initial capacity of 100
    * elements.
    */
   @SuppressWarnings("unchecked")
   public MyArrayList()
   {
      list = (T[]) new Object[DEFAULT_CAPACITY];
      size = 0;
   }

   /**
    * Inserts the specified element at the specified index.
    *
    * @param index
    *           the index into the array to which the element is to be inserted.
    *           Legal values from 0 to the list size, both inclusive.
    * @param element
    *           the element to be inserted into the list
    * @throws IndexOutOfBoundsException
    *            if the index is out of bounds
    */
   @Override
   public void add(int index, T element)
   {
      if (index < 0 || index > size)
      {
         throw new IndexOutOfBoundsException("Index:" + index);
      }

      if (size >= list.length)
      {
         expandCapacity();
      }

      for (int i = size - 1; i >= index; i--)
      {
         list[i + 1] = list[i];
      }

      list[index] = element;
      size++;
   }

   /**
    * Adds the specified element to the rear end of this list.
    *
    * @param element
    *           the element to be added to the rear end of the list
    */
   @Override
   public void add(T element)
   {
      add(size, element);
   }

   /**
    * Sets the element at the specified index
    *
    * @param index
    *           the index into the array to which the element is to be set.
    *           Legal values from 0 to the list size-1, both inclusive.
    * @param element
    *           the element to be set into the list at <code>index</code>
    * @throws IndexOutOfBoundsException
    *            if the index is out of bounds
    */
   @Override
   public void set(int index, T element)
   {
      if (index < 0 || index > size - 1)
      {
         throw new IndexOutOfBoundsException("index:" + index);
      }
      list[index] = element;
   }

   /**
    * Returns a reference to the element at the specified index.
    *
    * @param index
    *           the index to which the reference is to be retrieved from. Legal
    *           values from 0 to the list size-1, both inclusive.
    * @return a reference to the element at the specified index
    * @throws IndexOutOfBoundsException
    *            if the index is out of bounds
    */
   @Override
   public T get(int index)
   {
      if (index < 0 || index > size - 1)
      {
         throw new IllegalStateException("index:" + index);
      }
      return list[index];
   }

   /**
    * Removes and returns the element at the specified index.
    *
    * @param index
    *           the index of the element to be removed from the list. Legal
    *           values from 0 to list size-1, both inclusive.
    * @return the removed element
    * @throws IndexOutOfBoundsException
    *            if the index is out of bounds
    */
   @Override
   public T remove(int index)
   {
      if (index < 0 || index > size - 1)
      {
         throw new IndexOutOfBoundsException("index:" + index);
      }

      T result = list[index];
      for (int i = index; i < size - 1; i++)
      {
         list[i] = list[i + 1];
      }
      size--;

      return result;
   }

   /**
    * Removes and returns the specified element from this list.
    *
    * @param element
    *           the element to be removed from the list
    * @return the removed element
    * @throws IllegalStateException
    *            if the element is not in the list
    */
   @Override
   public T remove(T element)
   {
      int index = indexOf(element);

      if (index == -1)
      {
         throw new IllegalStateException();
      }

      return remove(index);
   }

   /**
    * Returns the index of the specified element.
    *
    * @param element
    *           the element for the index is to be retrieved
    * @return the integer index for this element
    */
   @Override
   public int indexOf(T element)
   {
      for (int i = 0; i < size; i++)
      {
         if (element.equals(list[i]))
         {
            return i;
         }
      }
      return -1;
   }

   /**
    * Returns <code>true</code> if this list contains the specified target
    * element.
    *
    * @param element
    *           the element that is being sought in the list
    * @return <code>true</code> if this list contains the elements. Otherwise,
    *         <code>false</code>
    */
   @Override
   public boolean contains(T element)
   {
      return (indexOf(element) != -1);
   }

   /**
    * Returns <code>true</code> if this list contains no elements.
    *
    * @return <code>true</code> if this list contains no elements. Otherwise,
    *         <code>false</code>
    */
   @Override
   public boolean isEmpty()
   {
      return size == 0;
   }

   /**
    * Returns <code>true</code> if this list is full but because the list is
    * unbounded the method always return <code>false</code>
    *
    * @return <code>true</code> if this list is full. Otherwise,
    *         <code>false</code>
    */
   @Override
   public boolean isFull()
   {
      return true;
   }

   /**
    * Returns the number of elements in this list
    *
    * @return the integer representation of number of elements in this list
    */
   @Override
   public int size()
   {
      return size;
   }

   /**
    * A string representation of the list with elements comma separated and
    * enclosed in a set of curly braces.<br/>
    * Example (3 string elements): "{A, B, C}"<br/>
    * Example (2 Integer elements): "{7, 11}"<br/>
    * Example (an empty queue): "{}"
    *
    * @return a string representation of the list
    */
   public String toString()
   {
      String result = "{";
      for (int i = 0; i < size(); i++)
      {
         result += list[i];
         if (i != size() - 1)
         {
            result += ", ";
         }
      }
      result += "}";
      return result;
   }

   private void expandCapacity()
   {
      @SuppressWarnings("unchecked")
      T[] tmp = (T[]) new Object[list.length * 2];
      for (int i = 0; i < size; i++)
      {
         tmp[i] = list[i];
      }
      list = tmp;
   }

}
