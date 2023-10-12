package artists;

import java.util.*;

/**
 * ArtistSetComparator
 * Auxilliary AVOIDERS comparator
 * Maintains the order of a set with a subset of artists
 * Used to compare the subsets inside the artists power set
 */

public class ArtistSetComparator implements Comparator<SortedSet<Artist>> {

    @Override
    public int compare(SortedSet<Artist> o1, SortedSet<Artist> o2) {
        int comp = o2.size() - o1.size();
        if (comp == 0) {
            Iterator<Artist> it1 = o1.iterator();
            Iterator<Artist> it2 = o2.iterator();
            while (it1.hasNext() && it2.hasNext() && comp == 0)
                comp = it1.next().compareTo(it2.next());
        }
        return comp;
    }
}

