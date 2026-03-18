package fr.rammex.prisonUltimateCool;

import fr.rammex.prisonUltimateCool.commands.MineCommand;
import fr.rammex.prisonUltimateCool.manager.PickaxeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrisonUltimateCool extends JavaPlugin {
    private static PrisonUltimateCool instance;
    private PickaxeManager pickaxeManager;

    @Override
    public void onEnable() {
        instance = this;
        this.pickaxeManager = new PickaxeManager();

        registerCommands();

    }

    @Override
    public void onDisable() {

    }

    private void registerCommands(){
        this.getCommand("mine").setExecutor(new MineCommand());
    }

    public static PrisonUltimateCool getInstance(){
        return instance;
    }

    public PickaxeManager getPickaxeManager() {
        return pickaxeManager;
    }
}
