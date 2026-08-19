import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class week4 extends JPanel {

    int playerX = 100;

    int playerWidth = 100;
    int playerHeight = 100;

    int playerY = 290 - playerHeight;

    int velocityY = 0;
    boolean jumping = false;

    Image playerImage;

    ArrayList<Rectangle> obstacles = new ArrayList<>();

    Timer timer;

    int obstacleTimer = 0;

    public week4() {

        setPreferredSize(new Dimension(600, 350));
        setBackground(Color.WHITE);

        playerImage = new ImageIcon("download.png").getImage();

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("LEFT"),
                "moveLeft"
        );

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("RIGHT"),
                "moveRight"
        );

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("SPACE"),
                "jump"
        );

        getActionMap().put("moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                playerX -= 10;
                repaint();
            }
        });

        getActionMap().put("moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                playerX += 10;
                repaint();
            }
        });

        getActionMap().put("jump", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!jumping) {
                    velocityY = -15;
                    jumping = true;
                }
            }
        });

        timer = new Timer(30, e -> updateGame());
        timer.start();
    }

    private void updateGame() {

        playerY += velocityY;
        velocityY++;

        if (playerY >= 290 - playerHeight) {
            playerY = 290 - playerHeight;
            velocityY = 0;
            jumping = false;
        }

        if (playerX < 0) {
            playerX = 0;
        }

        if (playerX > 500) {
            playerX = 500;
        }

        obstacleTimer++;

        if (obstacleTimer >= 40) {
            obstacles.add(new Rectangle(600, 255, 35, 35));
            obstacleTimer = 0;
        }

        for (Rectangle obstacle : obstacles) {
            obstacle.x -= 6;
        }

        obstacles.removeIf(obstacle -> obstacle.x < -40);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 290, 600, 5);

        g.drawImage(
                playerImage,
                playerX,
                playerY,
                playerWidth,
                playerHeight,
                this
        );

        for (Rectangle obstacle : obstacles) {

            int x = obstacle.x;
            int y = obstacle.y;

            g.setColor(new Color(100, 100, 100));

            g.fillOval(
                    x,
                    y,
                    obstacle.width,
                    obstacle.height
            );

            g.fillOval(
                    x + 8,
                    y - 5,
                    20,
                    15
            );
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: 0", 20, 30);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("2D Running Game - Week 4");

        week4 gamePanel = new week4();

        frame.add(gamePanel);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}