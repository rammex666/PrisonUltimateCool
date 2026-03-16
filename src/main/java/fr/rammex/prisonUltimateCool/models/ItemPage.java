package fr.rammex.prisonUltimateCool.models;

import org.bukkit.inventory.ItemStack;

public class ItemPage {
    String id;
    ItemStack item;
    int slot;

    // TODO : remplacere l'id par une InventoryPage plus facile pour l'attribuer a un inventaire

    public ItemPage(String id, ItemStack item, int slot){
        this.id = id;
        this.item = item;
        this.slot = slot;
    }

    public String getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
