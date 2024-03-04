
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// implements MouseListener
public class GUI {

    JFrame frame = new JFrame();

    static final int GRID_SIZE = World.GRID_SIZE;
    static final int CELL_SIZE = World.CELL_SIZE;

    static final Color GREEN = Color.GREEN;
    static final Color YELLOW = Color.YELLOW;

    private JPanel gamePanel;

    GUI() {
        frame.setTitle("The Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                // cellPanel.setBackground(Color.BLUE);
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
    }

    public void setCellColor(int rowNum, int colNum, Color color) {
        // Get the index of the cell panel based on row and column numbers
        int cellIndex = rowNum * GRID_SIZE + colNum;
        // Get the panel at that index
        JPanel cellPanel = (JPanel) gamePanel.getComponent(cellIndex);
        // Set the background color
        cellPanel.setBackground(color);
    }

}
