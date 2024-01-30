import java.util.Scanner;
public class HuntTheWumpusMain {
    private static Scanner reader = new Scanner (System.in);
    private static String line = "default";
    public static void main (String[] args) {
        Play play = new Play();
        play.printInstructions();
        play.initialise();
        while (play.getPlayerLife()) {
            play.printPlayerLocation();
            //checking if the player can "sense" any hazards in the neighbouring rooms
            play.nearHazards(play.getPlayerLocation());
            try { 
                line = reader.nextLine();
                if (line.equals("QUIT")) {
                    return;
                }
                //stroring the text aspect of user's input in a variable
                String command = line.replaceAll("[0-9]", "");
                //stroing the room number the user inputted in a variable 
                int room = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                //checking that the user inputted a room that can be reached through a tunnel
                if (!play.roomMap.get(play.getPlayerLocation()).contains(room)) {
                    throw new Exception("INVALID ROOM");
                }
            
                switch (command) {
                    case "MOVE": 
                        play.move(room);
                        break;
                    case "SHOOT":
                        play.shoot(room);
                        break;
                    default:
                        throw new Exception ("INVALID COMMAND, PLEASE TRY AGAIN");
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
