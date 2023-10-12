package artists;

import shows.Show;
import java.util.Comparator;

/**
 * CreditComparator - sorts the artist's credits by release year from most to least recent,
 * and lastly alphabetically by title
 */
class CreditComparator implements Comparator<Show> {
    @Override
    public int compare(Show o1, Show o2) {
        if (o2 == null) return -1;
        if (o1 == null) return 1;
        //1st parameter -- show year
        int o1year = o1.getReleaseDate();
        int o2year = o2.getReleaseDate();
        if (o1year != o2year) return o2year-o1year;
        //2nd parameter -- show name (alphabetically)
        else return o1.getTitle().compareTo(o2.getTitle());
    }
}
