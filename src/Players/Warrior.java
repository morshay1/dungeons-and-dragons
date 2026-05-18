package Players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Enemies.Enemy;
import Resources.Health;

public class Warrior extends Player {
    private int abilityCooldown;
    private int remainingCooldown;
    private static final int RANGE = 3;

    public Warrior(String name, int healthCapacity, int attackPoints, int defensePoints, int abilityCooldown) {
        super(name, healthCapacity, attackPoints, defensePoints, "Avenger’s Shield");
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }

    public String getAbilityCooldown() {
        return this.remainingCooldown + "/" + this.abilityCooldown;
    }

    @Override
    public void uponLevelingUp() {
        this.levelUp();
        this.remainingCooldown = 0;
        Health health = this.getHealth();
        health.setPool(health.getPool() + (this.getLevel() * 5));
        // health.setHealthAmount(health.getHealthPool()); maybe this is needed- not
        // sure yet
        this.setAttackPoints(this.getAttackPoints() + (this.getLevel() * 2));
        this.setDefensePoints(this.getDefense() + this.getLevel());
    }

    @Override
    public void onGameTick() {
        if (this.remainingCooldown > 0) {
            this.remainingCooldown = this.remainingCooldown - 1;
        }
    }

    @Override
    public void onAbilityCast(List<Enemy> enemies) {
        if (this.remainingCooldown > 0) {
            messageCallback.send("Tried to cast Avenger's Shield, but it is on cooldown.");
            return;
        }
        List<Enemy> enemiesWithinRange = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (range(this.getPosition(), enemy.getPosition()) < RANGE) {
                enemiesWithinRange.add(enemy);
            }
        }
        if (enemiesWithinRange.isEmpty()) {
            messageCallback.send("No enemies within range.");
            return;
        }
        this.remainingCooldown = this.abilityCooldown;
        this.getHealth().setAmount(Math.min(this.getHealth().getAmount() + (this.getDefense() * 10),
                this.getHealth().getPool()));
        Random rand = new Random();
        Enemy selectedEnemy = enemiesWithinRange.get(rand.nextInt(enemiesWithinRange.size()));
        int hit = this.getHealth().getPool() / 10;
        selectedEnemy.getHealth().reduceAmount(hit);
        if (selectedEnemy.getHealth().getAmount() <= 0) { // TODO maybe board should remove enemies
            enemies.remove(selectedEnemy);
            selectedEnemy.onDeath();
        }
    }

    @Override
    public String describe() {
        return super.describe() + String.format("\t\tCooldown: %s", getAbilityCooldown());
    }

}
