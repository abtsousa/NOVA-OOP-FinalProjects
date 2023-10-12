package dataStructures;

/** Returns an iterator that supports an offset
 * @param <E> generic object type that's being iterated
 */
public class OffsetIterator<E> implements Iterator<E> {

    //CONSTANTS
    private final Array<E> array;

    //VARIABLES
    private int current; //current index
    private final int offset; //offsets the iterator

    //CONSTRUCTORS

    /**
     * Iterator that supports an offset
     * For example, a 5 element array [0,1,2,3,4] can be offset by 2 and output in order the elements [2,3,4,0,1]
     * Array elements are obtained by calling array.get() since it cannot access them directly
     * (ArrayClass doesn't pass them and we cannot change its code)
     * @param array the array to iterate
     */
    public OffsetIterator(Array<E> array) {
        this.array = array;
        this.offset = 0;
        rewind();
    }

    /**
     * Iterator that supports an offset
     * For example, a 5 element array [0,1,2,3,4] can be offset by 2 and output in order the elements [2,3,4,0,1]
     * Array elements are obtained by calling array.get() since it cannot access them directly
     * (ArrayClass doesn't pass them and we cannot change its code)
     * @param array the array to iterate
     * @param offset offsets the result that the iterator returns
     */
    public OffsetIterator(Array<E> array, int offset) {
        this.array = array;
        this.offset = offset;
        rewind();
    }

    //METHODS
    @Override
    public void rewind() {current = 0;}

    @Override
    public boolean hasNext() {return current < array.size();}

    @Override
    public E next() {
        int index = (current + offset) % array.size(); //offsets the result and loops back to the start if it reaches the end
        E result = array.get(index);
        current++;
        return result;
    }
}
