import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Building Index using BST.
 * <p>
 * AlphaFreq.java
 * Andrew_id: ruidic
 *
 * @author Ruidi Chang
 */
public class AlphaFreq implements Comparator<Word> {
    /**
     * Compare with alpha and frequencies.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return order
     */
    @Override
    public int compare(Word o1, Word o2) {
        int alphaResult = o1.getWord().compareTo(o2.getWord());
        if (alphaResult != 0) {
            return alphaResult;
        }
        return Integer.compare(o1.getFrequency(), o2.getFrequency());
    }
}
