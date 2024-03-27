// Plant.java

import java.util.List;
import java.awt.Color;

/**
 * Represents a Plant organism within the game world. 
 * 
 * This class extends Organism and implements the herbivoreEdible interface,
 * indicating that they can be consumed by herbivores.
 */
public class Plant extends Organism implements herbivoreEdible {

    /**
     * Constructs a new Plant instance.
     */
    public Plant() {
        super();
    }

    /**
     * Overrides the move method for the Plant. Since plants do not move, this
     * method is implemented as empty.
     * 
     * @param world The world in which the plant exists. This parameter is included
     *              to maintain consistency with the Organism interface but is not
     *              used.
     */
    @Override
    public void move( World world ) {
    }

    /**
     * Attempts to reproduce based on the surrounding environment in the world. 
     * <p>
     * A plant can reproduce if there are at least 3 empty neighboring cells and
     * exactly 4 neighboring plants for cross-pollination.
     * 
     * @param world The world in which the plant attempts to reproduce. This
     *              provides the context necessary to check the surrounding cells
     *              and manage reproduction.
     */
    @Override
    public void reproduce( World world ) {

        int[] location = world.findOrganism( this );

        if ( location == null ) {
            return; 
        }

        int currentRow = location[0];
        int currentCol = location[1];

        List<Cell> emptyNeighbors = world.getEmptyNeighboringCells( currentRow, currentCol );
        List<Cell> neighboringPlants = world.getNeighboringCellsOfType( currentRow, currentCol, Plant.class );

        if ( !emptyNeighbors.isEmpty() && emptyNeighbors.size() >= 3 && neighboringPlants.size() == 4 ) {

            int index = RandomGenerator.nextNumber( emptyNeighbors.size() );
            Cell targetCell = emptyNeighbors.get( index );
            int[] newLocation = world.getDirectionForCell( targetCell, currentRow, currentCol );

            world.setCellOccupant( currentRow + newLocation[0], currentCol + newLocation[1], new Plant() );

        }
    }

    /**
     * Gets the life status of the plant.
     * 
     * @return true if the plant is alive, false otherwise. This implementation
     *         always returns true, as plants (at this stage) do not have a life
     *         status that changes.
     */
    public boolean getLifeStatus() {
        return alive;
    }

    /**
     * Gets the color associated with the plant for GUI representation.
     * 
     * @return The color used to represent the plant in the GUI: green.
     */
    @Override
    public Color getColor() {
        return GUI.GREEN;
    }
}
