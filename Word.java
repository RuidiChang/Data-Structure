import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Building Index using BST.
 * <p>
 * Word.java
 * Andrew_id: ruidic
 *
 * @author Ruidi Chang
 */
public class Word implements Comparable<Word> {
    /**
     * word.
     */
    private String word;
    /**
     * line number.
     */
    private Set<Integer> index;
    /**
     * frequency.
     */
    private int frequency;

    /**
     * Constructor with parameter.
     *
     * @param w word to wrap
     */
    public Word(String w) {
        word = w;
        index = new HashSet<>();
        frequency = 1;
    }

    /**
     * set word method.
     *
     * @param newWord the word to set
     */
    public void setWord(String newWord) {
        word = newWord;
    }

    /**
     * accessor of word.
     *
     * @return word
     */
    public String getWord() {
        return word;
    }

    /**
     * set frequency method.
     *
     * @param freq the frequency to set
     */
    public void setFrequency(int freq) {
        frequency = freq;
    }

    /**
     * accessor of frequency.
     *
     * @return frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Adds the line number to index.
     *
     * @param line the line number
     */
    public void addToIndex(Integer line) {
        index.add(line);
    }

    /**
     * Accessor of index.
     *
     * @return index
     */
    public Set<Integer> getIndex() {
        return index;
    }

    /**
     * compareTo method based on word.
     *
     * @param compareWord used to compare with current word
     * @return Negative if word comes before compareWord, Positive if after.
     */
    @Override
    public int compareTo(Word compareWord) {
        // Natural alphabetic order
        return word.compareTo(compareWord.word);
    }

    /**
     * toString method.
     *
     * @return String of each word.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> itr = index.iterator();
        sb.append("[");
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return String.format("%s %d %s", word, frequency, sb.toString());
    }
}
