# 🐉 Dungeons & Dragons Combat Simulator 🗡️

A simplified Dungeons & Dragons style combat simulator with a JavaFX GUI interface.

## 📜 Description

This project is a turn-based combat game inspired by Dungeons & Dragons mechanics. Players choose a character class, select an enemy to battle, and engage in combat using a simplified version of D&D's dice-roll combat system.

## ✨ Features

- **Character Selection** 🧙‍♂️: Choose from 6 character classes:
  - 🛡️ Fighter
  - ✝️ Paladin
  - 🏹 Ranger
  - 🗡️ Rogue
  - 📿 Cleric
  - 🔮 Sorcerer

- **Enemy Selection** 👾: Battle against different foes:
  - 🐙 Sahuagin
  - 💪 Goliath
  - 😈 Tiefling Charmer

- **Combat System** ⚔️:
  - Attack with dice-roll mechanics
  - Each character has unique stats (HP, Damage, Armor Class)
  - Use the healing ability once per battle if needed
  - Cast character-specific ability once per battle

- **Special Abilities** 💫:
  - Fighter: Intimidating Scream (reduces enemy AC)
  - Paladin: Smite (deals direct damage)
  - Ranger: Acid Arrow (deals damage and reduces enemy AC)
  - Rogue: Back Stab (deals high damage)
  - Cleric: Healing Light (restores health)
  - Sorcerer: Concentrated Magic (increases attack damage)

## 🚀 How to Run

1. Ensure you have Java 19 or later installed
2. Ensure you have Maven installed
3. Clone the repository
4. Run the following command:

```bash
mvn clean javafx:run
```

Alternatively, you can run the project directly from your IDE by executing the [`Main`](src/main/java/Main.java) class.

## 🎮 How to Play

1. Select your character from the dropdown menu
2. Select an enemy to battle
3. Click "Game Start" to begin the combat
4. Use the following actions during your turn:
  - **⚔️ Attack**: Attempt a basic attack against the enemy
  - **❤️ Heal**: Restore 20 HP (can only be used once per battle)
  - **✨ Spell**: Cast your character's unique ability (can only be used once per battle)
5. Combat continues until either you or your enemy is defeated

## 📁 Project Structure

- [`src/main/java`](src/main/java)
  - [`model/`](src/main/java/model): Game logic classes
    - [`src/main/java/model/Characters.java`](src/main/java/model/Characters.java): Character data and mechanics
    - [`src/main/java/model/Enemies.java`](src/main/java/model/Enemies.java): Enemy data and mechanics
    - [`src/main/java/model/BattleLogic.java`](src/main/java/model/BattleLogic.java): Core game mechanics and combat system
    - [`src/main/java/model/Spells.java`](src/main/java/model/Spells.java): Special ability implementations
  - [`controller/`](src/main/java/controller): UI controllers
    - [`src/main/java/controller/DndController.java`](src/main/java/controller/DndController.java): Main game controller
  - [`src/main/java/DndApplication.java`](src/main/java/DndApplication.java): JavaFX application entry point
  - [`src/main/java/Main.java`](src/main/java/Main.java): Program entry point

- [`src/main/resources`](src/main/resources)
  - `FXML/`: UI layout files
  - `Images/`: Character and enemy images

## 🛠️ Technologies Used

- Java 19
- JavaFX 22
- FXML
- Maven
- JUnit 5
- Lombok

## 🧪 Testing

The project includes unit tests for the combat mechanics in the [`src/test/java/model/BattleLogicTests.java`](src/test/java/model/BattleLogicTests.java) file. Run the tests using:

```bash
mvn test
```

## 📋 Requirements

- Java 19 or higher
- Maven
- JavaFX 22