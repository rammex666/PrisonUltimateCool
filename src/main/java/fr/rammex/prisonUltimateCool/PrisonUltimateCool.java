package fr.rammex.prisonUltimateCool;

import fr.rammex.prisonUltimateCool.commands.MineCommand;
import fr.rammex.prisonUltimateCool.economy.EconomyAPI;
import fr.rammex.prisonUltimateCool.economy.EconomyManager;
import fr.rammex.prisonUltimateCool.economy.EconomyRepository;
import fr.rammex.prisonUltimateCool.mine.MineZoneManager;
import fr.rammex.prisonUltimateCool.mine.MineZoneResetManager;
import fr.rammex.prisonUltimateCool.mine.events.MineZoneCreateListener;
import fr.rammex.prisonUltimateCool.mine.util.MineUtil;
import fr.rammex.prisonUltimateCool.pickaxe.effect.CustomEffectRegistry;
import fr.rammex.prisonUltimateCool.pickaxe.effect.list.Explosion;
import fr.rammex.prisonUltimateCool.pickaxe.events.EffectMineEvent;
import fr.rammex.prisonUltimateCool.pickaxe.PickaxeManager;
import fr.rammex.prisonUltimateCool.pickaxe.events.PickaxeMenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class PrisonUltimateCool extends JavaPlugin {

    private static PrisonUltimateCool instance;
    private PickaxeManager pickaxeManager;
    private MineZoneManager mineZoneManager;
    private MineUtil mineUtil;
    private MineZoneCreateListener mineZoneCreateListener;

    @Override
    public void onEnable() {
        instance = this;

        this.mineUtil = new MineUtil();

        this.pickaxeManager = new PickaxeManager();
        PickaxeManager.init(getDataFolder());

        this.mineZoneManager = new MineZoneManager();
        MineZoneManager.init(getDataFolder());

        this.mineZoneCreateListener = new MineZoneCreateListener();

        MineZoneResetManager.init(instance);
        MineZoneResetManager.startAll();


        registerCommands();
        registerEvents();
        registerEffects();

        try {
            EconomyRepository repository = new EconomyRepository(this);
            EconomyManager economyManager = new EconomyManager(this, repository);

            // Init API statique
            EconomyAPI.init(economyManager);

        } catch (SQLException e) {
            getLogger().severe("Impossible d'initialiser l'economy : " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            PickaxeManager.save();
            MineZoneManager.save();
            Bukkit.getLogger().info("[PrisonUltimateCool] Save des pioches et mines");
        }, 20 * 300, 20 * 300); // 5 minutes et ça save

    }

    @Override
    public void onDisable() {
        PickaxeManager.save();
        MineZoneManager.save();
        MineZoneResetManager.shutdown();
    }

    private void registerCommands(){
        this.getCommand("mine").setExecutor(new MineCommand());
    }

    private void registerEvents(){ 
        getServer().getPluginManager().registerEvents(new EffectMineEvent(), this);
        getServer().getPluginManager().registerEvents(this.mineZoneCreateListener, this);
        getServer().getPluginManager().registerEvents(new PickaxeMenuListener(), this);
    }

    private void registerEffects(){
        CustomEffectRegistry.register(Explosion.explosionEffect());
    }

    public static PrisonUltimateCool getInstance(){
        return instance;
    }

    public PickaxeManager getPickaxeManager() {
        return pickaxeManager;
    }
    public MineZoneManager getMineZoneManager(){
        return mineZoneManager;
    }

    public MineUtil getMineUtil() {
        return mineUtil;
    }

    public MineZoneCreateListener getMineZoneCreateListener() {
        return mineZoneCreateListener;
    }
}
