package fr.rammex.prisonUltimateCool.pickaxe.util;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffect;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffectRegistry;
import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
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


    @SuppressWarnings({ "deprecation", "removal" })
    public ItemStack createPickaxe(String name, Player player) {

        ItemStack item = new ItemStack(Material.WOODEN_PICKAXE, 1);
        ItemMeta itemMeta = item.getItemMeta();

        List<String> itemLore = new ArrayList<>();

        Map<Enchantment, Integer> echantementsMap = new HashMap<>();
        echantementsMap.put(Enchantment.EFFICIENCY, 15);

        Map<String, Integer> effectOnPickaxe = new HashMap<>();
        effectOnPickaxe.put("EXPLOSION",1);

        String playerUUID = player.getUniqueId().toString();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

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

        for (Map.Entry<Enchantment, Integer> entry : echantementsMap.entrySet()) {
            int level = entry.getValue();
            Enchantment enchantment = entry.getKey();
            if (enchantment == null) continue;

            itemLore.add(ChatColor.translateAlternateColorCodes('&',
                    "&6* " + enchantment.getName() + " [" + level + "/" + 100 + "]"));
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
        // pour bypass la limitation de l'enchantement
        item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 15);
        // 
        
        // TODO : changer la liste<Enchantement> en Map<Enchantement, Integer> pour gérer les niveaux d'enchantement et pas seulement leur présence

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
                echantementsMap,
                effectOnPickaxe
        );

        PickaxeManager.addPickaxe(starterPickaxe);

        return item;
    }

    @SuppressWarnings({ "deprecation", "removal" })
    public void UpdateLore(Pickaxe pickaxe,ItemStack itemStack,Player player){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        Map<String, Integer> effectOnPickaxe = pickaxe.getCustomEffects();
        Map<Enchantment, Integer> echantementsMap = pickaxe.getEnchantments();

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

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

        for (Map.Entry<Enchantment, Integer> entry : echantementsMap.entrySet()) {
            int level = entry.getValue();
            Enchantment enchantment = entry.getKey();

            itemStack.addUnsafeEnchantment(enchantment, level);

            if (enchantment == null) continue;

            lore.add(ChatColor.translateAlternateColorCodes('&',
                    "&6* " + enchantment.getName() + " [" + level + "/" + 100 + "]"));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }
}
