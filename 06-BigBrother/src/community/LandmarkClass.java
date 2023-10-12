package community;
import community.citizen.*;
import dataStructures.*;

/**
 * Landmark Class
 */
public class LandmarkClass implements Landmark {

    //VARIABLES
    private final String name; //Landmark's name
    private final int maxCapacity; //Landmark's maximum citizen capacity
    private int occupation; //Landmark's current citizen capacity
    private Array<Group> groups; //Array of citizen in the landmark

    /**
     * Landmarks are places where people meet. At any given moment, a person is, at most, in only one landmark.
     * Landmarks also manage their persons' groups. People can move from one group to another within the landmark,
     * by joining a person within that group. A person can also leave a group, becoming alone within that landmark.
     * @param name landmark's name
     * @param maxCapacity landmark's max capacity of people
     */
    LandmarkClass(String name, int maxCapacity)  {
        this.name=name;
        this.maxCapacity=maxCapacity;
        this.occupation = 0;
        this.groups = new ArrayClass<>();
    }

    //METHODS
    //Get
    @Override
    public String getName() {return name;}

    @Override
    public int getMaxCapacity() {return maxCapacity;}

    @Override
    public int countPeople() {return occupation;}

    @Override
    public int countGroups() {
        return groups.size();
    }

    @Override
    public Group getGroup(Citizen citizen) {
        Iterator<Group> it = listGroups();
        boolean found = false;
        Group group = null;
        while (it.hasNext() && !found) {
            group = it.next();
            found = group.hasCitizen(citizen);
        }
        return group;
    }

    //Boolean
    @Override
    public boolean isFull() {return countPeople()==getMaxCapacity();}

    @Override
    public boolean isEmpty() {return countPeople()==0;}

    //List
    @Override
    public Iterator<Group> listGroups() {return groups.iterator();}

    //Functional
    @Override
    public void addCitizen(Citizen citizen) {
        Group group = new GroupClass(citizen);
        if (citizen instanceof CitizenLimitedMemory) {
            ((CitizenLimitedMemory) citizen).resetGossipIndex();}
        groups.insertLast(group);
        occupation++;
    }

    @Override
    public void removeCitizen(Citizen citizen) {
        Group g = getGroup(citizen);
        g.removeFromGroup(citizen);
        if (g.isEmpty()) {deleteGroup(g);}
        occupation--;
    }

    @Override
    public void isolate (Citizen citizen) {
    getGroup(citizen).removeFromGroup(citizen); // Removes from current group
    Group group = new GroupClass(citizen);
    groups.insertLast(group);
    }

    @Override
    public void moveToGroup(Citizen citFrom, Citizen citTo) {
        Group groupFrom = getGroup(citFrom);
        Group groupTo = getGroup(citTo);

        groupFrom.removeFromGroup(citFrom);
        groupTo.addToGroup(citFrom);

        if (groupFrom.isEmpty()) {deleteGroup(groupFrom);}
        if (citFrom instanceof CitizenLimitedMemory) {
            ((CitizenLimitedMemory) citFrom).resetGossipIndex();}
    }

    /**
     * Garbage collector - deletes empty groups as identified by BigBrotherClass
     * @param g group being deleted
     */
    private void deleteGroup(Group g) {
        int i = groups.searchIndexOf(g);
        groups.removeAt(i);
    }
}