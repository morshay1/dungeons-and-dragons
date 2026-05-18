package Players;

import java.util.List;

import Resources.Energy;
import Enemies.Enemy;

public class Rogue extends Player {
    private Energy energy;;
    private int cost;
    private static final int RANGE = 2;

    public Rogue(String name, int healthCapacity, int attackPoints, int defensePoints, int cost) {
        super(name, healthCapacity, attackPoints, defensePoints, "Fan of Knives");
        this.energy = new Energy(100, 100);
        this.cost = cost;
    }

    @Override
    public void uponLevelingUp() {
        this.levelUp();
        this.energy.setAmount(100);
        this.attackPoints += 3 * level;
    }

    @Override
    public void onGameTick() {
        this.energy.setAmount(Math.min(this.energy.getAmount() + 10, 100));
    }

    @Override
    public void onAbilityCast(List<Enemy> enemies) {
        if (energy.getAmount() < cost) {
            messageCallback.send("Tried to cast Fan of Knives, but there's not enough energy.");
        } else {
            this.energy.reduceAmount(this.cost);
            for (Enemy enemy : enemies) {
                if (range(this.getPosition(), enemy.getPosition()) < RANGE) {
                    enemy.getHealth().reduceAmount(this.attackPoints);
                    // enemy tries to defend themselves
                    // remove dead enemies from board
                }
            }
        }
    }

    @Override
    public String describe() {
        return super.describe()
                + String.format("\t\tEnergy: %s", this.energy);
    }

}
