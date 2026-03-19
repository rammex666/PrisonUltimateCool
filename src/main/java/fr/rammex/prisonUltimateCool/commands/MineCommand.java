package fr.rammex.prisonUltimateCool.commands;

import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeUtil;
import fr.rammex.prisonUltimateCool.pickaxe.effect.LevelManager;
import fr.rammex.prisonUltimateCool.pickaxe.util.ItemBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MineCommand implements CommandExecutor {
    private final PickaxeUtil pickaxeUtil = new PickaxeUtil();
    private final LevelManager levelManager = new LevelManager();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String @NotNull [] args) {
        ItemBuilder itemBuilder = new ItemBuilder();
        Player player = (Player) sender;

        switch(args[0]){
            case "give":
                ItemStack pickaxeItem = itemBuilder.createPickaxe("&7Pioche Du Départ",player);
                player.getInventory().addItem(pickaxeItem);
            case "levelup":
                Pickaxe playerPickaxe = pickaxeUtil.getPlayerPickaxeHolding(player);
                ItemStack pickaxe = pickaxeUtil.getPlayerPickaxeItemStack(player);
                if(levelManager.canCustomEffectLevelUP(playerPickaxe,args[1])){
                    System.out.println("ca peut level up!");
                    levelManager.levelUpCustomEnchant(playerPickaxe,args[1]);
                    itemBuilder.UpdateLore(playerPickaxe,pickaxe,player);
                }
        }



        return false;
    }
}
