package Players;

import Tiles.Unit;

import java.util.List;

import Enemies.Enemy;
import Messages.DeathCallback;
import Messages.MessageCallback;
import Resources.Health;
import Tiles.Position;

public abstract class Player extends Unit {
    protected int experience;
    protected int level;
    protected String abilityName;

    public Player(String name, int healthCapacity, int attackPoints, int defensePoints, String abilityName) {
        super('@', name, healthCapacity, attackPoints, defensePoints);
        this.experience = 0;
        this.level = 1;
        this.abilityName = abilityName;
    }

    public void initialize(Position position, MessageCallback messageCallback) {
        DeathCallback enemyDeathCallback = () -> System.out.println(getName() + " died. Game over.");
        super.initialize(position, messageCallback, enemyDeathCallback);
    }

    public void levelUp() {
        if (experience >= (level * 50)) {
            experience = experience - (level * 50);
            level = level + 1;
            Health userHealth = getHealth();
            userHealth.setPool(userHealth.getPool() + (level * 10));
            userHealth.setAmount(userHealth.getPool());
            setAttackPoints(getAttackPoints() + (level * 4));
            setDefensePoints(getDefense() + level);
        }
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int newExperience) {
        experience = newExperience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int newLevel) {
        level = newLevel;
    }

    public abstract void uponLevelingUp();

    public abstract void onGameTick();

    public abstract void onAbilityCast(List<Enemy> enemies);

    @Override
    public void visit(Player player) {
        // do nothing
    }

    @Override
    public void visit(Enemy enemy) {
        System.out.println("DEBUG: Player battles " + enemy.getName());
        battle(enemy);
        System.out.println("DEBUG: enemy health after battle: " + enemy.getHealth());

        if (enemy.isDead()) {
            setExperience(getExperience() + enemy.getExperienceValue());
            if (getExperience() >= (level * 50)) {
                levelUp();
            }
            enemy.onDeath();
            setPosition(enemy.getPosition());
        }
    }

    @Override
    public void onDeath() {
        setTile('X');
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public String describe() {
        return super.describe()
                + String.format("\t\tLevel: %d\t\tExperience: %d/%d", getLevel(), getExperience(), getLevel() * 50);
    }

}
