import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Hieu Nguyen
 * @version 1.0
 * @userid pnguyen337
 * @GTID 903681705
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
            throw new IllegalArgumentException("Null data collection cannot be initialized");
        }

        size = 0;
        root = null;

        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Null elements cannot be added.");
            }
            add(element);
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
            throw new IllegalArgumentException("Null data cannot be added.");
        }
        root = addHelper(data, root);
    }

    /**
     * Add method helper that recursively iterates through the tree to add data
     *
     * @param data the data to be added
     * @param curr the current node
     * @return node for pointer reinforcement
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            curr = new BSTNode<>(data);
            return curr;
        }

        int compareAdd = data.compareTo(curr.getData());
        if (compareAdd < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        } else if (compareAdd > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        }

        return curr;
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
            throw new IllegalArgumentException("Null data cannot be removed.");
        }

        BSTNode<T> removed = new BSTNode<>(null);
        root = removeHelper(data, root, removed);
        if (removed.getData() == null) {
            throw new NoSuchElementException("No data found in the tree, cannot remove.");
        }
        size--;
        return removed.getData();
    }

    /**
     * Remove method helper that recursively iterates through the tree to remove data
     * If there are 2 children to consider, it will use removeSuccessor method
     * to remove Successor
     *
     * @param data the data to be removed
     * @param curr the current node
     * @param removed the node that stores removed data
     * @return node for pointer reinforcement
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> curr, BSTNode<T> removed) {
        if (curr == null) {
            return null;
        }

        int compareRem = data.compareTo(curr.getData());
        if (compareRem < 0) {
            curr.setLeft(removeHelper(data, curr.getLeft(), removed));
        } else if (compareRem > 0) {
            curr.setRight(removeHelper(data, curr.getRight(), removed));
        } else {
            removed.setData(curr.getData());

            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> successor = new BSTNode<>(data);
                curr.setRight(removeSuccessor(curr.getRight(), successor));
                curr.setData(successor.getData());
            }
        }

        return curr;
    }

    /**
     * Helper method for removeHelper that removes the successor
     *
     * @param curr the current node
     * @param removed node that stores removed data to return
     * @return node for pointer reinforcement
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> removed) {
        if (curr.getLeft() == null) {
            removed.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), removed));
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
            throw new IllegalArgumentException("Passed-in null data cannot be retrieved.");
        }
        return getHelper(data, root);
    }

    /**
     * Recursive helper method for the get method
     *
     * @param data the data to be retrieved
     * @param curr the current node
     * @return found data in the tree
     * @throws NoSuchElementException if the data is not found in the tree
     */
    private T getHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("Null data is not found in the tree");
        }

        int comparingGet = data.compareTo(curr.getData());
        if (comparingGet < 0) {
            return getHelper(data, curr.getLeft());
        } else if (comparingGet > 0) {
            return getHelper(data, curr.getRight());
        } else {
            return curr.getData();
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
            throw new IllegalArgumentException("Null data cannot be contained.");
        }
        return containHelper(data, root);
    }

    /**
     * Recursive helper method to check if tree contains the passed-in data
     *
     * @param data data to be searched for
     * @param curr the current node
     * @return whether data is found or not
     */
    private boolean containHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        }

        int compareContain = curr.getData().compareTo(data);
        if (curr.getData().equals(data)) {
            return true;
        } else if (compareContain < 0) {
            return containHelper(data, curr.getRight());
        } else if (compareContain > 0) {
            return containHelper(data, curr.getLeft());
        }
        return true;
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
        LinkedList<T> list = new LinkedList<>();
        preHelper(list, root);
        return list;
    }

    /**
     * Recursive helper method for preorder traversal
     *
     * @param list the list storing data for preorder traversal
     * @param curr the current node
     */
    private void preHelper(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            list.add(curr.getData());
            preHelper(list, curr.getLeft());
            preHelper(list, curr.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        LinkedList<T> list = new LinkedList<>();
        inHelper(list, root);
        return list;
    }

    /**
     * Recursive helper method for inorder traversal
     *
     * @param list the list storing data for inorder traversal
     * @param curr the current node
     */
    private void inHelper(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            inHelper(list, curr.getLeft());
            list.add(curr.getData());
            inHelper(list, curr.getRight());
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
        LinkedList<T> list = new LinkedList<>();
        postHelper(list, root);
        return list;
    }

    /**
     * Recursive helper method for postorder traversal
     *
     * @param list the list storing data for postorder traversal
     * @param curr the current node
     */
    private void postHelper(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            postHelper(list, curr.getLeft());
            postHelper(list, curr.getRight());
            list.add(curr.getData());
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
        if (root == null) {
            return new LinkedList<>();
        }
        LinkedList<T> list = new LinkedList<>();
        Queue<BSTNode<T>> queueLevel = new LinkedList<>();
        queueLevel.add(root);

        while (!queueLevel.isEmpty()) {
            BSTNode<T> curr = queueLevel.remove();

            if (curr != null) {
                list.add(curr.getData());
                if (curr.getLeft() != null) {
                    queueLevel.add(curr.getLeft());
                }
                if (curr.getRight() != null) {
                    queueLevel.add(curr.getRight());
                }
            }
        }
        return list;
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
        if (root == null) {
            return -1;
        }
        return heightHelper(root);
    }

    /**
     * Helper method for finding height of root
     *
     * @param curr the current node
     * @return height int of root
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int left = heightHelper(curr.getLeft());
        int right = heightHelper(curr.getRight());

        return Math.max(left, right) + 1;
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
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Either of two data must not be null to find path");
        }

        LinkedList<T> list = new LinkedList<>();
        BSTNode<T> ancestorNode = findDCA(root, data1, data2);
        pathLeft(ancestorNode, data1, list);
        list.removeLast();
        pathRight(ancestorNode, data2, list);

        return list;
    }

    /**
     * Recursive helper method to find the deepest common ancestor of two data
     *
     * @param curr the current node
     * @param data1 the data to start the path from
     * @param data2 the data to end the path
     * @return the deepest common ancestor node of two data
     */
    private BSTNode<T> findDCA(BSTNode<T> curr, T data1, T data2) {
        if (curr == null) {
            throw new NoSuchElementException("Data does not exist, cannot be found");
        }

        int smallCompare = data1.compareTo(curr.getData());
        int largeCompare = data2.compareTo(curr.getData());


        if (smallCompare > 0 && largeCompare > 0) {
            return findDCA(curr.getRight(), data1, data2);
        } else if (smallCompare < 0 && largeCompare < 0) {
            return findDCA(curr.getLeft(), data1, data2);
        } else {
            return curr;
        }
    }

    /**
     * Recursive helper method to traverse and add left side at the front of path
     *
     * @param curr the current node
     * @param data the data to start the path from
     * @param list the list storing the path
     */
    private void pathLeft(BSTNode<T> curr, T data, LinkedList<T> list) {
        if (curr == null) {
            throw new NoSuchElementException("Data does not exist, cannot be found");
        }

        int comparing = data.compareTo(curr.getData());
        if (comparing < 0) {
            list.addFirst(curr.getData());
            pathLeft(curr.getLeft(), data, list);
        } else if (comparing > 0) {
            list.addFirst(curr.getData());
            pathLeft(curr.getRight(), data, list);
        } else {
            list.addFirst(curr.getData());
        }
    }

    /**
     * Recursive helper method to traverse and add right side to the back of the path
     *
     * @param curr the current node
     * @param data the data to end the path
     * @param list the list storing the path
     */
    private void pathRight(BSTNode<T> curr, T data, LinkedList<T> list) {
        if (curr == null) {
            throw new NoSuchElementException("Data does not exist, cannot be found");
        }

        int comparing = data.compareTo(curr.getData());
        if (comparing < 0) {
            list.addLast(curr.getData());
            pathRight(curr.getLeft(), data, list);
        } else if (comparing > 0) {
            list.addLast(curr.getData());
            pathRight(curr.getRight(), data, list);
        } else {
            list.addLast(curr.getData());
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
