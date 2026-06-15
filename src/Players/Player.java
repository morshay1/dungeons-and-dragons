package players;

import java.util.List;

import enemies.Enemy;
import messages.DeathCallback;
import messages.MessageCallback;
import resources.Health;
import tiles.Position;
import tiles.Unit;
import tiles.HeroicUnit;

public abstract class Player extends Unit implements HeroicUnit<List<Enemy>> {
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
        DeathCallback deathCallback = () -> System.out.println(getName() + " died. Game over.");
        super.initialize(position, messageCallback, deathCallback);
    }

    public void levelUp() {
        if (experience >= (level * 50)) {
            experience = experience - (level * 50);
            level = level + 1;
            Health userHealth = getHealth();
            userHealth.setPool(userHealth.getPool() + (level * 10));
            userHealth.setAmount(userHealth.getPool());
            addAttackPoints(level * 4);
            addDefensePoints(level);
        }
    }

    protected void abilityDamage(Enemy enemy, int rawDamage) {
        int defenseRoll = enemy.defend();
        int damage = Math.max(rawDamage - defenseRoll, 0);

        messageCallback.send(enemy.getName() + " rolled " + defenseRoll + " defense points.");
        enemy.getHealth().reduceAmount(damage);
        messageCallback.send(getName() + " hit " + enemy.getName() + " for " + damage + " ability damage.");

        if (enemy.isDead()) {
            enemy.onDeath(this);
        }
    }

    public int getHealthAmount() {
        return health.getAmount();
    }

    public int getHealthPool() {
        return health.getPool();
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int newExperience) {
        experience = newExperience;
    }

    public void addExperience(int amount) {
        experience += amount;

        while (experience >= level * 50) {
            levelUp();
        }
    }

    public void setLevel(int newLevel) {
        level = newLevel;
    }

    public abstract void uponLevelingUp();

    public abstract void onGameTick();

    public abstract void castAbility(List<Enemy> enemies);

    @Override
    public void visit(Player player) {
        // do nothing
    }

    @Override
    public void visit(Enemy enemy) {
        battle(enemy);

        if (enemy.isDead()) {
            if (getExperience() >= (level * 50)) {
                levelUp();
            }
            setPosition(enemy.getPosition());
        }
    }

    @Override
    public void onDeath(Unit attacker) {
        setTile('X');
        deathCallback.call();
        messageCallback.send(getName() + " was killed.");

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
