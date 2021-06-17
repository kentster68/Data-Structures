import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("There can not be any null data here.");
        }
        for (T in : data) {
            add(in);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not add null data to the BST.");
        }
        root = addHelper(data, root);
    }

    /**
     * This is a helper method for add
     * @param data data passed in
     * @param curr the current node
     * @return node
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (curr.getData().compareTo(data) == 0) {
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
            return  curr;
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(addHelper(data, curr.getRight()));
            return curr;
        }
        return null;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        BSTNode<T> node = new BSTNode<>(null);
        root = removeHelper(root, node, data);

        if (node.getData() == null) {
            throw new NoSuchElementException("The data is not located within the BST.");
        }

        return node.getData();
    }

    /**
     * This is a helper method for removing
     * @param curr current node of path
     * @param temp the node to replace
     * @param data data to remove
     * @return the current node
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, BSTNode<T> temp, T data) {
        if (curr == null) {
            return curr;
        } else if (curr.getData().compareTo(data) == 0) {
            size--;
            temp.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> rep = new BSTNode<>(null);
                curr.setRight(successorHelper(curr.getRight(), rep));
                rep.setLeft(curr.getLeft());
                rep.setRight(curr.getRight());
                return rep;
            }
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(removeHelper(curr.getLeft(), temp, data));
            return curr;
        } else {
            curr.setRight(removeHelper(curr.getRight(), temp, data));
            return curr;
        }
    }

    /**
     * This is a helper method for finding successor
     * @param curr the current node
     * @param rep the node to replace
     * @return the current node
     */
    private BSTNode<T> successorHelper(BSTNode<T> curr, BSTNode<T> rep) {
        if (curr.getLeft() ==  null) {
            rep.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(successorHelper(curr.getLeft(), rep));
            return curr;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
     * This is a helper method for the get method
     * @param node the current node
     * @param data data to look for
     * @return node to link
     */
    private T getHelper(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The element is not in the BST.");
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
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
     * This is a contains helper method
     * @param node the current node
     * @param data the data to search for
     * @return if data is found
     */
    private boolean containsHelper(BSTNode<T> node, T data) {
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> pre = new ArrayList<>();
        preorderHelper(root, pre);

        return pre;
    }

    /**
     * Helper method in order to do pre-order traversal
     *
     * @param node the node to start
     * @param list the list to add nodes to
     */

    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree-=9
     */
    public List<T> inorder() {
        List<T> in = new ArrayList<>();
        inorderHelper(root, in);

        return in;
    }

    /**
     * Helper method to do inorder traversal
     *
     * @param node the node to start
     * @param list list to add nodes to
     */

    private void inorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> post = new ArrayList<>();
        postorderHelper(root, post);

        return post;
    }

    /**
     * Helper method in order to do postorder traversal
     *
     * @param node the node to start
     * @param list list to add nodes to
     */

    private void postorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorderHelper(node.getLeft(), list);
            postorderHelper(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> ret = new ArrayList<>();
        LinkedList<BSTNode<T>> que = new LinkedList<>();

        que.add(root);
        while (!que.isEmpty()) {
            BSTNode<T> cur = que.removeFirst();
            if (cur != null) {
                ret.add(cur.getData());
                que.add(cur.getLeft());
                que.add(cur.getRight());
            }
        }
        return ret;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return heightHelper(root);
    }

    /**
     *
     * @param node current node
     * @return height of node
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else if (size == 1) {
            return 0;
        } else {
            return Math.max(heightHelper(node.getLeft()), heightHelper(node.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("There are not enough elements to display.");
        }
        List<T> list = new LinkedList<>();

        kLargestHelper(root, (LinkedList<T>) list, k);

        return list;
    }

    /**
     * This is a helper method for finding kLargest
     * @param node the current node
     * @param list list to add to
     * @param k the inputted int
     */
    private void kLargestHelper(BSTNode<T> node, LinkedList<T> list, int k) {
        int s = 0;
        if (k == 0 || node == null) {
            return;
        }
        if (k == size) {
            kLargestHelper(node.getRight(), list, k);
            list.addFirst(node.getData());
            kLargestHelper(node.getLeft(), list, k);
        } else {
            if (k > s) {
                kLargestHelper(node.getRight(), list, k);
                list.addLast(node.getData());
            }
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
