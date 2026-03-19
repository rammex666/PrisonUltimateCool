package fr.rammex.prisonUltimateCool.commands;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import fr.rammex.prisonUltimateCool.pickaxe.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class MineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String @NotNull [] args) {
        ItemBuilder itemBuilder = new ItemBuilder();
        Player player = (Player) sender;

        if(args[0].equals("give")){
            ItemStack pickaxe = itemBuilder.createPickaxe("&7Pioche Du Départ",player);
            player.getInventory().addItem(pickaxe);

        } else if (args[0].equals("id")){
            ItemStack itemInHand = player.getItemInHand();

            ItemMeta meta = itemInHand.getItemMeta();
            NamespacedKey key = new NamespacedKey(PrisonUltimateCool.getInstance(), "id");

            if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                Bukkit.getLogger().info("Valeur: " + value);
            }
        }



        return false;
    }
}
