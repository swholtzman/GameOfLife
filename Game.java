
/**
 * Represents the game logic and state for the Game of Life simulation.
 */
public class Game {
    GUI gui;
    World world;

    /**
     * Constructs a Game object, initializing the GUI and the game world, and
     * populating the world.
     */
    Game() {
        gui = new GUI(this);
        world = new World();
        initializeWorld();
    }

    /**
     * Initializes the cells within the game world with random organisms and sets
     * their initial colors.
     */
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

    /**
     * Returns the current state of the game world.
     * 
     * @return The current game world.
     */
    public World getWorld() {
        return this.world;

    }

    /**
     * Advances the state of the game by one step, updating the organisms within the
     * world and refreshing the GUI.
     */
    public void advanceGame() {
        // Step 1: Update the game state
        world.updateOrganisms();

        // Step 2: Refresh the GUI based on the updated game state
        gui.refreshDisplay();
    }

}
