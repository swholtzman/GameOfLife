// World.java

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class World {

    public static final int GRID_SIZE = 20;
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    private List<Organism> organisms = new ArrayList<>();

    World() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                cells[i][j] = new Cell(null);
            }
        }
    }

    public int[] findOrganism(Organism organism) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (getCellOccupant(i, j) == organism) {
                    return new int[] { i, j }; // Return the organism's location
                }
            }
        }

        return null;
    }

    private void refreshOrganismsList() {
        organisms.clear();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getOccupant() != null) {
                    organisms.add(cell.getOccupant());
                }
            }
        }
    }

    public void setCellOccupant(int rowNum, int colNum, Organism organism) {
        cells[rowNum][colNum].setOccupant(organism);
    }

    public Organism getCellOccupant(int row, int col) {
        return cells[row][col].getOccupant();
    }

    public void moveOrganism(Organism organism, int newRow, int newCol) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (cells[i][j].getOccupant() == organism) {
                    cells[i][j].setOccupant(null);
                    cells[newRow][newCol].setOccupant(organism);
                    return;
                }
            }
        }
    }

    public List<Cell> getNeighboringCells(int row, int col) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
                neighbors.add(cells[newRow][newCol]);
            }
        }
        return neighbors;
    }

    public List<Cell> getEmptyNeighboringCells(int row, int col) {
        return getNeighboringCells(row, col).stream()
                .filter(cell -> cell.getOccupant() == null)
                .collect(Collectors.toList());
    }

    public List<Cell> getNeighboringCellsOfType(int row, int col, Class<?> type) {
        return getNeighboringCells(row, col).stream()
                .filter(cell -> type.isInstance(cell.getOccupant()))
                .collect(Collectors.toList());
    }

    public List<int[]> getPossibleMoves(int row, int col) {
        List<int[]> possibleMoves = new ArrayList<>();
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
                Cell potentialCell = cells[newRow][newCol];
                if (potentialCell.getOccupant() == null || potentialCell.getOccupant() instanceof herbivoreEdible) {
                    possibleMoves.add(new int[] { newRow, newCol });
                }
            }
        }

        return possibleMoves;
    }

    public int[] getDirectionForCell(Cell targetCell, int currentRow, int currentCol) {
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (currentRow + dRow >= 0 && currentRow + dRow < GRID_SIZE && currentCol + dCol >= 0
                        && currentCol + dCol < GRID_SIZE) {
                    if (cells[currentRow + dRow][currentCol + dCol] == targetCell) {
                        return new int[] { dRow, dCol };
                    }
                }
            }
        }
        return new int[] { 0, 0 };
    }

    public void compostAllDead() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Cell cell = cells[i][j];
                if (cell.getOccupant() != null && !cell.getOccupant().getLifeStatus()) {
                    cell.setOccupant(null);
                }
            }
        }
    }

    public void advanceGame() {
        refreshOrganismsList();

        for (Organism organism : new ArrayList<>(organisms)) {
            organism.move(this);
            organism.reproduce(this);
        }

        compostAllDead();
    }

}
