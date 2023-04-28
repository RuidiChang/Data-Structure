/**
 * 17683 Data Structures for Applications Programmers.
 * Homework 3 SortedLinkedList Implementation with Recursion.
 * <p>
 * SortedLinkedList.
 *
 * @author Ruidi Chang
 * Andrew_id ruidic
 */
public class SortedLinkedList implements MyListInterface {
    /**
     * inner static Node class.
     */
    private static class Node {
        /**
         * data stored in this Node.
         */
        private String data;
        /**
         * reference to next node.
         */
        private Node next;

        /**
         * constructor with parameter.
         *
         * @param word     word to store in this Node
         * @param nextNode next node
         */
        Node(String word, Node nextNode) {
            data = word;
            next = nextNode;
        }
    }

    /**
     * head of the sorted linked list.
     */
    private Node head;

    /**
     * no-arg constructor.
     */
    public SortedLinkedList() {
        head = null;
    }

    /**
     * constructor with unsorted String Array.
     *
     * @param unsorted unsorted String Array.
     */
    public SortedLinkedList(String[] unsorted) {
        if (unsorted.length == 0 || unsorted == null) {
            head = null;
        }
        addUnsorted(unsorted, 0);
    }

    /**
     * A recursive method to add new items.
     * Base case is stop adding new item and return when all elements are added.
     * Recursive case is adding next string, recurse it.
     *
     * @param unsorted unsorted String Array.
     * @param pos      the index to add.
     */
    private void addUnsorted(String[] unsorted, int pos) {
        if (pos < unsorted.length) {
            add(unsorted[pos]);
            addUnsorted(unsorted, pos + 1);
        } else {
            return;
        }
    }

    /**
     * add a string into the list.
     *
     * @param value String to be added.
     */
    @Override
    public void add(String value) {
        if ((value == null) || (value.length() == 0)) {
            return;
        }
        if (head == null) {
            head = new Node(value, null);
            return;
        }
        if (head.data.compareTo(value) > 0) {
            head = new Node(value, head);
            return;
        }
        addHelper(value, null, head);
    }

    /**
     * A recursive helper method to add the String to the list.
     * There are 3 base cases.
     * First is when next node is null and this is the end of the list, add to the end;
     * Second is deal with duplicates, which is not allowed and return;
     * The last one is when current node's data is bigger than value, add the String before current.
     *
     * @param value value to be added
     * @param prev  previous node
     * @param curr  current node
     */
    private void addHelper(String value, Node prev, Node curr) {
        if (curr == null) {
            prev.next = new Node(value, null);
            return;
        }
        if (curr.data.equals(value)) {
            // No duplicates allowed
            return;
        }
        if (curr.data.compareTo(value) > 0) {
            prev.next = new Node(value, curr);
            return;
        }

        addHelper(value, curr, curr.next);
    }

    /**
     * Checks the size (number of data items) of the list.
     *
     * @return the size of the list.
     */
    @Override
    public int size() {
        return sizeHelper(head);
    }

    /**
     * A recursive helper method of size().
     * Base case is when curr is null, return 0
     * Recursive case is when curr still has next, recurse it.
     *
     * @param curr passes the current node to be checked.
     * @return Base case returns 1; recursive case returns 1 + getSize(nextNode)
     */
    private int sizeHelper(Node curr) {
        // Base case
        if (curr == null) {
            return 0;
        }
        // Recursive case
        return 1 + sizeHelper(curr.next);
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Displays the values of the list.
     */
    @Override
    public void display() {
        if (isEmpty()) {
            System.out.println("[]");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            displayHelper(head, sb);
            sb.append("]");
            System.out.println(sb.toString());
        }
    }

    /**
     * A recursive helper method to display.
     * Base case is when curr.next is null, return it.
     * Recursive case is when curr still has next, recurse it and display.
     *
     * @param curr current node
     * @param sb   StringBuilder to append
     */
    private void displayHelper(Node curr, StringBuilder sb) {
        sb.append(curr.data);
        if (curr.next == null) {
            return;
        }
        sb.append(", ");
        displayHelper(curr.next, sb);
    }

    /**
     * Check whether a string is in the list.
     *
     * @param key String to check
     * @return true if found, false if not found
     */
    @Override
    public boolean contains(String key) {
        if ((key == null) || (key.length() == 0)) {
            return false;
        }
        return containsHelper(key, head);
    }

    /**
     * A recursive helper method to check whether the given key is in the list.
     * There are two base cases: one is not found and the other is current node is the key.
     * Recursive case is keep find the key if current node not equals to the key
     * until the current node is the end of the list.
     *
     * @param key  String key to search
     * @param curr current node
     * @return whether the key is in the sub-list
     */
    private boolean containsHelper(String key, Node curr) {
        if (curr == null) {
            return false;
        }
        if (curr.data.equals(key)) {
            return true;
        }
        return containsHelper(key, curr.next);
    }

    /**
     * remove the fist node.
     *
     * @return return null if the list is empty, return the first data
     */
    @Override
    public String removeFirst() {
        if (isEmpty()) {
            return null;
        }
        String first = head.data;
        head = head.next;
        return first;
    }

    /**
     * Remove the data at a specified index.
     *
     * @param index index to remove data
     * @return String object that is removed
     * @throws RuntimeException for invalid index value (index < 0 || index >= size())
     */
    @Override
    public String removeAt(int index) {
        if (index < 0 || index >= size()) {
            throw new RuntimeException();
        }
        if (index == 0) {
            return removeFirst();
        }
        return removeAtHelper(null, head, 0, index);
    }

    /**
     * A recursive helper method to remove data of a specific index.
     * There are two base cases, one is curr is null and not found, the other is found.
     * Recursive case is to check next node.
     *
     * @param prev      The previous node, default value is null
     * @param curr      The current node, default value is head
     * @param currIndex The current index
     * @param tgtIndex  The removal index
     * @return the node that is going to be removed
     */
    private String removeAtHelper(Node prev, Node curr, int currIndex, int tgtIndex) {
        if (curr == null) {
            throw new RuntimeException();
        }
        if (currIndex == tgtIndex) {
            prev.next = curr.next;
            return curr.data;
        }
        return removeAtHelper(curr, curr.next, currIndex + 1, tgtIndex);
    }


}
