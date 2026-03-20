package fr.rammex.prisonUltimateCool.mine.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class MineZoneCreateListener implements Listener {

    //création d'une mine via un tool ( un stick ) clique gauche première pose, clique droit deuxième pose
    private Map<Player, Location> firstPositions = new HashMap<>();
    private Map<Player, Location> secondPositions = new HashMap<>();
    
    @SuppressWarnings({ "deprecation", "incomplete-switch" })
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item != null && item.getType().equals(Material.STICK)){
            if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&6MineZone Tool"))){
                event.setCancelled(true);
                switch (event.getAction()) {
                    case Action.LEFT_CLICK_BLOCK:
                        player.sendMessage("1ère position définit");
                        firstPositions.put(player, event.getClickedBlock().getLocation());
                        break;
                    case Action.RIGHT_CLICK_BLOCK:
                        player.sendMessage("2ème position définit");
                        secondPositions.put(player, event.getClickedBlock().getLocation());
                        break;
                }
            }
        }

    }

    public Location getFirstPosition(Player player){
        return firstPositions.get(player);
    }

    public Location getSecondPosition(Player player){
        return secondPositions.get(player);
    }

}
