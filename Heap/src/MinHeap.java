import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Kent Barber
 * @version 1.0
 * @userid kbarber9
 * @GTID 903326160
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("There is null data within the heap.");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = data.size();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("There is null data within the heap.");
            }
            backingArray[i + 1] = data.get(i);
        }

        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }
    }

    /**
     * Helper method for a min downHeap
     *
     * @param index the index of current data
     */
    private void downHeap(int index) {
        if (index > size / 2) {
            return;
        }
        int child = indexHelper(index);
        while (child >= 0) {
            if (backingArray[child].compareTo(backingArray[index]) < 0) {
                T temp = backingArray[index];
                backingArray[index] = backingArray[child];
                backingArray[child] = temp;
                index = child;
                child = indexHelper(index);
            } else {
                child = -1;
            }
        }
    }

    /**
     * Helper method to find index of child nodes
     * @param index current index of data
     * @return the index of child
     */
    private int indexHelper(int index) {
        if (index * 2 > size || index > size / 2) {
            return -4;
        } else if ((index * 2) + 1 > size) {
            return index * 2;
        } else if (backingArray[(index * 2) + 1]
                .compareTo(backingArray[index * 2]) > 0) {
            return index * 2;
        } else {
            return (index * 2) + 1;
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not add null data to the heap.");
        }
        if (size >= backingArray.length - 1) {
            grow();
        }
        size++;
        backingArray[size] = data;
        upHeap();
    }

    /**
     * Helper method in order to upHeap
     */
    private void upHeap() {
        if (size > 1) {
            int index = size;
            int parentIndex = index / 2;
            while (parentIndex > 0) {
                if (backingArray[index].compareTo(backingArray[parentIndex]) < 0) {
                    T obj = backingArray[index];
                    backingArray[index] = backingArray[parentIndex];
                    backingArray[parentIndex] = obj;
                    if (index != 1) {
                        index = parentIndex;
                        parentIndex = parentIndex / 2;
                    }
                } else {
                    parentIndex = -1;
                }
            }
        }
    }

    /**
     * Private helper method in order to double capacity of array.
     */
    private void grow() {
        T[] temp = (T[]) new Comparable[backingArray.length * 2];
        for (int i = 1; i < backingArray.length; i++) {
            temp[i] = backingArray[i];
        }
        backingArray = temp;
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        }
        int index = size;
        T data = backingArray[1];
        backingArray[1] = backingArray[index];
        backingArray[index] = null;
        index = 1;
        size--;
        downHeap(index);

        return data;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the heap.");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

}
