package enemies;

import players.Player;
import tiles.Unit;
import tiles.Position;
import messages.CombatCallback;
import messages.DeathCallback;
import messages.MessageCallback;

public abstract class Enemy extends Unit {
    protected int experienceValue;

    public Enemy(char tile, String name, int healthCapacity, int attackPoints, int defensePoints, int experienceValue) {
        super(tile, name, healthCapacity, attackPoints, defensePoints);
        this.experienceValue = experienceValue;
    }

    public void initialize(Position position, MessageCallback messageCallback, CombatCallback combatCallback) {
        DeathCallback deathCallback = () -> System.out.println(getName() + " died. Game over.");
        super.initialize(position, messageCallback, deathCallback, combatCallback);
    }

    public abstract Position onGameTick(Player player);

    public int getExperienceValue() {
        return this.experienceValue;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public void visit(Player player) {
        battle(player);
    }

    @Override
    public void visit(Enemy enemy) {
        // do nothing
    }

    @Override
    public void onDeath(Unit attacker) {
        deathCallback.call();
        messageCallback.send(
                this.getName() + " died. " + attacker.getName() + " gained " + getExperienceValue() + " experience.");
        if (attacker instanceof Player player) {
            player.addExperience(this.experienceValue);
        }
    }

    @Override
    public String describe() {
        return super.describe()
                + String.format("\t\tExperience Value: %d", getExperienceValue());
    }
}
