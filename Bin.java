import java.awt.*;
import javax.swing.*;

public class Bin {

    int x;
    int y;

    int width = 100;
    int height = 100;

    Image binImage;

    public Bin(int x, int y) {

        this.x = x;
        this.y = y;

        binImage = new ImageIcon("assets/bin.png").getImage();
    }

    public void draw(Graphics g) {

        g.drawImage(binImage, x, y, width, height, null);
    }

    public Rectangle getBounds() {

        return new Rectangle(x, y, width, height);
    }
}