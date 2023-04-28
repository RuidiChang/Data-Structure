/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 4: HashTable Implementation with linear probing.
 * <p>
 * Andrew ID: ruidic
 *
 * @author Ruidi Chang
 */
public class MyHashTable implements MyHTInterface {

    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;
    /**
     * Default capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Load factor.
     */
    private static final double LOAD_FACTOR = 0.5;
    /**
     * Flag for deleted items.
     */
    private static final DataItem DELETED = new DataItem("#DEL#", 1);
    /**
     * The prime modular is 27.
     */
    public static final int MAGIC_NUMBER = 27;
    /**
     * Number of items in the hashTable.
     */
    private int size;
    /**
     * Number of collision in the hashTable.
     */
    private int collision;

    /**
     * Default constructor.
     */
    public MyHashTable() {
        hashArray = new DataItem[DEFAULT_CAPACITY];
        size = 0;
        collision = 0;
    }

    /**
     * Constructor with initial capacity.
     *
     * @param capacity Integer given as the initial capacity.
     */
    public MyHashTable(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException();
        } else {
            hashArray = new DataItem[capacity];
            size = 0;
        }
    }

    /**
     * Hash a string for English lowercase alphabet and blank.
     *
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        int hashValue = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            hashValue = (hashValue * MAGIC_NUMBER + (c - 'a' + 1)) % hashArray.length;
        }
        return hashValue;
    }

    /**
     * doubles array length and rehash items whenever the load factor is reached.
     */
    private void rehash() {
        DataItem[] tmp = hashArray;
        int newSize = nextPrime(hashArray.length * 2);
        System.out.println("Rehashing " + size + " items, new length is " + newSize);
        hashArray = new DataItem[newSize];
        collision = 0;
        size = 0;
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] != null) {
                insert(tmp[i].value, tmp[i].frequency);
            }
        }
        System.out.println("Rehashing " + size + " items, new length is " + newSize);
    }

    /**
     * Helper method for calculating prime number.
     *
     * @param i the start point for prime number search
     * @return a prime integer
     */
    private int nextPrime(int i) {
        while (!isPrime(i)) {
            i++;
        }
        return i;
    }

    /**
     * Helper method to check whether the given number is prime.
     *
     * @param n number to check
     * @return true if prime, false if not
     */
    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the size, number of items, of the table.
     *
     * @return the number of items in the table
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the number of collisions in relation to insert and rehash.
     * When rehashing process happens, the number of collisions should be properly updated.
     *
     * @return number of collisions
     */
    @Override
    public int numOfCollisions() {
        return collision;
    }

    /**
     * Returns the hash value of a String.
     * Assume that String value is going to be a word with all lowercase letters.
     *
     * @param value value for which the hash value should be calculated
     * @return int hash value of a String
     */
    @Override
    public int hashValue(String value) {
        return hashFunc(value);
    }

    /**
     * Displays the values of the table.
     * If an index is empty, it shows **
     * If previously existed data item got deleted, then it should show #DEL#
     */
    @Override
    public void display() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] == DELETED) {
                sb.append(hashArray[i].value).append(" ");
            } else if (hashArray[i] != null) {
                sb.append("[" + hashArray[i].value + ", " + hashArray[i].frequency + "]").append(" ");
            } else {
                sb.append("**").append(" ");
            }
        }
        System.out.println(sb.toString());
    }

    /**
     * Inserts a new String value (word).
     * Frequency of each word to be stored too.
     *
     * @param value String value to add
     */
    @Override
    public void insert(String value) {
        insert(value, 1);
    }

    /**
     * Helper method of insert.
     *
     * @param value     String value to add
     * @param frequency
     */
    private void insert(String value, int frequency) {
        if (!wordValid(value)) {
            return;
        }
        int hashVal = hashFunc(value);
        int index = hashVal;
        int i = -1;
        boolean findCollision = false;
        while (hashArray[index] != null) {
            if (hashArray[index].value.equals(value)) {
                break;
            }
            if (hashFunc(hashArray[index].value) == hashVal && hashArray[index].frequency != 0) {
                findCollision = true;
            }
            if (i == -1 && hashArray[index].frequency == 0) {
                i = index;
            }
            index = (index + 1) % hashArray.length;
        }
        if (hashArray[index] == null) {
            if (i != -1) {
                hashArray[i] = new DataItem(value, frequency);
            } else {
                hashArray[index] = new DataItem(value, frequency);
            }
            size++;
            if (findCollision) {
                collision++;
            }
            if (1.0 * size / hashArray.length > LOAD_FACTOR) {
                rehash();
            }
        } else {
            hashArray[index] = new DataItem(value, hashArray[index].frequency + 1);
        }
    }

    /**
     * Helper method to check the validation of String.
     *
     * @param key String to be checked
     * @return true if valid, false if not
     */
    private boolean wordValid(String key) {
        boolean validKey = true;
        if (key == null || key.length() == 0) {
            validKey = false;
        } else {
            for (int i = 0; i < key.length(); i++) {
                // lower case
                if (!((key.charAt(i) >= 'a' && key.charAt(i) <= 'z'))) {
                    validKey = false;
                }
            }
        }
        return validKey;
    }

    /**
     * Returns true if value is contained in the table.
     *
     * @param key String key value to search
     * @return true if found, false if not found.
     */
    @Override
    public boolean contains(String key) {
        return find(key) != -1;
    }

    /**
     * Returns the frequency of a key String.
     *
     * @param key string value to find its frequency
     * @return frequency value if found. If not found, return 0
     */
    @Override
    public int showFrequency(String key) {
        if (!wordValid(key)) {
            return 0;
        }
        if (!contains(key)) {
            return 0;
        }
        int hashVal = hashFunc(key);
        while (!hashArray[hashVal].value.equals(key)) {
            hashVal = (hashVal + 1) % hashArray.length;
        }
        return hashArray[hashVal].frequency;
    }

    /**
     * Removes and returns removed value.
     *
     * @param key String to remove
     * @return value that is removed. If not found, return null
     */
    public String remove(String key) {
        int index = find(key);
        if (index == -1) {
            return null;
        }
        hashArray[index].frequency = 0;
        size--;
        return hashArray[index].value;
    }

    /**
     * Find the index of a String key.
     *
     * @param key the String to find
     * @return if is found, return the index of the key, else return -1
     */
    private int find(String key) {
        int hashVal = hashFunc(key);
        int i = 1;
        while (hashArray[hashVal] != null && i <= hashArray.length) {
            if (hashArray[hashVal].frequency != 0 && hashArray[hashVal].value.equals(key)) {
                return hashVal;
            }
            hashVal = (hashVal + 1) % hashArray.length;
            i++;
        }
        return -1;
    }

    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        /**
         * DataItem constructor, default frequency is 1.
         *
         * @param itemValue       value store in this data item
         * @param frequencyNumber frequency
         */
        DataItem(String itemValue, int frequencyNumber) {
            this.value = itemValue;
            this.frequency = frequencyNumber;
        }

    }

}
