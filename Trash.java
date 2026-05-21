import java.awt.*;
import javax.swing.*;

public class Trash {

    int x;
    int y;

    int size = 45;

    Image trashImage;

    public Trash(int x, int y) {

        this.x = x;
        this.y = y;

        trashImage = new ImageIcon("assets/trash.png").getImage();
    }

    public void draw(Graphics g) {

        g.drawImage(trashImage, x, y, size, size, null);
    }

    public Rectangle getBounds() {

        return new Rectangle(x, y, size, size);
    }
}