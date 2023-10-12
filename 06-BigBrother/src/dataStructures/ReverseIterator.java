package dataStructures;

/**
 * Returns an iterator that goes through an array in reverse
 * @param <E> generic object type that's being iterated
 */
public class ReverseIterator<E> implements Iterator<E> {

    //CONSTANTS
    private final Array<E> array;

    //VARIABLES
    private int current;

    //CONSTRUCTORS
    /**
     * Returns an iterator that goes through an array in reverse
     * Array elements are obtained by calling array.get() since it cannot access them directly
     * (ArrayClass doesn't pass them and we cannot change its code)
     * @param array the array to iterate
     */
    public ReverseIterator(Array<E> array) {
        this.array = array;
        rewind();
    }

    //METHDOS
    @Override
    public void rewind() {current = array.size()-1;}

    @Override
    public boolean hasNext() {return current >= 0;}

    @Override
    public E next() {
        int index = current;
        E result = array.get(index);
        current--;
        return result;
    }
}
