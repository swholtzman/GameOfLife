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
     * The herbivore looks for empty cells or cells containing herbivoreEdible
     * organisms to move into.
     * 
     * @param world The world in which the herbivore moves and seeks food. It
     *              provides the context necessary for determining possible moves
     *              and the presence of food.
     */
    @Override
    public void move(World world) {

        if (!this.alive)
            return;

        int[] location = world.findOrganism(this);

        if (location == null) {
            return;
        }

        List<int[]> allPossibleMoves = world.getAllPossibleMoves(location[0], location[1]);

        List<int[]> possibleMoves = allPossibleMoves.stream()
                .filter(move -> {
                    Organism perhapsABerry = world.getCellOccupant(move[0], move[1]);
                    return perhapsABerry == null || perhapsABerry instanceof herbivoreEdible;
                })
                .collect(Collectors.toList());

        if (!possibleMoves.isEmpty()) {

            int moveIndex = RandomGenerator.nextNumber(possibleMoves.size());
            int[] moveTo = possibleMoves.get(moveIndex);

            this.eat(world.getCellOccupant(moveTo[0], moveTo[1]));
            world.moveOrganism(this, moveTo[0], moveTo[1]);
        }
    }

    /**
     * Attempts to reproduce based on specific conditions in the surrounding
     * environment of the world.
     * <p>
     * Herbivore reproduction can occur if there are at least two empty neighboring
     * cells for the offspring, at least one potential mate nearby, and sufficient
     * food sources (e.g., at least two plants) to sustain the population.
     * <p>
     * This method simulates the process of finding a suitable location and
     * conditions for reproducing a new Herbivore.
     * 
     * @param world The world in which the herbivore attempts to reproduce,
     *              providing the necessary context to identify suitable conditions
     *              for reproduction.
     */
    @Override
    public void reproduce(World world) {

        int[] location = world.findOrganism(this);

        if (location == null) {
            return;
        }

        int currentRow = location[0];
        int currentCol = location[1];

        List<Cell> emptyNeighbors = world.getEmptyNeighboringCells(currentRow, currentCol);
        List<Cell> potentialMates = world.getNeighboringCellsOfType(currentRow, currentCol, Herbivore.class);
        List<Cell> potentialFood = world.getNeighboringCellsOfType(currentRow, currentCol, herbivoreEdible.class);

        if (!emptyNeighbors.isEmpty() && emptyNeighbors.size() >= 2 && potentialMates.size() >= 1
                && potentialFood.size() >= 2) {

            int index = RandomGenerator.nextNumber(emptyNeighbors.size());
            Cell targetCell = emptyNeighbors.get(index);
            int[] newLocation = world.getDirectionForCell(targetCell, currentRow, currentCol);

            world.setCellOccupant(currentRow + newLocation[0], currentCol + newLocation[1], new Herbivore());

        }
    }

    /**
     * Allows the herbivore to consume food. If consumed, the hunger level
     * of the herbivore is reset to 0.
     * 
     * @param organism The organism that the herbivore attempts to eat. It must
     *                 implement the herbivoreEdible interface for the herbivore to
     *                 reset its hunger level.
     */
    private void eat(Organism organism) {

        if (organism instanceof herbivoreEdible) {
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

    /**
     * Increments the hunger level of the herbivore by one. This method is called to
     * simulate the herbivore getting hungrier over time.
     * If the hunger level reaches or exceeds the maximum hunger threshold, the
     * herbivore will die from starvation (alive = false).
     */
    @Override
    public void incrementHunger() {
        hunger++;
        if (hunger >= MAX_HUNGER) {
            this.alive = false;
        }
    }
}
