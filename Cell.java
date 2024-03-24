public class Cell {
    private Organism currentOccupant;

    public Cell(Organism occupant) {
        this.currentOccupant = occupant;
    }

    public Organism getOccupant() {
        return this.currentOccupant;
    }

    public void setOccupant(Organism occupant) {
        this.currentOccupant = occupant;
    }
}
