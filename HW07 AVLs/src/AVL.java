import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
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
            throw new IllegalArgumentException("Null data or element cannot be put into AVL.");
        }
        size = 0;
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Null data or element cannot be put into AVL.");
            } else {
                add(item);
            }
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
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added.");
        }
        root = addHelper(root, data);
    }

    /**
     * Helper method to add data to AVL tree.
     * The method will check duplicate, search for correct spot to add data,
     * call corresponding rotations to self-balance itself, and update height.
     *
     * @param node the node to compare data to
     * @param data the data to be added
     * @return the node representing the recursive state of the new tree
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        } else {
            return node; //if found duplicate, do nothing
        }

        updateHeightAndBalance(node);
        return balancing(node); // Rotate to balance AVL tree
    }

    /**
     * Helper method to update height and balance after each addition,
     * rotation, or removal
     *
     * @param node the node that needs to be balanced.
     */
    private void updateHeightAndBalance(AVLNode<T> node) {
        int leftHeight = -1;
        int rightHeight = -1;

        if (node.getLeft() != null) {
            leftHeight = node.getLeft().getHeight();
        }

        if (node.getRight() != null) {
            rightHeight = node.getRight().getHeight();
        }

        node.setHeight(1 + Math.max(leftHeight, rightHeight));
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Helper method that gets balance factor and balances the AVL tree.
     *
     * @param node node to check.
     * @return node and its subtree.
     */
    private AVLNode<T> balancing(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                // Right-Left rotation
                node.setRight(rightRotate(node.getRight()));
            }
            // Left rotation
            return leftRotate(node);
        } else if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                // Left-Right rotation
                node.setLeft(leftRotate(node.getLeft()));
            }
            // Right rotation
            return rightRotate(node);
        }
        return node;
    }


    /**
     * Helper method that rotates node to the left.
     *
     * @param node the node that needs rotating
     * @return node and its subtree
     */
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);

        updateHeightAndBalance(node);
        updateHeightAndBalance(newRoot);

        return newRoot;
    }

    /**
     * Helper method that rotates node to the right.
     *
     * @param node the node that needs rotating
     * @return node and its subtree
     */
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);

        updateHeightAndBalance(node);
        updateHeightAndBalance(newRoot);

        return newRoot;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
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
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(root, data, removed);

        if (removed.getData() == null) {
            throw new NoSuchElementException("No data found in the tree, cannot remove.");
        }

        size--;
        return removed.getData();
    }

    /**
     * Helper method to remove node from the AVL tree.
     *
     * @param node the current node we are on
     * @param data the data to be removed
     * @return node with pointer reinforcement
     */
    private AVLNode<T> removeHelper(AVLNode<T> node, T data, AVLNode<T> removed) {
        if (node == null) {
            return null; // Data not found.
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(node.getLeft(), data, removed));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(node.getRight(), data, removed));
        } else {
            removed.setData(node.getData());

            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> dummy = new AVLNode<>(data);
                node.setLeft(removePredecessor(node.getLeft(), dummy));
                node.setData(dummy.getData());
            }
        }

        updateHeightAndBalance(node);
        return balancing(node);
    }

    /**
     * Remove helper method to find predecessor of a given node and remove it.
     *
     * @param node the node to find predecessor of
     * @return the predecessor data
     */
    private AVLNode<T> removePredecessor(AVLNode<T> node, AVLNode<T> removed) {
        if (node.getRight() == null) {
            removed.setData(node.getData());
            return node.getLeft();
        }
        node.setRight(removePredecessor(node.getRight(), removed));
        updateHeightAndBalance(node);
        return balancing(node);
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
            throw new IllegalArgumentException("Cannot obtain a null data from the tree");
        }
        return getHelper(root, data);
    }

    /**
     * Helper method to get data in the AVL tree.
     *
     * @param data the data to get from tree
     * @param node the node to compare data to
     * @return the data that is found
     */
    private T getHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("Data not found in the tree");
        }

        if (data.compareTo(node.getData()) < 0) {
            return getHelper(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return getHelper(node.getRight(), data);
        } else {
            return node.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or re3ference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for a null element.");
        }
        return containsHelper(root, data);
    }

    /**
     * Helper method to validate if a data is in the AVL
     *
     * @param data the data being validated in the AVL
     * @param node the node being compared to data
     * @return boolean of whether data is in the AVL
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        }

        if (data.compareTo(node.getData()) < 0) {
            return containsHelper(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return containsHelper(node.getRight(), data);
        }

        return true;
    }


    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be in the tree.");
        }
        AVLNode<T> lastSavedNode = new AVLNode<>(null);
        AVLNode<T> result = predecessor(root, data, lastSavedNode);

        if (result == null) {
            throw new NoSuchElementException("Data is not found in the tree.");
        }

        return result.getData();
    }

    /**
     * Helper method to find predecessor of a given data.
     *
     * @param node the current node we are on
     * @param data the data to find predecessor of
     * @param dummy the node to save predecessor in case the predecessor is
     *              above the location of the node with given data
     * @return the predecessor of the data
     */
    private AVLNode<T> predecessor(AVLNode<T> node, T data, AVLNode<T> dummy) {
        if (node == null) {
            return null; // Either no smaller data found in the tree or the data does not exist
        }

        //Search for data to check predecessor
        if (data.compareTo(node.getData()) < 0) {
            return predecessor(node.getLeft(), data, dummy);
        } else if (data.compareTo(node.getData()) > 0) {
            dummy = node;
            return predecessor(node.getRight(), data, dummy);
        } else {
            // If data is found, check if left subtree is null or not
            if (node.getLeft() == null) {
                // If left subtree is null, return last saved dummy node
                return dummy;
            } else {
                // If left subtree exists, do default inorder predecessor method
                AVLNode<T> pre = new AVLNode<>(findPredecessor(node.getLeft()));
                return pre;
            }
        }
    }

    /**
     * Helper method to find predecessor value of a given node.
     *
     * @param node the node to find predecessor of
     * @return the predecessor data
     */
    private T findPredecessor(AVLNode<T> node) {
        if (node.getRight() != null) {
            return findPredecessor(node.getRight());
        }
        return node.getData();
    }


    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        }
        AVLNode<T> deepestNode = maxDeepestHelper(root);
        return deepestNode.getData();
    }

    /**
     * Helper method that find the deepest node with max value
     *
     * @param node the current node we are on
     * @return the deepest node with max value
     */
    private AVLNode<T> maxDeepestHelper(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return node;
        }

        int leftHeight = -1;
        int rightHeight = -1;

        if (node.getLeft() != null) {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            rightHeight = node.getRight().getHeight();
        }

        if (leftHeight > rightHeight) {
            return maxDeepestHelper(node.getLeft());
        } else {
            return maxDeepestHelper(node.getRight());
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
