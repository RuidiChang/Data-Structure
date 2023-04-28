/**
 * 17683-A2 Data Structures for Application Programmers.
 * HW1
 *
 * @author Ruidi Chang
 * @andrew_id ruidic
 * Nov 01 2022
 */
public class MyArray {
    /**
     * Internal String array.
     */
    private String[] myArray;
    /**
     * Size of MyArray.
     */
    private int size;
    /**
     * Default capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Default constructor.
     */
    public MyArray() {
        myArray = new String[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Constructor with parameter initialCapacity.
     *
     * @param initialCapacity
     */
    public MyArray(int initialCapacity) {
        myArray = new String[initialCapacity];
        size = 0;
    }

    /**
     * Add the provided text into MyArray.
     * Time complexity: Amortized O(1)
     *
     * @param text
     */
    public void add(String text) {
        if (text == null || text.equals("")) { // check input string
            return;
        }
        if (!text.matches("[A-Za-z]+")) { // Ignore texts that is not [A-Za-z]+
            return;
        }
        // Resize
        if (size == 0) {
            myArray = new String[1];
        } else if (myArray[myArray.length - 1] != null) {
            String[] temp = new String[myArray.length * 2]; //double up
            System.arraycopy(myArray, 0, temp, 0, size);
            myArray = temp;
        }
        myArray[size] = text;
        size++;
    }

    /**
     * Search the given word.
     * Time complexity: O(n)
     *
     * @param key
     * @return whether the given word is in myArray
     */
    public boolean search(String key) {
        if (key == null) { // check given word is null or not
            return false;
        }
        if (key.equals("")) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (myArray[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return number of elements in MyArray.
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Return Capacity of MyArray.
     *
     * @return myArray.length
     */
    public int getCapacity() {
        return myArray.length;
    }

    /**
     * Display MyArray.
     */
    public void display() {
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i] != null) {
                System.out.print(myArray[i]);
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /**
     * Removes duplicates in this MyArray.
     * Time complexity: O(n^2)
     */
    public void removeDups() {
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < i; j++) {
                if (myArray[i] != null && myArray[i].equals(myArray[j])) {
                    System.arraycopy(myArray, i + 1, myArray, i, myArray.length - i - 1);
                    myArray[myArray.length - 1] = null;
                    size--;
                    i--;
                }
            }
        }
    }

}
