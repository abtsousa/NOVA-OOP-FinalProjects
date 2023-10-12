package community;
import community.citizen.*;
import dataStructures.*;

/*
      ###.        (##            (##.            ,####          #########
       /###        ###,           (##            ####          ########
         ###        ###(          ####           ####        ########          #
          ###(       ###(          ###           ###        ######(          ###
##          ###       ####         (##(         ####       (#####         ######
####         ##(*      ####/        ###         (##       #####         #######
#####,        (###      #####       (###        (#*      ####        #######
#######         ###      (####       (##        #(     (##(        #####(
#########        ###(     (####      ####      ##     (##       #(###(
##########(       (###     ##&&&#     &&&      &%    ##       ####(
#############       #&&%   &&@                   (&&&      #####            ####
#############((      &#           (((((((((((           *&&&&          (########
###########&&&&&.&             (((*((*(((((((,,(             &&   ##############
#######%&&&&&&                ((*(((((((((((((((((               &&&&&##########
#####&&&&&&                  (((((((@@@@@@@#((((((                  &&&&&#######
########&&&&%                (((((((@@@  @@@((((((                  &&&#########
###########&&&&@             .(((((*(@@@@@#((((*((               &&&############
###############&&&&,           (((((@@@@@@@(((*((            &&&################
######(   ,, #######&&&&         ((@@@@&@@@@((*         &&@(####(###############
#####    ,@.  ##############&&&&/ ,@@@@@@@@@   *&&&(##########,(  ###( **  #####
######   ., ######################@@@@@@@@@@@#########(*##############(  #######
##################.(######(#####(@@@@@@ @@@@@@#######(,/  ######################
##############(  .@  #### , ####@@@@@@@*@@@@@@(######################((*(#######
###############  ..(###########(@@@@@@@@@@@@@@@##################### ,/.   ,####
(   /, ########################@@@@@@@@@@@@@@@@@(######## ,,  ######  ,.    ####
#############################(@@@@@@@@@@@@@@@@@@#########( &   ########   (#####
##########(   ,,  ############@@@@@@@@@ @@@@@@@@@(########((#(##################
########(     ,&,  ##########@@@@@@@@@@ @@@@@@@@@@##############################
###########     ############@@@@@@@@@@@ @@@@@@@@@@(###################  .. #####
###########################@@@@@@@@@@@@@@@@@@@@@@@@########*.. ######### ,    ##
###########################@@@@@@@@@@@@@@@@@@@@@@@@@(####(  ,.     #############
##########################@@@@@@@@@@@@@@@@@@@@@@@@@@############################
██████╗ ██╗ ██████╗     ██████╗ ██████╗  ██████╗ ████████╗██╗  ██╗███████╗██████╗
██╔══██╗██║██╔════╝     ██╔══██╗██╔══██╗██╔═══██╗╚══██╔══╝██║  ██║██╔════╝██╔══██╗
██████╔╝██║██║  ███╗    ██████╔╝██████╔╝██║   ██║   ██║   ███████║█████╗  ██████╔╝
██╔══██╗██║██║   ██║    ██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══██║██╔══╝  ██╔══██╗
██████╔╝██║╚██████╔╝    ██████╔╝██║  ██║╚██████╔╝   ██║   ██║  ██║███████╗██║  ██║
╚═════╝ ╚═╝ ╚═════╝     ╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
██╗███████╗    ██╗    ██╗ █████╗ ████████╗ ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗
██║██╔════╝    ██║    ██║██╔══██╗╚══██╔══╝██╔════╝██║  ██║██║████╗  ██║██╔════╝
██║███████╗    ██║ █╗ ██║███████║   ██║   ██║     ███████║██║██╔██╗ ██║██║  ███╗
██║╚════██║    ██║███╗██║██╔══██║   ██║   ██║     ██╔══██║██║██║╚██╗██║██║   ██║
██║███████║    ╚███╔███╔╝██║  ██║   ██║   ╚██████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝╚══════╝     ╚══╝╚══╝ ╚═╝  ╚═╝   ╚═╝    ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
                        ██╗   ██╗ ██████╗ ██╗   ██╗
                        ╚██╗ ██╔╝██╔═══██╗██║   ██║
                         ╚████╔╝ ██║   ██║██║   ██║
                          ╚██╔╝  ██║   ██║██║   ██║
                           ██║   ╚██████╔╝╚██████╔╝
                           ╚═╝    ╚═════╝  ╚═════╝
 */

/**
 * BigBrotherClass - System Management
 */
public class BigBrotherClass implements BigBrother {

    //VARIABLES
    private Array<Citizen> people; //Array of community's citizens
    private Array<Landmark> places; //Array of community's landmarks
    private Array<Gossip> gossips; //Array of community's gossips

    /**
     * System class - the Big Brother Gossip Tracking Application (trademark pending) is a surveillance
     * system for all your oppressive government needs. It tracks groups of citizens, places of interest, and gossips.
     */
    public BigBrotherClass() {
        people = new ArrayClass<>();
        places = new ArrayClass<>();
        gossips = new ArrayClass<>();
    }

    //METHODS
    //Get
       @Override
    public Citizen getCitizen(String name) {
        Iterator<Citizen> it = listCitizens();
        while (it.hasNext()) {
            Citizen citizen = it.next();
            if (citizen.getName().equals(name)) return citizen;
        }
        return null;
    }

    @Override
    public Array<Citizen> getMultipleCitizens(Array<String> stringArray) {
        Iterator<String> it = stringArray.iterator();
        Array<Citizen> citizens = new ArrayClass<>();
        while (it.hasNext()) {
            citizens.insertLast(getCitizen(it.next()));
        }
        return citizens;
    }

    @Override
    public Landmark getLandmark(String name) {
        Iterator<Landmark> it = listLandmarks();
        while (it.hasNext()) {
            Landmark place = it.next();
            if (place.getName().equals(name)) return place;
        }
        return null;
    }

    @Override
    public int getNumberGossips() {return gossips.size();}

    @Override
    public int countHottestGossips() {
        Iterator<Gossip> it = listGossips();
        int maxShares = 0;

        while (it.hasNext()) { //Finding the hottest gossips
            Gossip gossip = it.next();
            if (gossip.howManyShares() > maxShares) {
                maxShares = gossip.howManyShares();
            }
        }
        return maxShares;
    }

    @Override
    public String whoIsImposter(Array<String> stringArray) {
        Iterator<String> it = stringArray.iterator();
        boolean found = false;
        String imposter = null;
        while (it.hasNext() && !found) {
            String name = it.next();
            found = !hasCitizen(name);
            if (found) {
                imposter = name;
            }
        }
        return imposter;
    }

    //Add
    @Override
    public void addCitizen(Citizen citizen) { //LimitedMemory
        people.insertLast(citizen);
    }

    @Override
    public void addLandmark(int capacity, String name) {
        places.insertLast(new LandmarkClass(name, capacity));
    }

    //Boolean
    @Override
    public boolean hasCitizen(String name) {
        Iterator<Citizen> it = listCitizens();
        boolean found = false;
        while (it.hasNext() && !found) {
            found = it.next().getName().equals(name);
        }
        return found;
    }

    @Override
    public boolean hasLandmark(String name) {
        if (name.equals("home")) return true;
        Iterator<Landmark> it = listLandmarks();
        boolean found = false;
        while (it.hasNext() && !found) {
            found = it.next().getName().equals(name);
        }
        return found;
    }

    @Override
    public boolean hasGossip(Citizen creator, Array<Citizen> targets, String description) {
        Iterator<Gossip> it = listGossips();
        boolean same = false;
        while (it.hasNext() && !same) {
            same = it.next().compareTo(creator, targets, description);
        }
        return same;
    }

    @Override
    public boolean isInSameGroup(Citizen cit1, Citizen cit2) {
        Landmark land1 = cit1.getLocation();
        Landmark land2 = cit2.getLocation();
        return (land1.equals(land2) && land1.getGroup(cit1) == land2.getGroup(cit2));
    }

    //List
    @Override
    public Iterator<Citizen> listCitizens() {return people.iterator();}

    @Override
    public Iterator<Landmark> listLandmarks() {return places.iterator();}

    @Override
    public Iterator<Group> listGroupsIn(Landmark landmark) {
        return landmark.listGroups();
    }

    /**
     * Lists all community's gossips
     * @return list of all community's gossips
     */
    private Iterator<Gossip> listGossips() {return gossips.iterator();}

    @Override
    public Iterator<Gossip> listGossipAbout(Citizen citizen) {
        Array<Gossip> filtered = new ArrayClass<>();

        Iterator<Gossip> it = listGossips();

        while (it.hasNext()) {
            Gossip gossip = it.next();

            if (gossip.hasTarget(citizen)) {
                filtered.insertLast(gossip);
            }
        }
        return filtered.iterator();
    }

    @Override
    public Iterator<Gossip> listHottestGossip() {
        Array<Gossip> hottest = new ArrayClass<>();
        Iterator<Gossip> it = listGossips();

        while (it.hasNext()) { //Listing the hottest gossips
            Gossip gossip = it.next();

            if (gossip.howManyShares() == countHottestGossips()) {
                hottest.insertLast(gossip);
            }
        }
        return hottest.iterator();
    }

    //Functional
    @Override
    public void moveCitizen(Citizen citizen, Landmark landmark) {
        if (!citizen.isHome()) {
            citizen.getLocation().removeCitizen(citizen);
        }
        landmark.addCitizen(citizen);
        citizen.goTo(landmark);
    }

    @Override
    public void moveCitizenHome(Citizen citizen) {
        if (!citizen.isHome()) {
            citizen.getLocation().removeCitizen(citizen);
        }
        citizen.goHome();
    }



    //Gossip Management
    @Override
    public void startGossip(Gossip gossip) {
        gossips.insertLast(gossip);
        Citizen creator = gossip.getCreator();
        creator.hearGossip(gossip);
        removeForgottenGossips();
    }

    @Override
    public void shareGossip(Citizen sharer, Gossip g) {
        Landmark l = sharer.getLocation();
        Group group = l.getGroup(sharer);
        group.shareGossipWithGroup(sharer, g);
        g.addToShareChain(sharer);
        sharer.updateGossipIndex(g);
        removeForgottenGossips();
    }

    /**
     * Garbage collector
     * Checks if all community's gossips are remembered by someone
     * Removes all gossips that are remembered by no one
     */
    private void removeForgottenGossips() {
        Iterator<Gossip> spissog = new ReverseIterator<>(gossips);
    //scans gossips in reverse order so when removed there's no ArrayIndexOutOfBounds exception

        while (spissog.hasNext()) {
            Gossip g = spissog.next();
            if (g.isForgotten()) {
                int index = gossips.searchIndexOf(g);
                gossips.removeAt(index);
            }
        }
    }
}