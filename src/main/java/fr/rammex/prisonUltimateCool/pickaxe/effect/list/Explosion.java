package fr.rammex.prisonUltimateCool.pickaxe.effect.list;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffect;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Armadillo;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

public class Explosion {
    public static CustomEffect explosionEffect() {

        BiConsumer<Player, Block> explosionAction = (player, block) -> {
            //TODO : ajouter les blocks dans l'inventaire du joueur
            if (block == null || block.getType() == Material.AIR) return;

            int radius = 1; // rayon de l'explosion
            spawnMeteor(block.getLocation(), PrisonUltimateCool.getInstance());
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


    //TODO : ça marche super bien plus qu'a crée l'effet tatourite
    public static void spawnMeteor(Location target, Plugin plugin) {
        World world = target.getWorld();
        Location spawnLoc = target.clone().add(0, 40, 0);

        Armadillo armadillo = (Armadillo) world.spawn(spawnLoc, Armadillo.class);
        AttributeInstance size = armadillo.getAttribute(Attribute.SCALE);
        if(size != null) {
            size.setBaseValue(3.0);
        }

        armadillo.setAI(true);
        armadillo.setInvulnerable(true);
        armadillo.setSilent(true);
        armadillo.setGravity(true);
        armadillo.rollUp();

        new BukkitRunnable() {
            @Override
            public void run() {
                armadillo.setVelocity(new Vector(0, -2, 0));
            }
        }.runTaskLater(plugin, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!armadillo.isValid() || armadillo.isDead()) {
                    cancel();
                    return;
                }

                Vector current = armadillo.getVelocity();
                if (current.getY() > -2.0) {
                    armadillo.setVelocity(current.setY(Math.max(current.getY() - 0.1, -2.0)));
                }

                world.spawnParticle(Particle.FLAME, armadillo.getLocation(), 5, 0.2, 0.2, 0.2, 0.01);
                world.spawnParticle(Particle.SMOKE, armadillo.getLocation(), 3, 0.1, 0.1, 0.1, 0.01);

                world.playSound(armadillo.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.3f, 1.5f);

                if (armadillo.isOnGround()) {
                    world.createExplosion(armadillo.getLocation(), 4.0f, false, false);
                    world.spawnParticle(Particle.EXPLOSION, armadillo.getLocation(), 1);
                    armadillo.remove();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 2L, 1L);
    }
}
