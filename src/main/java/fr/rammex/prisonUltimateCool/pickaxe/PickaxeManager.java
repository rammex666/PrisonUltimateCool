package fr.rammex.prisonUltimateCool.pickaxe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PickaxeManager {

    private static final Map<String, Pickaxe> pickaxes = new HashMap<>();
    private static File file;
    private static Gson gson;

    public static void init(File dataFolder) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        file = new File(dataFolder, "pickaxes.json");

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Pickaxe.class, new PickaxeSerializer())
                .create();

        try {
            if (!file.exists()) {
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        load();
    }

    public static void addPickaxe(Pickaxe pickaxe) {
        pickaxes.put(pickaxe.getId(), pickaxe);
        save();
    }

    public static Pickaxe getPickaxe(String id) {
        return pickaxes.get(id);
    }

    public static void removePickaxe(String id) {
        pickaxes.remove(id);
        save();
    }

    public static void save() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(pickaxes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, Pickaxe>>() {}.getType();
            Map<String, Pickaxe> data = gson.fromJson(reader, type);

            if (data != null) {
                pickaxes.clear();
                pickaxes.putAll(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Pickaxe> getAll() {
        return pickaxes;
    }
}