package fr.rammex.prisonUltimateCool.pickaxe.events;

import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffect;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffectRegistry;
import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

public class EffectMineEvent implements Listener {
    PickaxeUtil pickaxeUtil = new PickaxeUtil();

    @EventHandler
    public void onMineEvent(BlockBreakEvent event){
        // TODO : handle les cas où le joueur ne tien pas de pickaxe 
        System.out.println("test");
        Player player = event.getPlayer();
        if(pickaxeUtil.isPlayerHoldingAPickaxe(player)){
            System.out.println("player tien une pickaxe");
            Pickaxe playerPickaxe = pickaxeUtil.getPlayerPickaxeHolding(player);
            handleProc(playerPickaxe, event.getBlock(), player);
        }

    }

    public static void handleProc(Pickaxe pickaxe, Block origin, Player player) {

        for (Map.Entry<String, Integer> entry : pickaxe.getCustomEffects().entrySet()) {
            System.out.println(entry.getKey());

            CustomEffect effect = CustomEffectRegistry.get(entry.getKey());
            if (effect == null) continue;

            int level = entry.getValue();

            double ratio = (double) level / effect.getLevelMax();
            double finalChance = 1 - Math.pow(1 - effect.getProc(), ratio * effect.getLevelMax());

            if (Math.random() <= finalChance) {
                System.out.println("proc");
                effect.onAction(player, origin);
            }
        }
    }
}
