import java.awt.*;
import javax.swing.*;

public class Car {

    int x;
    int y;

    int width = 150;
    int height = 80;

    int speed = 5;

    Image carImage;

    public Car(int x, int y) {

        this.x = x;
        this.y = y;

        carImage = new ImageIcon("assets/car.png").getImage();
    }

    public void update() {

        x += speed;

        if(x > 800) {

            x = -140;
        }
    }

    public void draw(Graphics g) {

        g.drawImage(carImage, x, y, width, height, null);
    }

    public Rectangle getBounds() {

        return new Rectangle(x, y, width, height);
    }
}