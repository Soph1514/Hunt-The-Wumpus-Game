import javax.swing.JPanel;
import java.awt.*;

public class GUI extends JPanel{
    private final int roomSize = 50; 

@Override
public void paintComponent(Graphics gg) {
    super.paintComponent(gg);
    Graphics2D g = (Graphics2D) gg;
    //make the edges of the shape smoother
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

    drawRooms(g);
}
   

public void drawRooms(Graphics2D g) {
    g.setColor(Color.darkGray);
    g.setStroke(new BasicStroke(2));

    for (int i = 0; i < tunnels.length; i++) {
        for (int link : tunnels[i]) {
            int x1 = rooms[i][0] + roomSize / 2;
            int y1 = rooms[i][1] + roomSize / 2;
            int x2 = rooms[link][0] + roomSize / 2;
            int y2 = rooms[link][1] + roomSize / 2;
            g.drawLine(x1, y1, x2, y2);
        }
    }

    g.setColor(Color.green);
    for (int[] r : rooms) {
        g.fillOval(r[0], r[1], roomSize, roomSize);
        g.drawString("1", r[0], r[1]);
        
    }

    g.setColor(Color.darkGray);
    for (int[] r : rooms) {
        g.drawOval(r[0], r[1], roomSize, roomSize);
    }

    int roomNum = 1;
    for (int[] r : rooms) {
            g.drawString(Integer.toString(roomNum), r[0], r[1]);
            roomNum++;
    }
}

int[][] rooms = {{334, 20}, {609, 220}, {499, 540}, {169, 540}, {62, 220},
{169, 255}, {232, 168}, {334, 136}, {435, 168}, {499, 255}, {499, 361},
{435, 447}, {334, 480}, {232, 447}, {169, 361}, {254, 336}, {285, 238},
{387, 238}, {418, 336}, {334, 393}};

int[][] tunnels = {{4, 7, 1}, {0, 9, 2}, {1, 11, 3}, {4, 13, 2}, {0, 5, 3},
{4, 6, 14}, {7, 16, 5}, {6, 0, 8}, {7, 17, 9}, {8, 1, 10}, {9, 18, 11},
{10, 2, 12}, {13, 19, 11}, {14, 3, 12}, {5, 15, 13}, {14, 16, 19},
{6, 17, 15}, {16, 8, 18}, {19, 10, 17}, {15, 12, 18}};



}