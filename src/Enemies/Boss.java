package Enemies;

import java.util.Random;

import Players.Player;
import Tiles.HeroicUnit;
import Tiles.Position;

public class Boss extends Enemy implements HeroicUnit<Player> {
    private Random random = new Random();
    private int visionRange;
    private int abilityFrequency;
    private int combatTicks;

    public Boss(char tile, String name, int healthCapacity, int attackPoints, int defensePoints, int experienceValue,
            int visionRange, int abilityFrequency) {
        super(tile, name, healthCapacity, attackPoints, defensePoints, experienceValue);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    private Position randomMove(Position position) {
        int x = position.getX();
        int y = position.getY();

        int move = random.nextInt(5);

        switch (move) {
            case 0:
                return new Position(x, y - 1);

            case 1:
                return new Position(x, y + 1);

            case 2:
                return new Position(x - 1, y);

            case 3:
                return new Position(x + 1, y);
            default:
                return position;
        }
    }

    @Override
    public Position onGameTick(Player player) {
        Position enemyPosition = this.getPosition();
        Position playerPosition = player.getPosition();

        int enemyX = enemyPosition.getX();
        int enemyY = enemyPosition.getY();

        if (range(enemyPosition, playerPosition) < this.visionRange) {
            if (combatTicks == abilityFrequency) {
                combatTicks = 0;
                this.castAbility(player);
            } else {
                combatTicks++;
                int dx = enemyX - playerPosition.getX();
                int dy = enemyY - playerPosition.getY();

                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        return new Position(enemyX - 1, enemyY);
                    } else {
                        return new Position(enemyX + 1, enemyY);
                    }
                } else {
                    if (dy > 0) {
                        return new Position(enemyX, enemyY - 1);
                    } else {
                        return new Position(enemyX, enemyY + 1);
                    }
                }
            }
        }
        combatTicks = 0;
        return randomMove(enemyPosition);
    }

    public void castAbility(Player player) {
        if (range(this.getPosition(), player.getPosition()) < visionRange) {
            int defense = player.defend();
            int damage = Math.max(this.attackPoints - defense, 0);
            player.getHealth().reduceAmount(damage);
        }
    }
}
