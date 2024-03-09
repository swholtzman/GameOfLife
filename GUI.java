
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Provides the graphical user interface for the Game of Life simulation.
 */
public class GUI {

    JFrame frame = new JFrame();

    static final int GRID_SIZE = World.GRID_SIZE;
    static final int CELL_SIZE = World.CELL_SIZE;

    static final Color GREEN = Color.GREEN;
    static final Color YELLOW = Color.YELLOW;
    static final Color LIGHT_BROWN = new Color(210, 180, 140);

    private JPanel gamePanel;

    private Game game;

    /**
     * Constructs the GUI for the game.
     * 
     * @param game The game instance to be associated with this GUI.
     */
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

    /**
     * Sets the color of a specific cell in the grid based on its state.
     * 
     * @param rowNum The row number of the cell.
     * @param colNum The column number of the cell.
     * @param color  The color to set the cell.
     */
    public void setCellColor(int rowNum, int colNum, Color color) {
        // Get the index of the cell panel based on row and column numbers
        int cellIndex = rowNum * GRID_SIZE + colNum;
        // Get the panel at that index
        JPanel cellPanel = (JPanel) gamePanel.getComponent(cellIndex);
        // Set the background color
        cellPanel.setBackground(color);
    }

    /**
     * Sets up a mouse listener for the game panel to detect clicks and advance the
     * game state.
     */
    public void setupMouseListener() {
        gamePanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                game.advanceGame();
                // System.out.println("Panel Clicked");

            }
        });
    }

    /**
     * Refreshes the display to reflect the current state of the game world.
     */
    public void refreshDisplay() {
        for (int i = 0; i < World.GRID_SIZE; i++) {

            for (int j = 0; j < World.CELL_SIZE; j++) {
                String cellType = game.getWorld().getCellType(i, j); // Assuming getWorld() and getCellType() methods
                                                                     // are implemented
                Color color;

                switch (cellType) {
                    case "Herbivore":
                        color = GUI.YELLOW;
                        break;
                    case "Plant":
                        color = GUI.GREEN;
                        break;
                    default:
                        color = LIGHT_BROWN; // Default color for empty or unknown cell type

                }

                setCellColor(i, j, color);
            }
        }
    }

}
