import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class week8 extends JPanel {

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

    int score = 0;
    long lastScoreTime;

    boolean gameStarted = false;
    boolean gameOver = false;

    JButton startButton;
    JButton playAgainButton;
    JButton exitButton;

    public week8() {

        setPreferredSize(new Dimension(600, 350));
        setBackground(Color.WHITE);
        setLayout(null);

        playerImage = new ImageIcon("download.png").getImage();

        // Start Button
        startButton = new JButton("START");
        startButton.setBounds(225, 180, 150, 45);

        startButton.addActionListener(e -> startGame());

        add(startButton);

        // Play Again Button
        playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(180, 210, 110, 35);
        playAgainButton.setVisible(false);

        playAgainButton.addActionListener(e -> restartGame());

        add(playAgainButton);

        // Exit Button
        exitButton = new JButton("Exit");
        exitButton.setBounds(310, 210, 110, 35);
        exitButton.setVisible(false);

        exitButton.addActionListener(e -> System.exit(0));

        add(exitButton);

        // Keyboard Controls
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

        // Move Left
        getActionMap().put("moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                if (gameStarted && !gameOver) {
                    playerX -= 10;

                    if (playerX < 0) {
                        playerX = 0;
                    }

                    repaint();
                }
            }
        });

        // Move Right
        getActionMap().put("moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                if (gameStarted && !gameOver) {
                    playerX += 10;

                    if (playerX > 500) {
                        playerX = 500;
                    }

                    repaint();
                }
            }
        });

        // Jump
        getActionMap().put("jump", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                if (gameStarted && !jumping && !gameOver) {
                    velocityY = -15;
                    jumping = true;
                }
            }
        });

        timer = new Timer(30, e -> updateGame());
    }

    // Start Game
    private void startGame() {

        gameStarted = true;

        startButton.setVisible(false);

        score = 0;
        obstacles.clear();

        obstacleTimer = 0;

        playerX = 100;
        playerY = 290 - playerHeight;

        velocityY = 0;
        jumping = false;

        lastScoreTime = System.currentTimeMillis();

        timer.start();

        repaint();
    }

    // Game Update
    private void updateGame() {

        if (!gameStarted || gameOver) {
            return;
        }

        // Player movement
        playerY += velocityY;
        velocityY++;

        if (playerY >= 290 - playerHeight) {

            playerY = 290 - playerHeight;

            velocityY = 0;

            jumping = false;
        }

        // Create obstacles
        obstacleTimer++;

        if (obstacleTimer >= 40) {

            obstacles.add(
                    new Rectangle(
                            600,
                            255,
                            35,
                            35
                    )
            );

            obstacleTimer = 0;
        }

        // Move obstacles
        for (Rectangle obstacle : obstacles) {
            obstacle.x -= 6;
        }

        // Remove old obstacles
        obstacles.removeIf(
                obstacle -> obstacle.x < -40
        );

        // Collision
        Rectangle playerHitBox = new Rectangle(
                playerX + 25,
                playerY + 10,
                50,
                85
        );

        for (Rectangle obstacle : obstacles) {

            if (playerHitBox.intersects(obstacle)) {

                gameOver = true;

                timer.stop();

                playAgainButton.setVisible(true);
                exitButton.setVisible(true);

                break;
            }
        }

        // Score
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastScoreTime >= 1000) {

            score++;

            lastScoreTime = currentTime;
        }

        repaint();
    }

    // Restart Game
    private void restartGame() {

        playerX = 100;
        playerY = 290 - playerHeight;

        velocityY = 0;
        jumping = false;

        obstacles.clear();

        obstacleTimer = 0;

        score = 0;

        gameOver = false;
        gameStarted = true;

        lastScoreTime = System.currentTimeMillis();

        playAgainButton.setVisible(false);
        exitButton.setVisible(false);

        timer.start();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Start Screen
        if (!gameStarted) {

            g.setColor(Color.BLACK);

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            32
                    )
            );

            g.drawString(
                    "2D RUNNING GAME",
                    145,
                    110
            );

            g.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            18
                    )
            );

            g.drawString(
                    "Press START to Play",
                    205,
                    155
            );

            return;
        }

        // Ground
        g.setColor(Color.BLACK);

        g.fillRect(
                0,
                290,
                600,
                5
        );

        // Player
        g.drawImage(
                playerImage,
                playerX,
                playerY,
                playerWidth,
                playerHeight,
                this
        );

        // Obstacles
        for (Rectangle obstacle : obstacles) {

            int x = obstacle.x;
            int y = obstacle.y;

            g.setColor(
                    new Color(
                            100,
                            100,
                            100
                    )
            );

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

        // Score
        g.setColor(Color.BLACK);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        g.drawString(
                "Score: " + score,
                20,
                30
        );

        // Game Over
        if (gameOver) {

            g.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            190
                    )
            );

            g.fillRect(
                    0,
                    0,
                    600,
                    350
            );

            g.setColor(Color.RED);

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            40
                    )
            );

            g.drawString(
                    "GAME OVER",
                    180,
                    130
            );

            g.setColor(Color.BLACK);

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            20
                    )
            );

            g.drawString(
                    "Final Score: " + score,
                    220,
                    170
            );
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame(
                "2D Running Game"
        );

        week8 gamePanel = new week8();

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