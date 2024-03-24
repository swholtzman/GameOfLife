import java.util.ArrayList;
import java.util.List;

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

     // This method populates the world with Plants and Herbivores randomly.
    public void initializeWorld() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int rand = RandomGenerator.nextNumber(100); // Assuming 100 to get a percentage-like choice
                if (rand < 15) {
                    // Place a Herbivore with 15% probability
                    this.cells[i][j].setOccupant(new Herbivore());
                } else if (rand < 30) {
                    // Place a Plant with additional 15% probability
                    this.cells[i][j].setOccupant(new Plant());
                }
            }
        }
    }

    public int[] findOrganism(Organism organism) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (getCellOccupant(i, j) == organism) {
                    return new int[] {i, j}; // Return the organism's location
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

    public void act(Organism organism) {
        if (organism instanceof Herbivore) {
            organism.move(this);
        } else if (organism instanceof Plant) {
            organism.reproduce(this);
        }
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

    public List<int[]> getPossibleMoves(int row, int col) {
        List<int[]> possibleMoves = new ArrayList<>();
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
                Cell potentialCell = cells[newRow][newCol];
                if (potentialCell.getOccupant() == null || potentialCell.getOccupant() instanceof herbivoreEdible) {
                    possibleMoves.add(new int[]{newRow, newCol});
                }
            }
        }

        return possibleMoves;
    }

    public void advanceGame() {
        refreshOrganismsList(); 
        
        for (Organism organism : new ArrayList<>(organisms)) {
            organism.move(this); 
            organism.reproduce(this); 
        }
    }
    
}
