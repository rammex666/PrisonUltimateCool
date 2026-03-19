package fr.rammex.prisonUltimateCool.pickaxe.effect;

import java.util.HashMap;
import java.util.Map;

public class CustomEffectRegistry {

    private static final Map<String, CustomEffect> effects = new HashMap<>();

    public static void register(CustomEffect effect) {
        effects.put(effect.getId(), effect);
    }

    public static CustomEffect get(String id) {
        return effects.get(id);
    }

    public static Map<String, CustomEffect> getAll() {
        return effects;
    }
}