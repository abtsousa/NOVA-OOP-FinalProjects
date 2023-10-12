package artists;
import java.util.*;

import exceptions.EmptyBioException;
import shows.*;
import exceptions.BioAlreadyExistsException;

/**
 * GenericArtist - registered artist who may have participated in one or more shows
 */
public class GenericArtist implements Artist {
    //VARIABLES
    private final String name; //Artist's name
    private String dateOfBirth; //Artist's birthday
    private String placeOfBirth; //Artist's place of birth
    private final SortedSet<Show> credits; //SortedSet of artist's reviews

    //CONSTRUCTORS
    /**
     * Registered artist who as yet to participate in any show with no bio except name
     * @param name artist's name
     */
    public GenericArtist(String name) {
        this.name = name;
        this.dateOfBirth = null;
        this.placeOfBirth = null;
        this.credits = new TreeSet<>(new CreditComparator());
    }

    /**
     * Registered artist who as yet to participate in any show
     * @param name artist's name
     * @param dateOfBirth artist's birthday
     * @param placeOfBirth artist's place of birthday
     */
    public GenericArtist(String name, String dateOfBirth, String placeOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.credits = new TreeSet<>(new CreditComparator());
    }

    //METHODS
    //GET
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDateOfBirth() throws EmptyBioException {
        if (dateOfBirth==null) throw new EmptyBioException();
        return dateOfBirth;
    }

    @Override
    public String getPlaceOfBirth() throws EmptyBioException {
        if (dateOfBirth==null) throw new EmptyBioException();
        return placeOfBirth;
    }

    @Override
    public Iterator<Show> getCredits() {
        return credits.iterator();
    }

    @Override
    public String getRole(Show credit) {
        boolean isShowrunner = this.equals(credit.getShowrunner());
        String role = "";
        if (!isShowrunner) role = "actor";
        else {
            if (credit instanceof Movie) role = "director";
            if (credit instanceof Series) role = "creator";
        }
        return role;
    }

   //LOGIC
    @Override
    public void addBio(String date, String placeOfBirth) throws BioAlreadyExistsException {
        if (this.dateOfBirth != null || this.placeOfBirth != null) throw new BioAlreadyExistsException();
        this.dateOfBirth = date;
        this.placeOfBirth = placeOfBirth;

    }
    @Override
    public void addCredits(Show show) {
        credits.add(show);
    }

    @Override
    public int compareTo(Artist other) {
        return name.compareTo(other.getName());
    }
}
