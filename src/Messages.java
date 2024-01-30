public class Messages {

    public static void gotEaten() {
        System.out.println("YOU LOST! THE WUMPUS ATE YOU! BETTER LUCK NEXT TIME");
    }

    public static void fellIntoPit() {
        System.out.println("AAHHHHHH... YOU FELL INTO A PIT! BETTER LUCK NEXT TIME HEHE" );
    }

    public static void pickedUpByBats() {
        System.out.println("OH NO! YOU GOT PICKED UP BY BATS AND DROPPED INTO A NEW ROOM");
    }

    public static void wumpusNearby() {
        System.out.println("YOU SMELL SOMETHING TERRIBLE, THE WUMPUS IS NEARBY");
    }

    public static void pitNearby() {
        System.out.println("YOU FEEL A DRAFT");
    }

    public static void batsNearby() {
        System.out.println("YOU HEAR A RUSTLING");
    }

    public static void youWin(){
        System.out.println("CONGRATULATIONS! YOU KILLED THE WUMPUS! YOU WIN.");
    }

    public static void youLost(){
        System.out.println("OH NO! YOU RAN OUT OF ARROWS! YOU LOSE");
    }
}
