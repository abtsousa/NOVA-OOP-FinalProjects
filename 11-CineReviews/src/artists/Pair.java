package artists;

import java.util.Set;

/**
 * A simple unordered pair (2-tuple) interface
 * @param <T> the object type of each pair element
 */
public interface Pair<T> extends Comparable<Pair<T>> {

    /**
     * @return the first object of the pair (a,b)
     */
    T getA();

    /**
     * @return the second object of the pair (a,b)
     */
    T getB();

    /**
     * Check if the pair is a subset in a larger set
     * @param set the larger set
     * @return true if both objects of the pair are contained in the larger set
     */
    boolean isIn(Set<T> set);
}
