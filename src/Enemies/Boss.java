package Enemies;

import Players.Player;
import Tiles.Position;

public class Boss extends Enemy {
    int visionRange;
    int abilityFrequency;
    int combatTicks;

    public Boss(char tile, String name, int healthCapacity, int attackPoints, int defensePoints, int experienceValue,
            int visionRange, int abilityFrequency) {
        super(tile, name, healthCapacity, attackPoints, defensePoints, experienceValue);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    public Position onGameTick(Player player) {
        return this.getPosition();
    }
}
