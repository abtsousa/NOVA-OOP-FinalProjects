package shows;

import artists.Artist;
import java.util.*;

/**
 * MovieClass - Movie registered in the app
 */
public class MovieClass extends AbstractShow implements Movie {

    //CONSTRUCTOR
    /**
     * Type of show with the following information:
     * @param title movie's title
     * @param director movie's director
     * @param duration movie's duration in minutes
     * @param ageCert movie's age certification
     * @param releaseDate movie's release date (year)
     * @param genres movie's genre(s)
     * @param topCast movie's top cast
     */
    public MovieClass(String title, Artist director, int duration, String ageCert, int releaseDate, List<String> genres, List<Artist> topCast) {
        super(title, director, duration, ageCert, releaseDate, genres, topCast);
    }

    //METHODS
    @Override
    public ShowType getShowType() {
        return ShowType.Movie;
    }

}