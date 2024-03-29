// Main.java

/**
 * The entry point of the Game of Life simulation.
 */
public class Main {

    Game game;

    /**
     * Constructs a Main object by initializing the game instance.
     */
    Main() {
        game = new Game();
    }

    /**
     * The main method that starts the game.
     * 
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        new Main();
    }
}
