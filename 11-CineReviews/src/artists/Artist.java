package artists;

import exceptions.BioAlreadyExistsException;
import exceptions.EmptyBioException;
import shows.Show;

import java.util.Iterator;

/**
 * Artist Interface:
 * Methods that manage the artist object
 */
public interface Artist extends Comparable<Artist> {

    //GET
    /**
     * Returns the artist's name
     * @return artist's name
     */
    String getName();

    /**
     * Returns the artist's date of birth
     * @return artist's date of birth
     * @throws EmptyBioException if it is empty (null);
     */
    String getDateOfBirth() throws EmptyBioException;

    /**
     * Returns the artist's place of birth
     * @return artist's place of birth
     * @throws EmptyBioException if it is empty (null);
     */
    String getPlaceOfBirth() throws EmptyBioException;

    /**
     * Lists shows the artist has participated in
     * @return lists shows the artist has participated in
     */
    Iterator<Show> getCredits();

    /**
     * Returns the role of the artist in the show
     * @param credit artist's role in this show
     * @return role of the artist in the show
     */
    String getRole(Show credit);

    //LOGIC
    /**
     * Adds bio information about the artist
     * @param dateOfBirth artist's date of birth
     * @param placeOfBirth artist's place of birth
     * @throws BioAlreadyExistsException if the artist already has a bio
     */
    void addBio(String dateOfBirth, String placeOfBirth) throws BioAlreadyExistsException;

    /**
     * Registers artist's participation in the show
     * @param show show artist has participated in
     */
    void addCredits(Show show);

}
