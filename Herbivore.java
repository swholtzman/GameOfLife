import java.util.List;

public class Herbivore extends Organism {
    Herbivore() {
        alive = true;
        hunger = 0;
    }

    public void hungerLevel() {
        if (this.hunger == 5) {
            this.alive = false;
            System.out.println("Herbivore died of hunger.");
        } else {
            this.hunger++;
            System.out.println("Herbivore hunger increased to " + this.hunger);
        }
    }
    
    public void eat(Cell cell) {
        if (cell.getOccupant() instanceof herbivoreEdible) {
            this.hunger = 0;
            System.out.println("Herbivore ate and reset hunger.");
        }
    }
    

    public int getHungerLevel() {
        return this.hunger;
    }

    public void performMove(List<int[]> possibleMoves, World world, int currentRow, int currentCol) {
    // Randomly select a move from the list of possible moves
    int index = RandomGenerator.nextNumber(possibleMoves.size());
    int[] selectedMove = possibleMoves.get(index);
    Cell[][] cellArr = world.getCellArr();

    Cell currentCell = cellArr[currentRow][currentCol];
    Cell targetCell = cellArr[selectedMove[0]][selectedMove[1]];

    // Check if the move is valid (not moving into another Herbivore or off the grid)
    if (!targetCell.isMarked() && (targetCell.getOccupant() == null || targetCell.getOccupant() instanceof Plant)) {
        // Eat the plant if present, which resets the Herbivore's hunger
        if (targetCell.getOccupant() instanceof Plant) {
            this.eat(targetCell); // Assume this method resets the Herbivore's hunger
            System.out.println("Herbivore ate a plant and reset hunger.");
        }

        // Move the Herbivore to the new Cell
        targetCell.setOccupant(this);

        // Remove the Herbivore from the old Cell
        currentCell.setOccupant(null);

        // Mark the new Cell as having moved into this turn
        targetCell.markAsMoved();

        // Optionally, log the move for debugging
        System.out.println("Moved Herbivore from [" + currentRow + "," + currentCol + "] to [" + selectedMove[0] + "," + selectedMove[1] + "]");
    }
}




}
