import java.util.List;

public class Plant extends Organism implements herbivoreEdible {
    public Plant() {
    }

    public void seed(int currentRow, int currentCol, World world) {
        List<int[]> possibleSeeds = world.calculatePossibleSeeds(currentRow, currentCol);
    
        if (possibleSeeds.size() >= 3 && world.hasFourPlants(currentRow, currentCol)) {
            int index = RandomGenerator.nextNumber(possibleSeeds.size());
            int[] selectedSeed = possibleSeeds.get(index);
            
            Cell targetCell = world.getCellArr()[selectedSeed[0]][selectedSeed[1]];
    
            // Check the new flag before seeding
            if (!targetCell.wasJustVacatedByHerbivore()) {
                targetCell.setOccupant(new Plant());
                System.out.println("Plant seeded at: " + selectedSeed[0] + "," + selectedSeed[1]);
            }
        }
    }
    
}

