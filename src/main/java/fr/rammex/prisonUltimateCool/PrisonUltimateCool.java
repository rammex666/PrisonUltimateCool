package fr.rammex.prisonUltimateCool;

import fr.rammex.prisonUltimateCool.commands.MineCommand;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffectRegistry;
import fr.rammex.prisonUltimateCool.pickaxe.effect.list.Efficiency;
import fr.rammex.prisonUltimateCool.pickaxe.effect.list.Explosion;
import fr.rammex.prisonUltimateCool.pickaxe.events.EffectMineEvent;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrisonUltimateCool extends JavaPlugin {

    //TODO : remove tout les sys out

    private static PrisonUltimateCool instance;
    private PickaxeManager pickaxeManager;

    @Override
    public void onEnable() {
        instance = this;

        this.pickaxeManager = new PickaxeManager();
        PickaxeManager.init(getDataFolder());

        registerCommands();
        registerEvents();
        registerEffects();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            PickaxeManager.save();
            System.out.println("save des pioches");
        }, 20 * 300, 20 * 300); // 5 minutes et ça save

    }

    @Override
    public void onDisable() {
        PickaxeManager.save();
    }

    private void registerCommands(){
        this.getCommand("mine").setExecutor(new MineCommand());
    }

    private void registerEvents(){ getServer().getPluginManager().registerEvents(new EffectMineEvent(), this);}

    private void registerEffects(){
        CustomEffectRegistry.register(Explosion.explosionEffect());
        CustomEffectRegistry.register(Efficiency.efficiencyEffect());
    }

    public static PrisonUltimateCool getInstance(){
        return instance;
    }

    public PickaxeManager getPickaxeManager() {
        return pickaxeManager;
    }
}
