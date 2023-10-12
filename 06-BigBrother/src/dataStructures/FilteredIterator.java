package dataStructures;

/**
 * Returns an iterator that skips elements that are on a filter
 * @param <E> generic object type that's being iterated
 */
public class FilteredIterator<E> implements Iterator<E> {

    //CONSTANTS
    private final Array<E> array;

    //VARIABLES
    private int current; //current index
    private final int offset; //offsets the iterator
    private final Array<E> filter; //array with filtered objects

    //CONSTRUCTORS
    /**
     *  Returns an iterator that skips elements that are on a filter
     *  Supports offsets as well
     *  Array elements are obtained by calling array.get() since it cannot access them directly
     *  (ArrayClass doesn't pass them and we cannot change its code)
     * @param array the array to iterate
     * @param filter the array that's being filtered
     */
    public FilteredIterator(Array<E> array, Array<E> filter) {
        this.array = array;
        this.offset = 0;
        this.filter = filter;
        rewind();
        advance();
    }

    /**
     *  Returns an iterator that skips elements that are on a filter
     *  Supports offsets as well
     *  Array elements are obtained by calling array.get() since it cannot access them directly
     *  (ArrayClass doesn't pass them and we cannot change its code)
     * @param array the array to iterate
     * @param filter the array that's being filtered
     * @param offset offsets the iterator results
     */
    public FilteredIterator(Array<E> array, Array<E> filter, int offset) {
        this.array = array;
        this.offset = offset;
        this.filter = filter;
        rewind();
        advance();
    }

    //METHODS
    @Override
    public void rewind() {current = 0;}

    public boolean hasNext() {return current < array.size();}

    /**
     * Skips elements that fit the criterion
     */
    private void advance() {
        while (current < array.size() && criterion()) {
            current++;
        }
    }

    /**
     * Checks if the element is in the filter array (element to be filtered)
     * @return true if the element is to be filtered
     */
    private boolean criterion() {return filter.searchForward(array.get(index()));}

    private int index() {return (current + offset) % array.size();} //offsets and loops back to the start if it

    @Override
    public E next() {
        E result = array.get(index());
        current++;
        advance();
        return result;
    }
}