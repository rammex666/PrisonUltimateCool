package fr.rammex.prisonUltimateCool.pickaxe;

import org.bukkit.enchantments.Enchantment;

import java.util.Map;

public class Pickaxe {
    private String id;
    private String owner; // attend un UUID d'un player
    private final String dateCreation;
    private Map<Enchantment, Integer> enchantments;
    private Map<String, Integer> customEffects;

    public Pickaxe(String id, String owner, String dateCreation, Map<Enchantment, Integer> enchantments, Map<String, Integer> customEffects){
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

    public Map<Enchantment, Integer> getEnchantments() {
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

    public void setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public void setCustomEffects(Map<String,Integer> customEffects) {
        this.customEffects = customEffects;
    }
}
