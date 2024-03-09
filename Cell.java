
/**
 * Represents a cell in the grid of the Game of Life simulation. Each cell can
 * contain an Organism and tracks movement and occupancy status related to game
 * logic.
 */
public class Cell {
    private Organism currentOccupant;
    private boolean movedIntoThisTurn;
    private boolean justVacatedByHerbivore;

    /**
     * Constructs a Cell with a potential organism occupant.
     * 
     * @param organism The organism initially occupying the cell, null if the cell
     *                 is empty.
     */
    public Cell(Organism organism) {
        this.currentOccupant = organism;
        this.movedIntoThisTurn = false;
    }

    /**
     * Returns the current occupant of the cell.
     * 
     * @return The organism currently occupying the cell, or null if it is empty.
     */
    public Organism getOccupant() {
        return this.currentOccupant;
    }

    /**
     * Checks if an organism has moved into this cell during the current turn.
     * 
     * @return true if the cell has been occupied this turn, false otherwise.
     */
    public boolean getMoveStatus() {
        return this.movedIntoThisTurn;
    }

    /**
     * Sets or changes the occupant of the cell. Prints a message indicating the
     * change.
     * 
     * @param organism The new organism to occupy the cell, or null to make the cell
     *                 empty.
     */
    public void setOccupant(Organism organism) {
        // System.out.println("Changing occupant of cell [" + /* Provide row and col if available */ "] from "
                // + (this.currentOccupant == null ? "null" : this.currentOccupant.getClass().getSimpleName()) + " to "
                // + (organism == null ? "null" : organism.getClass().getSimpleName()));
        this.currentOccupant = organism;
    }

    /**
     * Marks the cell as having been moved into during the current turn.
     */
    public void markAsMoved() {
        // System.out.println("Marking cell as moved.");
        this.movedIntoThisTurn = true;
    }

    /**
     * Checks if the cell has been marked as moved into during the current turn.
     * 
     * @return true if the cell is marked as moved into this turn, false otherwise.
     */
    public boolean isMarked() {
        return this.movedIntoThisTurn;
    }

    /**
     * Resets the flag indicating that the cell has been moved into, preparing it
     * for the next turn.
     */
    public void resetMovedIntoFlag() {
        this.movedIntoThisTurn = false;
    }

    /**
     * Checks if a herbivore has just vacated this cell.
     * 
     * @return true if a herbivore has just vacated the cell, false otherwise.
     */
    public boolean wasJustVacatedByHerbivore() {
        return justVacatedByHerbivore;
    }

    /**
     * Sets the flag indicating whether a herbivore has just vacated the cell.
     * 
     * @param justVacated True if a herbivore has just vacated the cell, false
     *                    otherwise.
     */
    public void setJustVacatedByHerbivore(boolean justVacated) {
        this.justVacatedByHerbivore = justVacated;
    }
}
