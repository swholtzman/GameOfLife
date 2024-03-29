// World.java

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class World {

    public static final int GRID_SIZE = 20;
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    private List<Organism> organisms = new ArrayList<>();

    /**
     * Initializes a new World object with a grid of empty cells.
     * Sets up a grid based on the predefined grid size, filling it with cells that
     * initially have no occupants.
     */
    World() {

        for ( int i = 0; i < GRID_SIZE; i++ ) {

            for ( int j = 0; j < GRID_SIZE; j++ ) {

                cells[i][j] = new Cell( null );
            }
        }
    }

    /**
     * Searches for the specified organism in the world and returns its location.
     * 
     * @param organism The organism to find within the world.
     * @return An array containing the row and column indexes of the organism if
     *         found, null otherwise.
     */

    public int[] findOrganism( Organism organism ) {

        for ( int i = 0; i < GRID_SIZE; i++ ) {

            for ( int j = 0; j < GRID_SIZE; j++ ) {

                if ( getCellOccupant(i, j) == organism ) {

                    return new int[] { i, j };
                }
            }
        }

        return null;
    }

    /**
     * Refreshes the list of organisms based on the current state of the world's
     * cells.
     * Iterates through all cells in the grid, adding any occupants found to the
     * list of organisms.
     */

    private void refreshOrganismsList() {

        organisms.clear();

        for ( Cell[] row : cells ) {

            for ( Cell cell : row ) {

                if ( cell.getOccupant() != null ) {

                    organisms.add( cell.getOccupant() );

                }
            }
        }
    }

    /**
     * Sets the occupant of a specific cell within the world.
     * 
     * @param rowNum   The row index of the cell to modify.
     * @param colNum   The column index of the cell to modify.
     * @param organism The new occupant of the cell. Pass null to clear the cell's
     *                 occupant.
     */
    public void setCellOccupant( int rowNum, int colNum, Organism organism ) {
        cells[rowNum][colNum].setOccupant( organism );
    }

    /**
     * Retrieves the occupant of a specified cell in the world.
     * 
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The Organism occupying the specified cell, or null if the cell is
     *         empty.
     */
    public Organism getCellOccupant( int row, int col ) {
        return cells[row][col].getOccupant();
    }

    /**
     * Moves an organism from its current location to a new cell within the world.
     * 
     * @param organism The organism to move.
     * @param newRow   The row index of the destination cell.
     * @param newCol   The column index of the destination cell.
     */
    public void moveOrganism( Organism organism, int newRow, int newCol ) {

        for ( int i = 0; i < GRID_SIZE; i++ ) {

            for ( int j = 0; j < GRID_SIZE; j++ ) {

                if ( cells[i][j].getOccupant() == organism ) {
                    
                    cells[i][j].setOccupant( null );
                    cells[newRow][newCol].setOccupant( organism );

                    return;
                }
            }
        }
    }

    /**
     * Retrieves a list of cells neighboring a specific cell.
     * 
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return A list of Cell objects that are direct neighbors of the specified
     *         cell.
     */
    public List<Cell> getNeighboringCells( int row, int col ) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

        for ( int[] dir : directions ) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if ( newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE ) {
                neighbors.add( cells[newRow][newCol] );
            }
        }
        return neighbors;
    }

    /**
     * Retrieves a list of empty cells neighboring a specific cell.
     * 
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return A list of empty Cell objects that are direct neighbors of the
     *         specified cell.
     */
    public List<Cell> getEmptyNeighboringCells( int row, int col ) {

        return getNeighboringCells( row, col ).stream()
                .filter( cell -> cell.getOccupant() == null )
                .collect( Collectors.toList() );
    }

    /**
     * Retrieves a list of cells neighboring a specific cell that contain a specific
     * type of occupant.
     * 
     * @param row  The row index of the cell.
     * @param col  The column index of the cell.
     * @param type The Class type of the occupant to filter by.
     * @return A list of Cell objects that are neighbors of the specified cell and
     *         contain occupants of the specified type.
     */
    public List<Cell> getNeighboringCellsOfType( int row, int col, Class<?> type ) {

        return getNeighboringCells( row, col ).stream()
                .filter( cell -> type.isInstance( cell.getOccupant() ) )
                .collect( Collectors.toList() );
    }

    /**
     * Retrieves all possible moves from a given cell, considering the entire grid.
     * 
     * @param row The row index of the starting cell.
     * @param col The column index of the starting cell.
     * @return A list of integer arrays, each representing a possible move as
     *         [newRow, newCol].
     */
    public List<int[]> getAllPossibleMoves( int row, int col ) {
        List<int[]> possibleMoves = new ArrayList<>();
        int[][] directions = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

        for ( int[] dir : directions ) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if ( newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE ) {
                possibleMoves.add(new int[] { newRow, newCol });
            }
        }
        return possibleMoves;
    }

    /**
     * Calculates the direction from one cell to another based on their grid
     * positions.
     * 
     * @param targetCell The cell to which the direction is calculated.
     * @param currentRow The row index of the starting cell.
     * @param currentCol The column index of the starting cell.
     * @return An array of integers representing the row and column movement
     *         required to reach the target cell from the current cell.
     */
    public int[] getDirectionForCell( Cell targetCell, int currentRow, int currentCol ) {

        for ( int dRow = -1; dRow <= 1; dRow++ ) {

            for ( int dCol = -1; dCol <= 1; dCol++ ) {

                if ( currentRow + dRow >= 0 && currentRow + dRow < GRID_SIZE && currentCol + dCol >= 0
                        && currentCol + dCol < GRID_SIZE ) {

                    if ( cells[currentRow + dRow][currentCol + dCol] == targetCell ) {

                        return new int[] { dRow, dCol };
                    }
                }
            }
        }
        return new int[] { 0, 0 };
    }

    /**
     * Iterates through all cells in the world, removing any occupants that are no
     * longer alive.
     * This method is typically used at the end of a turn to clean up the world
     * state.
     */
    public void compostAllDead() {

        for ( int i = 0; i < GRID_SIZE; i++ ) {

            for ( int j = 0; j < GRID_SIZE; j++ ) {

                Cell cell = cells[i][j];

                if ( cell.getOccupant() != null && !cell.getOccupant().getLifeStatus() ) {

                    cell.setOccupant( null );
                }
            }
        }
    }

    /**
     * Advances the state of the game by one tick.
     * Updates the list of organisms, then iterates through them to invoke their
     * move and reproduce behaviors. Finally, it clears out any dead organisms from
     * the world.
     */
    public void advanceGame() {
        refreshOrganismsList();

        for ( Organism organism : new ArrayList<>( organisms ) ) {
            organism.incrementHunger();
            organism.move( this );
            organism.reproduce( this );
        }

        compostAllDead();
    }

}
