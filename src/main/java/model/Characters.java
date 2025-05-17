package model;

import lombok.Data;
import lombok.AllArgsConstructor;

// This class represents a game character with various stats and a spell
@Data
@AllArgsConstructor
public class Characters {
    private String name;
    private int maxHp;
    private int currentHp;
    private int dmg;
    private int ac;
    private String imagePath;
    private Spells spell;

    // To display name in ComboBox
    @Override
    public String toString() {
        return name;
    }
}