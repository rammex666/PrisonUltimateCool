package fr.rammex.prisonUltimateCool.commands;

import fr.rammex.prisonUltimateCool.pickaxe.util.ItemBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MineCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String @NotNull [] args) {
        ItemBuilder itemBuilder = new ItemBuilder();
        Player player = (Player) sender;

        switch(args[0]){
            case "give":
                //TODO : sup cette commande et faire que ca donne la pioche de départ quand le joueur rejoint le serveur pour la première fois
                ItemStack pickaxeItem = itemBuilder.createPickaxe("&7Pioche Du Départ",player);
                player.getInventory().addItem(pickaxeItem);
                break;
            case "tp":
                
                break;
        }



        return false;
    }
}
