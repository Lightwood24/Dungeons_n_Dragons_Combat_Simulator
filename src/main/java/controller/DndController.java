package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Characters;
import model.Dnd;
import model.Enemies;
import model.Spells;

import java.util.ArrayList;
import java.util.List;
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

    // Flags to track if player has used their heal or spell
    private boolean hasHealed = false;
    private boolean hasCastSpell = false;
    private final Random random = new Random();

    public void initialize() {
        gameRulesTextArea.setDisable(true); // Make game rules text area read-only
        gameRulesTextArea.setText(gameRules);

        // Disable gameplay buttons until game starts
        attackButton.setDisable(true);
        healButton.setDisable(true);
        spellButton.setDisable(true);

        characters.add(new Characters("Fighter", 42, 42, 18, 17, "/Images/Fighter.png", new Spells.IntimidatingScream()));
        characters.add(new Characters("Paladin", 42, 42, 16, 20, "/Images/Paladin.png", new Spells.Smite()));
        characters.add(new Characters("Ranger", 42, 42, 16, 18, "/Images/Ranger.png", new Spells.AcidArrow()));
        characters.add(new Characters("Rogue", 36, 36, 20, 16, "/Images/Rogue.png", new Spells.BackStab()));
        characters.add(new Characters("Cleric", 36, 36, 20, 16, "/Images/Cleric.png", new Spells.HealingLight()));
        characters.add(new Characters("Sorcerer", 30, 30, 22, 15, "/Images/Sorcerer.png", new Spells.ConcentratedMagic()));

        enemies.add(new Enemies("Sahuagin", 38, 38, 17, 15, "/Images/Sahuagin.png"));
        enemies.add(new Enemies("Goliath", 60, 60, 15, 18, "/Images/Goliath.png"));
        enemies.add(new Enemies("Tiefling Charmer", 24, 24, 18, 16, "/Images/TieflingCharmer.png"));

        // Populate combo boxes
        charComboBox.getItems().addAll(characters);
        enemyComboBox.getItems().addAll(enemies);
        charComboBox.getSelectionModel().selectFirst();
        enemyComboBox.getSelectionModel().selectFirst();

        // Update character UI when selected
        charComboBox.setOnAction(event -> {
            Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
            if (selectedCharacter != null) {
                charHealthValue.setText(String.valueOf(selectedCharacter.getCurrentHp()));
                charAttackValue.setText(String.valueOf(selectedCharacter.getDmg()));
                charACValue.setText(String.valueOf(selectedCharacter.getAc()));
                charImageView.setImage(new Image(getClass().getResource(selectedCharacter.getImagePath()).toExternalForm()));
                spellButton.setText(selectedCharacter.getSpell().getName());
            }
        });

        // Update enemy UI when selected
        enemyComboBox.setOnAction(event -> {
            Enemies selectedEnemy = enemyComboBox.getSelectionModel().getSelectedItem();
            if (selectedEnemy != null) {
                enemyHealthValue.setText(String.valueOf(selectedEnemy.getCurrentHp()));
                enemyAttackValue.setText(String.valueOf(selectedEnemy.getDmg()));
                enemyACValue.setText(String.valueOf(selectedEnemy.getAc()));
                enemyImageView.setImage(new Image(getClass().getResource(selectedEnemy.getImagePath()).toExternalForm()));
            }
        });

        // Manually trigger initial UI updates
        charComboBox.getOnAction().handle(null);
        enemyComboBox.getOnAction().handle(null);

        // Start game button logic
        gameStartButton.setOnAction(event -> {
            charComboBox.setDisable(true);
            enemyComboBox.setDisable(true);
            attackButton.setDisable(false);
            healButton.setDisable(false);
            spellButton.setDisable(false);
            gameStartButton.setDisable(true);
        });

        // New game logic: reset state and UI
        newGameButton.setOnAction(event -> {
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
        });

        // Healing logic - only allowed once per game
        healButton.setOnAction(event -> {
            Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
            if (selectedCharacter != null && !hasHealed) {
                int maxHp = selectedCharacter.getMaxHp();
                int currentHp = selectedCharacter.getCurrentHp();
                int healAmount = 20;
                int newHp = Math.min(currentHp + healAmount, maxHp);

                selectedCharacter.setCurrentHp(newHp);
                charHealthValue.setText(String.valueOf(newHp));

                hasHealed = true;
                healButton.setDisable(true);
            }

            Alert healAlert = new Alert(Alert.AlertType.INFORMATION);
            healAlert.setTitle("Healing results");
            healAlert.setHeaderText("You've healed 20 HP");
            healAlert.setContentText("Pelor, the God of the Sun, Light, and Healing blessed you with his healing light");
            healAlert.showAndWait();
        });

        // Spell casting logic - only allowed once per game
        spellButton.setOnAction(event -> {
            Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
            Enemies selectedEnemy = enemyComboBox.getSelectionModel().getSelectedItem();

            if (selectedCharacter != null && selectedEnemy != null) {
                // Apply spell effect
                Spells spell = selectedCharacter.getSpell();
                spell.cast(selectedCharacter, selectedEnemy);

                // Update UI
                enemyHealthValue.setText(String.valueOf(selectedEnemy.getCurrentHp()));
                enemyAttackValue.setText(String.valueOf(selectedEnemy.getDmg()));
                enemyACValue.setText(String.valueOf(selectedEnemy.getAc()));
                charHealthValue.setText(String.valueOf(selectedCharacter.getCurrentHp()));
                charAttackValue.setText(String.valueOf(selectedCharacter.getDmg()));
                charACValue.setText(String.valueOf(selectedCharacter.getAc()));

                Alert spellAlert = new Alert(Alert.AlertType.INFORMATION);
                spellAlert.setTitle("Spell Results");
                spellAlert.setHeaderText("Spell Cast!");
                String spellMessage = switch (spell.getClass().getSimpleName()) {
                    case "ConcentratedMagic" -> "You channel your arcane power and unleash concentrated magic, raising your attack damage by 5!";
                    case "HealingLight" -> "A radiant glow heals your wounds, restoring 15 HP!";
                    case "IntimidatingScream" -> "You unleash a terrifying scream, reducing your enemy's defense by 4!";
                    case "Smite" -> "You call down divine power and smite your enemy for 10 damage!";
                    case "AcidArrow" -> "A sizzling acid arrow hits your enemy, dealing 5 damage and lowering their defense by 2!";
                    case "BackStab" -> "You sneak behind your enemy and strike with deadly precision, dealing 15 damage!";
                    default -> "A mysterious spell is cast!";
                };
                spellAlert.setContentText(spellMessage);
                spellAlert.showAndWait();
            }

            // Enemy counterattack after spell
            assert selectedCharacter != null;
            int roll = random.nextInt(20) + 1;
            Dnd.AttackResult enemyResult = Dnd.enemyAttack(selectedEnemy, selectedCharacter, roll);
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

            hasCastSpell = true;
            spellButton.setDisable(true);
        });

        // Main attack logic - player and then enemy
        attackButton.setOnAction(event -> {
            Characters selectedCharacter = charComboBox.getSelectionModel().getSelectedItem();
            Enemies selectedEnemy = enemyComboBox.getSelectionModel().getSelectedItem();

            if (selectedCharacter != null && selectedEnemy != null) {
                // Player attack
                int roll = random.nextInt(20) + 1;
                Dnd.AttackResult charResult = Dnd.characterAttack(selectedCharacter, selectedEnemy, roll);
                enemyHealthValue.setText(String.valueOf(selectedEnemy.getCurrentHp()));

                StringBuilder alertMessage = new StringBuilder();
                alertMessage.append(selectedCharacter.getName()).append(" rolled: ").append(charResult.getRoll()).append("\n");
                if (charResult.isHit()) {
                    alertMessage.append("Hit! Dealt ").append(charResult.getDamage()).append(" damage.\n\n");
                } else {
                    alertMessage.append("Missed!\n\n");
                }

                // Enemy counterattack if still alive
                if (selectedEnemy.getCurrentHp() > 0) {
                    roll = random.nextInt(20) + 1;
                    Dnd.AttackResult enemyResult = Dnd.enemyAttack(selectedEnemy, selectedCharacter, roll);
                    charHealthValue.setText(String.valueOf(selectedCharacter.getCurrentHp()));

                    alertMessage.append(selectedEnemy.getName()).append(" rolled: ").append(enemyResult.getRoll()).append("\n");
                    if (enemyResult.isHit()) {
                        alertMessage.append("Hit! Dealt ").append(enemyResult.getDamage()).append(" damage.\n");
                    } else {
                        alertMessage.append("Missed!\n");
                    }
                }

                // Determine outcome of battle
                if (selectedEnemy.getCurrentHp() <= 0) {
                    alertMessage.append("\nVictory! You defeated the ").append(selectedEnemy.getName()).append("!");
                } else if (selectedCharacter.getCurrentHp() <= 0) {
                    alertMessage.append("\nDefeat... You were slain by the ").append(selectedEnemy.getName()).append(".");
                }

                Alert battleAlert = new Alert(Alert.AlertType.INFORMATION);
                battleAlert.setTitle("Battle Results");
                battleAlert.setHeaderText("Combat Summary");
                battleAlert.setContentText(alertMessage.toString());
                battleAlert.showAndWait();
            }
        });
    }
}
