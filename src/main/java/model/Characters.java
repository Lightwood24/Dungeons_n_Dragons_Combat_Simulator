package model;

// This class represents a game character with various stats and a spell
public class Characters {

    private String name;
    private int maxHp;
    private int currentHp;
    private int dmg;
    private int ac;
    private String imagePath;
    private Spells spell;

    public Characters(String name, int maxHp, int currentHp, int dmg, int ac, String imagePath, Spells spell) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.dmg = dmg;
        this.ac = ac;
        this.imagePath = imagePath;
        this.spell = spell;
    }

    public Spells getSpell() {
        return spell;
    }

    public void setSpell(Spells spell) {
        this.spell = spell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Override toString so that the character's name will be shown in ComboBox
    @Override
    public String toString() {
        return name;
    }
}
