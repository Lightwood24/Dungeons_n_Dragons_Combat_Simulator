package model;

public class Spells {
    private String name;

    public Spells(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void cast(Characters caster, Enemies target) {}


    public static class IntimidatingScream extends Spells {
        public IntimidatingScream() {
            super("Intimidating Scream");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int newAC = target.getAc() - 4;
            target.setAc(Math.max(newAC, 0));
        }
    }

    public static class Smite extends Spells {
        public Smite() {
            super("Smite");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int damage = 10;
            int newHp = target.getCurrentHp() - damage;
            target.setCurrentHp(Math.max(newHp, 1));
        }
    }

    public static class AcidArrow extends Spells {
        public AcidArrow() {
            super("Acid Arrow");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int newAC = target.getAc() - 2;
            target.setAc(Math.max(newAC, 1));

            int damage = 5;
            int newHp = target.getCurrentHp() - damage;
            target.setCurrentHp(Math.max(newHp, 0));
        }
    }

    public static class BackStab extends Spells {
        public BackStab() {
            super("Back Stab");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int damage = 15;
            int newHp = target.getCurrentHp() - damage;
            target.setCurrentHp(Math.max(newHp, 1));
        }
    }

    public static class HealingLight extends Spells {
        public HealingLight() {
            super("Healing Light");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int healAmount = 15;
            int newHp = caster.getCurrentHp() + healAmount;
            caster.setCurrentHp(Math.min(newHp, caster.getMaxHp()));
        }
    }

    public static class ConcentratedMagic extends Spells {
        public ConcentratedMagic() {
            super("Concentrated Magic");
        }

        @Override
        public void cast(Characters caster, Enemies target) {
            int newDmg = caster.getDmg() + 5;
            caster.setDmg(newDmg);
        }
    }
}