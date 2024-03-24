
/**
 * Represents the game logic and state for the Game of Life simulation.
 */
public class Game {
    public static int turns;

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

            for (int j = 0; j < World.GRID_SIZE; j++) {
                int randNum = RandomGenerator.nextNumber(99);

                if (randNum >= 85) {
                    world.setCellOccupant(i, j, new Herbivore());
                    gui.setCellColor(i, j, GUI.YELLOW);

                } else if (randNum >= 65) {
                    world.setCellOccupant(i, j, new Plant());
                    gui.setCellColor(i, j, GUI.GREEN);

                } else {
                    world.setCellOccupant(i, j, null);
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
        world.advanceGame(); // Advances all organisms in the world
    
        turns++; // Increment turn counter
    
        gui.refreshDisplay(); // Refresh GUI to reflect the new state
    }
    

}
