import java.util.ArrayList;
import java.util.List;
import java.awt.Color;


public class Herbivore extends Organism implements carnivoreEdible, omnivoreEdible {
    private int MAX_HUNGER = 5;

    public Herbivore() {
        super();
    }

    @Override
    public void move(World world) {
        hunger++;
        if (!this.alive) return;

        int[] location = world.findOrganism(this);
        if (location == null) return; // If the herbivore's location is not found, do nothing.

        List<int[]> possibleMoves = world.getPossibleMoves(location[0], location[1]);

        if (!possibleMoves.isEmpty()) {
            int moveIndex = RandomGenerator.nextNumber(possibleMoves.size());
            int[] moveTo = possibleMoves.get(moveIndex);

            this.eat(world.getCellOccupant(moveTo[0], moveTo[1]));
            world.moveOrganism(this, moveTo[0], moveTo[1]);
            

            // Increase hunger after moving
            
            if (hunger == MAX_HUNGER) {
                this.alive = false;
                
            }
        }
    }

    @Override
    public void reproduce(World world) {
        // Herbivore reproduction logic could be implemented here if needed.
    }

    private void eat(Organism organism) {
        if (organism instanceof herbivoreEdible) {
            
            this.hunger = 0; // Reset hunger if the organism eaten is edible by a herbivore.
        }
    }

    public boolean getLifeStatus() {
        return alive;
    }

    @Override
    public Color getColor() {
        return GUI.YELLOW;
    }
}
