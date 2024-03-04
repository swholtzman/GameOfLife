
public class Cell {
    private Organism currentOccupant;

    public Cell(Organism organism) {
        this.currentOccupant = organism;
    }

    public Organism getOccupant() {
        return this.currentOccupant;
    }

    public void setOccupant(Organism organism) {
        this.currentOccupant = organism;
    }
}
