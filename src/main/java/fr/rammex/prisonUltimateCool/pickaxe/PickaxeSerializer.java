package fr.rammex.prisonUltimateCool.pickaxe;

import com.google.gson.*;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Type;
import java.util.*;

public class PickaxeSerializer implements JsonSerializer<Pickaxe>, JsonDeserializer<Pickaxe> {

    @Override
    public JsonElement serialize(Pickaxe pickaxe, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        obj.addProperty("id", pickaxe.getId());
        obj.addProperty("owner", pickaxe.getOwner());
        obj.addProperty("dateCreation", pickaxe.getDateCreation());

        JsonObject enchantObjt = new JsonObject();
        for (Map.Entry<Enchantment, Integer> entry : pickaxe.getEnchantments().entrySet()) {
            enchantObjt.addProperty(entry.getKey().getKey().getKey(), entry.getValue());
        }
        obj.add("enchantments", enchantObjt);

        JsonObject effectsObj = new JsonObject();
        for (Map.Entry<String, Integer> entry : pickaxe.getCustomEffects().entrySet()) {
            effectsObj.addProperty(entry.getKey(), entry.getValue());
        }
        obj.add("customEffects", effectsObj);

        return obj;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Pickaxe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();

        String id = obj.get("id").getAsString();
        String owner = obj.get("owner").getAsString();
        String date = obj.get("dateCreation").getAsString();

        Map<Enchantment, Integer> enchantments = new HashMap<>();

        if (obj.has("enchantments")) {
            JsonObject enchantObj = obj.getAsJsonObject("enchantments");

            for (String key : enchantObj.keySet()) {
                int level = enchantObj.get(key).getAsInt();
                enchantments.put(Enchantment.getByKey(NamespacedKey.minecraft(key)), level);
            }
        }

        Map<String, Integer> effects = new HashMap<>();

        if (obj.has("customEffects")) {
            JsonObject effectsObj = obj.getAsJsonObject("customEffects");

            for (String key : effectsObj.keySet()) {
                int level = effectsObj.get(key).getAsInt();
                effects.put(key, level);
            }
        }

        return new fr.rammex.prisonUltimateCool.pickaxe.Pickaxe(id, owner, date, enchantments, effects);
    }
}