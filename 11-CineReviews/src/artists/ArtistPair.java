package artists;

import java.util.*;

/**
 * ArtistPair - pair of artists who may have worked together
 */
public class ArtistPair implements Pair<Artist> {

    //VARIABLES
    private final Artist a;
    private final Artist b;

    //CONSTRUCTOR
    /**
     * Pair of artists who may have worked together
     * @param a this artist
     * @param b that artist
     */
    public ArtistPair(Artist a, Artist b) {
        if (a.compareTo(b)>0) { //force alphabetical order
            this.a=b;
            this.b=a;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    //METHODS

    /**
     * @return the first artist
     */
    @Override
    public Artist getA() {
        return a;
    }

    /**
     * @return the second artist
     */
    @Override
    public Artist getB() {
        return b;
    }

    @Override
    public int compareTo(Pair other) {
        if (a.equals((Artist) other.getA())) return (b.compareTo((Artist) other.getB()));
        else return (a.compareTo((Artist) other.getA()));
    }

    /**
     * Checks if both artists are in an Artist set
     * @param set where both artist may be in
     * @return true if both artists are in the set
     */
    @Override
    public boolean isIn(Set<Artist> set) {
        return (set.contains(a) && set.contains(b));
    }

}
