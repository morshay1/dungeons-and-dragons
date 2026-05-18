package Players;

import java.util.List;

import Enemies.Enemy;

public class Hunter extends Player {
    private int range;
    private int arrowsCount;
    // private int ticksCount;

    public Hunter(String name, int healthCapacity, int attackPoints, int defensePoints, int range) {
        super(name, healthCapacity, attackPoints, defensePoints, "Shoot");
        this.range = range;
        this.arrowsCount = 10 * this.level;
        // this.ticksCount = 0;
    }

    public int getArrows() {
        return arrowsCount;
    }

    public int getRange() {
        return range;
    }

    @Override
    public void uponLevelingUp() {
    }

    @Override
    public void onGameTick() {
    }

    @Override
    public void onAbilityCast(List<Enemy> enemies) {
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tArrows: %d\t\tRange: %d", getArrows(), getRange());
    }

}
