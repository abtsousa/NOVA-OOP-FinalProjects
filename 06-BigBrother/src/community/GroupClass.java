package community;
import community.citizen.Citizen;
import dataStructures.*;

/**
 * Group Class
 */
public class GroupClass implements Group {

   // VARIABLES
    private Array<Citizen> members; //Array of citizen group's members

    /**
     * Constructor
     * @param citizen group's first member
     */
    GroupClass (Citizen citizen) {
        this.members = new ArrayClass<>();
        addToGroup(citizen);
    }

    // METHODS
    //Getter
    @Override
    public int getSize() {return members.size();}

    @Override
    public Array<Citizen> getMembers() {
        return members;
    }

    //Boolean
    @Override
    public boolean hasCitizen(Citizen citizen) {return members.searchForward(citizen);}

    @Override
    public boolean isEmpty() {return getSize()==0;}

    //List
    @Override
    public Iterator<Citizen> listMembers() {return members.iterator();}

    //Functional
    @Override
    public void addToGroup(Citizen citizen) {members.insertLast(citizen);}

    @Override
    public void removeFromGroup(Citizen outcast) {
        int index = members.searchIndexOf(outcast);
        members.removeAt(index);
    }

    @Override
    public void shareGossipWithGroup(Citizen gossiper, Gossip gossip) {
        Iterator<Citizen> it = listMembers();

        while (it.hasNext()) {
            Citizen c = it.next();
            if (!c.getName().equals(gossiper.getName())) {
                c.hearGossip(gossip);
            }
        }
    }
}