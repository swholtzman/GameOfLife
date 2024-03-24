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
            return;
        }
    
        int currentRow = location[0], currentCol = location[1];
        List<Cell> neighbors = world.getNeighboringCells(currentRow, currentCol);
    
        // Directly count neighboring plants and empty cells here
        long neighboringPlants = neighbors.stream().filter(cell -> cell.getOccupant() instanceof Plant).count();
        List<Cell> emptyNeighbors = neighbors.stream().filter(cell -> cell.getOccupant() == null).collect(Collectors.toList());
    
        if (emptyNeighbors.size() >= 3 && neighboringPlants == 4) {
            int index = RandomGenerator.nextNumber(emptyNeighbors.size());
            Cell targetCell = emptyNeighbors.get(index);
            targetCell.setOccupant(new Plant());
        }
    }

    private boolean hasFourPlants(List<Cell> neighbors) {
        long plantCount = neighbors.stream()
                .filter(cell -> cell.getOccupant() instanceof Plant)
                .count();

        return plantCount == 4;
    }

    @Override
    public Color getColor() {
        return GUI.GREEN;
    }
}
