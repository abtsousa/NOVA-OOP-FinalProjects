package community;
import community.citizen.Citizen;
import dataStructures.*;

/**
 * Landmark interface
 * Methods that manage landmark objects and some group managment
 * Landmarks add && remove citizens, create && delete groups
 */
public interface Landmark {

    /**
     * Returns the landmark's name
     * @return landmark's name
     */
    String getName();

    /**
     * Returns the landmark's max people capacity
     * @return landmark's max people capacity
     */
    int getMaxCapacity();

    /**
     * Returns the landmark's current occupation of people
     * @return landmark's current occupation of people
     */
    int countPeople();

    /**
     * Returns the landmark's number of groups
     * @return landmark's number of groups
     */
    int countGroups();

    /**
     * Returns a group (list of people together)
     * @param citizen citizen in the group
     *                pre: citizen must be in landmark
     * @return the group where the citizen is
     */
    Group getGroup(Citizen citizen);

    /**
     * Checks if the landmark has full capacity of people
     * @return true if the landmark has full of people
     */
    boolean isFull();

    /**
     * Checks if the landmark is empty of people
     * @return true if the landmark empty of people
     */
    boolean isEmpty();

    /**
     * List the groups inside the landmark
     * @return a list of the groups inside the landmark
     */
    Iterator<Group> listGroups();

    /**
     * Moves a citizen to the landmark who creates a group alone
     * There's no need for an addGroup method, as groups are added only if a citizen joins the
     * landmark ( .addCitizen() ) or is isolated from its current group ( .isolate() )
     * pre hasCitizen(citizen)
     * @param citizen citizen entering the landmark and creaing a group alone
     *                Citizen must not be in landmark (citizen.getLocation() != this)
     */
    void addCitizen(Citizen citizen);

    /**
     * Removes citizen from the landmark and the previous citizen's group
     * pre hasCitizen(citizen)
     * pre Citizen must be in the landmark
     * @param citizen citizen being removed from the landmark and the previous citizen's group
     *                pre: citizen must be in landmark
     */
    void removeCitizen(Citizen citizen);

    /**
     * Makes a person leave the current group, but not the landmark the person is currently on
     * @param citizen citizen being isolated
     */
    void isolate (Citizen citizen);

    /**
     * Joins a person to a group
     * pre isInSameGroup
     * @param name_From person's name joining
     * @param name_To person's name in the landmark
     */
    void moveToGroup(Citizen name_From, Citizen name_To);
}
