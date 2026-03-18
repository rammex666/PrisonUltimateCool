package fr.rammex.prisonUltimateCool.models;

import fr.rammex.prisonUltimateCool.util.ActionEffectUseType;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.BiConsumer;

public class CustomEffect {
    private final String id;
    private final String name;
    private final String description;
    private final Integer levelMax;
    private final double proc;
    private final List<Effect> onEquip;
    private final BiConsumer<ActionEffectUseType, Player> action;

    public CustomEffect(String id, String name, String description, Integer levelMax, double proc, List<Effect> onEquip, BiConsumer<ActionEffectUseType, Player> action){
        this.id = id;
        this.name = name;
        this.description = description;
        this.onEquip = onEquip;
        this.action = action;
        this.levelMax = levelMax;
        this.proc = proc;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Effect> getOnEquip() {
        return onEquip;
    }

    public Integer getLevelMax() {
        return levelMax;
    }

    public double getProc() {
        return proc;
    }

    public void onAction(Player player, ActionEffectUseType actionEffectUseType) {
        if (this.action != null) {
            action.accept(actionEffectUseType, player);
        }
    }
}
