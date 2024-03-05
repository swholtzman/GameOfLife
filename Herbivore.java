


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



}
