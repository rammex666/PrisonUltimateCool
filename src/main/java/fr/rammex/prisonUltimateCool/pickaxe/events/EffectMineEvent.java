package fr.rammex.prisonUltimateCool.pickaxe.events;

import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffect;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffectRegistry;
import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import fr.rammex.prisonUltimateCool.mine.util.MineUtil;
import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeUtil;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

public class EffectMineEvent implements Listener {
    private final PickaxeUtil pickaxeUtil = new PickaxeUtil();
    private final MineUtil mineUtil = PrisonUltimateCool.getInstance().getMineUtil();

    @EventHandler
    public void onMineEvent(BlockBreakEvent event){
        Player player = event.getPlayer();

        if(!mineUtil.isPlayerInAMine(player) && !mineUtil.bypass){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cVous devez être dans une mine pour miner!"));
            event.setCancelled(true);
        } 

        if(pickaxeUtil.isPlayerHoldingAPickaxe(player)){
            Pickaxe playerPickaxe = pickaxeUtil.getPlayerPickaxeHolding(player);
            handleProc(playerPickaxe, event.getBlock(), player);
        }

    }

    public static void handleProc(Pickaxe pickaxe, Block origin, Player player) {

        for (Map.Entry<String, Integer> entry : pickaxe.getCustomEffects().entrySet()) {

            CustomEffect effect = CustomEffectRegistry.get(entry.getKey());
            if (effect == null) continue;

            int level = entry.getValue();

            double ratio = (double) level / effect.getLevelMax();
            double finalChance = 1 - Math.pow(1 - effect.getProc(), ratio * effect.getLevelMax());

            if (Math.random() <= finalChance) {
                effect.onAction(player, origin);
            }
        }
    }
}
