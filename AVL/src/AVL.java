import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        for (T e : data) {
            if (e == null) {
                throw new IllegalArgumentException("Can not add null data to the AVL.");
            }
            add(e);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not add null data to the AVL.");
        }
        if (size == 0) {
            root = new AVLNode<T>(data);
            size++;
        } else {
            addHelper(root, data);
        }
    }

    /**
     * This is a helper method for add
     * @param data data passed in
     * @param curr the current node
     *
     */
    private void addHelper(AVLNode<T> curr, T data) {
        if (curr.getData().compareTo(data) == 0) {
            return;
        } else if (curr.getData().compareTo(data) > 0) {
            if (curr.getLeft() == null) {
                curr.setLeft(new AVLNode<T>(data));
                size++;
            } else {
                addHelper(curr.getLeft(), data);
            }
        } else {
            if (curr.getRight() == null) {
                curr.setRight(new AVLNode<T>(data));
                size++;
            } else {
                addHelper(curr.getRight(), data);
            }
        }
        heightHelper(curr);
        balanceHelper(curr);
        rebalance(curr);
    }

    /**
     * Helper method in order to adjust height of AVL
     * @param curr the node passed through
     */
    private void heightHelper(AVLNode<T> curr) {
        if (curr != null) {
            int leftH = 0;
            int rightH = 0;
            if (curr.getLeft() != null) {
                leftH = curr.getLeft().getHeight();
            }

            if (curr.getRight() != null) {
                rightH = curr.getRight().getHeight();
            }

            if (curr.getLeft() == null && curr.getRight() == null) {
                curr.setHeight(0);
            } else {
                curr.setHeight(1 + Math.max(leftH, rightH));
            }
        }
    }

    /**
     * Helper method in order to adjust Balance Factor in AVL
     * @param curr the node passed through
     */
    private void balanceHelper(AVLNode<T> curr) {
        if (curr != null) {
            int leftH = 0;
            int rightH = 0;

            if (curr.getLeft() != null) {
                leftH = curr.getLeft().getHeight();
                if (curr.getRight() == null) {
                    rightH = -1;
                }
            }

            if (curr.getRight() != null) {
                rightH = curr.getRight().getHeight();
                if (curr.getLeft() == null) {
                    leftH = -1;
                }
            }
            curr.setBalanceFactor(leftH - rightH);
        }
    }

    /**
     * Helper method to do a right rotation
     * @param currNode the node passed through
     */
    private void rightRotation(AVLNode<T> currNode) {
        AVLNode<T> middleChild = currNode.getLeft().getRight();
        AVLNode<T> middleNode  = currNode.getLeft();
        AVLNode<T> root = new AVLNode<T>(currNode.getData());
        root.setRight(currNode.getRight());
        root.setLeft(middleChild);
        heightHelper(root);
        balanceHelper(root);
        middleNode.setRight(root);
        heightHelper(middleNode);
        balanceHelper(middleNode);

        currNode.setData(middleNode.getData());
        currNode.setLeft(middleNode.getLeft());
        currNode.setRight(middleNode.getRight());
        currNode.setHeight(middleNode.getHeight());
        currNode.setBalanceFactor(middleNode.getBalanceFactor());
    }

    /**
     * Helper method to do a left rotation
     * @param currNode the node passed through
     */
    private void leftRotation(AVLNode<T> currNode) {
        AVLNode<T> middleChild = currNode.getRight().getLeft();
        AVLNode<T> middleNode  = currNode.getRight();
        AVLNode<T> root = new AVLNode<T>(currNode.getData());
        root.setLeft(currNode.getLeft());

        root.setRight(middleChild);
        balanceHelper(root);
        heightHelper(root);

        middleNode.setLeft(root);
        balanceHelper(middleNode);
        heightHelper(middleNode);


        currNode.setData(middleNode.getData());
        currNode.setLeft(middleNode.getLeft());
        currNode.setRight(middleNode.getRight());
        currNode.setHeight(middleNode.getHeight());
        currNode.setBalanceFactor(middleNode.getBalanceFactor());
    }

    /**
     * Helper method in order to rebalance the AVL
     * @param currNode the node passed through
     */
    private void rebalance(AVLNode<T> currNode) {
        if (currNode != null) {
            if (currNode.getBalanceFactor() == -2) {
                if (currNode.getRight().getBalanceFactor() == -1
                        || currNode.getRight().getBalanceFactor() == 0) {
                    leftRotation(currNode);

                } else if (currNode.getRight().getBalanceFactor() == 1) {
                    rightRotation(currNode.getRight());
                    leftRotation(currNode);
                }
            } else if (currNode.getBalanceFactor() == 2) {
                if (currNode.getLeft().getBalanceFactor() == 1
                        || currNode.getLeft().getBalanceFactor() == 0) {

                    rightRotation(currNode);
                } else if (currNode.getLeft().getBalanceFactor() == -1) {

                    leftRotation(currNode.getLeft());
                    rightRotation(currNode);
                }
            }
        }
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        AVLNode<T> dummyNode = new AVLNode<>(null);
        if (data == null) {
            throw new IllegalArgumentException("The given data is null.");
        } else {
            root = removeHelper(data, root, dummyNode, null);
        }
        if (dummyNode.getData() == null) {
            throw new NoSuchElementException("The data was not found in the AVL.");
        } else {
            size--;
        }
        return dummyNode.getData();
    }

    /**
     * Helper method for the remove method
     * @param data data passed in
     * @param node node passed in
     * @param dummyNode dummy node
     * @param predecessor predecessor of node
     * @return node
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> dummyNode, AVLNode<T> predecessor) {
        if (node == null) {
            throw new NoSuchElementException("The data is not contained within the AVL.");
        }
        if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(data, node.getRight(), dummyNode, predecessor));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), dummyNode, predecessor));
        } else if (node.getData().equals(data)) {
            if (predecessor == null) {
                dummyNode.setData(node.getData());
            }
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                node.setData(predHelper(node.getLeft()));
                predecessor = new AVLNode<>(null);
                predecessor.setData(node.getData());
                node.setLeft(removeHelper(node.getData(),
                        node.getLeft(), dummyNode, predecessor));
            }
        }
        heightHelper(node);
        balanceHelper(node);
        rebalance(node);

        return node;
    }

    /**
     * Helper method to find predecessor
     * @param curr the node passed through
     * @return data
     */
    private T predHelper(AVLNode<T> curr) {
        if (curr.getRight() != null) {
            return predHelper(curr.getRight());
        } else {
            return curr.getData();
        }
    }


    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        return getHelper(root, data);
    }

    /**
     * Helper method for get method
     * @param node the node passed through
     * @param data the data passed through
     * @return data
     */
    private T getHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The element is not in the AVL.");
        } else if (node.getData().compareTo(data) == 0) {
            return node.getData();
        } else if (node.getData().compareTo(data) > 0) {
            return getHelper(node.getLeft(), data);
        } else {
            return getHelper(node.getRight(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not look for null data.");
        }
        return containsHelper(root, data);
    }

    /**
     * Helper method for contain method
     * @param node the node passed through
     * @param data the data passed through
     * @return if data is found
     *
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        } else if (node.getData().equals(data)) {
            return true;
        } else if (node.getData().compareTo(data) > 0) {
            return containsHelper(node.getLeft(), data);
        } else {
            return containsHelper(node.getRight(), data);
        }
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find a path of letters in the tree that spell out a particular word,
     * if the path exists.
     *
     * Ex: Given the following AVL
     *
     *                   g
     *                 /   \
     *                e     i
     *               / \   / \
     *              b   f h   n
     *             / \         \
     *            a   c         u
     *
     * wordSearch([b, e, g, i, n]) returns the list [b, e, g, i, n],
     * where each letter in the returned list is from the tree and not from
     * the word array.
     *
     * wordSearch([h, i]) returns the list [h, i], where each letter in the
     * returned list is from the tree and not from the word array.
     *
     * wordSearch([a]) returns the list [a].
     *
     * wordSearch([]) returns an empty list [].
     *
     * wordSearch([h, u, g, e]) throws NoSuchElementException. Although all
     * 4 letters exist in the tree, there is no path that spells 'huge'.
     * The closest we can get is 'hige'.
     *
     * To do this, you must first find the deepest common ancestor of the
     * first and last letter in the word. Then traverse to the first letter
     * while adding letters on the path to the list while preserving the order
     * they appear in the word (consider adding to the front of the list).
     * Finally, traverse to the last letter while adding its ancestor letters to
     * the back of the list. Please note that there is no relationship between
     * the first and last letters, in that they may not belong to the same
     * branch. You will most likely have to split off to traverse the tree when
     * searching for the first and last letters.
     *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you may have to add to the front and
     * back of the list.
     *
     * You will only need to traverse to the deepest common ancestor once.
     * From that node, go to the first and last letter of the word in one
     * traversal each. Failure to do so will result in a efficiency penalty.
     * Validating the path against the word array efficiently after traversing
     * the tree will NOT result in an efficiency penalty.
     *
     * If there exists a path between the first and last letters of the word,
     * there will only be 1 valid path.
     *
     * You may assume that the word will not contain duplicate letters.
     *
     * WARNING: Do not return letters from the passed-in word array!
     * If a path exists, the letters should be retrieved from the tree.
     * Returning any letter from the word array will result in a penalty!
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @return list containing the path of letters in the tree that spell out
     * the word, if such a path exists. Order matters! The ordering of the
     * letters in the returned list should match that of the word array.
     * @throws java.lang.IllegalArgumentException if the word array is null
     * @throws java.util.NoSuchElementException if the path is not in the tree
     */
    public List<T> wordSearch(T[] word) {
        List<T> list = new LinkedList<>();
        if (word == null) {
            throw new IllegalArgumentException("There is null data in the array");
        }
        return list;
    }

    /**
     * Helper method for wordSearch
     *
     * started late :(
     */
    private void wordSearchHelper() {
        
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

   
}