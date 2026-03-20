package fr.rammex.prisonUltimateCool.mine;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MineZoneResetManager {

    private static final Map<String, BukkitTask> tasks = new HashMap<>();
    private static final Random RANDOM = new Random();
    private static JavaPlugin plugin;

    public static void init(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
    }

    public static void startAll() {
        for (MineZone mine : MineZoneManager.getAll().values()) {
            startTask(mine);
        }
    }

    public static void startTask(MineZone mine) {
        stopTask(mine.getId());

        long intervalTicks = mine.getResetCooldown() * 20L;

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            resetMine(mine);
        }, intervalTicks, intervalTicks);

        tasks.put(mine.getId(), task);
    }

    
    public static void stopTask(String mineId) {
        BukkitTask task = tasks.remove(mineId);
        if (task != null) task.cancel();
    }

    public static void forceReset(String mineId) {
        MineZone mine = MineZoneManager.getMineZone(mineId);
        if (mine != null) resetMine(mine);
    }

    public static void shutdown() {
        tasks.values().forEach(BukkitTask::cancel);
        tasks.clear();
    }

    private static void resetMine(MineZone mine) {
        World world = Bukkit.getWorld(mine.getWorldName());
        if (world == null) {
            plugin.getLogger().warning("[MineReset] Monde introuvable pour la mine : " + mine.getId());
            return;
        }

        List<Material> blocks = mine.getBlocksMine();
        if (blocks == null || blocks.isEmpty()) {
            plugin.getLogger().warning("[MineReset] Aucun bloc défini pour la mine : " + mine.getId());
            return;
        }

        int minX = (int) Math.min(mine.getX1(), mine.getX2());
        int maxX = (int) Math.max(mine.getX1(), mine.getX2());
        int minY = (int) Math.min(mine.getY1(), mine.getY2());
        int maxY = (int) Math.max(mine.getY1(), mine.getY2());
        int minZ = (int) Math.min(mine.getZ1(), mine.getZ2());
        int maxZ = (int) Math.max(mine.getZ1(), mine.getZ2());

        int totalBlocks = (maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    Material chosen = blocks.get(RANDOM.nextInt(blocks.size()));
                    block.setType(chosen, false);
                }
            }
        }

        plugin.getLogger().info("[MineReset] Mine \"" + mine.getName() + "\" régénérée (" + totalBlocks + " blocs).");
    }
}