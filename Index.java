import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Building Index using BST.
 * <p>
 * Index.java
 * Andrew_id: ruidic
 *
 * @author Ruidi Chang
 */
public class Index {
    /**
     * builds an index tree from the given file.
     *
     * @param fileName given filename
     * @return built index tree
     */
    public BST<Word> buildIndex(String fileName) throws FileNotFoundException {
        return buildIndex(fileName, null);
    }

    /**
     * builds an index tree from the given file by using comparator.
     *
     * @param fileName   given filename
     * @param comparator comparator used when building the tree
     * @return built index tree
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) throws FileNotFoundException {
        BST<Word> tree = new BST<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(fileName));
            int lineNum = 0;
            while (sc.hasNextLine()) {
                lineNum++;
                String line = sc.nextLine();
                String[] inputs = line.split("\\W");
                for (String input : inputs) {
                    if (input.matches("^[A-Za-z]+$")) {
                        String word = input;
                        // convert to lower case if comparator is IgnoreCase
                        if (comparator instanceof IgnoreCase) {
                            word = word.toLowerCase();
                        }
                        Word wordObj = tree.search(new Word(word));
                        if (wordObj == null) {
                            Word newWord = new Word(word);
                            newWord.addToIndex(lineNum);
                            tree.insert(newWord);
                        } else {
                            wordObj.addToIndex(lineNum);
                            wordObj.setFrequency(wordObj.getFrequency() + 1);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the file.");
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return tree;
    }

    /**
     * builds an index tree from the given list by using comparator.
     *
     * @param list       given list
     * @param comparator comparator used when building the tree
     * @return built index tree
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        if (list == null) {
            return new BST<Word>();
        }
        BST<Word> tree = new BST<>(comparator);
        for (Word word : list) {
            if (word != null && word.getWord().matches("[a-zA-Z]+")) {
                //only insert if the word doesn't exist
                //do nothing if the word exist
                if (comparator instanceof IgnoreCase) {
                    word.setWord(word.getWord().toLowerCase());
                    tree.insert(word);
                } else {
                    tree.insert(word);
                }
            }
        }
        return tree;
    }

    /**
     * Sort the Word object using AlphaFreq comparator.
     *
     * @param tree given tree
     * @return sorted Arraylist
     */
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
        /*
         * Even though there should be no ties with regard to words in BST,
         * in the spirit of using what you wrote,
         * use AlphaFreq comparator in this method.
         */
        if (tree == null) {
            return new ArrayList<Word>();
        }
        ArrayList<Word> result = new ArrayList<Word>();
        Iterator<Word> itr = tree.iterator();
        while (itr.hasNext()) {
            result.add(itr.next());
        }
        Collections.sort(result, new AlphaFreq());
        return result;
    }

    /**
     * Sort the Word object using Frequency comparator.
     *
     * @param tree given tree
     * @return sorted Arraylist
     */
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {
        if (tree == null) {
            return new ArrayList<Word>();
        }
        ArrayList<Word> result = new ArrayList<Word>();
        Iterator<Word> itr = tree.iterator();
        while (itr.hasNext()) {
            result.add(itr.next());
        }
        Collections.sort(result, new Frequency());
        return result;
    }

    /**
     * gets the highest frequency Nodes of a tree.
     *
     * @param tree given tree
     * @return word list with the highest frequency
     */
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
        if (tree == null) {
            return new ArrayList<Word>();
        }
        ArrayList<Word> sortedByFreq = sortByFrequency(tree);
        int highest = sortedByFreq.get(0).getFrequency();
        ArrayList<Word> hFWords = new ArrayList<>();
        for (Word word : sortedByFreq) {
            if (word.getFrequency() == highest) {
                hFWords.add(word);
            } else {
                break;
            }
        }
        return hFWords;
    }
}
