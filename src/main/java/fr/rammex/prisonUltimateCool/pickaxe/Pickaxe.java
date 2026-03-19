package fr.rammex.prisonUltimateCool.pickaxe;

import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.Map;

public class Pickaxe {
    private String id;
    private String owner; // attend un UUID d'un player
    private final String dateCreation;
    private List<Enchantment> enchantments;
    private Map<String, Integer> customEffects;

    public Pickaxe(String id, String owner, String dateCreation, List<Enchantment> enchantments, Map<String, Integer> customEffects){
        this.id = id;
        this.owner = owner;
        this.dateCreation = dateCreation;
        this.enchantments = enchantments;
        this.customEffects = customEffects;
    }

    public String getId() {
        return id;
    }

    public Map<String,Integer> getCustomEffects() {
        return customEffects;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEnchantments(List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public void setCustomEffects(Map<String,Integer> customEffects) {
        this.customEffects = customEffects;
    }
}
