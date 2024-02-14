import java.util.*;
public class Play {

    //fields
    private static final int NUM_ARROWS = 5;
    private int numArrows = NUM_ARROWS; 
    private int extraArrows = 3;
    private boolean playerLife = true;
    private int playerLocation;
    private Hazards hazard = new Hazards();
    private ArrayList<Integer> emptyRooms = new ArrayList<>(); //all the rooms that will be empty after random allocation of hazards AND ARROWS
    public Map<Integer, List<Integer>> roomMap = new HashMap<>();; //store all the rooms and their according neighbouring rooms 
    private Set<Integer> notEmptyRooms = new HashSet<>();; //will store the rooms that the hazards are located in 
    private Set<Integer> roomsWithArrow = new HashSet<>();//ensure that no more than 1 arrow is in a room

        //methods for the player
    
        public int getPlayerLocation() {
            return this.playerLocation;
        }
    
        public boolean getPlayerLife() {
            return this.playerLife;
        }

        public void setPlayerLife(boolean b) {
            this.playerLife = b;
        }

        public int getArrows(){
            return this.numArrows;
        }
     
    public void initialise() {
        //creating a list of all the empty rooms, the list will update as the hazard goes 
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
        hazard.setPitLocation(tempList.get(0));
        hazard.setWumpusLocation(tempList.get(1));
        playerLocation = tempList.get(2);
        ArrayList<Integer> batsLocationTemp = new ArrayList<>();
        batsLocationTemp.add(tempList.get(3));
        batsLocationTemp.add(tempList.get(4));
        batsLocationTemp.add(tempList.get(5));
        hazard.setBatsLocation(batsLocationTemp);

        //removes those 6 rooms from emptyRooms
        emptyRooms.removeAll(notEmptyRooms);

        //call resupply
        resupply();
    }

    //method to move the player from one room to another 
    public void move(int room) {

        //walked into a room with wumpus
        if (hazard.getWumpusLocation() == room) {
            System.out.println("YOU LOST! THE WUMPUS ATE YOU! BETTER LUCK NEXT TIME");
            playerLife = false;
            return;
        }
        else {
            //walked into a room with a bottomless pit
            if (hazard.getPitLocation() == room ) {
                System.out.println("AAHHHHHH... YOU FELL INTO A PIT! BETTER LUCK NEXT TIME HEHE" );
                playerLife = false;
                return;
            }
            //walked into a room with bats, statement checks if the room the user inputed is in the list of rooms the bats are located in
            if (hazard.getBatsLocation().contains(room)) {
                Random random = new Random();
                // inspired by https://www.baeldung.com/java-random-list-element
                int newRoom = emptyRooms.get(random.nextInt(emptyRooms.size()));
                playerLocation = newRoom;
                System.out.println("OH NO! YOU GOT PICKED UP BY BATS AND DROPPED INTO A NEW ROOM");
            }
            else {
                //if walked into an empty room
                playerLocation = room;
            }
        }
        if(roomsWithArrow.contains(playerLocation)){
            System.out.println("YOU FOUND AN ARROW");
            roomsWithArrow.remove(playerLocation);//take away arrow from room
            emptyRooms.add(playerLocation);//room is now empty again
            extraArrows--;//take away one extra arrow
            numArrows++;
        }
    }

    public void nearHazards(int room) {
        List<Integer> neighbourRooms = roomMap.get(room);
        //checking if the wumpus is in one of the neighbouring rooms
        if (neighbourRooms.contains(hazard.getWumpusLocation())) {
            System.out.println("YOU SMELL SOMETHING TERRIBLE, THE WUMPUS IS NEARBY");
        }
        //checking if the pit is in one of the neighbouring rooms
        if (neighbourRooms.contains(hazard.getPitLocation())) {
            System.out.println("YOU FEEL A DRAFT");
        }
        //checking if bats are in neighbouring rooms
        //method disjoint returns false if there is at least one common element in two lists
        if (!Collections.disjoint(neighbourRooms, hazard.getBatsLocation())) {
            System.out.println("YOU HEAR A RUSTLING");
        }
    }

    public void shoot(int room){//user decides what room to shoot into.
        int arrowLocation = room;
        int wumpusLocation = hazard.getWumpusLocation();

        //condition where user can only shoot into connected rooms -> in the main

        //you killed the wumpus -> you win. you run out of arrows -> you lose.
        if(numArrows > 0){
            numArrows--;
            //you win
            if(arrowLocation == wumpusLocation){
                System.out.println("CONGRATULATIONS! YOU KILLED THE WUMPUS! YOU WIN.");
                playerLife = false; //exit the game
            } else {
                System.out.println("YOU MISSED!");
                //wumpus has a 75% chance of hearing the arrow and moving to a new room
                Random random = new Random();
                if(random.nextDouble() < 0.75){
                    int index = random.nextInt(emptyRooms.size()-1);
                    int num = emptyRooms.get(index);
                    hazard.setWumpusLocation(num);
                    System.out.println("TINK! THE WUMPUS HEARD THE ARROW AND ESCAPED TO A NEW RANDOM ROOM.");
                }
            }
        }

        if(numArrows == 0 && extraArrows != 0){
            System.out.println("YOU HAVE RUN OUT OF ARROWS BUT CAN PICK UP ARROWS LEFT BEHIND BY FALLEN HUNTERS. THERE ARE " + extraArrows + " IN THE CAVE SYSTEM FOR YOU TO FIND.");
        }
        if(numArrows == 0 && extraArrows == 0 && arrowLocation != wumpusLocation){//player can wander and collect arrows
            System.out.println("OH NO! YOU RAN OUT OF ARROWS! YOU LOSE");
            playerLife = false; //exit the game
        }
    }

    //function for 
    public void resupply(){
        //allocates the random arrows
        Random random = new Random();
        while(roomsWithArrow.size() < extraArrows){
            int index = random.nextInt(emptyRooms.size()-1);//random number from emptyRooms()
            int num = emptyRooms.get(index);
                emptyRooms.remove(index);//room is not empty as it now has an arrow
                roomsWithArrow.add(num);
        }
    }

    public void printPlayerLocation() {
        System.out.println("YOU ARE IN THE ROOM: " + playerLocation+ ", THE TUNNELS LEED TO ROOMS:" + roomMap.get(playerLocation));
    }

    public void printInstructions() {
        System.out.print("\nYOU ARE A FAMOUS HUNTER DESCENDING DOWN INTO THE CAVES OF DARKNESS, LAIR OF THE INFAMOUS MAN-EATING WUMPUS. \n" 
        + "YOU ARE EQUIPPED WITH 5 ARROWS, AND ALL YOUR SENSES. THERE ARE 20 ROOMS CONNECTED BY TUNNELS, YOU CAN ONLY MOVE OR SHOOT INTO NEIGHBOURING ROOMS. \n\n"
        + "HAZARDS (YOU CAN SENSE THEM FROM ONE ROOM AWAY): \n\n"
        + "A) 1 PIT, WHICH FATAL TO FALL INTO. YOU WILL FEEL A DRAFT IF YOU ARE NEAR IT\n" + "B) 3 SUPER-BATS, THAT WILL PICK YOU UP AND DROP YOU IN SOME RANDOM ROOM IN THE NETWORK. YOU WILL HEAR RUSTLING NEAR THEM. \n" 
        + "C) THE WUMPUS ITSLEF, WHICH HAS A TERRIBLE SMELL. THE WUMPUS HAS A CHANCE OF MOVING INTO ANOTHER RANDOM ROOM OF 0.75 AFTER EVERY MISS. \nIF YOU BLUNDER INTO THE SAME ROOM AS THE WUMPUS, YOU LOSE...\n\n"
        + "COMMANDS: \n1) 'SHOOT', PLEASE SPECIFY A ROOM, E.G SHOOT7 \n"
        + "2) 'MOVE', PLEASE SPECIFY A ROOM, E.G MOVE7 \n3) 'MAP' TO DISPLAY A VISUAL REPRESENTATION OF THE CAVE SYSTEM. THE ROOM YOU ARE IN IS RED AND THE ROOMS THAT YOU HAVE ALREADY VISITED ARE ORANGE \n4) 'AMMO' TO CHECK HOW MANY ARROWS YOU HAVE LEFT \n5) 'QUIT' TO TERMINATE THE GAME \n\n"  
        + "YOUR GOAL IS TO SHOOT THE WUMPUS BEFORE SOMETHING TERRIBLE HAPPENS TO YOU. GOOD LUCK HUNTING! \n\n");
    }
}