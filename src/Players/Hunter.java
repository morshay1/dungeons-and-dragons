package Players;

import java.util.Comparator;
import java.util.List;

import Enemies.Enemy;

public class Hunter extends Player {
    private int hunterRange;
    private int arrowsCount;
    private int ticksCount;

    public Hunter(String name, int healthCapacity, int attackPoints, int defensePoints, int hunterRange) {
        super(name, healthCapacity, attackPoints, defensePoints, "Shoot");
        this.hunterRange = hunterRange;
        this.arrowsCount = 10 * this.level;
        this.ticksCount = 0;
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
            this.ticksCount++;
        }
    }

    @Override
    public void castAbility(List<Enemy> enemies) {
        if (arrowsCount == 0) {
            messageCallback.send("Tried to cast " + this.abilityName + ", but there's not enough arrows.");
            return;
        }

        Enemy closestEnemy = enemies.stream()
                .filter(enemy -> range(this.getPosition(), enemy.getPosition()) <= hunterRange)
                .min(Comparator.comparingDouble(enemy -> range(this.getPosition(), enemy.getPosition())))
                .orElse(null);

        if (closestEnemy == null) {
            messageCallback.send("No enemies within range.");
            return;
        }
        arrowsCount--;
        abilityDamage(closestEnemy, attackPoints);
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tArrows: %d\t\tRange: %d", arrowsCount, hunterRange);
    }
}
