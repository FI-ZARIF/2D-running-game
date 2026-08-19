import java.awt.*;
import javax.swing.*;

public class Week2 {
    public static void main(String[] args) {

        JFrame frame = new JFrame("2D Running Game");
        frame.setSize(600, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("2D Running Game");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(180, 40, 250, 30);
 
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(220, 120, 140, 40);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(220, 180, 140, 40);

        JLabel score = new JLabel("Score: 0");
        score.setBounds(20, 280, 100, 20);

        panel.add(title);
        panel.add(startButton);
        panel.add(exitButton);
        panel.add(score);

        frame.add(panel);
        frame.setVisible(true);
    }
}