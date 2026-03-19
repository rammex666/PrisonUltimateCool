package fr.rammex.prisonUltimateCool.pickaxe.util;

public enum InventoryRow {
    CHEST_1(9),
    CHEST_2(18),
    CHEST_3(27),
    CHEST_4(36),
    CHEST_5(45),
    CHEST_6(54);

    int slot;

    InventoryRow(int slot) {
    }

    public int getSlot() {
        return slot;
    }
}
