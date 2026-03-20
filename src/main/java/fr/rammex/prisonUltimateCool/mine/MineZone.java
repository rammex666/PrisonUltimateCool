package fr.rammex.prisonUltimateCool.mine;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

public class MineZone {
    //TODO : ajouter le système de rank require pour aller dans la zone quand le sys de rank seras fait
    private final String id;
    private final String name;
    private final String worldName;
    private final List<Material> blocksMine;
    private final double x1;
    private final double x2;
    private final double y1;
    private final double y2;
    private final int resetCooldown;

    public MineZone(String id, String name, String worldName, List<Material> blocksMine, double x1, double x2, double y1, double y2, int resetCooldown){
        this.id = id;
        this.name = name;
        this.worldName = worldName;
        this.blocksMine = blocksMine;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.resetCooldown = resetCooldown;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWorldName() {
        return worldName;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public int getResetCooldown() {
        return resetCooldown;
    }

    public List<Material> getBlocksMine() {
        return blocksMine;
    }
}
