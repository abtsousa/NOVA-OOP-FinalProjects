package community;
import community.citizen.*;
import dataStructures.*;

/**
 * Big Brother Interface
 * Methods that manage community (citizens, landmarks, groups, gossips)
 * Intermediates between Main and other classes
 * Manages, lists and verifies information of other classes
 */
public interface BigBrother {

    /**
     * Returns the citizen
     * @param citizen the wanted citizen's name
     * @return the citizen
     */
    Citizen getCitizen(String citizen);

    /**
     * Converts String Array of citizen's names to Citizen Array
     * pre hasCitizen(citizen) to all citizen's names
     * @param citizens array of provided citizens' names
     * @return array of citizens
     */
    Array<Citizen> getMultipleCitizens(Array<String> citizens);

    /**
     * Returns the landmark
     * @param landmark the wanted landmark's name
     * @return the landmark
     */
    Landmark getLandmark(String landmark);

    /**
     * Returns the number of all gossips that exist in the community
     * @return number of all gossips that exist in the community
     */
    int getNumberGossips();

    /**
     * Returns the number of most shared gossip(s)'s
     * @return number of most shared gossip(s)'s
     */
    int countHottestGossips();

    /**
     * Checks if all citizens are part of the community
     * @param stringArray Array of all citizens' name
     * @return first found name who isn't a citizen OR
     *         null if all citizens are part of the community
     */
    String whoIsImposter(Array<String> stringArray);

    /**
     * Adds a citizen to the community
     * @param citizen citizen being added
     */
    void addCitizen(Citizen citizen);

    /**
     * Creates a landmark
     * @param capacity landmark's max people capacity
     * @param name landmark's name
     */
    void addLandmark(int capacity, String name);

    /**
     * Checks if a citizen is part of the community
     * @param name citizen's name
     * @return true if the citizen is part of the community
     */
    boolean hasCitizen(String name);

    /**
     * Checks if a landmark exists
     * @param name landmark's name
     * @return true if the landmark exists
     */
    boolean hasLandmark(String name);

    /**
     * Checks from all community's gossips if there is already a gossip with same the creator && targets && description
     * pre bb.hasCitizen(creator)
     * pre bb.hasCitizen(targets)
     * @param creator gossip's creator being compared
     * @param targets targeted citizens being compared
     * @param description gossip's description being compared
     * @return true if there is already a gossip in the community with the same creator && targets && description
     */
    boolean hasGossip(Citizen creator, Array<Citizen> targets, String description);

    /**
     * Checks if cit1 and cit2 are in the same group
     * @param cit1 citizen involved
     * @param cit2 citizen involved
     * @return true if cit1 and cit2 are in the same group
     */
    boolean isInSameGroup(Citizen cit1, Citizen cit2);

    /**
     * Lists all community's citizens
     * @return iterator of all community's citizens
     */
    Iterator<Citizen> listCitizens();

    /**
     * Lists all landmarks
     * @return iterator of all landmarks
     */
    Iterator<Landmark> listLandmarks();

    /**
     * Lists all landmark's groups
     * @param landmark landmark we want to list the groups from
     * @return iterator of the landmark's groups
     */
    Iterator<Group> listGroupsIn(Landmark landmark);

    /**
     * Lists the gossip about a particular person
     * @param citizen the wanted person
     * @return iterator of gossip about a particular person
     */
    Iterator<Gossip> listGossipAbout(Citizen citizen);

    /**
     * Lists the most shared gossip
     * @return list of the most shared gossip
     */
    Iterator<Gossip> listHottestGossip();

    /**
     * Moves a citizen home
     * @param citizen citizen being moved
     */
    void moveCitizenHome(Citizen citizen);

    /**
     * Moves a person to a landmark
     * @param citizen the wanted person
     * @param landmark the landmark the person is moving to
     */
    void moveCitizen(Citizen citizen, Landmark landmark);

    /**
     * Starts a new gossip
     * @param gossip gossip itself
     */
    void startGossip(Gossip gossip);

    /**
     * Gossiper shares the maximum amount of gossips with the group
     * @param gossiper the citizen who's sharing the gossip
     * @param g gossip being shared
     */
    void shareGossip(Citizen gossiper, Gossip g);
}