package fr.rammex.prisonUltimateCool.pickaxe.util;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffect;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffectRegistry;
import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeManager;
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

public class ItemBuilder {

    @SuppressWarnings("deprecation")
    public ItemStack createItemForInventory(String name, String id, int slot, int amount, Material material, List<String> lore){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }
    
    @SuppressWarnings("deprecation")
    public ItemStack createMineZoneTool(){
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6MineZone Tool"));
        item.setItemMeta(itemMeta);

        return item;
    }


    @SuppressWarnings("deprecation")
    public ItemStack createPickaxe(String name, Player player) {

        ItemStack item = new ItemStack(Material.WOODEN_PICKAXE, 1);
        ItemMeta itemMeta = item.getItemMeta();

        List<String> itemLore = new ArrayList<>();

        Map<String, Integer> effectOnPickaxe = new HashMap<>();
        effectOnPickaxe.put("EXPLOSION",1);
        effectOnPickaxe.put("EFFICIENCY",7);

        String playerUUID = player.getUniqueId().toString();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        itemLore.add(ChatColor.translateAlternateColorCodes('&', "&7* Pioche de : " + player.getName()));
        itemLore.add(ChatColor.translateAlternateColorCodes('&', "&7----------------------"));
        itemLore.add(" ");

        for (Map.Entry<String, Integer> entry : effectOnPickaxe.entrySet()) {
            String effectId = entry.getKey();
            int level = entry.getValue();

            CustomEffect effect = CustomEffectRegistry.get(effectId);
            if (effect == null) continue;

            itemLore.add(ChatColor.translateAlternateColorCodes('&',
                    "&6* " + effect.getName() + " [" + level + "/" + effect.getLevelMax() + "]"));
        }

        itemMeta.setLore(itemLore);

        String id = "pickaxe_" + UUID.randomUUID();

        NamespacedKey key = new NamespacedKey(PrisonUltimateCool.getInstance(), "pickaxe_id");

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

        Pickaxe starterPickaxe = new Pickaxe(
                id,
                playerUUID,
                date,
                new ArrayList<>(),
                effectOnPickaxe
        );

        PickaxeManager.addPickaxe(starterPickaxe);

        return item;
    }

    @SuppressWarnings("deprecation")
    public void UpdateLore(Pickaxe pickaxe,ItemStack itemStack,Player player){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        Map<String, Integer> effectOnPickaxe = pickaxe.getCustomEffects();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&7* Pioche de : " + player.getName()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7----------------------"));
        lore.add(" ");

        for (Map.Entry<String, Integer> entry : effectOnPickaxe.entrySet()) {
            String effectId = entry.getKey();
            int level = entry.getValue();

            CustomEffect effect = CustomEffectRegistry.get(effectId);
            if (effect == null) continue;

            lore.add(ChatColor.translateAlternateColorCodes('&',
                    "&6* " + effect.getName() + " [" + level + "/" + effect.getLevelMax() + "]"));
        }

        System.out.println("update du lore");

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }
}
