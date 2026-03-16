package fr.rammex.prisonUltimateCool.util;

import fr.rammex.prisonUltimateCool.models.ItemPage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    // TODO : adapter avec le InventoryPage
    public void createItemForInventory(String name, String id, int slot, int amount, Material material, List<String> lore){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        ItemPage itemPage = new ItemPage(id,item,slot);

    }
}
