package Players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Enemies.Enemy;
import Resources.Mana;

public class Mage extends Player {
    private Mana mana;
    private final int manaCost;
    private int spellPower;
    private final int hitsCount;
    private final int abilityRange;

    public Mage(String name, int healthCapacity, int attackPoints, int defensePoints, int manaPool, int manaCost,
            int spellPower, int hitsCount, int abilityRange) {
        super(name, healthCapacity, attackPoints, defensePoints, "Blizzard");
        this.mana = new Mana(manaPool / 4, manaPool);
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    public int getSpellPower() {
        return spellPower;
    }

    @Override
    public void uponLevelingUp() {
        this.levelUp();
        mana.setPool(mana.getPool() + 25 * level);
        mana.setAmount(Math.min(mana.getAmount() + mana.getPool() / 4, mana.getPool()));
        this.spellPower = this.spellPower + 10 * level;
    }

    @Override
    public void onGameTick() {
        mana.setAmount(Math.min(mana.getPool(), mana.getAmount() + level));
    }

    @Override
    public void onAbilityCast(List<Enemy> enemies) {
        if (mana.getAmount() < manaCost) {
            messageCallback.send("Tried to cast Blizzard, but there's not enough mana.");
        } else {
            mana.setAmount(mana.getAmount() - manaCost);
            int hits = 0;
            List<Enemy> enemiesWithinRange = new ArrayList<>();
            for (Enemy enemy : enemies) {
                if (range(this.getPosition(), enemy.getPosition()) < abilityRange) {
                    enemiesWithinRange.add(enemy);
                }
            }
            if (enemiesWithinRange.isEmpty()) {
                messageCallback.send("No enemies within range.");
                return;
            }
            while (hits < hitsCount) {
                Random rand = new Random();
                Enemy selectedEnemy = enemiesWithinRange.get(rand.nextInt(enemiesWithinRange.size()));
                // TODO: enemy defend itself, remove enemy if he died
                selectedEnemy.getHealth().reduceAmount(this.spellPower);
                if (selectedEnemy.getHealth().getAmount() <= 0) { // TODO maybe board should remove enemies
                    enemies.remove(selectedEnemy);
                    selectedEnemy.onDeath();
                }
                hits++;
            }
        }
    }

    @Override
    public String describe() {
        return super.describe()
                + String.format("\t\tMana: %s\t\tSpell Power: %d", this.mana, getSpellPower());
    }
}
