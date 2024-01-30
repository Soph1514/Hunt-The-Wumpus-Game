import java.util.*;

public class Player extends Messages{
    //attributes 
    private static final int NUM_ARROWS = 5;
    private int numArrows; 
    private boolean playerLife;
    private int playerLocation;

    private ArrayList<Integer> emptyRooms; //all the rooms that will be empty after random allocation of hazards
    public Map<Integer, List<Integer>> roomMap; //store all the rooms and their according neighbouring rooms 
    private Set<Integer> notEmptyRooms; //will store the rooms that the hazards are located in 

    private Bats bats;
    private Pit pit;
    private Wumpus wumpus;

    //constructor 
    public Player() {
        this.numArrows = NUM_ARROWS;
        this.playerLife = true;
        this.emptyRooms = new ArrayList<>(); 
        this.roomMap = new HashMap<>(); 
        this.notEmptyRooms = new LinkedHashSet<>();
        this.bats = new Bats(); 
        this.pit = new Pit();
        this.wumpus = new Wumpus();
    }

    public void setPlayerLocation(int room) {
        this.playerLocation = room;
    }

    public int getPlayerLocation() {
        return this.playerLocation;
    }

    public boolean getPlayerLife() {
        return this.playerLife;
    }

    public void initialise() {
        //creating a list of all the empty rooms, the list will update as the game goes 
        for (int i=1; i < 21; i++) {
            emptyRooms.add(i); 
        }

        List<List<Integer>> neighbourCaves = new ArrayList<>();
        Collections.addAll(neighbourCaves, Arrays.asList(2,5,8),Arrays.asList(1,3,10),Arrays.asList(2,4,12),Arrays.asList(3,5,14),Arrays.asList(1,4,6),
        Arrays.asList(5,7,15),Arrays.asList(6,8,17), Arrays.asList(1,7,9), Arrays.asList(8,10,18), Arrays.asList(2,9,11), Arrays.asList(10,12,19), 
        Arrays.asList(3,11,13), Arrays.asList(12,14,20), Arrays.asList(4,13,15), Arrays.asList(6,14,16), Arrays.asList(15,17,20), Arrays.asList(7,16,18), 
        Arrays.asList(9,17,19), Arrays.asList(11,18,20), Arrays.asList(13,16,19));

        for (int i = 0; i < 20; i++) {
            roomMap.put(i+1, neighbourCaves.get(i));
        }

        //caves.forEach((k,v) -> System.out.println(k + " " + v));                                                                                                                                                                                                                                                                                                                                                                                                                              

        //calling methods for random allocation of the hazards 
        //generate 6 rooms from 20
        Random random = new Random();
        while (notEmptyRooms.size() < 6){
            Integer num = random.nextInt(21);
            if(num > 0){
                notEmptyRooms.add(num);
            }
        }

        //asign each room to an entity(player, pit, wumpus, bats)
        List<Integer> tempList = new ArrayList<>(notEmptyRooms);
        pit.setPitLocation(tempList.get(0));
        wumpus.setWumpusLocation(tempList.get(1));
        playerLocation = tempList.get(2);
        ArrayList<Integer> batsLocationTemp = new ArrayList<>();
        batsLocationTemp.add(tempList.get(3));
        batsLocationTemp.add(tempList.get(4));
        batsLocationTemp.add(tempList.get(5));
        bats.setBatsLocation(batsLocationTemp);

        System.out.println(playerLocation + " " + pit.getPitLocation() + " " + wumpus.getWumpusLocation() + " " + bats.getBatsLocation());

        //removes those 6 rooms from emptyRooms
        emptyRooms.removeAll(notEmptyRooms);
        System.out.println(emptyRooms);

    }

    //method to move the player from one room to another 
    public void move(int room) {
        //walked into a room with wumpus
        if (wumpus.getWumpusLocation() == room) {
            gotEaten();
            playerLife = false;
        }
        else {
            //walked into a room with a bottomless pit
            if (pit.getPitLocation() == room ) {
                fellIntoPit();
                playerLife = false;
            }
            //walked into a room with bats, statement checks if the room the user inputed is in the list of rooms the bats are located in
            if (bats.getBatsLocation().contains(room)) {
                Random random = new Random();
                // inspired by https://www.baeldung.com/java-random-list-element
                int newRoom = emptyRooms.get(random.nextInt(emptyRooms.size()));
                playerLocation = newRoom;
                pickedUpByBats();
                printPlayerLocation();

            }
            else {
                //if walked into an empty room
                playerLocation = room;
            }
            
        }
    }


    public void nearHazards(int room) {
        List<Integer> neighbourRooms = roomMap.get(room);
        //checking if the wumpus is in one of the neighbouring rooms
        if (neighbourRooms.contains(wumpus.getWumpusLocation())) {
            wumpusNearby();
        }
        //checking if the pit is in one of the neighbouring rooms
        if (neighbourRooms.contains(pit.getPitLocation())) {
            pitNearby();
        }
        //checking if bats are in neighbouring rooms
        //method disjoint returns false of there is at least one common element in two lists
        if (!Collections.disjoint(neighbourRooms, bats.getBatsLocation())) {
            batsNearby();
        }
    }

    public void shoot(int userInput){//user decides what room to shoot into.
        int arrowLocation = userInput;
        int wumpusLocation = wumpus.getWumpusLocation();

        //condition where user can only shoot into connected rooms -> in the main

        //you killed the wumpus -> you win. you run out of arrows -> you lose.
        if(numArrows > 0){
            numArrows--;
            //you win
            if(arrowLocation == wumpusLocation){
                youWin();
                playerLife = false; //exit the game
            } else {
                System.out.println("YOU MISSED!");
            }
            if(numArrows == 0){
                youLost();
                playerLife = false; //exit the game
            }
        }
    }

    public void printPlayerLocation() {
        System.out.println("YOU ARE IN THE ROOM: " + playerLocation + ", THE TUNNELS LEED TO ROOMS:" + roomMap.get(playerLocation));
    }

    public void printInstructions() {
        System.out.print("YOU ARE A FAMOUS HUNTER DESCENDING DOWN INTO THE CAVES OF DARKNESS, LAIR OF THE INFAMOUS MAN-EATING WUMPUS. \n" 
        + "YOU ARE EQUIPPED WITH 5 ARROWS, AND ALL YOUR SENSES. THERE ARE 20 ROOMS CONNECTED BY TUNNELS, YOU CAN ONLY MOVE OR SHOOT INTO THE NEIGHBOURING ROOMS \n"
        + "HAZARDS (YOU CAN SENSE THEM FROM ONE ROOM AWAY): \n\n"
        + "A) 1 PIT, WHICH IS BOTTOMLESS AND FATAL TO FALL INTO \n" + "B) 3 SUPER-BATS, THAT WILL PICK YOU UP AND DROP YOU IN SOME RANDOM ROOM IN THE NETWORK. \n" 
        + "C) THE WUMPUS ITSLEF. THE WUMPUS HAS A CHANCE OF MOVING INTO ANOTHER ROOM OF 0.75 PER EVERY 7 COMMANDS YOU MAKE. \nIF YOU BLUNDER INTO THE SAME ROOM AS THE WUMPUS, YOU LOSE...\n\n"
        + "YOU CAN SMELL THE WUMPUS, IF YOU HEAR RUSTLING THIS MEANS THE BATS ARE NEABY AND IF YOU FEEL A DRAFT, A PIT IS NEARBY.\n"
        + "THE TWO COMMANDS YOU CAN USE ARE 'MOVE' OR 'SHOOT'. AFTER TYPING THE COMMAND SPECIFY THE ROOM, E.G SHOOT7. \nIF YOU WANT TO TERMINATE THE GAME TYPE 'QUIT'.\n\n" 
        + "YOUR GOAL IS TO SHOOT THE WUMPUS BEFORE SOMETHING TERRIBLE HAPPENS TO YOU. GOOD LUCK HUNTING! \n");
    }



}