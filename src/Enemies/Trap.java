package Enemies;

import Players.Player;
import Tiles.Position;

public class Trap extends Enemy {
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(char tile, String name, int healthCapacity, int attackPoints, int defensePoints, int experienceValue,
            int visibilityTime, int invisibilityTime) {
        super(tile, name, healthCapacity, attackPoints, defensePoints, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
    }

    @Override
    public Position onGameTick(Player player) {
        this.visible = this.ticksCount < this.visibilityTime;
        if (this.ticksCount == (this.visibilityTime + this.invisibilityTime)) {
            this.ticksCount = 0;
        } else {
            this.ticksCount++;
        }
        if (this.range(this.getPosition(), player.getPosition()) < 2) {
            this.battle(player);
        }
        return this.getPosition();
    }

    @Override
    public char getTile() {
        return this.visible ? super.getTile() : '.';
    }
}
