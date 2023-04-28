import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Building Index using BST.
 * <p>
 * BST.java
 * Andrew_id: ruidic
 *
 * @param <T> data type of objects
 * @author Ruidi Chang
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * root.
     */
    private Node<T> root;
    /**
     * comparator.
     */
    private Comparator<T> comparator;

    /**
     * default constructor.
     */
    public BST() {
        this(null);
    }

    /**
     * constructor with parameter.
     *
     * @param comp comparator
     */
    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }

    /**
     * Accessor of comparator.
     *
     * @return comparator
     */
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * get the data stored in root.
     *
     * @return root.data
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }

    /**
     * get the height of the BST.
     *
     * @return height
     */
    public int getHeight() {
        if (root == null) {
            return 0;
        }
        return getHeightHelper(root) - 1;
    }

    /**
     * helper method to get the height of the given sub-tree recursively.
     *
     * @param curr current node
     * @return height of the given sub-tree
     */
    private int getHeightHelper(Node<T> curr) {
        // base case
        if (curr == null) {
            return 0;
        }
        // recursive case
        return 1 + Math.max(getHeightHelper(curr.left), getHeightHelper(curr.right));
    }

    /**
     * get the number of nodes.
     *
     * @return number of nodes
     */
    public int getNumberOfNodes() {
        if (root == null) {
            return 0;
        }
        return getNumberOfNodesHelper(root);
    }

    /**
     * helper method of getNumberOfNodes() to get nodes' number recursive.
     *
     * @param current current node
     * @return number of nodes
     */
    private int getNumberOfNodesHelper(Node<T> current) {
        //base case
        if (current == null) {
            return 0;
        }
        //recursive case
        return 1 + getNumberOfNodesHelper(current.left) + getNumberOfNodesHelper(current.right);
    }

    /**
     * Given the value (object), tries to find it.
     *
     * @param toSearch Object value to search
     * @return The value (object) of the search result. If not found, null.
     */
    @Override
    public T search(T toSearch) {
        if (toSearch == null) {
            return null;
        }
        return searchHelper(root, toSearch);
    }

    /**
     * Private helper method to search for the given data in BST recursively.
     *
     * @param toSearch Object value to search
     * @param current  current node
     * @return If found, search result; if not found, null
     */
    private T searchHelper(Node<T> current, T toSearch) {
        // base case: not found
        if (current == null) {
            return null;
        }
        int compareVal = 0;
        if (comparator == null) {
            compareVal = toSearch.compareTo(current.data);
        } else {
            compareVal = comparator.compare(toSearch, current.data);
        }
        // base case: find and return;
        if (compareVal == 0) {
            return current.data;
        }
        // recursive case
        if (compareVal < 0) {
            return searchHelper(current.left, toSearch);
        } else {
            return searchHelper(current.right, toSearch);
        }
    }

    /**
     * Inserts a value (object) to the tree.
     * No duplicates allowed.
     *
     * @param toInsert a value (object) to insert into the tree.
     */
    @Override
    public void insert(T toInsert) {
        if (toInsert == null) {
            return;
        }
        if (root == null) {
            root = new Node<T>(toInsert);
            return;
        }
        insertHelper(root, toInsert);
    }

    /**
     * helper method to insert the given data into BST recursively.
     *
     * @param current  current node
     * @param toInsert a value (object) to insert into the tree
     */
    private void insertHelper(Node<T> current, T toInsert) {
        // base case
        if (current == null) {
            return;
        }
        int compareVal = 0;
        if (comparator == null) {
            compareVal = toInsert.compareTo(current.data);
        } else {
            compareVal = comparator.compare(toInsert, current.data);
        }
        // recursive case
        Node<T> parent = current;
        if (compareVal < 0) {
            current = current.left;
            if (current == null) {
                parent.left = new Node<T>(toInsert);
                return;
            } else {
                insertHelper(current, toInsert);
            }
        } else if (compareVal > 0) {
            current = parent.right;
            if (current == null) {
                parent.right = new Node<T>(toInsert);
                return;
            } else {
                insertHelper(current, toInsert);
            }
            // base case;
        } else {
            return;
        }
    }

    /**
     * Iterator method.
     *
     * @return Iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    /**
     * Inner BSTIterator class.
     */
    private class BSTIterator implements Iterator<T> {
        /**
         * Helper stack.
         */
        private Stack<Node<T>> stack;

        /**
         * Default constructor.
         */
        BSTIterator() {
            stack = new Stack<>();
            Node<T> curr = root;
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
        }

        /**
         * check next.
         *
         * @return True if not empty, false if empty
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * next() method to get next T.
         * Pop and check whether the new first has right node
         * If yes, add the right node and go to the leftest node.
         *
         * @return T T of next Node
         */
        @Override
        public T next() {
            if (stack.isEmpty()) {
                return null;
            }
            Node<T> nextNode = stack.pop();
            if (nextNode.right != null) {
                Node<T> current = nextNode.right;
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }
            return nextNode.data;
        }
    }

    /**
     * static nested Node class for Node.
     *
     * @param <T> T data of the node
     */
    private static class Node<T> {
        /**
         * Key data T.
         */
        private T data;
        /**
         * reference to left children nodes.
         */
        private Node<T> left;
        /**
         * reference to right children nodes.
         */
        private Node<T> right;

        /**
         * Constructor Node.
         *
         * @param d data
         */
        Node(T d) {
            this(d, null, null);
        }

        /**
         * Constructor with a new node and left right children.
         *
         * @param d data
         * @param l left node
         * @param r right node
         */
        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }
    }

}
