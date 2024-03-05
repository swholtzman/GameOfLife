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
        // Loop through all cells to update organisms
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < CELL_SIZE; j++) {
                Cell cell = cellArr[i][j];
                Organism occupant = cell.getOccupant();
                if (occupant instanceof Herbivore) {
                    Herbivore herbivore = (Herbivore) occupant;
                    herbivore.hungerLevel(); // Update the hunger level
                    
                    if (!herbivore.alive) {
                        cell.setOccupant(null); // Remove the herbivore if it's no longer alive
                    } else {
                        moveHerbivore(i, j); // Move the herbivore if it's alive
                    }
                }
            }
        }
        resetMovementFlags();
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

    public void moveHerbivore(int currentRow, int currentCol) {
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
        List<int[]> possibleMoves = new ArrayList<>();

        for (int[] direction : directions) {
            int newRow = currentRow + direction[0];
            int newCol = currentCol + direction[1];

            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < CELL_SIZE) {
                Cell targetCell = cellArr[newRow][newCol];
                if (targetCell.getOccupant() == null || targetCell.getOccupant() instanceof Plant) {
                    possibleMoves.add(new int[] { newRow, newCol });
                }
            }
        }

        if (!possibleMoves.isEmpty()) {
            int index = RandomGenerator.nextNumber(possibleMoves.size()); // Correctly guarded call
            int[] selectedMove = possibleMoves.get(index);

            Cell currentCell = cellArr[currentRow][currentCol];
            Herbivore herbivore = (Herbivore) currentCell.getOccupant();
            if (cellArr[selectedMove[0]][selectedMove[1]].getOccupant() instanceof Plant) {
                herbivore.eat(cellArr[selectedMove[0]][selectedMove[1]]);
                // Optionally, remove the plant after being eaten
                cellArr[selectedMove[0]][selectedMove[1]].setOccupant(null);
            }
            cellArr[selectedMove[0]][selectedMove[1]].setOccupant(herbivore);
            currentCell.setOccupant(null);

            // Mark the new location as having moved this turn if needed
            // cellArr[selectedMove[0]][selectedMove[1]].markAsMoved();
        }
    }

    public void resetMovementFlags() {
        System.out.println("Resetting movement flags for all cells.");
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < CELL_SIZE; j++) {
                cellArr[i][j].resetMovedIntoFlag();
            }
        }
    }

}
