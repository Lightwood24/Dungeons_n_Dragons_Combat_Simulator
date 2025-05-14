package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DndTests {

    private Characters character;
    private Enemies enemy;

    @BeforeEach
    public void setup() {
        character = new Characters("Hero", 100, 100, 20, 15, "path/to/hero/image", null);
        enemy = new Enemies("Villain", 80, 80, 15, 12, "path/to/villain/image");
    }

    @AfterEach
    public void teardown() {
        character = null;
        enemy = null;
    }

    @Test
    public void testCharacterAttackSuccess() {
        // Set up character and enemy data
        character.setDmg(10);
        enemy.setAc(12);  // enemy's AC (should be halved for threshold comparison)
        enemy.setCurrentHp(30);

        // Simulate the roll so that it always passes (greater than threshold)
        int roll = 13;  // This will be above the threshold (AC / 2 = 6)

        // Call the method to test
        Dnd.AttackResult result = Dnd.characterAttack(character, enemy, roll);

        // Assertions to verify success
        assertTrue(result.isHit(), "Attack should hit");
        assertEquals(5, result.getDamage(), "Damage should be halved (10 / 2)");
        assertEquals(25, enemy.getCurrentHp(), "Enemy's HP should decrease by 5");
    }

    @Test
    public void testCharacterAttackFailure() {
        // Set up character and enemy data
        character.setDmg(10);
        enemy.setAc(20);  // enemy's AC (threshold will be 20 / 2 = 10)
        enemy.setCurrentHp(30);

        // Simulate the roll so that it always fails (less than threshold)
        int roll = 5;  // This will be below the threshold (AC / 2 = 10)

        // Call the method to test
        Dnd.AttackResult result = Dnd.characterAttack(character, enemy, roll);

        // Assertions to verify failure
        assertFalse(result.isHit(), "Attack should miss");
        assertEquals(0, result.getDamage(), "Damage should be 0");
        assertEquals(30, enemy.getCurrentHp(), "Enemy's HP should remain the same");
    }

    @Test
    public void testEnemyAttackSuccess() {
        // Set up character and enemy data
        character.setAc(15);  // character's AC (threshold will be 15 / 2 = 7)
        enemy.setDmg(12);
        character.setCurrentHp(50);

        // Simulate the roll so that it always passes (greater than threshold)
        int roll = 10;  // This will be above the threshold (AC / 2 = 7)

        // Call the method to test
        Dnd.AttackResult result = Dnd.enemyAttack(enemy, character, roll);

        // Assertions to verify success
        assertTrue(result.isHit(), "Attack should hit");
        assertEquals(6, result.getDamage(), "Damage should be halved (12 / 2)");
        assertEquals(44, character.getCurrentHp(), "Character's HP should decrease by 6");
    }

    @Test
    public void testEnemyAttackFailure() {
        // Set up character and enemy data
        character.setAc(25);  // character's AC (threshold will be 25 / 2 = 12)
        enemy.setDmg(12);
        character.setCurrentHp(50);

        // Simulate the roll so that it always fails (less than threshold)
        int roll = 5;  // This will be below the threshold (AC / 2 = 12)

        // Call the method to test
        Dnd.AttackResult result = Dnd.enemyAttack(enemy, character, roll);

        // Assertions to verify failure
        assertFalse(result.isHit(), "Attack should miss");
        assertEquals(0, result.getDamage(), "Damage should be 0");
        assertEquals(50, character.getCurrentHp(), "Character's HP should remain the same");
    }
}
