package fr.rammex.prisonUltimateCool.commands;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import fr.rammex.prisonUltimateCool.mine.MineZone;
import fr.rammex.prisonUltimateCool.mine.MineZoneManager;
import fr.rammex.prisonUltimateCool.mine.MineZoneResetManager;
import fr.rammex.prisonUltimateCool.mine.events.MineZoneCreateListener;
import fr.rammex.prisonUltimateCool.pickaxe.util.ItemBuilder;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
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
        MineZoneCreateListener mineZoneCreateListener = PrisonUltimateCool.getInstance().getMineZoneCreateListener();
        
        Player player = (Player) sender;

        switch(args[0]){
            case "bypass":
                PrisonUltimateCool.getInstance().getMineUtil().bypass = !PrisonUltimateCool.getInstance().getMineUtil().bypass;
                player.sendMessage("bypass : " + PrisonUltimateCool.getInstance().getMineUtil().bypass);
                return true;

            case "give":
                if(args.length < 2){
                    player.sendMessage("&cUsage : /mine give <pickaxe/tool>");
                    return false;
                }
                if(args[1].equalsIgnoreCase("pickaxe")){
                    ItemStack pickaxeItem = itemBuilder.createPickaxe("&7Pioche Du Départ",player);
                    player.getInventory().addItem(pickaxeItem);
                    return true;
                } else if (args[1].equalsIgnoreCase("tool")){
                    ItemStack toolItem = itemBuilder.createMineZoneTool();
                    player.getInventory().addItem(toolItem);
                    return true;
                } else {
                    player.sendMessage("&cUsage : /mine give <pickaxe/tool>");
                    return false;
                }
            case "createzone":
                if(args.length < 3){
                    player.sendMessage("&cUsage : /mine createzone <name> <resetCooldown>");
                    return false;
                } else {
                    String name = args[1];
                    int resetCooldown;
                    try {
                        resetCooldown = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        player.sendMessage("&cLe cooldown de réinitialisation doit être un nombre valide.");
                        return false;
                    }
                    if(mineZoneCreateListener.getFirstPosition(player) == null || mineZoneCreateListener.getSecondPosition(player) == null){
                        player.sendMessage("&cVous devez définir les positions de la mine avec le MineZone Tool avant de créer la zone!");
                        return false;
                    } else {
                        Location pos1 = mineZoneCreateListener.getFirstPosition(player);
                        Location pos2 = mineZoneCreateListener.getSecondPosition(player);
                        List<Material> blocks = List.of(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE);

                        MineZone mineZone = new MineZone(
                            name, name,
                            pos1.getWorld().getName(),
                            blocks,
                            pos1.getX(), pos2.getX(),  // x1, x2
                            pos1.getY(), pos2.getY(),  // y1, y2
                            pos1.getZ(), pos2.getZ(),  // z1, z2
                            resetCooldown
                        );
                        player.sendMessage("&aMine créée avec succès!");
                        MineZoneManager.addMineZone(mineZone);
                        MineZoneResetManager.startTask(mineZone);

                    }
                    return false;
                }
        }



        return false;
    }
}
