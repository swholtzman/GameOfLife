// Omnivore.java 

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

public class Omnivore extends Organism implements carnivoreEdible {

    private int MAX_HUNGER = 5;

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

        // incrementHunger();

        if (!this.alive)
            return;

        int[] location = world.findOrganism( this );

        if (location == null) {
            return;
        }

        List<int[]> allPossibleMoves = world.getAllPossibleMoves( location[0], location[1] );

        List<int[]> possibleMoves = allPossibleMoves.stream()
                .filter( move -> {
                    Organism perhapsAFood = world.getCellOccupant( move[0], move[1] );
                    return perhapsAFood == null || perhapsAFood instanceof omnivoreEdible;
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
     * Allows the herbivore to consume food. If consumed, the hunger level
     * of the herbivore is reset to 0.
     * 
     * @param organism The organism that the herbivore attempts to eat. It must
     *                 implement the herbivoreEdible interface for the herbivore to
     *                 reset its hunger level.
     */
    private void eat( Organism organism ) {

        if ( organism instanceof omnivoreEdible ) {
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
        return GUI.BLUE;
    }

    /**
     * Attempts to reproduce based on the surrounding environment in the world. 
     * <p>
     * A plant can reproduce if there are at least 3 empty neighboring cells and
     * exactly 4 neighboring plants for cross-pollination.
     * 
     * @param world The world in which the plant attempts to reproduce. This
     *              provides the context necessary to check the surrounding cells
     *              and manage reproduction.
     */
    @Override
    public void reproduce( World world ) {

        int[] location = world.findOrganism( this );

        if ( location == null ) {
            return; 
        }

        int currentRow = location[0];
        int currentCol = location[1];

        List<Cell> emptyNeighbors = world.getEmptyNeighboringCells( currentRow, currentCol );
        List<Cell> potentialMates = world.getNeighboringCellsOfType( currentRow, currentCol, Omnivore.class );
        List<Cell> potentialFood1 = world.getNeighboringCellsOfType( currentRow, currentCol, Herbivore.class );
        List<Cell> potentialFood2 = world.getNeighboringCellsOfType( currentRow, currentCol, Carnivore.class );
        List<Cell> potentialFood3 = world.getNeighboringCellsOfType( currentRow, currentCol, Plant.class );

        if ( !emptyNeighbors.isEmpty() && emptyNeighbors.size() >= 3 && potentialMates.size() >= 1 && ( ( potentialFood1.size() + potentialFood2.size() + potentialFood3.size() ) >= 2 ) ) {

            int index = RandomGenerator.nextNumber( emptyNeighbors.size() );
            Cell targetCell = emptyNeighbors.get( index );
            int[] newLocation = world.getDirectionForCell( targetCell, currentRow, currentCol );

            world.setCellOccupant( currentRow + newLocation[0], currentCol + newLocation[1], new Omnivore() );

        }
    }

    @Override
    public void incrementHunger() {
        hunger++;
    }
    
}
