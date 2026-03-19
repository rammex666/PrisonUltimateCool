package fr.rammex.prisonUltimateCool.pickaxe.effect.list;

import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffect;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

public class Explosion {
    public static CustomEffect explosionEffect() {

        BiConsumer<Player, Block> explosionAction = (player, block) -> {
            //TODO : ajouter les blocks dans l'inventaire du joueur
            if (block == null || block.getType() == Material.AIR) return;

            int radius = 1; // rayon de l'explosion

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block target = block.getLocation().clone().add(x, y, z).getBlock();
                        int random = ThreadLocalRandom.current().nextInt(1, 3); // faire une explosion avec plus d'effet
                        if (target.getType() != Material.AIR && random == 1) {
                            target.setType(Material.AIR); // très rapide pour 10k blocks/sec
                        }
                    }
                }
            }

            block.getWorld().playEffect(block.getLocation(), Effect.EXTINGUISH, 1);
        };

        return new CustomEffect(
                "EXPLOSION",            // id
                "Explosion",            // nom
                "Fait exploser les blocs autour", // description
                1,                    // levelMax
                1.00,                   // chance de proc (5%)
                List.of(),              // effets onEquip si besoin
                explosionAction         // action BiConsumer
        );
    }
}
