package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Characters;
import model.BattleLogic;
import model.Enemies;
import model.Spells;
import util.DataLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DndController {

    @FXML private Button gameStartButton;
    @FXML private Button newGameButton;
    @FXML private Button attackButton;
    @FXML private Button healButton;
    @FXML private Button spellButton;
    @FXML private Label charHealthValue;
    @FXML private Label charAttackValue;
    @FXML private Label charACValue;
    @FXML private Label enemyHealthValue;
    @FXML private Label enemyAttackValue;
    @FXML private Label enemyACValue;
    @FXML private ImageView charImageView;
    @FXML private ImageView enemyImageView;
    @FXML private ComboBox<Characters> charComboBox;
    @FXML private ComboBox<Enemies> enemyComboBox;
    @FXML private TextArea gameRulesTextArea;

    private final List<Characters> characters = new ArrayList<>();
    private final List<Enemies> enemies = new ArrayList<>();
    private final Random random = new Random();

    private boolean hasHealed = false;
    private boolean hasCastSpell = false;

    public void initialize() {
        gameRulesTextArea.setDisable(true);
        String gameRules = """
                Here are the rules of the battle:
                
                1. Choose your hero and a foe worthy of your steel.
                2. Click 'Start Game' to commence your duel.
                3. Use 'Attack' to strike your enemy. Beware — they will strike back!
                   There is a chance you may miss your attack!
                4. Use 'Heal' once per battle to restore 20 HP.
                5. Use your hero's spell once per battle to get an advantage on the battlefield.
                   Your spell will weaken the enemy, but it won't be enough to defeat it.
                   Beware — your foe will strike back!
                6. Battle ends when your champion or your foe falls.
                
                May the dice roll in your favor.""";
        gameRulesTextArea.setText(gameRules);

        attackButton.setDisable(true);
        healButton.setDisable(true);
        spellButton.setDisable(true);

        characters.addAll(DataLoader.loadCharacters());
        enemies.addAll(DataLoader.loadEnemies());

        charComboBox.getItems().addAll(characters);
        enemyComboBox.getItems().addAll(enemies);
        charComboBox.getSelectionModel().selectFirst();
        enemyComboBox.getSelectionModel().selectFirst();

        charComboBox.setOnAction(event -> updateCharacterUI());
        enemyComboBox.setOnAction(event -> updateEnemyUI());

        updateCharacterUI();
        updateEnemyUI();

        gameStartButton.setOnAction(event -> {
            charComboBox.setDisable(true);
            enemyComboBox.setDisable(true);
            attackButton.setDisable(false);
            healButton.setDisable(false);
            spellButton.setDisable(false);
            gameStartButton.setDisable(true);
        });

        newGameButton.setOnAction(event -> resetGame());
        healButton.setOnAction(event -> handleHeal());
        spellButton.setOnAction(event -> handleSpell());
        attackButton.setOnAction(event -> handleAttack());
    }

    private void updateCharacterUI() {
        Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null) {
            charHealthValue.setText(String.valueOf(selectedCharacter.getCurrentHp()));
            charAttackValue.setText(String.valueOf(selectedCharacter.getDmg()));
            charACValue.setText(String.valueOf(selectedCharacter.getAc()));
            charImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(selectedCharacter.getImagePath())).toExternalForm()));
            spellButton.setText(selectedCharacter.getSpell().getName());
        }
    }

    private void updateEnemyUI() {
        Enemies selectedEnemy = enemyComboBox.getSelectionModel().getSelectedItem();
        if (selectedEnemy != null) {
            enemyHealthValue.setText(String.valueOf(selectedEnemy.getCurrentHp()));
            enemyAttackValue.setText(String.valueOf(selectedEnemy.getDmg()));
            enemyACValue.setText(String.valueOf(selectedEnemy.getAc()));
            enemyImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(selectedEnemy.getImagePath())).toExternalForm()));
        }
    }

    private void resetGame() {
        hasHealed = false;
        hasCastSpell = false;
        charComboBox.setDisable(false);
        enemyComboBox.setDisable(false);
        gameStartButton.setDisable(false);
        attackButton.setDisable(true);
        healButton.setDisable(true);
        spellButton.setDisable(true);

        Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
        Enemies selectedEnemy = enemyComboBox.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null) {
            selectedCharacter.setCurrentHp(selectedCharacter.getMaxHp());
            charHealthValue.setText(String.valueOf(selectedCharacter.getCurrentHp()));
        }
        if (selectedEnemy != null) {
            selectedEnemy.setCurrentHp(selectedEnemy.getMaxHp());
            enemyHealthValue.setText(String.valueOf(selectedEnemy.getCurrentHp()));
        }
    }

    private void handleHeal() {
        Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null && !hasHealed) {
            int newHp = Math.min(selectedCharacter.getCurrentHp() + 20, selectedCharacter.getMaxHp());
            selectedCharacter.setCurrentHp(newHp);
            charHealthValue.setText(String.valueOf(newHp));
            hasHealed = true;
            healButton.setDisable(true);

            Alert healAlert = new Alert(Alert.AlertType.INFORMATION);
            healAlert.setTitle("Healing results");
            healAlert.setHeaderText("You've healed 20 HP");
            healAlert.setContentText("Pelor, the God of the Sun, Light, and Healing blessed you with his healing light");
            healAlert.showAndWait();
        }
    }

    private void handleSpell() {
        Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
        Enemies selectedEnemy = enemyComboBox.getSelectionModel().getSelectedItem();

        if (selectedCharacter != null && selectedEnemy != null) {
            Spells spell = selectedCharacter.getSpell();
            spell.cast(selectedCharacter, selectedEnemy);
            updateCharacterUI();
            updateEnemyUI();

            Alert spellAlert = new Alert(Alert.AlertType.INFORMATION);
            spellAlert.setTitle("Spell Results");
            spellAlert.setHeaderText("Spell Cast!");
            spellAlert.setContentText(getSpellMessage(spell));
            spellAlert.showAndWait();

            handleEnemyCounterattack(selectedCharacter, selectedEnemy);
            hasCastSpell = true;
            spellButton.setDisable(true);
        }
    }

    private void handleAttack() {
        Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
        Enemies selectedEnemy = enemyComboBox.getSelectionModel().getSelectedItem();

        if (selectedCharacter != null && selectedEnemy != null) {
            int roll = random.nextInt(20) + 1;
            BattleLogic.AttackResult charResult = BattleLogic.characterAttack(selectedCharacter, selectedEnemy, roll);
            enemyHealthValue.setText(String.valueOf(selectedEnemy.getCurrentHp()));

            StringBuilder alertMessage = new StringBuilder();
            alertMessage.append(selectedCharacter.getName()).append(" rolled: ").append(charResult.getRoll()).append("\n");
            if (charResult.isHit()) {
                alertMessage.append("Hit! Dealt ").append(charResult.getDamage()).append(" damage.\n\n");
            } else {
                alertMessage.append("Missed!\n\n");
            }

            Alert attackAlert = new Alert(Alert.AlertType.INFORMATION);
            attackAlert.setTitle("Attack Results");
            attackAlert.setHeaderText("You attacked!");
            attackAlert.setContentText(alertMessage.toString());
            attackAlert.showAndWait();

            // Check if enemy is dead
            if (selectedEnemy.getCurrentHp() <= 0) {
                Alert victoryAlert = new Alert(Alert.AlertType.INFORMATION);
                victoryAlert.setTitle("Victory!");
                victoryAlert.setHeaderText("Enemy Defeated");
                victoryAlert.setContentText("You have slain the " + selectedEnemy.getName() + "!");
                victoryAlert.showAndWait();

                // Optionally disable buttons or reset the game state here
                attackButton.setDisable(true);
                spellButton.setDisable(true);
                healButton.setDisable(true);

            } else {
                handleEnemyCounterattack(selectedCharacter, selectedEnemy);
            }
        }
    }


    private void handleEnemyCounterattack(Characters selectedCharacter, Enemies selectedEnemy) {
        int roll = random.nextInt(20) + 1;
        BattleLogic.AttackResult enemyResult = BattleLogic.enemyAttack(selectedEnemy, selectedCharacter, roll);
        charHealthValue.setText(String.valueOf(selectedCharacter.getCurrentHp()));

        StringBuilder enemyAttackMessage = new StringBuilder();
        enemyAttackMessage.append(selectedEnemy.getName()).append(" rolled: ").append(enemyResult.getRoll()).append("\n");
        if (enemyResult.isHit()) {
            enemyAttackMessage.append("Hit! Dealt ").append(enemyResult.getDamage()).append(" damage.\n");
        } else {
            enemyAttackMessage.append("Missed!\n");
        }

        Alert enemyAttackAlert = new Alert(Alert.AlertType.INFORMATION);
        enemyAttackAlert.setTitle("Enemy's Counterattack");
        enemyAttackAlert.setHeaderText("Enemy's Response");
        enemyAttackAlert.setContentText(enemyAttackMessage.toString());
        enemyAttackAlert.showAndWait();

        if (selectedCharacter.getCurrentHp() <= 0) {
            Alert deathAlert = new Alert(Alert.AlertType.INFORMATION);
            deathAlert.setTitle("You Died");
            deathAlert.setHeaderText("Defeat...");
            deathAlert.setContentText("You were slain by the " + selectedEnemy.getName() + " during their counterattack.");
            deathAlert.showAndWait();

            attackButton.setDisable(true);
            spellButton.setDisable(true);
            healButton.setDisable(true);
        }
    }

    private String getSpellMessage(Spells spell) {
        return switch (spell.getClass().getSimpleName()) {
            case "ConcentratedMagic" -> "You channel your arcane power and unleash concentrated magic, raising your attack damage by 5!";
            case "HealingLight" -> "A radiant glow heals your wounds, restoring 15 HP!";
            case "IntimidatingScream" -> "You unleash a terrifying scream, reducing your enemy's defense by 4!";
            case "Smite" -> "You call down divine power and smite your enemy for 10 damage!";
            case "AcidArrow" -> "A sizzling acid arrow hits your enemy, dealing 5 damage and lowering their defense by 2!";
            case "BackStab" -> "You sneak behind your enemy and strike with deadly precision, dealing 15 damage!";
            default -> "A mysterious spell is cast!";
        };
    }
}
