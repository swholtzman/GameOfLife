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

    // public void setCellColor(int rowNum, int colNum, Color color) {
    //     gui.setCellColor(rowNum, colNum, color);
    // }

}
