package shows;

import artists.Artist;
import java.util.*;

/**
 * SeriesClass - Series registered in the app
 */
public class SeriesClass extends AbstractShow implements Series {

    //CONSTRUCTOR

    /**
     * Type of show with the following information:
     * @param title series' title
     * @param creator series' creator
     * @param seasons series' number of seasons
     * @param ageCert series' age certification
     * @param releaseDate series's release daye (year)
     * @param genres series's genre(s)
     * @param topCast series' top cast
     */
    public SeriesClass(String title, Artist creator, int seasons, String ageCert, int releaseDate, List<String> genres, List<Artist> topCast) {
        super(title, creator, seasons, ageCert, releaseDate, genres, topCast);
    }

    //METHODS
    @Override
    public ShowType getShowType() {
        return ShowType.Series;
    }

}