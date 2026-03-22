package fr.rammex.prisonUltimateCool.pickaxe.effect.list;

public enum EnchantmentsMaxValue {
    EFFICIENCY("efficiency", 100);

    private final String id;
    private final int maxLevel;

    EnchantmentsMaxValue(String id, int maxLevel) {
        this.id = id;
        this.maxLevel = maxLevel;
    }

    public String getId() {
        return id;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public static int getMaxLevelById(String id) {
        for (EnchantmentsMaxValue enchant : values()) {
            if (enchant.id.equalsIgnoreCase(id)) {
                return enchant.maxLevel;
            }
        }
        return -1;
    }
}