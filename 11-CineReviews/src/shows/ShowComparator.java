package shows;

import java.util.Comparator;

/**
 * ShowComparator - lists the shows sorted from the highest to the lowest score,
 * then by released date and lastly alphabetically by title
 */
public class ShowComparator implements Comparator<Show> {

    //METHOD
    @Override
    public int compare(Show o1, Show o2) {
        if (o2 == null) return -1;
        if (o1 == null) return 1;
        //1st parameter -- show score
        double o1score = o1.getScore();
        double o2score = o2.getScore();

        if (o1score > o2score) return -1;
        else if (o1score < o2score) return 1;

        //2nd parameter -- show year
        int o1year = o1.getReleaseDate();
        int o2year = o2.getReleaseDate();
        if (o1year!=o2year) return o2year-o1year;

        //3rd parameter -- show name (alphabetically)
        else return o1.getTitle().compareTo(o2.getTitle());
    }
}