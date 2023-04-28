import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 5 Document Similarity.
 * <p>
 * Andrew id: ruidic
 *
 * @author Ruidi Chang
 */
public class Similarity {
    /**
     * Number of words.
     */
    private BigInteger numOfWords = BigInteger.ZERO;
    /**
     * Number of lines.
     */
    private int numOfLines;
    /**
     * Private HashMap used to store all the unique words and their frequencies.
     */
    private Map<String, BigInteger> frequencyOfWord = new HashMap<String, BigInteger>();

    /**
     * Constructor with parameter String.
     *
     * @param string The String to be read.
     */
    public Similarity(String string) {
        if (string == null || string.length() == 0) {
            return;
        }
        String[] text = string.split("\\W");
        for (String word : text) {
            if (word.matches("[a-zA-Z]+")) {
                word = word.toLowerCase();
                numOfWords = numOfWords.add(BigInteger.ONE);
                if (frequencyOfWord.containsKey(word)) {
                    frequencyOfWord.put(word, frequencyOfWord.get(word).add(BigInteger.ONE));
                } else {
                    frequencyOfWord.put(word, BigInteger.ONE);
                }
            }
        }
    }

    /**
     * Constructor with parameter String.
     *
     * @param file The file to be read.
     */
    public Similarity(File file) {
        if (file == null) {
            return;
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                numOfLines++;
                if (line.length() == 0) {
                    continue;
                }
                String[] text = line.split("\\W");
                for (String word : text) {
                    if (word.matches("[a-zA-Z]+")) {
                        word = word.toLowerCase();
                        numOfWords = numOfWords.add(BigInteger.ONE);
                        if (frequencyOfWord.containsKey(word)) {
                            frequencyOfWord.put(word, frequencyOfWord.get(word).add(BigInteger.ONE));
                        } else {
                            frequencyOfWord.put(word, BigInteger.ONE);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Accessor of number of Lines.
     *
     * @return number of lines
     */
    public int numOfLines() {
        return numOfLines;
    }

    /**
     * Accessor of number of words.
     *
     * @return number of words
     */
    public BigInteger numOfWords() {
        return numOfWords;
    }

    /**
     * Get the number of words with no duplicates.
     *
     * @return number of distinct words
     */
    public int numOfWordsNoDups() {
        return frequencyOfWord.size();
    }

    /**
     * Calculates the Euclidean norm of the frequency vector.
     *
     * @return Euclidean norm
     */
    public double euclideanNorm() {
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger freq : frequencyOfWord.values()) {
            sum = sum.add(freq.pow(2));
        }
        return Math.sqrt(sum.doubleValue());
    }

    /**
     * Calculates the dot product between two frequency vectors.
     *
     * @param map given frequency vector
     * @return dot product
     */
    public double dotProduct(Map<String, BigInteger> map) {
        if (map == null || map.isEmpty()) {
            return 0;
        }
        if (frequencyOfWord == null || frequencyOfWord.isEmpty()) {
            return 0;
        }
        BigInteger dot = BigInteger.ZERO;
        if (map.size() > frequencyOfWord.size()) {
            for (String s : frequencyOfWord.keySet()) {
                if (map.containsKey(s)) {
                    dot = dot.add(map.get(s).multiply(frequencyOfWord.get(s)));
                }
            }
        } else {
            for (String s : map.keySet()) {
                if (frequencyOfWord.containsKey(s)) {
                    dot = dot.add(map.get(s).multiply(frequencyOfWord.get(s)));
                }
            }
        }
        return dot.doubleValue();
    }

    /**
     * Calculates the distance between two frequency vectors.
     *
     * @param map given frequency vector
     * @return cosine distance
     */
    public double distance(Map<String, BigInteger> map) {
        if (map == null || map.isEmpty()) {
            return Math.PI / 2;
        }
        if (frequencyOfWord == null || frequencyOfWord.isEmpty()) {
            return Math.PI / 2;
        }
        if (map.equals(frequencyOfWord)) {
            return 0;
        }
        double dot = dotProduct(map);
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger freq : map.values()) {
            sum = sum.add(freq.pow(2));
        }
        double euc = Math.sqrt(sum.doubleValue());
        return Math.acos(dot / (euclideanNorm() * euc));
    }

    /**
     * Accessor of frequencyOfWord.
     *
     * @return copy of frequencyOfWord
     */
    public Map<String, BigInteger> getMap() {
        return new HashMap<String, BigInteger>(frequencyOfWord);
    }
}
