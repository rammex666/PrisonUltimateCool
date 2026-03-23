package fr.rammex.prisonUltimateCool.pickaxe.effect;

import fr.rammex.prisonUltimateCool.pickaxe.Pickaxe;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeUtil;

import java.util.Map;

public class LevelManager {
    PickaxeUtil pickaxeUtil = new PickaxeUtil();

    public boolean canCustomEffectLevelUP(Pickaxe pickaxe, String customEffectID){
        if(pickaxeUtil.pickaxeHaveEchant(pickaxe,customEffectID)){
            int levelMax = CustomEffectRegistry.get(customEffectID).getLevelMax();
            return pickaxeUtil.getLevelEnchant(pickaxe, customEffectID) < levelMax;
        }
        return false;
    }

    public void levelUpCustomEnchant(Pickaxe pickaxe, String customEffectID){
        Map<String,Integer> enchants = pickaxe.getCustomEffects();
        enchants.put(customEffectID,enchants.get(customEffectID)+1);
        pickaxe.setCustomEffects(enchants);
    }


    // TODO : après avoir crée une currency ( token ) gérer si le joueur peut en fonction de sa balance

}
