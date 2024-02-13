


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI extends JFrame {

    static final int GRID_SIZE = World.GRID_SIZE;
    static final int CELL_SIZE = World.CELL_SIZE;

    GUI() {
        setTitle("The Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                gamePanel.add(cellPanel);
            }
        }

        add(gamePanel);
        pack();


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = (int) (screenWidth * (3.0/4.0));
        int frameHeight = (int) (screenWidth * (3.0/4.0));
        setSize(frameWidth, frameHeight);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // public void setColour(){};

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }

}
