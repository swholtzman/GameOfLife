


public class Game {
    GUI gui;
    World world;

    Game() {
        gui = new GUI(this);
        world = new World();
        initializeWorld();
    }

    private void initializeWorld() {

        for (int i = 0; i < World.GRID_SIZE; i++) {

            for (int j = 0; j < World.CELL_SIZE; j++) {
                int randNum = RandomGenerator.nextNumber(99);

                if (randNum >= 85) {
                    world.setCellType(i, j, new Herbivore());
                    gui.setCellColor(i, j, GUI.YELLOW); 

                } else if (randNum >= 65) {
                    world.setCellType(i, j, new Plant());
                    gui.setCellColor(i, j, GUI.GREEN); 

                } else {
                    world.setCellType(i, j, null);
                    gui.setCellColor(i, j, GUI.LIGHT_BROWN); 
                }
            }
        }
    }

    public World getWorld() {
        return this.world;

    }


    public void advanceGame() {
        // Step 1: Update the game state
        world.updateOrganisms(); // Assuming this method updates the state of all cells
        
        // Step 2: Refresh the GUI based on the updated game state
        gui.refreshDisplay();
    }
    
    
}
