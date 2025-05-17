package model;

import lombok.Getter;
import java.util.Random;

// Utility class for DnD-style combat logic
public class Dnd {

    private static Random random;

    // Sets the random generator for testing
    public static void setRandom(Random random) {
        Dnd.random = random;
    }

    // Inner class representing the result of an attack
    @Getter
    public static class AttackResult {
        private final boolean hit;
        private final int damage;
        private final int roll;

        public AttackResult(boolean hit, int damage, int roll) {
            this.hit = hit;
            this.damage = damage;
            this.roll = roll;
        }

    }

    // Character attacks enemy using given roll
    public static AttackResult characterAttack(Characters character, Enemies enemy, int roll) {
        int threshold = enemy.getAc() / 2;

        if (roll > threshold) {
            int damage = character.getDmg() / 2;
            enemy.setCurrentHp(Math.max(enemy.getCurrentHp() - damage, 0));
            return new AttackResult(true, damage, roll);
        }

        return new AttackResult(false, 0, roll);
    }

    // Enemy attacks character using given roll
    public static AttackResult enemyAttack(Enemies enemy, Characters character, int roll) {
        int threshold = character.getAc() / 2;

        if (roll > threshold) {
            int damage = enemy.getDmg() / 2;
            character.setCurrentHp(Math.max(character.getCurrentHp() - damage, 0));
            return new AttackResult(true, damage, roll);
        }

        return new AttackResult(false, 0, roll);
    }
}
