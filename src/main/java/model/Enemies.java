package model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Enemies {
    private String name;
    private int maxHp;
    private int currentHp;
    private int dmg;
    private int ac;
    private String imagePath;

    // To display name in ComboBox
    @Override
    public String toString() {
        return name;
    }
}
