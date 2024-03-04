


public class Herbivore extends Organism {
    Herbivore() {
    }

    private void hungerLevel() {
        if (this.hunger == 5) {
            this.alive = false;
        } else {
            this.hunger++;
        }
    }

    public void eat(Cell cell) {
        if (cell.getOccupant() instanceof herbivoreEdible) {
            this.hunger = 0;
        }
    }

    public int getHungerLevel() {
        return this.hunger;
    }



}
