import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
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
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not add null data into the deque.");
        } else {
            if (size == 0) {
                LinkedNode<T> temp = new LinkedNode<T>(data);
                head = temp;
                tail = head;
                size++;
            } else {
                LinkedNode<T> temp = new LinkedNode<T>(data);
                head.setPrevious(temp);
                temp.setNext(head);
                head = temp;
                size++;
            }
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not add null data to the deque.");
        } else {
            LinkedNode<T> temp = new LinkedNode<T>(data);
            if (size == 0) {
                head = temp;
                tail = head;
                size++;
            } else {
                tail.setNext(temp);
                temp.setPrevious(tail);
                tail = temp;
                size++;
            }
        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0 && head == null && tail == null) {
            throw new NoSuchElementException("The deque is empty so there is nothing to get.");
        } else {
            LinkedNode<T> first = head;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                LinkedNode<T> curr = head.getNext();
                curr.setPrevious(null);
                head = curr;
            }
            size--;
            return first.getData();
        }

    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        LinkedNode<T> temp = tail;
        if (size == 0 && head == null && tail == null) {
            throw new NoSuchElementException("The deque is empty so there is nothing to get.");
        }
        LinkedNode<T> previous = null;
        LinkedNode<T> current = head;

        while (current.getNext() != null) {
            previous = current;
            current = current.getNext();
        }

        LinkedNode<T> result = tail;
        tail = previous;
        if (tail == null) {
            head = null;
        } else {
            tail.setNext(null);
        }
        size--;

        return result.getData();
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0 && head == null && tail == null) {
            throw new NoSuchElementException("The deque is empty so there is nothing to get.");
        }
        return head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0 && head == null && tail == null) {
            throw new NoSuchElementException("The deque is empty so there is nothing to get.");
        }
        return tail.getData();
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
