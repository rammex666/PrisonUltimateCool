package fr.rammex.prisonUltimateCool.mine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MineZoneManager {
    private static final Map<String, MineZone> mines = new HashMap<>();
    private static File file;
    private static Gson gson;

    public static void init(File dataFolder) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        file = new File(dataFolder, "mines.json");

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(MineZone.class, new MineZoneSerializer())
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

    public static void addMineZone(MineZone mineZone) {
        mines.put(mineZone.getId(), mineZone);
        save();
    }

    public static MineZone getMineZone(String id) {
        return mines.get(id);
    }

    public static void removeMineZone(String id) {
        mines.remove(id);
        save();
    }

    public static void save() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(mines, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, MineZone>>() {}.getType();
            Map<String, MineZone> data = gson.fromJson(reader, type);

            if (data != null) {
                mines.clear();
                mines.putAll(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, MineZone> getAll() {
        return mines;
    }
}
