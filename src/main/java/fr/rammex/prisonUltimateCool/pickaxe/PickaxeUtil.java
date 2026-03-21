package fr.rammex.prisonUltimateCool.pickaxe;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class PickaxeUtil {

    @SuppressWarnings("deprecation")
    public boolean isPlayerHoldingAPickaxe(Player player){
        ItemStack itemInHand = player.getItemInHand();

        ItemMeta meta = itemInHand.getItemMeta();
        NamespacedKey key = new NamespacedKey(PrisonUltimateCool.getInstance(), "pickaxe_id");

        return meta.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    public boolean pickaxeHaveEchant(Pickaxe pickaxe, String enchantID){
        for (Map.Entry<String, Integer> entry : pickaxe.getCustomEffects().entrySet()) {
            if(entry.getKey().equals(enchantID)){
                return true;
            }
        }

        return false;
    }

    public Integer getLevelEnchant(Pickaxe pickaxe, String enchantID){
        return pickaxe.getCustomEffects().get(enchantID);
    }

    public ItemStack getPlayerPickaxeItemStack(Player player){
        return player.getInventory().getItemInMainHand();
    }

    public Pickaxe getPlayerPickaxeHolding(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(PrisonUltimateCool.getInstance(), "pickaxe_id");

        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            return null;
        }

        String id = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (id == null) {
            return null;
        }

        Pickaxe pickaxe = PickaxeManager.getPickaxe(id);

        if (pickaxe != null && pickaxe.getOwner().equals(player.getUniqueId().toString())) {
            return pickaxe;
        }

        return null;
    }


}
