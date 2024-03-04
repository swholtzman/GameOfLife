// import java.awt.Color;

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

    }

    public void setCellType(int rowNum, int colNum, Organism organism) {
        cellArr[rowNum][colNum] = new Cell(organism);
    }

    public void updateOrganisms() {
        // Loop through all cells to update organisms
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < CELL_SIZE; j++) {
                Cell cell = cellArr[i][j];
                if (cell.getOccupant() instanceof Herbivore) {
                    Herbivore herbivore = (Herbivore) cell.getOccupant();
                    herbivore.getHungerLevel(); // Update hunger level
                    if (!herbivore.alive) {
                        // Handle the death of the herbivore
                        cell.setOccupant(null); // Remove from cell
                    }
                }
            }
        }
    }

    public void moveHerbivore(Herbivore herbivore, int newRow, int newCol) {
        // Assuming you have logic to determine the destination cell coordinates
        Cell destinationCell = cellArr[newRow][newCol];
        if (destinationCell.getOccupant() instanceof herbivoreEdible) {
            herbivore.eat(destinationCell); // Herbivore eats if the cell contains a plant
        }
        // Additional logic to actually move the herbivore to the new cell might be
        // needed
    }

}
