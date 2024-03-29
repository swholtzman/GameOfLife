// Carnivore.java

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Carnivore organism within the game world. Carnivores can move,
 * eat, and potentially reproduce within the game environment. They consume
 * organisms marked as carnivoreEdible.
 * This class extends Organism and implements the omnivoreEdible interface,
 * indicating that they can also be consumed by omnivores.
 */
public class Carnivore extends Organism implements omnivoreEdible {

    private int MAX_HUNGER = 5;

    /**
     * Moves the carnivore based on available moves that meet its conditions for
     * movement and feeding.
     * <p>
     * The carnivore searches for empty cells or cells containing organisms it can
     * consume (marked as carnivoreEdible).
     * 
     * @param world The world in which the carnivore exists, providing the context
     *              necessary for determining possible moves and locating prey.
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
                    Organism perhapsASausage = world.getCellOccupant(move[0], move[1]);
                    return perhapsASausage == null || perhapsASausage instanceof carnivoreEdible;
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
     * Allows the carnivore to consume food. Consuming a food item resets the
     * carnivore's hunger level to 0.
     * <p>
     * This method is invoked after a successful move to a cell containing a
     * carnivoreEdible organism.
     * 
     * @param organism The organism that the carnivore attempts to eat. Must
     *                 implement the carnivoreEdible interface for the carnivore to
     *                 feed and reset its hunger.
     */
    private void eat(Organism organism) {

        if (organism instanceof carnivoreEdible) {
            this.hunger = 0;
        }
    }

    /**
     * Retrieves the life status of the carnivore.
     * 
     * @return true if the carnivore is alive, false otherwise. The carnivore's life
     *         status is determined by its hunger level and interactions within the
     *         world.
     */
    @Override
    public boolean getLifeStatus() {
        return alive;
    }

    /**
     * Gets the color associated with the carnivore for GUI representation.
     * 
     * @return The color used to represent the carnivore in the GUI; red.
     */
    @Override
    public Color getColor() {
        return GUI.RED;
    }

    /**
     * Attempts to reproduce based on the surrounding environment in the world.
     * <p>
     * Reproduction for a carnivore requires at least 3 empty neighboring cells for
     * offspring, at least 1 potential mate nearby, and at least 2 sources of
     * food (herbivores or omnivores) in the surrounding cells to ensure the
     * offspring's survival.
     * 
     * @param world The world in which the carnivore attempts to reproduce,
     *              providing the necessary context for assessing conditions and
     *              managing offspring.
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
        List<Cell> potentialMates = world.getNeighboringCellsOfType(currentRow, currentCol, Carnivore.class);
        List<Cell> potentialFood1 = world.getNeighboringCellsOfType(currentRow, currentCol, Herbivore.class);
        List<Cell> potentialFood2 = world.getNeighboringCellsOfType(currentRow, currentCol, Omnivore.class);

        if (!emptyNeighbors.isEmpty() && emptyNeighbors.size() >= 3 && potentialMates.size() >= 1
                && ((potentialFood1.size() + potentialFood2.size()) >= 2)) {

            int index = RandomGenerator.nextNumber(emptyNeighbors.size());
            Cell targetCell = emptyNeighbors.get(index);
            int[] newLocation = world.getDirectionForCell(targetCell, currentRow, currentCol);

            world.setCellOccupant(currentRow + newLocation[0], currentCol + newLocation[1], new Carnivore());

        }
    }

    /**
     * Increments the hunger level of the carnivore. If the hunger level reaches or
     * exceeds the maximum threshold, the carnivore dies.
     * <p>
     * This method is called at each tick of the game to simulate the carnivore's
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
