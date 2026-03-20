package fr.rammex.prisonUltimateCool.mine;

import com.google.gson.*;
import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineZoneSerializer implements JsonSerializer<MineZone>, JsonDeserializer<MineZone> {

    @Override
    public JsonElement serialize(MineZone mineZone, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        obj.addProperty("id", mineZone.getId());
        obj.addProperty("name", mineZone.getName());
        obj.addProperty("worldName", mineZone.getWorldName());
        obj.addProperty("resetCooldown", mineZone.getResetCooldown());
        obj.addProperty("x1", mineZone.getX1());
        obj.addProperty("x2", mineZone.getX2());
        obj.addProperty("y1", mineZone.getY1());
        obj.addProperty("y2", mineZone.getY2());


        JsonArray blocksArray = new JsonArray();

        for (Material material : mineZone.getBlocksMine()) {
            JsonObject blockObj = new JsonObject();

            blockObj.addProperty("type", material.toString());

            blocksArray.add(blockObj);
        }

        obj.add("blocksMine", blocksArray);

        return obj;
    }

    @Override
    public MineZone deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();

        String id = obj.get("id").getAsString();
        String name = obj.get("name").getAsString();
        String worldName = obj.get("worldName").getAsString();
        int resetCooldown = obj.get("resetCooldown").getAsInt();
        double x1 = obj.get("x1").getAsDouble();
        double x2 = obj.get("x2").getAsDouble();
        double y1 = obj.get("y1").getAsDouble();
        double y2 = obj.get("y2").getAsDouble();

        List<Material> blocksMine = new ArrayList<>();

        if (obj.has("blocksMine")) {
            JsonArray materialArray = obj.getAsJsonArray("blocksMine");

            for (JsonElement e : materialArray) {
                JsonObject blocksMineOjb = e.getAsJsonObject();

                String key = blocksMineOjb.get("key").getAsString();

                Material material = Material.getMaterial(key);
                if (material != null) {
                    blocksMine.add(material);
                }
            }
        }

        return new MineZone(id, name, worldName, blocksMine, x1, x2, y1, y2, resetCooldown);
    }
}