import java.util.List;

/**
 * Represents a Herbivore organism in the Game of Life simulation. Herbivores
 * can move,
 * eat plants, and die of hunger.
 */
public class Herbivore extends Organism {

    /**
     * Constructs a Herbivore with initial alive status set to true and hunger level
     * to 0.
     */
    Herbivore() {
        alive = true;
        hunger = 0;
    }

    /**
     * Updates the hunger level of the Herbivore. If the hunger level reaches a
     * certain
     * threshold, the Herbivore dies of hunger.
     */
    public void hungerLevel() {
        if (this.hunger == 5) {
            this.alive = false;
            // System.out.println("Herbivore died of hunger.");
        } else {
            this.hunger++;
            // System.out.println("Herbivore hunger increased to " + this.hunger);
        }
    }

    /**
     * Consumes the plant in the specified cell, resetting the hunger level of the
     * Herbivore.
     * If the cell does not contain a plant or an edible organism, the hunger level
     * remains unchanged.
     * 
     * @param cell The cell containing the plant or edible organism to be consumed.
     */
    public void eat(Cell cell) {
        if (cell.getOccupant() instanceof herbivoreEdible) {
            this.hunger = 0;
            // System.out.println("Herbivore ate and reset hunger.");
        }
    }

    /**
     * Returns the current hunger level of the Herbivore.
     * 
     * @return The hunger level of the Herbivore.
     */
    public int getHungerLevel() {
        return this.hunger;
    }

    /**
     * Moves the Herbivore to a new cell chosen from a list of possible moves. The
     * move is determined
     * randomly from the provided list. If the target cell contains a plant, the
     * Herbivore eats the
     * plant and resets its hunger level. This method also handles the logistics of
     * moving the Herbivore
     * from its current cell to the new cell, including marking the cells as moved
     * into or vacated.
     * 
     * @param possibleMoves A list of integer arrays representing possible new
     *                      positions for the Herbivore.
     * @param world         The world in which the Herbivore is moving.
     * @param currentRow    The current row index of the Herbivore.
     * @param currentCol    The current column index of the Herbivore.
     */
    public void performMove(List<int[]> possibleMoves, World world, int currentRow, int currentCol) {
        // Randomly select a move from the list of possible moves
        int index = RandomGenerator.nextNumber(possibleMoves.size());
        int[] selectedMove = possibleMoves.get(index);
        Cell[][] cellArr = world.getCellArr();

        Cell currentCell = cellArr[currentRow][currentCol];
        Cell targetCell = cellArr[selectedMove[0]][selectedMove[1]];

        // Check if the move is valid (not moving into another Herbivore or off the
        // grid)
        if (!targetCell.isMarked() && (targetCell.getOccupant() == null || targetCell.getOccupant() instanceof Plant)) {
            // Eat the plant if present, which resets the Herbivore's hunger
            if (targetCell.getOccupant() instanceof Plant) {
                this.eat(targetCell); // Assume this method resets the Herbivore's hunger
                // System.out.println("Herbivore ate a plant and reset hunger.");
            }

            // Move the Herbivore to the new Cell
            targetCell.setOccupant(this);

            // Remove the Herbivore from the old Cell
            currentCell.setOccupant(null);

            // Mark the new Cell as having moved into this turn
            targetCell.markAsMoved();

            // System.out.println("Moved Herbivore from [" + currentRow + "," + currentCol +
            // "] to [" + selectedMove[0] + "," + selectedMove[1] + "]");
        }
        currentCell.setJustVacatedByHerbivore(true);
    }

}
