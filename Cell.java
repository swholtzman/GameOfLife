// Cell.java

/**
 * Represents a single cell within the game world. A cell can contain an
 * organism, acting as the basic unit of space in the world.
 * <p>
 * Cells are used to manage the occupancy of organisms within the grid of the
 * game world, allowing for tracking and manipulation of organism positions.
 */
public class Cell {
    private Organism currentOccupant;

    /**
     * Constructs a Cell instance with an initial occupant.
     * 
     * @param occupant The organism that initially occupies the cell. Can be null if
     *                 the cell is initially empty.
     */
    public Cell( Organism occupant ) {
        this.currentOccupant = occupant;
    }

    /**
     * Retrieves the current occupant of the cell.
     * 
     * @return The organism currently occupying the cell, or null if the cell is
     *         empty.
     */
    public Organism getOccupant() {
        return this.currentOccupant;
    }

    /**
     * Sets or updates the occupant of the cell.
     * 
     * @param occupant The new organism to occupy the cell. Passing null will clear
     *                 the current occupant, making the cell empty.
     */
    public void setOccupant( Organism occupant ) {
        this.currentOccupant = occupant;
    }
}
