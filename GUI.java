
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// implements MouseListener
public class GUI {

    JFrame frame = new JFrame();

    static final int GRID_SIZE = World.GRID_SIZE;
    static final int CELL_SIZE = World.CELL_SIZE;

    static final Color GREEN = Color.GREEN;
    static final Color YELLOW = Color.YELLOW;

    private JPanel gamePanel;

    private Game game;

    GUI(Game game) {
        this.game = game;

        frame.setTitle("The Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                gamePanel.add(cellPanel);
            }
        }

        frame.add(gamePanel);
        frame.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = (int) (screenWidth * (3.0 / 4.0));
        int frameHeight = (int) (screenWidth * (3.0 / 4.0));
        frame.setSize(frameWidth, frameHeight);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        setupMouseListener();

    }

    public void setCellColor(int rowNum, int colNum, Color color) {
        // Get the index of the cell panel based on row and column numbers
        int cellIndex = rowNum * GRID_SIZE + colNum;
        // Get the panel at that index
        JPanel cellPanel = (JPanel) gamePanel.getComponent(cellIndex);
        // Set the background color
        cellPanel.setBackground(color);
    }

    public void setupMouseListener() {
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                game.advanceGame();
                System.out.println("Panel Clicked");
            }
        });
    }

    public void refreshDisplay() {
        for (int i = 0; i < World.GRID_SIZE; i++) {
            for (int j = 0; j < World.CELL_SIZE; j++) {
                String cellType = game.getWorld().getCellType(i, j); // Assuming getWorld() and getCellType() methods are implemented
                Color color;
                switch (cellType) {
                    case "Herbivore":
                        color = GUI.GREEN;
                        break;
                    case "Plant":
                        color = GUI.YELLOW;
                        break;
                    default:
                        color = Color.WHITE; // Default color for empty or unknown cell types
                }
                setCellColor(i, j, color);
            }
        }
    }
    

}
