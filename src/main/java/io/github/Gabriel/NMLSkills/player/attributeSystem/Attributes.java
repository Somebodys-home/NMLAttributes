package io.github.Gabriel.NMLSkills.player.attributeSystem;

public class Attributes {
    // CORE STATS: stats that get saved to the file
    private int level;
    private int exp;
    private int attributePoints;
    private int vitality;
    private int strength;
    private int stamina;
    private double maxEnergy;
    private double currentEnergy;

    // SUB STATS: stats that are just from calculations
    private int vitalityBonus;
    private double strengthBonus;
    private int staminaBonus; // increases max energy

    public Attributes(int level, int attributePoints, int vitality, int strength, int stamina, int maxEnergy, int currentEnergy) {
        this.level = level;
        exp = 0;
        this.vitality = vitality;
        this.strength = strength;
        this.stamina = stamina;

        staminaBonus = 10 * stamina; // increases max energy per level
        vitalityBonus = this.vitality; // increases max health per level
        strengthBonus = 3 * this.strength; // % damage increase per level
        this.maxEnergy = maxEnergy + staminaBonus;
        this.currentEnergy = currentEnergy;
        this.attributePoints = attributePoints;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
        vitalityBonus = this.vitality;
    }

    public int getVitalityBonus() {
        return vitalityBonus;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        strengthBonus = 3 * this.strength;
    }

    public double getStrengthBonus() {
        return strengthBonus;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
        staminaBonus = 15 * stamina;
        maxEnergy = 100 + staminaBonus;

        if (currentEnergy > maxEnergy) {
            currentEnergy = maxEnergy;
        }
    }

    public int getStaminaBonus() {
        return staminaBonus;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        int prevLevel = this.level;
        this.level = level;
        int levelDifference = this.level - prevLevel;

        if (levelDifference > 0) {
            // Player gained levels
            int pointsToAdd = Math.max(levelDifference, 1);
            this.attributePoints += pointsToAdd;
        } else if (levelDifference < 0) {
            // Player lost levels
            int pointsToRemove = Math.abs(levelDifference); // Convert to positive
            this.attributePoints = Math.max(this.attributePoints - pointsToRemove, 0); // Remove points but don't go below 0
        }
    }

    public double getMaxEnergy() {
        return maxEnergy;
    }

    public double getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(double currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public int getAttributePoints() {
        return attributePoints;
    }

    public void setAttributePoints(int attributePoints) {
        this.attributePoints = attributePoints;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
