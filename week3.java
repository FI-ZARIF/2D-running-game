import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class week3 extends JPanel {


    int playerX = 100;

    
    int playerWidth = 100;
    int playerHeight = 100;

    int playerY = 200 - playerHeight;

    int velocityY = 0;
    boolean jumping = false;

    Timer timer;

    Image playerImage;


    public week3() {

        setPreferredSize(new Dimension(600, 350));
        setBackground(Color.WHITE);

playerImage = new ImageIcon("download.png").getImage();

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke("SPACE"),
                "jump"
        );

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


        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.BLACK);

        g.fillRect(
                0,
                290,
                600,
                5
        );

        g.drawImage(
                playerImage,
                playerX,
                playerY,
                playerWidth,
                playerHeight,
                this
        );
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame(
                "2D Running Game - Week 3" );

        week3 gamePanel = new week3();

        frame.add(gamePanel);

        frame.pack();

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(null);

        frame.setResizable(false);

        frame.setVisible(true);
    }
}