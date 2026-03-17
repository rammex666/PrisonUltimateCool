package fr.rammex.prisonUltimateCool.manager;

import fr.rammex.prisonUltimateCool.models.Pickaxe;

import java.util.Map;

public class PickaxeManager {
    private static Map<Pickaxe, String> pickaxeMap;

    public static void addPickaxe(Pickaxe pickaxe,String playerUUID){
        pickaxeMap.put(pickaxe,playerUUID);
    }

    public static void changePlayerHolder(Pickaxe pickaxe,String playerUUID){
        if(pickaxeMap.containsKey(pickaxe)){
            pickaxeMap.remove(pickaxe);
            pickaxeMap.put(pickaxe,playerUUID);
        }
    }

    public static void changePickaxeOwner(Pickaxe pickaxe,String playerUUID){
        pickaxe.setOwner(playerUUID);
    }
}
