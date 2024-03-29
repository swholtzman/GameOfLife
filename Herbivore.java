// Herbivore.java

import java.util.List;
import java.util.stream.Collectors;
import java.awt.Color;

/**
 * Represents a Herbivore organism within the game world. Herbivores can move,
 * eat, and potentially reproduce within the game environment. They can eat
 * organisms that are marked as herbivoreEdible.
 * This class extends Organism and implements both carnivoreEdible and
 * omnivoreEdible interfaces, indicating that they can be consumed by carnivores
 * and omnivores respectively.
 */
public class Herbivore extends Organism implements carnivoreEdible, omnivoreEdible {
    private int MAX_HUNGER = 5;

    /**
     * Constructs a new Herbivore instance with initial settings. Initializes the
     * herbivore with a specific hunger level and sets it as alive.
     */
    public Herbivore() {
        super();
    }

    /**
     * Moves the herbivore to a new location based on available moves that meet its
     * conditions for movement and feeding. 
     * <p>
     * The herbivore looks for empty cells or
     * <p>
     * cells containing herbivoreEdible organisms to move into.
     * Increases the hunger level with each move and checks if the hunger level
     * exceeds the maximum hunger threshold, at which point the herbivore dies.
     * 
     * @param world The world in which the herbivore moves and seeks food. It
     *              provides the context necessary for determining possible moves
     *              and the presence of food.
     */
    @Override
    public void move( World world ) {
        hunger++;
        if (!this.alive)
            return;

        int[] location = world.findOrganism( this );

        if (location == null) {
            return;
        }

        List<int[]> allPossibleMoves = world.getAllPossibleMoves( location[0], location[1] );

        List<int[]> possibleMoves = allPossibleMoves.stream()
                .filter( move -> {
                    Organism perhapsABerry = world.getCellOccupant( move[0], move[1] );
                    return perhapsABerry == null || perhapsABerry instanceof herbivoreEdible;
                } )
                .collect( Collectors.toList() );

        if ( !possibleMoves.isEmpty() ) {

            int moveIndex = RandomGenerator.nextNumber( possibleMoves.size() );
            int[] moveTo = possibleMoves.get( moveIndex );

            this.eat(world.getCellOccupant( moveTo[0], moveTo[1] ) );
            world.moveOrganism( this, moveTo[0], moveTo[1] );

            if ( hunger >= MAX_HUNGER ) {
                this.alive = false;
            }
        }
    }

    /**
     * Handles the reproduction logic for the herbivore.
     * 
     * @param world The world in which the herbivore attempts to reproduce. Provides
     *              the necessary context for reproduction, including finding mates
     *              and suitable locations for offspring.
     */
    @Override
    public void reproduce( World world ) {
        // @TODO: Herbivore reproduction logic will be implemented here.
    }

    /**
     * Allows the herbivore to consume food. If consumed, the hunger level
     * of the herbivore is reset to 0.
     * 
     * @param organism The organism that the herbivore attempts to eat. It must
     *                 implement the herbivoreEdible interface for the herbivore to
     *                 reset its hunger level.
     */
    private void eat( Organism organism ) {

        if ( organism instanceof herbivoreEdible ) {
            this.hunger = 0;
        }
    }

    /**
     * Retrieves the life status of the herbivore.
     * 
     * @return true if the herbivore is alive, false otherwise.
     */
    @Override
    public boolean getLifeStatus() {
        return alive;
    }

    /**
     * Gets the color associated with the herbivore for GUI representation.
     * 
     * @return The color used to represent the herbivore in the GUI, typically
     *         yellow.
     */
    @Override
    public Color getColor() {
        return GUI.YELLOW;
    }
}
