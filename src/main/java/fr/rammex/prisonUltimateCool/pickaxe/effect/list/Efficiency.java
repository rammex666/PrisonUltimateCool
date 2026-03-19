package fr.rammex.prisonUltimateCool.pickaxe.effect.list;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeUtil;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Efficiency {
    private static PickaxeUtil pickaxeUtil = new PickaxeUtil();
    public static CustomEffect efficiencyEffect(){

        // TODO : check pourquoi ca marche pas ;(

        CustomEffect efficiencyEffect = new CustomEffect(
                "EFFICIENCY",
                "Efficacité Custom",
                "Augmente la vitesse de minage en fonction du niveau et du type de pioche",
                100,
                1.0,
                List.of(),
                (player, block) -> {

                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item == null || item.getType().isAir()) return;

                    Pickaxe pickaxe = pickaxeUtil.getPlayerPickaxeHolding(player);
                    if (pickaxe == null) return;

                    int level = pickaxe.getCustomEffects().getOrDefault("EFFICIENCY", 1);
                    level = Math.min(level, 10); // levelMax

                    double baseSpeed;
                    switch (item.getType()) {
                        case WOODEN_PICKAXE -> baseSpeed = 2.0;
                        case STONE_PICKAXE -> baseSpeed = 4.0;
                        case IRON_PICKAXE -> baseSpeed = 6.0;
                        case DIAMOND_PICKAXE -> baseSpeed = 8.0;
                        case NETHERITE_PICKAXE -> baseSpeed = 9.0;
                        default -> baseSpeed = 1.0;
                    }

                    double speed = baseSpeed * (1 + 0.3 * level);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (block.getType() != Material.AIR) {
                                block.breakNaturally(item);
                            }
                        }
                    }.runTaskLater(PrisonUltimateCool.getInstance(), Math.max(1, (int)(20 / speed)));
                }
        );
        return efficiencyEffect;
    }
}
