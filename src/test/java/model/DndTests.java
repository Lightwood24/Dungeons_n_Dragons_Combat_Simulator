package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DndTests {

    private Characters character;
    private Enemies enemy;

    // Set up a new character and enemy before each test
    @BeforeEach
    public void setup() {
        character = new Characters("Hero", 100, 100, 20, 15, "path/to/hero/image", null);
        enemy = new Enemies("Villain", 80, 80, 15, 12, "path/to/villain/image");
    }

    // Clean up after each test
    @AfterEach
    public void teardown() {
        character = null;
        enemy = null;
    }

    // Test when character successfully hits the enemy
    @Test
    public void testCharacterAttackSuccess() {
        character.setDmg(10);
        enemy.setAc(12);
        enemy.setCurrentHp(30);

        int roll = 13;  // roll is high enough to hit

        Dnd.AttackResult result = Dnd.characterAttack(character, enemy, roll);

        assertTrue(result.isHit(), "Attack should hit");
        assertEquals(5, result.getDamage(), "Damage should be halved (10 / 2)");
        assertEquals(25, enemy.getCurrentHp(), "Enemy's HP should decrease by 5");
    }

    // Test when character fails to hit the enemy
    @Test
    public void testCharacterAttackFailure() {
        character.setDmg(10);
        enemy.setAc(20);
        enemy.setCurrentHp(30);

        int roll = 5;  // roll is too low to hit

        Dnd.AttackResult result = Dnd.characterAttack(character, enemy, roll);

        assertFalse(result.isHit(), "Attack should miss");
        assertEquals(0, result.getDamage(), "Damage should be 0");
        assertEquals(30, enemy.getCurrentHp(), "Enemy's HP should remain the same");
    }

    // Test when enemy successfully hits the character
    @Test
    public void testEnemyAttackSuccess() {
        character.setAc(15);
        enemy.setDmg(12);
        character.setCurrentHp(50);

        int roll = 10;  // roll is high enough to hit

        Dnd.AttackResult result = Dnd.enemyAttack(enemy, character, roll);

        assertTrue(result.isHit(), "Attack should hit");
        assertEquals(6, result.getDamage(), "Damage should be halved (12 / 2)");
        assertEquals(44, character.getCurrentHp(), "Character's HP should decrease by 6");
    }

    // Test when enemy fails to hit the character
    @Test
    public void testEnemyAttackFailure() {
        character.setAc(25);
        enemy.setDmg(12);
        character.setCurrentHp(50);

        int roll = 5;  // roll is too low to hit

        Dnd.AttackResult result = Dnd.enemyAttack(enemy, character, roll);

        assertFalse(result.isHit(), "Attack should miss");
        assertEquals(0, result.getDamage(), "Damage should be 0");
        assertEquals(50, character.getCurrentHp(), "Character's HP should remain the same");
    }
}
