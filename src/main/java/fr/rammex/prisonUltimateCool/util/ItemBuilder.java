package fr.rammex.prisonUltimateCool.util;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import fr.rammex.prisonUltimateCool.models.CustomEffect;
import fr.rammex.prisonUltimateCool.models.Pickaxe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ItemBuilder {

    public ItemStack createItemForInventory(String name, String id, int slot, int amount, Material material, List<String> lore){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack createPickaxe(Player player){
        ItemStack item = new ItemStack(Material.WOODEN_PICKAXE, 1);
        ItemMeta itemMeta = item.getItemMeta();
        List<String> itemLore = new ArrayList<>();
        Map<CustomEffect,Integer> effectOnPickaxe = new HashMap<>();
        String playerUUID = player.getUniqueId().toString();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&7Pioche Basique"));

        itemLore.add("* Pioche de : "+player.getName());
        itemLore.add("----------------------");
        for(CustomEffect effect : effectOnPickaxe.keySet()){
            itemLore.add("* "+effect.getName()+" ["+effectOnPickaxe.get(effect)+"/"+effect.getLevelMax()+"]");
        }

        itemMeta.setLore(itemLore);

        NamespacedKey key = new NamespacedKey(PrisonUltimateCool.getInstance(), "id");

        String id = "pickaxe_" + ThreadLocalRandom.current().nextInt(100000);

        itemMeta.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                id
        );

        item.setItemMeta(itemMeta);

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd MMMM yyyy",
                Locale.FRANCE
        );

        String date = today.format(formatter);

        Pickaxe starterPickaxe = new Pickaxe(id, playerUUID, date, new ArrayList<>(), effectOnPickaxe);
        PrisonUltimateCool.getInstance().getPickaxeManager().addPickaxe(starterPickaxe, playerUUID);
        return item;
    }
}
