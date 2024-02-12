import javax.swing.*;
import java.awt.BorderLayout;
import java.util.Scanner;
public class HuntTheWumpusMain {
    private static Scanner reader = new Scanner (System.in);
    public static void main (String[] args) {
        //infinite game loop
        while(true) {
        Play play = new Play();
        String line = "default";
        play.printInstructions();
        play.initialise();
        GUI gui = new GUI(play.getPlayerLocation());;

            //loop for each game
            while (play.getPlayerLife()) {
                play.printPlayerLocation();
                gui.setplayerRoom(play.getPlayerLocation());
                //checking if the player can "sense" any hazards in the neighbouring rooms
                play.nearHazards(play.getPlayerLocation());
                try { 
                    line = reader.nextLine();
                    //stroring the text aspect of user's input in a variable
                    String command = line.replaceAll("[0-9]", "");
                
                    switch (command) {
                        //will display the visual representation of the cave network and point where the player is
                        case "MAP":
                        JFrame f = new JFrame();
                        f.setSize(700, 650);
                        f.add(gui, BorderLayout.CENTER);
                        f.setLocationRelativeTo(null);
                        f.setVisible(true);
                        break;
                        case "AMMO":
                            int ammo = play.getArrows();
                            if(ammo > 1){
                                System.out.println("YOU HAVE " + ammo + " ARROWS LEFT");
                            } else if(ammo == 1) {
                                System.out.println("YOU HAVE " + ammo + " ARROW LEFT");
                            } else {
                                System.out.println("YOU HAVE NO ARROWS LEFT");
                            }
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
                        //exit out of the loop
                            return;
                        default:
                            throw new Exception ("INVALID COMMAND, PLEASE TRY AGAIN");
                    }
                    if (!play.getPlayerLife()) {
                        System.out.println("PLAY AGAIN? Y/N");
                        switch(reader.nextLine()) {
                            case "Y":
                            //breaks out of the inner loop but not the outer to restart the game
                            break;
                            case "N":
                            //terminate the game
                                return;
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
