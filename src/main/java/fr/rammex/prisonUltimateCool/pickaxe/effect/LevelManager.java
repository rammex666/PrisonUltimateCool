package fr.rammex.prisonUltimateCool.pickaxe.effect;

import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeUtil;
import org.bukkit.entity.Player;

import java.util.Map;

public class LevelManager {
    PickaxeUtil pickaxeUtil = new PickaxeUtil();

    // Check juste si l'enchant peut level up en fonction du cap level et si l'enchant est bien sur la pickaxe du joueur
    public boolean canCustomEffectLevelUP(Pickaxe pickaxe, String customEffectID){
        if(pickaxeUtil.pickaxeHaveEchant(pickaxe,customEffectID)){
            System.out.println("la pickaxe a l'enchant");
            int levelMax = CustomEffectRegistry.get(customEffectID).getLevelMax();
            return pickaxeUtil.getLevelEnchant(pickaxe, customEffectID) < levelMax;
        }
        return false;
    }

    public void levelUpCustomEnchant(Pickaxe pickaxe, String customEffectID){
        System.out.println("level up de "+customEffectID);
        Map<String,Integer> enchants = pickaxe.getCustomEffects();
        enchants.put(customEffectID,enchants.get(customEffectID)+1);
        pickaxe.setCustomEffects(enchants);
    }


    // TODO : après avoir crée une currency ( token ) gérer si le joueur peut en fonction de sa balance

}
