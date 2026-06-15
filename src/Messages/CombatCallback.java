package messages;

import tiles.Unit;

public interface CombatCallback {
    void onCombat(Unit attacker, Unit defender);
}