package Players;

import java.util.List;

import Enemies.Enemy;

public class Hunter extends Player {
    private int range;
    private int arrowsCount;
    private int ticksCount;

    public Hunter(String name, int healthCapacity, int attackPoints, int defensePoints, int range) {
        super(name, healthCapacity, attackPoints, defensePoints, "Shoot");
        this.range = range;
        this.arrowsCount = 10 * this.level;
        this.ticksCount = 0;
    }

    public int getArrows() {
        return arrowsCount;
    }

    public int getRange() {
        return range;
    }

    @Override
    public void uponLevelingUp() {
        this.levelUp();
        this.arrowsCount += 10 * level;
        this.attackPoints += 2 * level;
        this.defensePoints += level;
    }

    @Override
    public void onGameTick() {
        if (this.ticksCount == 10) {
            this.arrowsCount += level;
            this.ticksCount = 0;
        } else {
            this.ticksCount += 1;
        }
    }

    @Override
    public void onAbilityCast(List<Enemy> enemies) {
        this.arrowsCount -= 1;
        // Deal damage equals to attack points to the closest enemy within range (The
        // enemy will try to defend itself)
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tArrows: %d\t\tRange: %d", getArrows(), getRange());
    }
}
