import java.util.ArrayList;

public class Hazards extends Messages{
     
    //fields
    private ArrayList<Integer> batsLocation; //a list of 3 rooms where bats are located 
    private int pitLocation;
    private int wumpusLocation;

    //methods for the pit
    public int getPitLocation() {
         return pitLocation;
    }
 
    public void setPitLocation(int pitLocation) {
         this.pitLocation = pitLocation;
    }

    //methods for the bats
    public ArrayList<Integer> getBatsLocation() {
        return batsLocation;
    }
 
    public void setBatsLocation(ArrayList<Integer> batsLocation) {
         this.batsLocation = batsLocation;
    }

    //methods for the wumpus
    public int getWumpusLocation() {
        return wumpusLocation;
    }

    public void setWumpusLocation(int wumpusLocation) {
        this.wumpusLocation = wumpusLocation;
    }
}
