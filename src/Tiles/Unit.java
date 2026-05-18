package Tiles;

import Players.Player;
import Resources.Health;
import Enemies.Enemy;
import Messages.DeathCallback;
import Messages.MessageCallback;

public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected int attackPoints;
    protected int defensePoints;
    protected MessageCallback messageCallback;
    protected DeathCallback enemyDeathCallback;

    protected Unit(char tile, String name, int healthCapacity, int attackPoints, int defensePoints) {
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
    }

    protected void initialize(Position position, MessageCallback messageCallback, DeathCallback enemyDeathCallback) {
        super.initialize(position);
        this.messageCallback = messageCallback;
        this.enemyDeathCallback = enemyDeathCallback;
    }

    public void interact(Tile tile) {
        tile.accept(this);
    }

    protected void battle(Unit defender) {
        int attackRoll = attack();
        int defenseRoll = defender.defend();
        int damage = attackRoll - defenseRoll;

        messageCallback.send(this.getName() + " engaged in combat with " + defender.getName() + ".");
        messageCallback.send(this.describe());
        messageCallback.send(defender.describe());
        messageCallback.send(this.getName() + " rolled " + attackRoll + " attack points.");
        messageCallback.send(defender.getName() + " rolled " + defenseRoll + " defense points.");

        if (damage > 0) {
            defender.getHealth().reduceAmount(damage);
            messageCallback.send(this.getName() + " dealt " + damage + " damage to " + defender.getName() + ".");
        } else {
            messageCallback.send(this.getName() + " dealt 0 damage to " + defender.getName() + ".");
        }
    }

    public int attack() {
        int roll = (int) (Math.random() * (attackPoints + 1));
        return roll;
    }

    public int defend() {
        int roll = (int) (Math.random() * (defensePoints + 1));
        return roll;
    }

    public boolean isDead() {
        return health.getAmount() <= 0;
    }

    public void visit(Empty e) {
        Position oldPosition = this.getPosition();
        this.setPosition(e.getPosition());
        e.setPosition(oldPosition);
    }

    public void visit(Wall w) {
        // do nothing
    }

    public abstract void visit(Player p);

    public abstract void visit(Enemy e);

    public abstract void onDeath();

    public String getName() {
        return name;
    }

    public Health getHealth() {
        return health;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int newAttackPoints) {
        attackPoints = newAttackPoints;
    }

    public int getDefense() {
        return defensePoints;
    }

    public void setDefensePoints(int newDefensePoints) {
        defensePoints = newDefensePoints;
    }

    public int range(Position p, Position q) {
        return (int) Math.sqrt(Math.pow(p.getX() - q.getX(), 2) + Math.pow(p.getY() - q.getY(), 2));
    }

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth(), getAttackPoints(),
                getDefense());
    }
}
