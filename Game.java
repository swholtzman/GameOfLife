


public class Game {
    GUI gui;
    World world;

    Game() {
        gui = new GUI();
        world = new World();
        initializeWorld();
    }

    private void initializeWorld() {

        for (int i = 0; i < World.GRID_SIZE; i++) {

            for (int j = 0; j < World.CELL_SIZE; j++) {

                int randNum = RandomGenerator.nextNumber(99);

                if (randNum >= 85) {

                    world.setCellType(i, j, new Herbivore());
                    gui.setCellColor(i, j, GUI.GREEN); 

                } else if (randNum >= 65) {
                    
                    world.setCellType(i, j, new Plant());
                    gui.setCellColor(i, j, GUI.YELLOW); 
                }
            }
        }
    }
    
}
