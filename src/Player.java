import java.util.*;

public class Player {
    //attributes 
    private static final int NUM_ARROWS = 5;
    private int numArrows; 
    private boolean playerLife;
    private int playerPos;

 
    //constructor 
    public Player(int numArrows, boolean playerLife, int playerPos) {
        this.numArrows = NUM_ARROWS;
        this.playerLife = true;
        
    }


    public void initialise() {
        //creating a list of all the empty rooms, the list will update as the game goes 
        ArrayList<Integer> emptyRooms = new ArrayList<>();
        for (int i=1; i < 21; i++) {
            emptyRooms.add(i); 
        }

        List<List<Integer>> neighbourCaves = new ArrayList<>();
        Collections.addAll(neighbourCaves, Arrays.asList(2,5,8),Arrays.asList(1,3,10),Arrays.asList(2,4,12),Arrays.asList(3,5,14),Arrays.asList(1,4,6),
        Arrays.asList(5,7,15),Arrays.asList(6,8,17), Arrays.asList(1,7,9), Arrays.asList(8,10,18), Arrays.asList(2,9,11), Arrays.asList(10,12,19), 
        Arrays.asList(3,11,13), Arrays.asList(12,14,20), Arrays.asList(4,13,15), Arrays.asList(6,14,16), Arrays.asList(15,17,20), Arrays.asList(7,16,18), 
        Arrays.asList(9,17,19), Arrays.asList(11,18,20), Arrays.asList(13,16,19));

        Map<Integer, List<Integer>> caves = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            caves.put(i+1, neighbourCaves.get(i));
        }

        //caves.forEach((k,v) -> System.out.println(k + " " + v));
                                                                                                                                                                                                                                                                                                                                                                                                                              
        
        //printing out instructions 
        System.out.println();

        //calling methods for random allocation of the hazards 
    }

}