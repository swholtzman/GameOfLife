
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the world in which the game takes place, containing a grid of
 * cells.
 */
public class World {

    public static final int GRID_SIZE = 20; // The size of the grid.
    public static final int CELL_SIZE = 20; // The size of each cell.

    public Cell cell;

    private GUI gui;

    Herbivore herbivore;
    Plant plant;
    Cell cellArr[][];

    /**
     * Constructs a World object, initializing the grid with empty cells.
     */
    World() {
        cellArr = new Cell[GRID_SIZE][CELL_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {

            for (int j = 0; j < CELL_SIZE; j++) {
                cellArr[i][j] = new Cell(null); // Initialize each cell with no occupant

            }
        }
    }

    /**
     * Sets the type of organism in a specified cell.
     * 
     * @param rowNum   The row number of the cell.
     * @param colNum   The column number of the cell.
     * @param organism The organism to place in the cell.
     */
    public void setCellType(int rowNum, int colNum, Organism organism) {
        cellArr[rowNum][colNum] = new Cell(organism);
    }

    /**
     * Updates the state of all organisms within the world.
     */
    public void updateOrganisms() {
        resetHerbivoreMovementFlags(); // Reset only the just vacated flags here
        updateHerbivores();
        seedPlants();
        resetMovementFlags(); // Reset all move flags here
    }

    /**
     * Updates the state of all herbivores within the world.
     */
    private void updateHerbivores() {
        for (int i = 0; i < GRID_SIZE; i++) {

            for (int j = 0; j < CELL_SIZE; j++) {
                Cell cell = cellArr[i][j];
                Organism occupant = cell.getOccupant();

                if (occupant instanceof Herbivore && !cell.isMarked()) {
                    Herbivore herbivore = (Herbivore) occupant;
                    herbivore.hungerLevel();

                    if (!herbivore.alive) {
                        // remove dead herbivore
                        cell.setOccupant(null);
                    }
                }
            }
        }
        for (int i = 0; i < GRID_SIZE; i++) {

            for (int j = 0; j < CELL_SIZE; j++) {
                Cell cell = cellArr[i][j];

                if (!cell.isMarked()) {
                    Organism occupant = cell.getOccupant();

                    if (occupant instanceof Herbivore) {
                        Herbivore herbivore = (Herbivore) occupant;

                        if (herbivore.alive) {
                            attemptMoveHerbivore(i, j);
                        }
                    }
                }
            }
        }
    }

    /**
     * Resets the movement flags for all herbivores in the world. This method is
     * called at the beginning of each game update cycle to prepare for herbivore
     * movements.
     */
    private void resetHerbivoreMovementFlags() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < CELL_SIZE; j++) {
                cellArr[i][j].setJustVacatedByHerbivore(false);
            }
        }
    }

    /**
     * Returns the type of organism present in a specific cell.
     * 
     * @param i The row index of the cell.
     * @param j The column index of the cell.
     * @return A string representing the type of organism in the cell ("Herbivore",
     *         "Plant", or "Empty").
     */
    public String getCellType(int i, int j) {
        Organism occupant = cellArr[i][j].getOccupant();

        if (occupant instanceof Herbivore) {
            return "Herbivore";

        } else if (occupant instanceof Plant) {
            return "Plant";

        } else {
            return "Empty";

        }
    }

    /**
     * Attempts to move a herbivore from its current cell to a new cell. This method
     * calculates possible moves based on the current position and the state of
     * surrounding cells.
     * 
     * @param currentRow The current row of the herbivore.
     * @param currentCol The current column of the herbivore.
     */
    public void attemptMoveHerbivore(int currentRow, int currentCol) {
        Herbivore herbivore = (Herbivore) cellArr[currentRow][currentCol].getOccupant();
        List<int[]> possibleMoves = calculatePossibleMoves(currentRow, currentCol);

        if (!possibleMoves.isEmpty() && herbivore != null) {
            herbivore.performMove(possibleMoves, this, currentRow, currentCol);
        }
    }

    /**
     * Calculates possible moves for a herbivore based on its current position. This
     * method takes into account the state of surrounding cells and determines where
     * the herbivore can move.
     * 
     * @param currentRow The row index from which to calculate moves.
     * @param currentCol The column index from which to calculate moves.
     * @return A list of integer arrays, where each array represents a possible move
     *         as [newRow, newCol].
     */
    private List<int[]> calculatePossibleMoves(int currentRow, int currentCol) {
        List<int[]> possibleMoves = new ArrayList<>();
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

        for (int[] dir : directions) {
            int newRow = currentRow + dir[0];
            int newCol = currentCol + dir[1];
            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < CELL_SIZE) {
                Cell potentialCell = cellArr[newRow][newCol];
                if (!potentialCell.isMarked()
                        && (potentialCell.getOccupant() == null
                                || potentialCell.getOccupant() instanceof Plant)) {
                    possibleMoves.add(new int[] { newRow, newCol });
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Retrieves the array of cells representing the world.
     * 
     * @return A two-dimensional array of Cell objects representing the grid of the
     *         world.
     */
    public Cell[][] getCellArr() {
        return this.cellArr;
    }

    /**
     * Resets the movement flags for all cells. This method is called after all
     * movements have been processed to prepare the cells for the next update cycle.
     */
    public void resetMovementFlags() {
        System.out.println("Resetting movement flags for all cells.");

        for (int i = 0; i < GRID_SIZE; i++) {

            for (int j = 0; j < CELL_SIZE; j++) {
                cellArr[i][j].resetMovedIntoFlag();

            }
        }
    }

    /**
     * Seeds new plants in the world. This method is called as part of the game
     * update
     * cycle to simulate plant reproduction.
     */
    private void seedPlants() {
        for (int i = 0; i < GRID_SIZE; i++) {

            for (int j = 0; j < CELL_SIZE; j++) {
                Cell cell = cellArr[i][j];
                Organism occupant = cell.getOccupant();

                if (occupant instanceof Plant && !cell.isMarked()) {
                    ((Plant) occupant).seed(i, j, this);
                }
            }
        }
    }

    /**
     * Calculates possible locations for planting seeds based on the current
     * position of a plant.
     * This method considers the surrounding cells and determines where new plants
     * can be seeded.
     * 
     * @param currentRow The row index of the current plant.
     * @param currentCol The column index of the current plant.
     * @return A list of integer arrays, where each array represents a possible
     *         location for seeding as [newRow, newCol].
     */
    public List<int[]> calculatePossibleSeeds(int currentRow, int currentCol) {
        List<int[]> possibleSeeds = new ArrayList<>();
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

        for (int[] dir : directions) {
            int newRow = currentRow + dir[0];
            int newCol = currentCol + dir[1];

            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < CELL_SIZE) {
                Cell potentialCell = cellArr[newRow][newCol];

                if (potentialCell.getOccupant() == null && !potentialCell.isMarked()) {
                    possibleSeeds.add(new int[] { newRow, newCol });
                }
            }
        }
        return possibleSeeds;
    }

    /**
     * Determines if there are four plants surrounding a given cell. This method is
     * used
     * to assess the conditions for plant reproduction.
     * 
     * @param currentRow The row index of the cell to check.
     * @param currentCol The column index of the cell to check.
     * @return true if there are exactly four plants surrounding the cell, false
     *         otherwise.
     */

    public boolean hasFourPlants(int currentRow, int currentCol) {
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
        int plantCount = 0;

        for (int[] dir : directions) {
            int newRow = currentRow + dir[0];
            int newCol = currentCol + dir[1];
            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < CELL_SIZE) {
                Cell potentialCell = cellArr[newRow][newCol];
                if (potentialCell.getOccupant() instanceof Plant) {
                    plantCount++;
                }
            }
        }

        return plantCount == 4;
    }

}
