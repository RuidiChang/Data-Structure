import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 2 Solve Josephus problem using different data structures
 * and different algorithms and compare running times.
 * <p>
 * I will choose the playWithLLAt method.
 * Time complexity for the three method is all O(n^2).
 * ArrayDeque and LinkedList are similar but ArrayDeque is somewhat better because it is circular.
 * playWithLLAt find next node before remove(index) so it is quicker as finding is faster than copying and pasting.
 * <p>
 * Andrew ID: ruidic
 *
 * @author Ruidi Chang
 */
public class Josephus {

    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithAD(int size, int rotation) {
        // TODO your implementation here
        // check size
        if (size <= 0) {
            throw new RuntimeException("Input size not valid.");
        }
        // check rotation
        if (rotation <= 0) {
            throw new RuntimeException("Rotation size not valid.");
        }
        // construct a queue
        Queue<Integer> queue = new ArrayDeque<>();
        // add people into the queue
        for (int i = 1; i <= size; i++) {
            queue.add(i);
        }

        // kill process
        while (queue.size() > 1) { // The elimination proceeds until one person remains.
            int rotationCheck = rotation % queue.size();
            if (rotationCheck == 0) { // if rotation = 0, the for-loop below will do nothing
                rotationCheck = queue.size();
            }
            for (int i = 0; i < rotationCheck - 1; i++) { // do rotation
                int tmp = queue.poll();
                queue.add(tmp);
            }
            queue.poll(); // kill one selected person
        }
        return queue.peek();
    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {
        // TODO your implementation here
        // check size
        if (size <= 0) {
            throw new RuntimeException("Input size not valid.");
        }
        // check rotation
        if (rotation <= 0) {
            throw new RuntimeException("Rotation size not valid.");
        }
        // construct a linkedlist
        Queue<Integer> linkedList = new LinkedList<>();
        // add people into the queue
        for (int i = 1; i <= size; i++) {
            linkedList.add(i);
        }

        // kill process
        while (linkedList.size() > 1) { // The elimination proceeds until one person remains.
            int rotationCheck = rotation % linkedList.size();
            if (rotationCheck == 0) { // if rotation = 0, the for-loop below will do nothing
                rotationCheck = linkedList.size();
            }
            for (int i = 0; i < rotationCheck - 1; i++) { // do rotation
                int tmp = linkedList.remove();
                linkedList.add(tmp);
            }
            linkedList.remove(); // kill one selected person
        }
        return linkedList.poll();
    }

    /**
     * Uses LinkedList class to find the survivor's position.
     * <p>
     * However, do NOT use the LinkedList as Queue/Deque
     * Instead, use the LinkedList as "List"
     * That means, it uses index value to find and remove a person to be executed in the circle
     * <p>
     * Note: Think carefully about this method!!
     * When in doubt, please visit one of the office hours!!
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLLAt(int size, int rotation) {
        // TODO your implementation here
        // check size
        if (size <= 0) {
            throw new RuntimeException("Input size not valid.");
        }
        // check rotation
        if (rotation <= 0) {
            throw new RuntimeException("Rotation size not valid.");
        }
        // construct a list
        List<Integer> list = new LinkedList<>();
        // add people into the queue
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }

        // kill process
        int index = -1;
        while (list.size() > 1) { // The elimination proceeds until one person remains.
            int rotationCheck = rotation % list.size();
            if (rotationCheck == 0) {
                rotationCheck = list.size();
            }
            index = (index + rotationCheck) % list.size();
            list.remove(index); // kill one selected person by using index value
            index -= 1;
        }
        return list.get(0);
    }

}
