package fr.rammex.prisonUltimateCool.models;

import fr.rammex.prisonUltimateCool.util.InventoryRow;

public class InventoryPage {
    String id;
    String displayName;
    InventoryRow rows;

    public InventoryPage(String id, String displayName, InventoryRow rows){
        this.id = id;
        this.displayName = displayName;
        this.rows = rows;
    }

    // TODO : crée setters et getters
}
