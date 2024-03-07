
public class Cell {
    private Organism currentOccupant;
    private boolean movedIntoThisTurn;
    private boolean justVacatedByHerbivore;

    public Cell(Organism organism) {
        this.currentOccupant = organism;
        this.movedIntoThisTurn = false;
    }

    public Organism getOccupant() {
        return this.currentOccupant;
    }

    public boolean getMoveStatus() {
        return this.movedIntoThisTurn;
    }

    public void setOccupant(Organism organism) {
        System.out.println("Changing occupant of cell [" + /* Provide row and col if available */ "] from "
                + (this.currentOccupant == null ? "null" : this.currentOccupant.getClass().getSimpleName()) + " to "
                + (organism == null ? "null" : organism.getClass().getSimpleName()));
        this.currentOccupant = organism;
    }

    public void markAsMoved() {
        System.out.println("Marking cell as moved.");
        this.movedIntoThisTurn = true;
    }

    public boolean isMarked() {
        return this.movedIntoThisTurn;
    }

    public void resetMovedIntoFlag() {
        this.movedIntoThisTurn = false;
    }

    public boolean wasJustVacatedByHerbivore() {
        return justVacatedByHerbivore;
    }

    public void setJustVacatedByHerbivore(boolean justVacated) {
        this.justVacatedByHerbivore = justVacated;
    }
}
