package fr.rammex.prisonUltimateCool.mine.util;

import fr.rammex.prisonUltimateCool.mine.MineZone;
import fr.rammex.prisonUltimateCool.mine.MineZoneManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.Map;

public class MineUtil {

    public boolean isPlayerInAMine(Player player){

        Map<String, MineZone> mineZones = MineZoneManager.getAll();

        for(MineZone mineZone : mineZones.values()){
            double x1 = mineZone.getX1();
            double x2 = mineZone.getX2();
            double y1 = mineZone.getY1();
            double y2 = mineZone.getY2();
            double z1 = mineZone.getZ1();
            double z2 = mineZone.getZ2();
            World world = Bukkit.getWorld(mineZone.getWorldName());

            BoundingBox box = BoundingBox.of(
                    new Location(world, x1, y1, z1),
                    new Location(world, x2, y2, z2)
            );

            return box.contains(player.getLocation().toVector());
        }


        return false;
    }
}
