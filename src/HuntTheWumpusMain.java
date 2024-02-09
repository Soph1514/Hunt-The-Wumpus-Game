import javax.swing.*;
import java.awt.BorderLayout;
import java.util.Scanner;
public class HuntTheWumpusMain {
    private static Scanner reader = new Scanner (System.in);
    public static void main (String[] args) {
        Play play = new Play();
        GUI gui = new GUI();
        String line = "default";
        play.printInstructions();
        play.initialise();

        
        

        while (play.getPlayerLife()) {
            play.printPlayerLocation();
            //checking if the player can "sense" any hazards in the neighbouring rooms
            play.nearHazards(play.getPlayerLocation());
            try { 
                line = reader.nextLine();
                //stroring the text aspect of user's input in a variable
                String command = line.replaceAll("[0-9]", "");
            
                switch (command) {
                    case "MAP":
                    JFrame f = new JFrame();
                    f.setSize(700, 650);
                    f.add(gui, BorderLayout.CENTER);
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);
                    break;
                    case "MOVE": 
                        //stroing the room number the user inputted in a variable 
                        int room = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                          //checking that the user inputted a room that can be reached through a tunnel
                        if (!play.roomMap.get(play.getPlayerLocation()).contains(room)) {
                            throw new Exception("INVALID ROOM");
                        }
                        else {
                            play.move(room);
                        }
                        break;
                    case "SHOOT":
                        room = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                        if (!play.roomMap.get(play.getPlayerLocation()).contains(room)) {
                            throw new Exception("INVALID ROOM");
                        }
                        else {
                            play.shoot(room);
                        }
                        break;
                    case "QUIT":
                        return;
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
