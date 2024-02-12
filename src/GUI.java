import javax.swing.JPanel;
import java.awt.*;
import java.util.HashSet;

public class GUI extends JPanel{
    private final int roomSize = 50;
    private int playerRoom;
    private HashSet<Integer> visitedRooms = new HashSet<>();

    //contructor takes in the room that the player is currently in 
    public GUI(int playerRoom, HashSet<Integer> visitedRooms) {
        this.playerRoom = playerRoom;
        this.visitedRooms = visitedRooms;
    }

    //useful for updating the room that should be coloured in pink 
    public void setplayerRoom(int playerLocation) {
        this.playerRoom = playerLocation;
    }

@Override
public void paintComponent(Graphics gg) {
    super.paintComponent(gg);
    Graphics2D g = (Graphics2D) gg;
    //make the edges of the shape smoother
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

    drawRooms(g, playerRoom, visitedRooms);

}

public void drawRooms(Graphics2D g, int playerRoom, HashSet<Integer> visitedRooms) {
    //draw lines connections between rooms
    g.setColor(Color.darkGray);
    g.setStroke(new BasicStroke(2));

    for (int i = 0; i < tunnels.length; i++) {
        for (int link : tunnels[i]) {
            int x1 = rooms[i][0] + roomSize / 2;
            int y1 = rooms[i][1] + roomSize / 2;
            int x2 = rooms[link-1][0] + roomSize / 2;
            int y2 = rooms[link-1][1] + roomSize / 2;
            g.drawLine(x1, y1, x2, y2);
        }
    }

    //colour all the rooms in green
    g.setColor(Color.green);
    for (int[] r : rooms) {
        g.fillOval(r[0], r[1], roomSize, roomSize);
    }

    //colour the outer layer of the rooms
    g.setColor(Color.darkGray);
    for (int[] r : rooms) {
        g.drawOval(r[0], r[1], roomSize, roomSize);
        
    }

    //place numbers next to the rooms
    int roomNum = 1;
    for (int[] r : rooms) {
            g.drawString(Integer.toString(roomNum), r[0], r[1]);
            roomNum++;
    }
    //colour the rooms that the player already visited in orange
    if (!visitedRooms.isEmpty()) {
        for (int room: visitedRooms) {
            g.setColor(Color.orange);
            g.fillOval(rooms[room-1][0], rooms[room-1][1], roomSize, roomSize);
        }
    }

    //colour the room where the player currently is in red
    g.setColor(Color.RED);
    g.fillOval(rooms[playerRoom-1][0], rooms[playerRoom-1][1], roomSize, roomSize);
}

//x,y coordinates were taken from an online source that match a flat dodecahedron
private int[][] rooms = {{334, 20}, {609, 220}, {499, 540}, {169, 540}, {62, 220},
{169, 255}, {232, 168}, {334, 136}, {435, 168}, {499, 255}, {499, 361},
{435, 447}, {334, 480}, {232, 447}, {169, 361}, {254, 336}, {285, 238},
{387, 238}, {418, 336}, {334, 393}};

//links to the neighboring rooms that were used to draw line connections
private int[][] tunnels = {{2, 5, 8}, {1, 3, 10}, {2, 4, 12}, {3, 5, 14}, {1, 4, 6},
{5, 7, 15}, {6, 8, 17}, {1, 7, 9}, {8, 10, 18}, {2, 9, 11}, {10, 12, 19},
{3, 11, 13}, {12, 14, 20}, {4, 13, 15}, {6, 14, 16}, {15, 17, 20},
{7, 16, 18}, {9, 17, 19}, {11, 18, 20}, {13, 16, 19}};

}