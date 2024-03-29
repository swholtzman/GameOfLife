// Omnivore.java 

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an Omnivore organism within the game world. Omnivores can move,
 * eat a wide variety of foods, and potentially reproduce within the game
 * environment. They can consume organisms marked as omnivoreEdible, including
 * both plant and animal material.
 * This class extends Organism and implements the carnivoreEdible interface,
 * indicating that they can be consumed by carnivores.
 */
public class Omnivore extends Organism implements carnivoreEdible {

    private int MAX_HUNGER = 5;

    /**
     * Moves the omnivore to a new location based on available moves that meet its
     * conditions for movement and feeding.
     * <p>
     * The omnivore looks for empty cells or cells containing omnivoreEdible
     * organisms, which include both plants and other organisms, to move into.
     * 
     * @param world The world in which the omnivore moves and seeks food. It
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
                    Organism perhapsAFood = world.getCellOccupant(move[0], move[1]);
                    return perhapsAFood == null || perhapsAFood instanceof omnivoreEdible;
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
     * Allows the omnivore to consume food. If the consumed organism is
     * omnivoreEdible, the hunger level of the omnivore is reset to 0.
     * 
     * @param organism The organism that the omnivore attempts to eat. It must
     *                 implement the omnivoreEdible interface for the omnivore to
     *                 feed and reset its hunger level.
     */
    private void eat(Organism organism) {

        if (organism instanceof omnivoreEdible) {
            this.hunger = 0;
        }
    }

    /**
     * Retrieves the life status of the omnivore.
     * 
     * @return true if the omnivore is alive, false otherwise. The life status is
     *         based on the omnivore's ability to feed and avoid exceeding its
     *         maximum hunger level.
     */
    @Override
    public boolean getLifeStatus() {
        return alive;
    }

    /**
     * Gets the color associated with the omnivore for GUI representation.
     * 
     * @return The color used to represent the omnivore in the GUI; blue.
     */
    @Override
    public Color getColor() {
        return GUI.BLUE;
    }

    /**
     * Attempts to reproduce based on the surrounding environment in the world.
     * <p>
     * Reproduction for an omnivore requires at least 3 empty neighboring cells for
     * offspring, at least one potential mate nearby, and at least two sources of
     * food (which can be herbivores, carnivores, or plants) in the surrounding
     * cells to ensure the offspring's survival.
     * 
     * @param world The world in which the omnivore attempts to reproduce, providing
     *              the necessary context for assessing conditions and managing
     *              offspring.
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
        List<Cell> potentialMates = world.getNeighboringCellsOfType(currentRow, currentCol, Omnivore.class);
        List<Cell> potentialFood1 = world.getNeighboringCellsOfType(currentRow, currentCol, Herbivore.class);
        List<Cell> potentialFood2 = world.getNeighboringCellsOfType(currentRow, currentCol, Carnivore.class);
        List<Cell> potentialFood3 = world.getNeighboringCellsOfType(currentRow, currentCol, Plant.class);

        if (!emptyNeighbors.isEmpty() && emptyNeighbors.size() >= 3 && potentialMates.size() >= 1
                && ((potentialFood1.size() + potentialFood2.size() + potentialFood3.size()) >= 2)) {

            int index = RandomGenerator.nextNumber(emptyNeighbors.size());
            Cell targetCell = emptyNeighbors.get(index);
            int[] newLocation = world.getDirectionForCell(targetCell, currentRow, currentCol);

            world.setCellOccupant(currentRow + newLocation[0], currentCol + newLocation[1], new Omnivore());

        }
    }

    /**
     * Increments the hunger level of the omnivore. If the hunger level reaches or
     * exceeds the maximum threshold, the omnivore dies.
     * <p>
     * This method is called at each turn of the game to simulate the omnivore's
     * increasing hunger over time.
     */
    @Override
    public void incrementHunger() {
        hunger++;
        if (hunger >= MAX_HUNGER) {
            this.alive = false;
        }
    }

}
