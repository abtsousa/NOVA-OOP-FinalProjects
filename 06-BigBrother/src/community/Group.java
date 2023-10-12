package community;
import community.citizen.Citizen;
import dataStructures.*;

/**
 * Group interface
 * Methods that manage groups objects
 * Manages members and shares gossips
 */
public interface Group {

    /**
     * Returns the number of members in the group
     * @return number of members in the group
     */
    int getSize();

    /**
     * Returns citizen array of group's members
     * @return citizen array of group's members
     */
    Array<Citizen> getMembers();

    /**
     * Checks if a person is a member of the group
     * @param citizen citizen being checked
     * @return true if the person is a member of the group
     */
    boolean hasCitizen(Citizen citizen);

    /**
     * Checks if the group has no members
     * @return true if the group has no members
     */
    boolean isEmpty();

    /**
     * Lists all people part of the group
     * @return iterator with group members
     */
    Iterator<Citizen> listMembers();

    /**
     * Joins a person to a group
     * @param citizen new member
     */
    void addToGroup(Citizen citizen);

    /**
     * Removes the person from the group
     * pre person's is part of the group
     * @param outcast person being removed from the group
     *                pre: must be in group
     */
    void removeFromGroup(Citizen outcast);

    /**
     * Share gossip within the current group in the current landmark
     * @param gossiper person's name sharing gossip
     * @param gossip gossip itself
     */
    void shareGossipWithGroup(Citizen gossiper, Gossip gossip);
}
