// GUI.java

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Provides the graphical user interface for the Game of Life simulation.
 */
public class GUI {

    JFrame frame = new JFrame();

    static final int GRID_SIZE = World.GRID_SIZE;
    static final int CELL_SIZE = World.GRID_SIZE;

    static final Color GREEN = new Color( 91, 146, 121 );
    static final Color YELLOW = new Color( 243, 201, 105 );
    static final Color RED = new Color( 135, 61, 72 );
    static final Color BLUE = new Color( 78, 118, 166 );
    static final Color LIGHT_BROWN = new Color( 205, 178, 162 );

    private JPanel gamePanel;

    private Game game;

    /**
     * Constructs the GUI for the game.
     * 
     * @param game The game instance to be associated with this GUI.
     */
    GUI( Game game ) {
        this.game = game;

        frame.setTitle( "The Game of Life" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        gamePanel = new JPanel();
        gamePanel.setLayout( new GridLayout( GRID_SIZE, GRID_SIZE ) );

        for ( int i = 0; i < GRID_SIZE; i++ ) {

            for ( int j = 0; j < GRID_SIZE; j++ ) {
                JPanel cellPanel = new JPanel();
                cellPanel.setPreferredSize( new Dimension( CELL_SIZE, CELL_SIZE ) );
                cellPanel.setBorder( BorderFactory.createLineBorder( LIGHT_BROWN ) );
                gamePanel.add( cellPanel );

            }
        }

        frame.add( gamePanel );
        frame.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int frameWidth = (int) ( screenWidth * ( 3.0 / 4.0 ) );
        int frameHeight = (int) ( screenWidth * ( 3.0 / 4.0 ) );
        frame.setSize( frameWidth, frameHeight );

        frame.setLocationRelativeTo( null );
        frame.setVisible( true );

        setupMouseListener();

    }

    /**
     * Sets the color of a specific cell in the grid based on its state.
     * 
     * @param rowNum The row number of the cell.
     * @param colNum The column number of the cell.
     * @param color  The color to set the cell.
     */
    public void setCellColor( int rowNum, int colNum, Color color ) {
        int cellIndex = rowNum * GRID_SIZE + colNum;
        JPanel cellPanel = (JPanel) gamePanel.getComponent( cellIndex );
        cellPanel.setBackground( color );
    }

    /**
     * Sets up a mouse listener for the game panel to detect clicks and advance the
     * game state.
     */
    public void setupMouseListener() {
        gamePanel.addMouseListener( new MouseAdapter() {

            @Override
            public void mouseClicked( MouseEvent e ) {
                super.mouseClicked(e);
                game.advanceGame();
                clearDead();
                refreshDisplay();

                frame.setTitle("The Game of Life - Turn: " + Game.turns);
            }
        });
    }

    /**
     * Iterates over all cells in the game world, identifying cells that are empty
     * For each empty cell found, it sets the cell's color to a light brown,
     * thereby "clearing the dead" (maybe a Monty Pythin reference?)
     */
    public void clearDead() {

        for (int i = 0; i < World.GRID_SIZE; i++) {

            for (int j = 0; j < World.GRID_SIZE; j++) {

                Organism occupant = game.getWorld().getCellOccupant( i, j );

                if ( occupant == null ) {
                    // System.out.println("cell is null");
                    setCellColor( i, j, LIGHT_BROWN );

                }
            }
        }
    }

    /**
     * Refreshes the display to reflect the current state of the game world.
     */
    public void refreshDisplay() {
        for ( int i = 0; i < World.GRID_SIZE; i++ ) {
            for ( int j = 0; j < World.GRID_SIZE; j++ ) {
                Organism occupant = game.getWorld().getCellOccupant(i, j);
                Color color = ( occupant != null ) ? occupant.getColor() : GUI.LIGHT_BROWN;
                setCellColor( i, j, color );
            }
        }
    }

}
