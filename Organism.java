// Organism.java

import java.awt.Color;

/**
 * Represents a general organism in the Game of Life simulation. This abstract
 * class provides the foundational characteristics and behaviors that are common
 * to all organisms.
 */
public abstract class Organism {
    boolean alive; // Indicates whether the organism is alive.
    int hunger; // Represents the hunger level of the organism.

    /**
     * Constructs an Organism with default properties. Specific characteristics
     * defined in the subclasses.
     */
    Organism() {
        this.alive = true;
        this.hunger = 0;
    }

    /**
     * Abstract method that defines how an organism moves within the game world. The
     * actual movement logic is implemented in subclasses based on the type of
     * organism.
     *
     * @param world The world in which the organism exists, providing the necessary
     *              context for movement decisions.
     */
    public abstract void move(World world);

    /**
     * Abstract method that defines the reproduction behavior of an organism.
     * Subclasses implement this method based on their specific reproduction
     * mechanisms.
     *
     * @param world The world in which the organism exists, offering the context
     *              needed for finding suitable reproduction conditions.
     */
    public abstract void reproduce(World world);

    /**
     * Abstract method to retrieve the color representation of the organism.
     */
    public abstract Color getColor();

    /**
     * Abstract method to check if the organism is alive.
     */
    public abstract boolean getLifeStatus();

    /**
     * Abstract method to increment the hunger level of the organism.
     */
    public abstract void incrementHunger();
}
