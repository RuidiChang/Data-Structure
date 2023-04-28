import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Building Index using BST.
 * <p>
 * IgnoreCase.java
 * Andrew_id: ruidic
 *
 * @author Ruidi Chang
 */
public class IgnoreCase implements Comparator<Word> {
    /**
     * Compare with alpha ignore case.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return order
     */
    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord().toLowerCase().compareTo(o2.getWord().toLowerCase()
        );
    }
}
