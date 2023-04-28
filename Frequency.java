import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Building Index using BST.
 * <p>
 * Frequency.java
 * Andrew_id: ruidic
 *
 * @author Ruidi Chang
 */
public class Frequency implements Comparator<Word> {
    /**
     * Compare with frequencies.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return order
     */
    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o2.getFrequency(), o1.getFrequency());
    }
}
