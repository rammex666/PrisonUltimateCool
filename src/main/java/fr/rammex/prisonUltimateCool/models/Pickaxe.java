package fr.rammex.prisonUltimateCool.models;

import org.bukkit.enchantments.Enchantment;

import java.util.List;

public class Pickaxe {
    private final String id;
    private String owner; // attend un UUID d'un player
    private final String dateCreation;
    private List<Enchantment> enchantments;
    private List<CustomEffect> customEffects;

    public Pickaxe(String id, String owner, String dateCreation, List<Enchantment> enchantments, List<CustomEffect> customEffects){
        this.id = id;
        this.owner = owner;
        this.dateCreation = dateCreation;
        this.enchantments = enchantments;
        this.customEffects = customEffects;
    }

    public String getId() {
        return id;
    }

    public List<CustomEffect> getCustomEffects() {
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

    public void setEnchantments(List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public void setCustomEffects(List<CustomEffect> customEffects) {
        this.customEffects = customEffects;
    }
}
