package fr.rammex.prisonUltimateCool.pickaxe.effect.list;

public enum CustomEffectIDS {
    Explosion("EXPLOSION");

    private final String id;

    CustomEffectIDS(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
