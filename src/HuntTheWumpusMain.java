import java.util.Scanner;
public class HuntTheWumpusMain {
    private static Scanner reader = new Scanner (System.in);
    private static String line = "default";
    public static void main (String[] args) {
        Player player = new Player();
        player.printInstructions();
        player.initialise();
        while (player.getPlayerLife()) {
            player.printPlayerLocation();
            try { 
                line = reader.nextLine();
                if (line.equals("QUIT")) {
                    return;
                }
                String command = line.replaceAll("[0-9]", "");
                int room = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                if (!player.roomMap.get(player.getPlayerLocation()).contains(room)) {
                    throw new Exception("INVALID ROOM");
                }
        
                switch (command) {
                    case "MOVE": 
                        player.move(room);
                        player.nearHazards(room);
                        break;

                    case "SHOOT":
                        player.shoot(room);
                        break;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
