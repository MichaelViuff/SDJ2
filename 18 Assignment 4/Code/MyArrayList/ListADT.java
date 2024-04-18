package MyArrayList;

/**
 * ListADT defines the interface to a list collection - the abstract data type
 * List. Elements are referenced by contiguous numeric indices. The list should
 * allow duplicate elements and could allow <code>null</code> elements.
 *
 * @param <T> the data type of elements in the collection
 * @author Steffen Vissing Andersen
 * @version (Version 1.0, 8 / 12 / 2008 by Lewis and Chase)
 */
public interface ListADT<T>
{
  /**
   * Inserts the specified element at the specified index.
   *
   * @param index   the index into the array to which the element is to be inserted.
   *                Legal values from 0 to the list size, both inclusive.
   * @param element the element to be inserted into the list
   * @throws IllegalStateException     if the list is full and trying to insert at the end
   * @throws IndexOutOfBoundsException if the index is out of bounds
   * @throws IllegalArgumentException  if there is a mismatch in the input, e.g. if a null element
   *                                   is not allowed
   */
  public void add(int index, T element);

  /**
   * Adds the specified element to the rear end of this list.
   *
   * @param element the element to be added to the rear end of the list
   * @throws IllegalStateException    if the list is full
   * @throws IllegalArgumentException if there is a mismatch in the input, e.g. if a
   *                                  <code>null</code> element is not allowed
   */
  public void add(T element);

  /**
   * Sets the element at the specified index
   *
   * @param index   the index into the array to which the element is to be set.
   *                Legal values from 0 to the list size-1, both inclusive.
   * @param element the element to be set into the list at <code>index</code>
   * @throws IndexOutOfBoundsException if the index is out of bounds
   * @throws IllegalArgumentException  if there is a mismatch in the input, e.g. if a
   *                                   <code>null</code> element is not allowed
   */
  public void set(int index, T element);

  /**
   * Returns a reference to the element at the specified index.
   *
   * @param index the index to which the reference is to be retrieved from. Legal
   *              values from 0 to the list size-1, both inclusive.
   * @return a reference to the element at the specified index
   * @throws IndexOutOfBoundsException if the index is out of bounds
   */
  public T get(int index);

  /**
   * Removes and returns the element at the specified index.
   *
   * @param index the index of the element to be removed from the list. Legal
   *              values from 0 to list size-1, both inclusive.
   * @return the removed element
   * @throws IndexOutOfBoundsException if the index is out of bounds
   */
  public T remove(int index);

  /**
   * Removes and returns the specified element from this list.
   *
   * @param element the element to be removed from the list
   * @return the removed element
   * @throws IllegalStateException if the element is not in the list
   */
  public T remove(T element);

  /**
   * Returns the index of the specified element.
   *
   * @param element the element for the index is to be retrieved
   * @return the integer index for this element
   */
  public int indexOf(T element);

  /**
   * Returns <code>true</code> if this list contains the specified target element.
   *
   * @param element the element that is being sought in the list
   * @return <code>true</code> if this list contains the elements. Otherwise, <code>false</code>
   */
  public boolean contains(T element);

  /**
   * Returns <code>true</code> if this list contains no elements.
   *
   * @return <code>true</code> if this list contains no elements. Otherwise, <code>false</code>
   */
  public boolean isEmpty();

  /**
   * Returns <code>true</code> if this list is full. Unbounded implementation always
   * return <code>true</code>
   *
   * @return <code>true</code> if this list is full. Otherwise, <code>false</code>
   */
  public boolean isFull();

  /**
   * Returns the number of elements in this list
   *
   * @return the integer representation of number of elements in this list
   */
  public int size();
}
