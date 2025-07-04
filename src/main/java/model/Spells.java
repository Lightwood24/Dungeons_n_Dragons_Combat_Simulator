package model;

import lombok.Getter;

@Getter
public class Spells {
    private final String name;  // Name of the spell

    public Spells(String name) {
        this.name = name;
    }

    // This method will be overridden by subclasses
    public void cast(Characters caster, Enemies target) {}

    // Spell 1: Reduces enemy's armor class (AC) by 4
    public static class IntimidatingScream extends Spells {
        public IntimidatingScream() {
            super("Intimidating Scream");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int newAC = target.getAc() - 4;
            target.setAc(Math.max(newAC, 0));  // Prevents AC from dropping below 0
        }
    }

    // Spell 2: Deals 10 damage
    public static class Smite extends Spells {
        public Smite() {
            super("Smite");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int damage = 10;
            int newHp = target.getCurrentHp() - damage;
            target.setCurrentHp(Math.max(newHp, 1));  // Keeps enemy alive with at least 1 HP
        }
    }

    // Spell 3: Deals 5 damage and reduces AC by 2
    public static class AcidArrow extends Spells {
        public AcidArrow() {
            super("Acid Arrow");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            // Reduce AC by 2
            int newAC = target.getAc() - 2;
            target.setAc(Math.max(newAC, 1));  // AC cannot go below 1

            // Deal 5 damage
            int damage = 5;
            int newHp = target.getCurrentHp() - damage;
            target.setCurrentHp(Math.max(newHp, 0));  // HP cannot go below 0
        }
    }

    // Spell 4: Deals 15 damage
    public static class BackStab extends Spells {
        public BackStab() {
            super("Back Stab");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int damage = 15;
            int newHp = target.getCurrentHp() - damage;
            target.setCurrentHp(Math.max(newHp, 1));  // Keeps target barely alive
        }
    }

    // Spell 5: Heals the caster by 15 HP
    public static class HealingLight extends Spells {
        public HealingLight() {
            super("Healing Light");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int healAmount = 15;
            int newHp = caster.getCurrentHp() + healAmount;
            caster.setCurrentHp(Math.min(newHp, caster.getMaxHp()));  // Prevents overhealing
        }
    }

    // Spell 6: Boosts caster's damage by 5
    public static class ConcentratedMagic extends Spells {
        public ConcentratedMagic() {
            super("Concentrated Magic");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int newDmg = caster.getDmg() + 5;
            caster.setDmg(newDmg);  // Increases character's damage
        }
    }

    public static Spells getByName(String name) {
        return switch (name) {
            case "IntimidatingScream" -> new IntimidatingScream();
            case "Smite" -> new Smite();
            case "AcidArrow" -> new AcidArrow();
            case "BackStab" -> new BackStab();
            case "HealingLight" -> new HealingLight();
            case "ConcentratedMagic" -> new ConcentratedMagic();
            default -> null;
        };
    }
}