// import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

public class World {

    public static final int GRID_SIZE = 20;
    public static final int CELL_SIZE = 20;

    public Cell cell;

    private GUI gui;

    Herbivore herbivore;
    Plant plant;
    Cell cellArr[][];

    World() {
        cellArr = new Cell[GRID_SIZE][CELL_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {

            for (int j = 0; j < CELL_SIZE; j++) {
                cellArr[i][j] = new Cell(null); // Initialize each cell with no occupant

            }
        }
    }

    public void setCellType(int rowNum, int colNum, Organism organism) {
        cellArr[rowNum][colNum] = new Cell(organism);
    }

    public void updateOrganisms() {
        resetHerbivoreMovementFlags(); // Reset only the just vacated flags here
        updateHerbivores();
        seedPlants();
        resetMovementFlags(); // Reset all move flags here
    }

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

    private void resetHerbivoreMovementFlags() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < CELL_SIZE; j++) {
                cellArr[i][j].setJustVacatedByHerbivore(false);
            }
        }
    }

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

    public void attemptMoveHerbivore(int currentRow, int currentCol) {
        Herbivore herbivore = (Herbivore) cellArr[currentRow][currentCol].getOccupant();
        List<int[]> possibleMoves = calculatePossibleMoves(currentRow, currentCol);

        if (!possibleMoves.isEmpty() && herbivore != null) {
            herbivore.performMove(possibleMoves, this, currentRow, currentCol);
        }
    }

    private List<int[]> calculatePossibleMoves(int currentRow, int currentCol) {
        List<int[]> possibleMoves = new ArrayList<>();
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

        for (int[] dir : directions) {
            int newRow = currentRow + dir[0];
            int newCol = currentCol + dir[1];
            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < CELL_SIZE) {
                Cell potentialCell = cellArr[newRow][newCol];
                if (!potentialCell.isMarked()
                        && (potentialCell.getOccupant() == null || potentialCell.getOccupant() instanceof Plant)) {
                    possibleMoves.add(new int[] { newRow, newCol });
                }
            }
        }
        return possibleMoves;
    }

    public Cell[][] getCellArr() {
        return this.cellArr;
    }

    public void resetMovementFlags() {
        System.out.println("Resetting movement flags for all cells.");

        for (int i = 0; i < GRID_SIZE; i++) {

            for (int j = 0; j < CELL_SIZE; j++) {
                cellArr[i][j].resetMovedIntoFlag();

            }
        }
    }

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
