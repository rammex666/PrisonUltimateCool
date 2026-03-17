package fr.rammex.prisonUltimateCool.models;

import fr.rammex.prisonUltimateCool.util.ActionEffectUseType;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.BiConsumer;

public class CustomEffect {
    //TODO : rajouter de la RNG ( taux de proc )
    private final String id;
    private final String name;
    private final String description;
    private final List<Effect> onEquip;
    private final BiConsumer<ActionEffectUseType, Player> action;

    public CustomEffect(String id, String name, String description, List<Effect> onEquip, BiConsumer<ActionEffectUseType, Player> action){
        this.id = id;
        this.name = name;
        this.description = description;
        this.onEquip = onEquip;
        this.action = action;
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

    public void onAction(Player player, ActionEffectUseType actionEffectUseType) {
        if (this.action != null) {
            action.accept(actionEffectUseType, player);
        }
    }
}
