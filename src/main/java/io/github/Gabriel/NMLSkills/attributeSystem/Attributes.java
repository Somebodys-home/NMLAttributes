package io.github.Gabriel.NMLSkills.attributeSystem;

public class Attributes {
    // CORE STATS: stats that get saved to the profiles.yml
    private int level;
    private int exp;
    private int exp2NextLevel;
    private int attributePoints;
    private int vitality;
    private int strength;
    private int arcane;
    private int stamina;
    private int maxEnergy;
    private int currentEnergy;
    private int currentOverhealth;
    private int maxOverhealth;

    // SUB STATS: stats that are just from calculations
    private int vitalityBonus;
    private int strengthBonus;
    private int energyBonus; // increases max energy
    private int overhealthBonus;


    public Attributes(int level, int exp, int attributePoints,
                      int vitality,
                      int strength,
                      int arcane,
                      int stamina,
                      int currentEnergy, int maxEnergy,
                      int currentOverhealth, int maxOverhealth) {
        this.level = level;
        this.exp = exp;
        exp2NextLevel = 100;
        this.vitality = vitality;
        this.strength = strength;
        this.stamina = stamina;
        this.arcane = arcane;

        energyBonus = 15 * this.stamina; // increases max energy per stamina level
        vitalityBonus = this.vitality; // increases max health per vitality level
        strengthBonus = 3 * this.strength; // increases melee damage increase per strength level
        overhealthBonus = 5 * this.arcane; // increases overhealth per arcane level
        this.currentEnergy = currentEnergy;
        this.maxEnergy = maxEnergy + energyBonus;
        this.attributePoints = attributePoints;
        this.currentOverhealth = currentOverhealth;
        this.maxOverhealth = maxOverhealth + overhealthBonus;
    }

    // level
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
            int pointsToRemove = Math.abs(levelDifference);
            this.attributePoints = Math.max(this.attributePoints - pointsToRemove, 0); // Remove points but don't go below 0
        }
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp2NextLevel() {
        return exp2NextLevel;
    }

    public void setExp2NextLevel(int exp2NextLevel) {
        this.exp2NextLevel = exp2NextLevel;
    }

    // attribute points
    public int getAttributePoints() {
        return attributePoints;
    }

    public void setAttributePoints(int attributePoints) {
        this.attributePoints = attributePoints;
    }

    // vitality
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

    // strength
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        strengthBonus = 3 * this.strength;
    }

    public int getStrengthBonus() {
        return strengthBonus;
    }

    // arcane
    public int getArcane() {
        return arcane;
    }

    public void setArcane(int arcane) {
        this.arcane = arcane;

        overhealthBonus = 5 * this.arcane;
        maxOverhealth = 50 + overhealthBonus;

        if (currentOverhealth > maxOverhealth) {
            currentOverhealth = maxOverhealth;
        }
    }

    public int getMaxOverhealth() {
        return maxOverhealth;
    }

    public int getCurrentOverhealth() {
        return currentOverhealth;
    }

    public void setCurrentOverhealth(int currentOverhealth) {}

    public int getOverhealthBonus() {
        return overhealthBonus;
    }

    // stamina
    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
        energyBonus = 15 * stamina;
        maxEnergy = 100 + energyBonus;

        if (currentEnergy > maxEnergy) {
            currentEnergy = maxEnergy;
        }
    }

    public int getEnergyBonus() {
        return energyBonus;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }
}
