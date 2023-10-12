/**
 * A gossip tracking application
 *  @author Afonso Brás Sousa (LEI n.º 65263)
 *  @author Alexandre Cristóvão (LEI n.º 65143)
 */

import community.*;
import community.citizen.*;
import dataStructures.*;
import java.util.Scanner;

/**
 * Main Class
 * Executes the Big Brother Gossip Tracking Application
 */
public class Main {

    //Valid commands
    private enum Command {
        //FUNCTIONAL
        EXIT, HELP, UNKNOWN,
        //ADD
        LANDMARK, FORGETFUL, GOSSIPER, SEALED,
        //GOSSIP MANAGEMENT
        GOSSIP, START,
        //LIST
        PEOPLE, GROUPS, SECRETS, INFOTAINMENT, HOTTEST, LANDMARKS,
        //MOVE
        ISOLATE, JOIN, GO
    }

    //Output messages
    private enum MSG {
        //CONSTANTS
        UNKNOWN_COMMAND("Unknown command. Type help to see available commands.\n"),
        LANDMARK_CANNOT_CREATE("Cannot create a landmark called home. You know, there is no place like home!\n"),
        LANDMARK_NONE("This community does not have any landmarks yet!\n"),
        CITIZENS_NONE("This community does not have any people yet!\n"),
        HOME_NO_SURVEILLANCE("You must understand we have no surveillance tech at home! Privacy is our top concern!\n"),
        GOSSIP_DUPLICATE("Duplicate gossip!\n"),
        GOSSIP_NOT_KNOWN("No gossips we are aware of!\n"),
        GOSSIP_NOT_SHARED("No gossips were shared, yet!\n"),
        EXIT("Bye!\n"),

        //Messages with parameters
        //Must call them with MSG.NAME_OF_MESSAGE.msg in printf to be used as format strings
        //Unlike .print() .printf() doesn't call .toString() on the first argument
        INVALID_LANDMARK_CAPACITY("Invalid landmark capacity %d!\n"),
        INVALID_GOSSIPS_CAPACITY("Invalid gossips capacity %d!\n"),
        CITIZEN_ALREADY_EXISTS("%s already exists!\n"),
        CITIZEN_NO_EXIST("%s does not exist!\n"),
        LANDMARK_NO_EXIST("Unknown place %s!\n"),
        LANDMARK_ALREADY_EXISTS("Landmark %s already exists!\n"),
        LANDMARK_ALREADY_THERE("What do you mean go to %s? %s is already there!\n"),
        LANDMARK_FULL("%s is too crowded! %s went home.\n"),
        LANDMARK_EMPTY("Nobody is at %s!\n"),
        LANDMARK_NOT_IN("%s is not in %s!\n"),
        AT_HOME("%s is at home!\n"),
        ALREADY_ALONE("%s is already alone!\n"),
        NOW_ALONE("%s is now alone at %s.\n"),
        NOW_AT("%s is now at %s.\n"),
        OTHER_COMPANY("%s needs company from someone else!\n"),
        GOSSIP_HOTTEST("The hottest gossips were shared %d times!\n"),
        GROUP_SAME("%s and %s are already in the same group!\n"),
        GROUP_JOIN("%s joined %s, at the %s.\n"),
        GOSSIP_TARGET_INVALID_NUMBER("Invalid number %d of gossip targets!\n"),
        GOSSIP_SHARE_NOBODY("%s has nobody to gossip with right now!\n"),
        BORING_LIFE("%s lives a very boring life!\n"),
        KNOWS_NOTHING("%s knows nothing!\n"),
        KNOWS_THINGS("%s knows things:\n"),
        KNOW_ABOUT_SOMEONE("Here is what we know about %s:\n"),
        NO_SNITCH("%s does not wish to gossip right now!!\n"),
        SHARE_GOSSIP("%s shared with %s, some hot news!\n"),
        START_GOSSIP("Have you heard about %s? %s\n"),
        SUCCESS_LANDMARK("%s added.\n"),
        SUCCESS_PEOPLE("%s at %s knows %d gossips.\n"),
        SUCCESS_SECRETS("%s %s\n");

        private final String msg;

        @Override
        public String toString() {return msg;}

        MSG(String msg) {this.msg = msg;}
    }

    /**
     * Main program
     * Executes the Big Brother Gossip Tracking Application
     * Manages user input and program output
     * @param args - arguments to execute the app. None are used.
     */
    public static void main(String[] args) {
        BigBrother bb = new BigBrotherClass();
        Scanner input = new Scanner(System.in);
        processCommands(bb,input);
        input.close();
    }

    //METHODS
    //USER COMMAND MANAGEMENT
    private static Command getCommand(Scanner in) {
        try {
            String cmd = in.next().toUpperCase();
            return Command.valueOf(cmd);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }

    private static void processCommands(BigBrother bb, Scanner in)   {
        Command command;
        do {
            command = getCommand(in);
            processCommand(bb, command, in);
        } while (!command.equals(Command.EXIT));
    }

    private static void processCommand(BigBrother bb, Command command, Scanner input) {
        switch (command) {
            case LANDMARK -> addLandmark(input, bb);
            case FORGETFUL -> addCitizenLimitedMemory(input, bb, Type.FORGETFUL);
            case GOSSIPER -> addCitizenUnlimitedMemory(input, bb, Type.GOSSIPER);
            case SEALED -> addCitizenUnlimitedMemory(input, bb, Type.SEALEDLIPS);
            case GOSSIP -> shareGossip(input, bb);
            case START -> startGossip(input, bb);
            case PEOPLE -> listCitizens(bb);
            case GROUPS -> listGroups(input, bb);
            case SECRETS -> listSecrets(input, bb);
            case INFOTAINMENT -> listInfotainment(input, bb);
            case HOTTEST -> listHottestGossip(bb);
            case LANDMARKS -> listLandmarks(bb);
            case ISOLATE -> isolate(input, bb);
            case JOIN -> joinGroup(input, bb);
            case GO -> goTo(input, bb);
            case HELP -> listCommands();
            case EXIT -> System.out.print(MSG.EXIT);
            case UNKNOWN -> System.out.print(MSG.UNKNOWN_COMMAND);
        }
    }

    //ADD CMDs
    /**
     * LANDMARK COMMAND
     * Adds a new landmark to the community
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void addLandmark(Scanner in, BigBrother bb) {
        //Inputs
        int capacity = in.nextInt();
        String name = in.nextLine().trim();

        //Exceptions
        if (capacity <= 0) { //Check if landmark's capacity not positive
            System.out.printf(MSG.INVALID_LANDMARK_CAPACITY.msg, capacity);}

        else if (name.equals("home")) { //Check if landmark's name == home
            System.out.printf(MSG.LANDMARK_CANNOT_CREATE.msg);}

        else if (bb.hasLandmark(name)) { //Check if landmark already exists
            System.out.printf(MSG.LANDMARK_ALREADY_EXISTS.msg, name);}

        else {
            bb.addLandmark(capacity, name); //Adds landmark
            System.out.printf(MSG.SUCCESS_LANDMARK.msg,name); //Print feedback message
        }
    }

    /**
     * FORGETFUL COMMAND
     * Adds a citizen with limited memory
     * @param in scanner for user input
     * @param bb BigBrother instance
     * @param type of citizen
     */
    private static void addCitizenLimitedMemory(Scanner in, BigBrother bb, Type type) {
        //Inputs
        int memory = in.nextInt();
        String name = in.nextLine().trim();

        //Exceptions
        if (memory <= 0) { //Check if gossip's capacity not positive
            System.out.printf(MSG.INVALID_GOSSIPS_CAPACITY.msg, memory);}

        else if (bb.hasCitizen(name)) { //Check if citizen's name already exists
            System.out.printf(MSG.CITIZEN_ALREADY_EXISTS.msg, name);}

        else {
            //Adds citizen
            Citizen citizen = new CitizenForgetful(name, memory);
            bb.addCitizen(citizen);
            System.out.println(citizen); //Prints feedback message
        }
    }

    /**
     * GOSSIPER && SEALED COMMANDS
     * Adds a citizen with unlimited memory
     * @param in scanner for user input
     * @param bb BigBrother instance
     * @param type of citizen
     */
    private static void addCitizenUnlimitedMemory(Scanner in, BigBrother bb, Type type) {
        //Input
        String name = in.nextLine().trim();

        //Exception
        if (bb.hasCitizen(name)) { //Check if citizen's name already exists
            System.out.printf(MSG.CITIZEN_ALREADY_EXISTS.msg, name);}

        else {
            Citizen citizen = null;

            //Adds citizen
            //Modular and easily extensible
            switch(type) {
                case GOSSIPER -> citizen = new CitizenGossiper(name);
                case SEALEDLIPS -> citizen = new CitizenSealedLips(name);
            }
            bb.addCitizen(citizen);
            System.out.println(citizen); //Prints feedback message
        }
    }

    //GOSSIP CMDs
    /**
     * GOSSIP COMMAND
     * Share a gossip within the current group in the current landmark
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void shareGossip(Scanner in, BigBrother bb) {
        //Inputs
        String name = in.nextLine().trim();
        Citizen cit = bb.getCitizen(name);

        //Exceptions
        if (!bb.hasCitizen(name)) { //Check if citizen's name already exists
            System.out.printf(MSG.CITIZEN_NO_EXIST.msg, name);}

        else if (cit.isAlone()) { //Check if citizen is alone
            System.out.printf(MSG.GOSSIP_SHARE_NOBODY.toString(), name);}

        else if (cit.countRememberedGossips()==0) { //Check if citizen knows no gossips
            System.out.printf(MSG.KNOWS_NOTHING.toString(), name);}

        else if (!cit.hasGossipsToShare()) { //Check if citizen won't share gossip
            System.out.printf(MSG.NO_SNITCH.toString(), name);}

        else {
            //Print feedback message
            Group group = cit.getLocation().getGroup(cit);
            System.out.printf(MSG.SHARE_GOSSIP.msg, name, printGroup(group, cit));
            Iterator<Gossip> gos;
            if (cit instanceof CitizenNarcissist) gos = ((CitizenNarcissist) cit).listShareableGossips();
            else gos = cit.listGossipsSharingOrder();

            //Share & print gossips
            for (int i =0; gos.hasNext() && i<cit.getLoquacity();i++)   {
                Gossip g = gos.next();
                bb.shareGossip(cit, g);
                System.out.printf(g.getDescription()+"\n");
            }
        }
    }

    /**
     * START COMMAND
     * Starts a new gossip
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void startGossip(Scanner in, BigBrother bb) {
        //Inputs
        String creator = in.nextLine().trim();
        int targetN = in.nextInt();
        in.nextLine();

        //Creates array of possible targets
        Array<String> potentialTargets = new ArrayClass<>();
        for (int i = 0; i < targetN; i++) {
            String target = in.nextLine().trim();
            potentialTargets.insertLast(target);
        }

        String description = in.nextLine().trim();

        //Exceptions
        if (!bb.hasCitizen(creator)) { //Check if creator exists
            System.out.printf(MSG.CITIZEN_NO_EXIST.toString(), creator);}

        else if (targetN <= 0) { //Check if number of targets <=0
            System.out.printf(MSG.GOSSIP_TARGET_INVALID_NUMBER.toString(), targetN);}

        else { //Check if all targets exist
            Iterator<String> it = potentialTargets.iterator();
            String imposter = bb.whoIsImposter(potentialTargets);
            boolean allTargetsExist = imposter==null;

            if (!allTargetsExist) {
                System.out.printf(MSG.CITIZEN_NO_EXIST.toString(), imposter);

            } else { //Check if gossip is duplicate
                Array<Citizen> trueTargets = bb.getMultipleCitizens(potentialTargets);
                if (bb.hasGossip(bb.getCitizen(creator), trueTargets, description)) {
                    System.out.printf(MSG.GOSSIP_DUPLICATE.toString());}

                else {
                    //Start gossip
                    bb.startGossip(new GossipClass(bb.getCitizen(creator), trueTargets, description));

                    //Print feedback message
                    System.out.printf(MSG.START_GOSSIP.msg,printIterator(trueTargets.iterator()),description);
                }
            }
        }
    }

    //LIST CMDs
    /**
     * PEOPLE COMMAND
     * Lists all the persons in the community
     * @param bb BigBrother instance
     */
    private static void listCitizens(BigBrother bb) {
        Iterator<Citizen> it = bb.listCitizens();

        //Exception
        if (!it.hasNext()) { //Check if there are any citizens
            System.out.print(MSG.CITIZENS_NONE);}

        else {
            //List Citizens
            while (it.hasNext()) {
                Citizen c = it.next();
                String location = c.getLocationName();
                System.out.printf(MSG.SUCCESS_PEOPLE.toString(), c.getName(), location, c.countRememberedGossips());
            }
        }
    }

    /**
     * GROUPS COMMAND
     * Lists the groups composition in a landmark
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void listGroups(Scanner in, BigBrother bb) {
        //Input
        String landmark = in.nextLine().trim();

        //Exceptions
        if (landmark.equals("home")) { //Check if it is at home
            System.out.print(MSG.HOME_NO_SURVEILLANCE);}

        else if (!bb.hasLandmark(landmark)) { //Check if landmark exists
            System.out.printf(MSG.LANDMARK_NO_EXIST.toString(), landmark);}

        else if (bb.getLandmark(landmark).isEmpty()) { //Check if landmark is empty of people
            System.out.printf(MSG.LANDMARK_EMPTY.toString(), landmark);}

        else {
            //Lists Groups
            Landmark land = bb.getLandmark(landmark);
            Iterator<Group> it = bb.listGroupsIn(land);
            System.out.printf("%d groups at %s:\n", land.countGroups(), landmark);
            while (it.hasNext()) {
                Group g = it.next();
                System.out.println(printGroup(g,null));
            }
        }
    }

    /**
     * SECRETS COMMAND
     * Lists the gossip about a particular person
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void listSecrets(Scanner in, BigBrother bb) {
        //Input
        String person = in.nextLine().trim();

        if (!bb.hasCitizen(person)) { //Check if the person’s name does not exist in the community
            System.out.printf(MSG.CITIZEN_NO_EXIST.toString(), person);}

        else { //If the person has no secrets
            Iterator<Gossip> it = bb.listGossipAbout(bb.getCitizen(person));
            if (!it.hasNext()) {
                System.out.printf(MSG.BORING_LIFE.msg, person);}

            else {
                //Print feedback message
                System.out.printf(MSG.KNOW_ABOUT_SOMEONE.toString(), person);

                //Print person's secrets
                while (it.hasNext()) {
                    Gossip gossip = it.next();
                    System.out.printf(MSG.SUCCESS_SECRETS.toString(), gossip.countRememberedBy(), gossip.getDescription());
                }
            }
        }
    }

    /**
     * INFOTAINMENT
     * Lists the gossips a particular person is aware of
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void listInfotainment(Scanner in, BigBrother bb) {
        //Input
        String person = in.nextLine().trim();

        //Exceptions
        if (!bb.hasCitizen(person)) { //Check if the person’s name does not exist in the community
            System.out.printf(MSG.CITIZEN_NO_EXIST.msg, person);}

        else { //Check if the person knows nothing
            Citizen cit = bb.getCitizen(person);
            Iterator<Gossip> it = cit.listGossipsSharingOrder();
            if (!it.hasNext()) {System.out.printf(MSG.KNOWS_NOTHING.msg, person);}

            else {
                //Print feedback message
                System.out.printf(MSG.KNOWS_THINGS.msg, person);

                //Print gossips the person knows
                while (it.hasNext()) {
                    Gossip gossip = it.next();
                    System.out.println(gossip.getDescription());
                }
            }
        }
    }

    /**
     * HOTTEST COMMAND
     * Lists the most shared gossip
     * @param bb BigBrother instance
     */
    private static void listHottestGossip(BigBrother bb) {

        Iterator<Gossip> it = bb.listHottestGossip();

        int hotness = bb.countHottestGossips();

        //Exceptions
        if (bb.getNumberGossips()==0) { //If there are no known gossips
            System.out.print(MSG.GOSSIP_NOT_KNOWN);
        } else {

            if (hotness == 0) { //If no gossip was shared
                System.out.print(MSG.GOSSIP_NOT_SHARED);
            } else {
                //Print feedback message
                System.out.printf(MSG.GOSSIP_HOTTEST.msg, hotness);

                //List
                while (it.hasNext()) {
                    Gossip hot = it.next();
                    System.out.print(hot.getDescription()+"\n");
                }
            }
        }
    }

    /**
     * LANDMARKS COMMAND
     * displays the list of landmarks in the community
     * @param bb BigBrother instance
     */
    private static void listLandmarks(BigBrother bb) {
        Iterator<Landmark> it = bb.listLandmarks();

        //Exceptions
        if (!it.hasNext()) { //Check if there are no landmarks
            System.out.print(MSG.LANDMARK_NONE);}

        else {
            //List
            while (it.hasNext()) {
                Landmark l = it.next();
                System.out.printf("%s: %d %d.\n", l.getName(), l.getMaxCapacity(), l.countPeople());
            }
        }
    }

    //MOVEMENT CMDs
    /**
     * ISOLATE PROGRAM
     * Makes a person leave the current group, but not the landmark the person is currently on
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void isolate(Scanner in, BigBrother bb) {
        //Input
        String name = in.nextLine().trim();

        //Exceptions
        if (!bb.hasCitizen(name)) { //Check if the person’s name does not exist in the community
            System.out.printf(MSG.CITIZEN_NO_EXIST.msg, name);
        } else {
            Citizen cit = bb.getCitizen(name);
            Landmark land = cit.getLocation();

            if (cit.isHome()) { //Check if the person is at home
                System.out.printf(MSG.AT_HOME.msg, name);}

            else if (land.getGroup(cit).getSize() == 1) { //Check if the person is already alone
                System.out.printf(MSG.ALREADY_ALONE.msg, name);}

            else {
                //Isolate
                land.isolate(cit);

                //Print feedback message
                System.out.printf(MSG.NOW_ALONE.msg, name, land.getName());
            }
        }
    }

    /**
     * JOIN COMMAND
     * Joins a person to a group
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void joinGroup(Scanner in, BigBrother bb) {
        //Inputs
        String nameFrom = in.nextLine().trim();
        String nameTo = in.nextLine().trim();

        //Exceptions
        if (nameFrom.equals(nameTo)) { //Check if the first and the second person are the same,
            System.out.printf(MSG.OTHER_COMPANY.msg, nameFrom);}

        else if (!bb.hasCitizen(nameFrom)) { //Check if the first person’s name does not exist in the community
            System.out.printf(MSG.CITIZEN_NO_EXIST.msg, nameFrom);}

        else if (!bb.hasCitizen(nameTo)) { //Check if the second person’s name does not exist in the community
            System.out.printf(MSG.CITIZEN_NO_EXIST.msg, nameTo);}

        else {
            Citizen citFrom = bb.getCitizen(nameFrom);
            Citizen citTo = bb.getCitizen(nameTo);
            Landmark landFrom = citFrom.getLocation();
            Landmark landTo = citTo.getLocation();

            if (citFrom.isHome()) { //Check if the first person is at home, rather than in a landmark
                System.out.printf(MSG.AT_HOME.msg, nameFrom);}

            else if (!landFrom.equals(landTo)) { //Check if the second person is not in the same landmark
                System.out.printf(MSG.LANDMARK_NOT_IN.msg, nameTo, landFrom.getName());}

            else if (bb.isInSameGroup(citFrom, citTo)) { //Check if the first person and the second person are already in the same group
                System.out.printf(MSG.GROUP_SAME.msg, nameFrom, nameTo);}

            else {
                //Print feedback message
                Group groupTo = landTo.getGroup(citTo);
                System.out.printf(MSG.GROUP_JOIN.msg, nameFrom, printGroup(groupTo, null), landTo.getName());
                landFrom.moveToGroup(citFrom, citTo); //Join
            }
        }
    }

    /**
     * GO COMMAND
     * Moves a person to a landmark, or home
     * @param in scanner for user input
     * @param bb BigBrother instance
     */
    private static void goTo(Scanner in, BigBrother bb) {
        //Inputs
        String citizen = in.nextLine().trim();
        String landmark = in.nextLine().trim();

        //Exceptions
        if (!bb.hasCitizen(citizen)) { ///Check if the person’s name does not exist in the community
            System.out.printf(MSG.CITIZEN_NO_EXIST.msg, citizen);}

        else if (!bb.hasLandmark(landmark)) { //Check if the place the person is going is not a known landmark in the community
            System.out.printf(MSG.LANDMARK_NO_EXIST.msg, landmark);}

        else {
            Citizen cit = bb.getCitizen(citizen);
            Landmark landTo = bb.getLandmark(landmark);
            String landFromName = cit.getLocationName().toUpperCase();

            if (landFromName.equals(landmark.toUpperCase())) { //Check if the person decides to go to the place the person is already in
                System.out.printf(MSG.LANDMARK_ALREADY_THERE.msg, landmark, citizen);}

            else if (landTo != null && landTo.isFull()) { //If not going home and landmark is full
                if (!cit.isHome()) {bb.moveCitizenHome(cit);} //If not at home, then go home
                System.out.printf(MSG.LANDMARK_FULL.msg, landmark, citizen);}

            else {
                if (!landmark.equals("home")) { //Check if not going home
                    bb.moveCitizen(cit, landTo);} //Go to landmark...
                else bb.moveCitizenHome(cit); //...else go home

                //Print feedback message
                System.out.printf(MSG.NOW_AT.msg, citizen, cit.getLocationName());
            }
        }
    }

    //OTHER CMDs
    /**
     * Creates a string of the iterator with its elements
     * @param it iterator being printed
     * @return string with all the iterator's elements separated by commas
     */
    private static String printIterator(Iterator<Citizen> it) {
        String string = "";
        while (it.hasNext()) {
            Citizen member = it.next();
            string += member.getName();
            if (it.hasNext()) string+=", "; //if not the last member
        }
        return string;
    }

    /**
     * Creates a string of the group's members' names according to certain specifications
     * @param group group being printed
     * @param exception citizen not printed
     * @return string of the group's members' names according to certain specifications
     */
    private static String printGroup(Group group, Citizen exception) {
        Iterator<Citizen> it;
        if (exception == null) it = group.listMembers(); //no exception
        else {
            Array<Citizen> filter = new ArrayClass<>();
            filter.insertLast(exception); //creates a filter with the unwanted citizen
            it = new FilteredIterator<>(group.getMembers(), filter);
        }
        return printIterator(it);
    }

    /**
     * HELP COMMAND
     * Shows the available commands
     */
    private static void listCommands() {
        System.out.print("""
                landmark - adds a new landmark to the community
                landmarks - displays the list of landmarks in the community
                forgetful - adds a forgetful person to the community
                gossiper - adds a gossiper person to the community
                sealed - adds a sealed lips person to the community
                people - lists all the persons in the community
                go - moves a person to a landmark, or home
                join - joins a person to a group
                groups - lists the groups composition in a landmark
                isolate - makes a person leave the current group, but not the landmark the person is currently on
                start - starts a new gossip
                gossip - share a gossip within the current group in the current landmark
                secrets - lists the gossip about a particular person
                infotainment - lists the gossips a particular person is aware of
                hottest - lists the most shared gossip
                help - shows the available commands
                exit - terminates the execution of the program
                """);
    }
}