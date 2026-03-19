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

        JsonArray enchantsArray = new JsonArray();
        for (Enchantment ench : pickaxe.getEnchantments()) {
            JsonObject enchObj = new JsonObject();

            enchObj.addProperty("key", ench.getKey().getKey());

            enchObj.addProperty("level", 1);

            enchantsArray.add(enchObj);
        }
        obj.add("enchantments", enchantsArray);

        JsonObject effectsObj = new JsonObject();
        for (Map.Entry<String, Integer> entry : pickaxe.getCustomEffects().entrySet()) {
            effectsObj.addProperty(entry.getKey(), entry.getValue());
        }
        obj.add("customEffects", effectsObj);

        return obj;
    }

    @Override
    public Pickaxe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();

        String id = obj.get("id").getAsString();
        String owner = obj.get("owner").getAsString();
        String date = obj.get("dateCreation").getAsString();

        List<Enchantment> enchantments = new ArrayList<>();

        if (obj.has("enchantments")) {
            JsonArray enchantsArray = obj.getAsJsonArray("enchantments");

            for (JsonElement e : enchantsArray) {
                JsonObject enchObj = e.getAsJsonObject();

                String key = enchObj.get("key").getAsString();

                Enchantment ench = Enchantment.getByKey(NamespacedKey.minecraft(key));
                if (ench != null) {
                    enchantments.add(ench);
                }
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