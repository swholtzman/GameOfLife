


public class World {

    public static final int GRID_SIZE = 20;
    public static final int CELL_SIZE = 20;


    public Cell cell;

    Herbivore herbivore;
    Plant plant;
    // Cell cellArr[][] = new Cell[][];



    World() {
    
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < CELL_SIZE; j++) {
              int randNum = RandomGenerator.nextNumber(99);
                if (randNum >= 85) {
                    cell = new Cell(herbivore);
                    
                } else if (randNum >= 65) {
                    cell = new Cell(plant);

                }
            }
        }

    }

}
