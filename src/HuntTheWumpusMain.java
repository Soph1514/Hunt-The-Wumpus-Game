import javax.swing.*;
import java.awt.BorderLayout;
import java.util.Scanner;
import java.util.HashSet;
import java.util.InputMismatchException;

public class HuntTheWumpusMain {
    private static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        // infinite game loop
        while (true) {
            Play play = new Play();
            String line = "default";
            play.printInstructions();
            play.initialise();
            HashSet<Integer> visitedRooms = new HashSet<>();
            GUI gui;

            // loop for each game
            while (play.getPlayerLife()) {
                play.printPlayerLocation();
                // updating the list of visited rooms
                visitedRooms.add(play.getPlayerLocation());
                // creating a new gui object with updated parameters
                gui = new GUI(play.getPlayerLocation(), visitedRooms);
                // checking if the player can "sense" any hazards in the neighbouring rooms
                play.nearHazards(play.getPlayerLocation());
                try {
                    line = reader.nextLine();
                    // stroring the text aspect of user's input in a variable
                    String command = line.replaceAll("[0-9]", "");
                    switch (command) {
                        // will display the visual representation of the cave network, point where the
                        // player is now and which rooms the player have already visited
                        case "MAP":
                            JFrame f = new JFrame();
                            f.setSize(700, 650);
                            f.add(gui, BorderLayout.CENTER);
                            f.setLocationRelativeTo(null);
                            f.setVisible(true);
                            break;
                        case "AMMO":
                            int ammo = play.getArrows();
                            if (ammo > 1) {
                                System.out.println("YOU HAVE " + ammo + " ARROWS LEFT");
                            } else if (ammo == 1) {
                                System.out.println("YOU HAVE " + ammo + " ARROW LEFT");
                            } else {
                                System.out.println("YOU HAVE NO ARROWS LEFT");
                            }
                            break;
                        case "MOVE":
                            // stroing the room number the user inputted in a variable
                            int room = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                            // checking that the user inputted a room that can be reached through a tunnel
                            if (!play.roomMap.get(play.getPlayerLocation()).contains(room)) {
                                throw new InputMismatchException("INVALID ROOM");
                            } else {
                                play.move(room);
                            }
                            visitedRooms.add(room);
                            break;
                        case "SHOOT":
                            room = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                            if (!play.roomMap.get(play.getPlayerLocation()).contains(room)) {
                                throw new InputMismatchException("INVALID ROOM");
                            } else {
                                play.shoot(room);
                            }
                            break;
                        case "QUIT":
                            // exit out of the outer loop
                            System.exit(0);
                        default:
                            throw new InputMismatchException("INVALID COMMAND, PLEASE TRY AGAIN");
                    }
                    // if (!play.getPlayerLife()) {
                    // System.out.println("PLAY AGAIN? Y/N");
                    // switch(reader.nextLine()) {
                    // case "Y":
                    // //breaks out of the inner loop but not the outer to restart the game
                    // break;
                    // case "N":
                    // //terminate the game
                    // System.exit(0);
                    // default:
                    // throw new InputMismatchException("INVALID COMMAND, PLEASE TYPE Y OR N");
                    // }
                    // }
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("OOOPS SOMETHING WENT WRONG... DID YOU INPUT THE ROOMS NUMBER?");
                }
            }
            while (!play.getPlayerLife()) {
                System.out.println("PLAY AGAIN? Y/N");
                try {
                    switch (reader.nextLine()) {
                        case "Y":
                            // breaks out of the inner loop but not the outer to restart the game
                            play.setPlayerLife(true);
                            break;
                        case "N":
                            // terminate the game
                            System.exit(0);
                        default:
                            throw new InputMismatchException("INVALID COMMAND, PLEASE TYPE Y OR N");
                    }
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
