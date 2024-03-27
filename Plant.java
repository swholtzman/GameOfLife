// Plant.java

import java.util.List;
import java.util.stream.Collectors;
import java.awt.Color;

public class Plant extends Organism implements herbivoreEdible {

    public Plant() {
        super();
    }

    @Override
    public void move(World world) {
    }

    @Override
    public void reproduce(World world) {
        int[] location = world.findOrganism(this);
        if (location == null) {
            return; // The plant is not in the world
        }

        int currentRow = location[0];
        int currentCol = location[1];
        List<Cell> emptyNeighbors = world.getEmptyNeighboringCells(currentRow, currentCol);
        List<Cell> neighboringPlants = world.getNeighboringCellsOfType(currentRow, currentCol, Plant.class);

        // Correct reproduction conditions: at least 3 empty cells and exactly 4
        // neighboring plants
        if (!emptyNeighbors.isEmpty() && emptyNeighbors.size() >= 3 && neighboringPlants.size() == 4) {
            int index = RandomGenerator.nextNumber(emptyNeighbors.size());
            Cell targetCell = emptyNeighbors.get(index);
            int[] newLocation = world.getDirectionForCell(targetCell, currentRow, currentCol);
            world.setCellOccupant(currentRow + newLocation[0], currentCol + newLocation[1], new Plant());
        }
    }

    private boolean hasFourPlants(List<Cell> neighbors) {
        long plantCount = neighbors.stream()
                .filter(cell -> cell.getOccupant() instanceof Plant)
                .count();

        return plantCount == 4;
    }

    public boolean getLifeStatus() {
        return alive;
    }

    @Override
    public Color getColor() {
        return GUI.GREEN;
    }
}
