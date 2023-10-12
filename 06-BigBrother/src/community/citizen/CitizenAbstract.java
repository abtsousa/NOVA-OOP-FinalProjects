package community.citizen;
import community.*;
import dataStructures.*;

abstract class CitizenAbstract implements Citizen {

    //CONSTANTS
    private static final Landmark HOME_LOCATION = null; //Designated value for Home

    //VARIABLES
    final String name; //Citizen's name
    int loquacity; //How many consecutive gossips the citizen shares at a time
    int nextGossipIndex; //Index that tracks the next gossip to share
    private Landmark location; //Citizen's current location
    Array<Gossip> rememberedGossips; //Gossips the citizen remembers

    // CONSTRUCTOR
    CitizenAbstract(String name) {
        this.name = name;
        this.location = HOME_LOCATION; //Starts at home
        this.rememberedGossips = new ArrayClass<>();
        nextGossipIndex = 0; //Starts sharing from the oldest gossip
    }

    // METHODS
    //Getters
    @Override
    public String getName() {return name;}

    @Override
    public Landmark getLocation() {return location;}

    @Override
    public String getLocationName() {
        if (isHome()) {return "home";}
        else return getLocation().getName();
    }

    @Override
    public int getLoquacity() {return loquacity;}

    @Override
    public int countRememberedGossips() {return rememberedGossips.size();}

    //Boolean
    @Override
    public boolean isHome() {return location == HOME_LOCATION;}

    @Override
    public boolean isAlone() {
        if (isHome()) return true;
        else {
            Group group = location.getGroup(this);
            return (group.getSize()==1);
        }
    }

    @Override
    public boolean hasGossipsToShare() {return listRememberedGossips().hasNext();}

    //List
    @Override
    public Iterator<Gossip> listRememberedGossips() {return rememberedGossips.iterator();}

    @Override
    public Iterator<Gossip> listGossipsSharingOrder() {
        return new OffsetIterator<>(rememberedGossips,nextGossipIndex);
    }

    //Functional
    @Override
    public void goHome() {location = HOME_LOCATION;}

    @Override
    public void goTo(Landmark landmark) {location = landmark;}

    @Override
    public void updateGossipIndex(Gossip g) {
        int index = rememberedGossips.searchIndexOf(g);
        nextGossipIndex = (++index) % rememberedGossips.size(); //add 1 to nextShare and loop if it reaches the end
    }

    @Override
    public void hearGossip(Gossip gossip) {
        if (!rememberedGossips.searchForward(gossip)) { //if the gossip isn't already in the array <=> if the citizen doesn't know about the gossip
            rememberedGossips.insertLast(gossip);
            gossip.addToRememberedBy(this);
        }
    }
}