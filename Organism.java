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
     * Represents the ability of the organism to move within the game world.
     * The implementation of this method will depend on the type of organism.
     * For example, herbivores might move towards food, while plants do not move.
     *
     * @param world The world in which the organism exists. This provides the context needed for movement.
     */
    public abstract void move( World world );

    /**
     * Represents the organism's ability to reproduce. Different organisms will
     * have different conditions and methods for reproduction.
     * For instance, plants might seed nearby empty cells, while herbivores could have other mechanisms.
     *
     * @param world The world in which the organism exists. This provides the context needed for reproduction.
     */
    public abstract void reproduce( World world );


    /*
     * Abstract method for each Organism to "get" their colour when requested.
     */
    public abstract Color getColor();


    /*
     * Abstract method to return the status of whether or not an organism is alive.
     */
    public abstract boolean getLifeStatus();
}

