package fr.rammex.prisonUltimateCool.pickaxe.gui;

import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeUtil;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffect;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffectRegistry;
import fr.rammex.prisonUltimateCool.pickaxe.effect.list.CustomEffectIDS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class MainInventoryPickaxe {
    PickaxeUtil pickaxeUtil = new PickaxeUtil();


    public void openInventory(Pickaxe pickaxe, Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, ChatColor.translateAlternateColorCodes('&',"&dMenu Pioche"));

        inventory.setItem(13,createExplosionEffectItemStack(pickaxe));

        player.openInventory(inventory);
    }

    private ItemStack createExplosionEffectItemStack(Pickaxe pickaxe){
        ItemStack item = new ItemStack(Material.TNT,1);
        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        String effectID = CustomEffectIDS.Explosion.getId();
        int actualLevel = pickaxeUtil.getLevelEnchant(pickaxe, effectID);
        CustomEffect customEffect = CustomEffectRegistry.get(effectID);

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&cExplosion"));
        lore.add(ChatColor.translateAlternateColorCodes('&',customEffect.getDescription()));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&',"&d---------------------------------"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&d"+actualLevel+"/"+customEffect.getLevelMax()));

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }



}
