package fr.rammex.prisonUltimateCool.pickaxe.events;

import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeUtil;
import fr.rammex.prisonUltimateCool.pickaxe.gui.MainInventoryPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PickaxeMenuListener implements Listener {
    PickaxeUtil pickaxeUtil = new PickaxeUtil();
    MainInventoryPickaxe mainInventoryPickaxe = new MainInventoryPickaxe();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(pickaxeUtil.isPlayerHoldingAPickaxe(player)){
            Pickaxe pickaxe = pickaxeUtil.getPlayerPickaxeHolding(player);
            switch (event.getAction()) {
                case Action.RIGHT_CLICK_AIR:
                    event.setCancelled(true);
                    mainInventoryPickaxe.openInventory(pickaxe,player);
                    break;
                case Action.RIGHT_CLICK_BLOCK:
                    event.setCancelled(true);
                    mainInventoryPickaxe.openInventory(pickaxe,player);
                    break;
            }
        }
    }
}
