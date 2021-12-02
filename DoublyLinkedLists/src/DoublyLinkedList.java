import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;
    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @throws IllegalArgumentException  if data is null
     */

    public void addAtIndex(int index, T data) {
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The given index is outside the bounds of the list.");
        } else if (data == null) {
            throw new IllegalArgumentException("You can not add null data to the list");
        }
        if (index == 0) {
            newNode.setNext(head);

            if (head != null) {
                head.setPrevious(newNode);
            }
            head = newNode;
        } else {
            DoublyLinkedListNode<T> beforeNode;

            if (index < size / 2) {
                beforeNode = head;

                for (int i = 1; i < index; ++i) {
                    beforeNode = beforeNode.getNext();
                }
            } else {
                beforeNode = tail;
                for (int i = size - index - 1; i > 0; --i) {
                    beforeNode = beforeNode.getPrevious();
                }
            }

            if (beforeNode.getNext() != null) {
                beforeNode.getNext().setPrevious(newNode);
                // beforeNode.next.prev = newNode;
                newNode.setNext(beforeNode.getNext());
                beforeNode.setNext(newNode);
                newNode.setPrevious(beforeNode);
            } else {
                beforeNode.setNext(newNode);
                newNode.setPrevious(beforeNode);
                tail = newNode;
            }
        }
        if (size == 0) {
            tail = newNode;
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data to the list");
        } else if (head == null) {
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<T>(data);
            head = temp;
            tail = head;
        } else {
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<T>(data);
            head.setPrevious(temp);
            temp.setNext(head);
            head = temp;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data to the list");
        }
        DoublyLinkedListNode<T> temporary = new DoublyLinkedListNode<>(data);
        if (head == null) {
            head = temporary;
            tail = head;
        } else {
            tail.setNext(temporary);
            temporary.setPrevious(tail);
            tail = temporary;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        DoublyLinkedListNode<T> target = nodeAtIndex(index);
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The given index is outside the bounds of the list.");
        }
        if (target.getPrevious() != null) {
            target.getPrevious().setNext(target.getNext());
        } else {
            head = target.getNext();
        }
        if (target.getNext() != null) {
            target.getNext().setPrevious(target.getPrevious());
        } else {
            tail = target.getPrevious();
        }
        size--;
        return target.getData();
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        DoublyLinkedListNode<T> first = head;
        if (head != null) {
            DoublyLinkedListNode<T> next = head.getNext();
            if (next != null) {
                next.setPrevious(null);
            }
            head = null;
            head = next;
            size--;
        } else {
            throw new NoSuchElementException();
        }
        return first.getData();
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        DoublyLinkedListNode<T> last = tail;
        if (head == null) {
            throw new NoSuchElementException("There is nothing to remove since the list is empty.");
        }
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            DoublyLinkedListNode<T> current = head;
            while (current.getNext() != tail) {
                current = current.getNext();
            }
            current.setNext(null);
            tail = current;
        }
        size--;
        return last.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The given index is outside the bounds of the list.");
        } else {
            DoublyLinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0 && head == null && tail == null) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        DoublyLinkedListNode<T> current = tail;
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data to the list.");
        } else if (current == null) {
            throw new NoSuchElementException();
        }
        if (data != null) {
            int i = size - 1;
            boolean found = false;
            int count = 0;
            int indexfound = 0;

            while (current.getPrevious() != null && !current.getData().equals(data)) {

                current = current.getPrevious();
                if (current.getData().equals(data)) {
                    indexfound = (i - 1);
                }
                i--;
            }
            removeAtIndex(indexfound);
            return current.getData();
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] obj = (T[]) new Object[size];
        DoublyLinkedListNode<T> current = head;
        int i = 0;
        while (current != null) {
            obj[i] = current.getData();
            current = current.getNext();
            i++;
        }
        return obj;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Private helper method in order to find node at particular index
     *
     * @param index the index of the node
     * @return node at index
     */
    private DoublyLinkedListNode<T> nodeAtIndex(int index) {
        DoublyLinkedListNode<T> targetNode;
        // Find the node to remove:
        if (index < size / 2) {
            targetNode = head;
            for (int i = 0; i < index; ++i) {
                targetNode = targetNode.getNext();
            }
        } else {
            targetNode = tail;
            for (int i = size - 1; i > index; --i) {
                targetNode = targetNode.getPrevious();
            }
        }
        return targetNode;
    }
}
