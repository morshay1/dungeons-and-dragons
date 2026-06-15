package tiles;

import players.Player;
import resources.Health;
import enemies.Enemy;
import messages.DeathCallback;
import messages.MessageCallback;

public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected int attackPoints;
    protected int defensePoints;
    protected MessageCallback messageCallback;
    protected DeathCallback deathCallback;

    protected Unit(char tile, String name, int healthCapacity, int attackPoints, int defensePoints) {
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
    }

    protected void initialize(Position position, MessageCallback messageCallback, DeathCallback deathCallback) {
        super.initialize(position);
        this.messageCallback = messageCallback;
        this.deathCallback = deathCallback;
    }

    public void interact(Tile tile) {
        tile.accept(this);
    }

    protected void battle(Unit defender) {
        int attackRoll = attack();
        int defenseRoll = defender.defend();
        int damage = Math.max(attackRoll - defenseRoll, 0);

        messageCallback.send(this.getName() + " engaged in combat with " + defender.getName() + ".");
        messageCallback.send(this.getName() + " rolled " + attackRoll + " attack points.");
        messageCallback.send(defender.getName() + " rolled " + defenseRoll + " defense points.");

        defender.getHealth().reduceAmount(damage);

        messageCallback.send(this.getName() + " dealt " + damage + " damage to " + defender.getName() + ".");

        if (defender.isDead()) {
            defender.onDeath(this);
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

    public abstract void onDeath(Unit attacker);

    public String getName() {
        return name;
    }

    public Health getHealth() {
        return health;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void addAttackPoints(int newAttackPoints) {
        attackPoints += newAttackPoints;
    }

    public int getDefense() {
        return defensePoints;
    }

    public void addDefensePoints(int newDefensePoints) {
        defensePoints += newDefensePoints;
    }

    public int range(Position p, Position q) {
        return (int) Math.sqrt(Math.pow(p.getX() - q.getX(), 2) + Math.pow(p.getY() - q.getY(), 2));
    }

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth(), getAttackPoints(),
                getDefense());
    }
}
