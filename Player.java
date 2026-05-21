import java.awt.*;
import javax.swing.*;

public class Player {

    int x;
    int y;

    int width = 80;
    int height = 80;

    int speed = 4;

    boolean up;
    boolean down;
    boolean left;
    boolean right;

    boolean carryingTrash = false;

    Image playerImage;

    public Player(int x, int y) {

        this.x = x;
        this.y = y;

        playerImage = new ImageIcon("assets/player.png").getImage();
    }

    public void update() {

        if(up) y -= speed;
        if(down) y += speed;
        if(left) x -= speed;
        if(right) x += speed;

        // Limites da tela
        if(x < 0) x = 0;
        if(y < 0) y = 0;

        if(x > 740) x = 740;
        if(y > 520) y = 520;
    }

    public void draw(Graphics g) {

        g.drawImage(playerImage, x, y, width, height, null);

        // Indicador de lixo
        if(carryingTrash) {

            g.setColor(Color.YELLOW);

            g.fillOval(x + 35, y, 15, 15);
        }
    }

    public Rectangle getBounds() {

        return new Rectangle(x, y, width, height);
    }
}