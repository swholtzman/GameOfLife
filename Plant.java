import java.util.List;

/**
 * Represents a Plant organism in the Game of Life simulation. Plants can seed
 * new plants
 * under certain conditions. This class extends the abstract Organism class and
 * implements
 * the herbivoreEdible interface, indicating that it can be eaten by herbivores.
 */
public class Plant extends Organism implements herbivoreEdible {

    /**
     * Constructs a Plant with default properties.
     */
    public Plant() {
    }

    /**
     * Attempts to seed a new plant in the surrounding area based on the current
     * position in the world. This method calculates possible seeding locations and
     * seeds a new plant.
     * 
     * @param currentRow The row index of the plant attempting to seed.
     * @param currentCol The column index of the plant attempting to seed.
     * @param world      The world in which the plant exists and attempts to seed
     *                   new plants.
     */
    public void seed(int currentRow, int currentCol, World world) {
        List<int[]> possibleSeeds = world.calculatePossibleSeeds(currentRow, currentCol);

        if (possibleSeeds.size() >= 3 && world.hasFourPlants(currentRow, currentCol)) {
            int index = RandomGenerator.nextNumber(possibleSeeds.size());
            int[] selectedSeed = possibleSeeds.get(index);

            Cell targetCell = world.getCellArr()[selectedSeed[0]][selectedSeed[1]];

            // Check the new flag before seeding
            if (!targetCell.wasJustVacatedByHerbivore()) {
                targetCell.setOccupant(new Plant());
                // System.out.println("Plant seeded at: " + selectedSeed[0] + "," +
                // selectedSeed[1]);
            }
        }
    }

}
