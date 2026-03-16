package fr.rammex.prisonUltimateCool;

import fr.rammex.prisonUltimateCool.commands.MineCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrisonUltimateCool extends JavaPlugin {
    private static PrisonUltimateCool instance;

    @Override
    public void onEnable() {
        instance = this;

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
}
