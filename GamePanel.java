import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    final int WIDTH = 800;
    final int HEIGHT = 600;

    Timer timer;

    Player player;

    Car car;

    Image backgroundImage;

    ArrayList<Trash> trashes = new ArrayList<>();

    Random random = new Random();

    int score = 0;
    int coins = 0;

    int pollution = 0;
    int maxPollution = 100;

    // 1 minuto
    int timeLeft = 60;

    int deliveredTrash = 0;
    int maxTrash = 15;

    long lastSecond = System.currentTimeMillis();
    long lastSpawn = System.currentTimeMillis();

    boolean gameOver = false;
    boolean victory = false;

    Bin bin;

    public GamePanel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addKeyListener(this);

        setFocusable(true);

        player = new Player(100, 100);

        bin = new Bin(700, 500);

        car = new Car(0, 270);

        // Fundo do cenário
        backgroundImage = new ImageIcon("assets/background.png").getImage();

        spawnTrash();

        timer = new Timer(16, this);

        timer.start();
    }

    public void spawnTrash() {

        if(deliveredTrash + trashes.size() < maxTrash) {

            int x = random.nextInt(700);
            int y = random.nextInt(500);

            trashes.add(new Trash(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Desenha cenário
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);

        player.draw(g);

        bin.draw(g);

        car.draw(g);

        for (Trash trash : trashes) {

            trash.draw(g);
        }

        // HUD
        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString("Pontos: " + score, 20, 30);

        g.drawString("Moedas: " + coins, 20, 60);

        g.drawString("Tempo: " + timeLeft, 20, 90);

        g.drawString("Poluição: " + pollution + "%", 20, 120);

        g.drawString("Lixos coletados: " + deliveredTrash + "/" + maxTrash, 20, 150);

        g.drawString("Velocidade: " + player.speed, 20, 180);

        if(player.carryingTrash) {

            g.drawString("Carregando lixo", 20, 210);
        }

        // GAME OVER
        if(gameOver) {

            g.setColor(Color.RED);

            g.setFont(new Font("Arial", Font.BOLD, 50));

            g.drawString("GAME OVER", 220, 300);
        }

        // VITÓRIA
        if(victory) {

            g.setColor(Color.GREEN);

            g.setFont(new Font("Arial", Font.BOLD, 50));

            g.drawString("VOCÊ VENCEU!", 180, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(gameOver || victory) {

            repaint();

            return;
        }

        player.update();

        car.update();

        // Timer
        if(System.currentTimeMillis() - lastSecond >= 1000) {

            timeLeft--;

            lastSecond = System.currentTimeMillis();
        }

        // Spawn automático de lixo
        if(System.currentTimeMillis() - lastSpawn >= 3000) {

            spawnTrash();

            pollution += 5;

            lastSpawn = System.currentTimeMillis();
        }

        // Coleta lixo
        for(int i = 0; i < trashes.size(); i++) {

            Trash trash = trashes.get(i);

            if(player.getBounds().intersects(trash.getBounds())) {

                if(!player.carryingTrash) {

                    player.carryingTrash = true;

                    trashes.remove(i);

                    break;
                }
            }
        }

        // Entrega lixo
        if(player.getBounds().intersects(bin.getBounds())) {

            if(player.carryingTrash) {

                player.carryingTrash = false;

                deliveredTrash++;

                score += 20;

                coins += 10;

                pollution -= 5;

                if(pollution < 0) {

                    pollution = 0;
                }

                // Velocidade aumenta a cada 200 pontos
                if(score % 200 == 0) {

                    player.speed += 1;
                }
            }
        }

        // Colisão carro
        if(player.getBounds().intersects(car.getBounds())) {

            gameOver = true;
        }

        // Vitória
        if(deliveredTrash >= maxTrash && trashes.size() == 0 && !player.carryingTrash) {

            victory = true;
        }

        // Game over
        if(timeLeft <= 0 || pollution >= maxPollution) {

            gameOver = true;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) player.up = true;

        if(key == KeyEvent.VK_S) player.down = true;

        if(key == KeyEvent.VK_A) player.left = true;

        if(key == KeyEvent.VK_D) player.right = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) player.up = false;

        if(key == KeyEvent.VK_S) player.down = false;

        if(key == KeyEvent.VK_A) player.left = false;

        if(key == KeyEvent.VK_D) player.right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}