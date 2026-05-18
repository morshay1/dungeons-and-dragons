package Enemies;

import Players.Player;
import Tiles.Position;

public class Monster extends Enemy {
    int visionRange;

    public Monster(char tile, String name, int healthCapacity, int attackPoints, int defensePoints, int experienceValue,
            int visionRange) {
        super(tile, name, healthCapacity, attackPoints, defensePoints, experienceValue);
        this.visionRange = visionRange;
    }

    @Override
    public Position onGameTick(Player player) {
        return this.getPosition();
    }

}
