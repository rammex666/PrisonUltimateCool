package fr.rammex.prisonUltimateCool.economy;

import org.bukkit.entity.Player;

/**
 * API publique d'Infinitya Economy
 * Utilisation : EconomyAPI api = EconomyAPI.get();
 */
public class EconomyAPI {

    private static EconomyAPI instance;
    private final EconomyManager manager;

    private EconomyAPI(EconomyManager manager) {
        this.manager = manager;
    }

    public static void init(EconomyManager manager) {
        instance = new EconomyAPI(manager);
    }

    public static EconomyAPI get() {
        if (instance == null) throw new IllegalStateException("EconomyAPI non initialisée !");
        return instance;
    }

    public double getBalance(Player player, Currency currency) {
        return manager.getBalance(player.getUniqueId(), currency);
    }

    public void deposit(Player player, Currency currency, double amount) {
        manager.deposit(player.getUniqueId(), currency, amount);
    }

    public boolean withdraw(Player player, Currency currency, double amount) {
        return manager.withdraw(player.getUniqueId(), currency, amount);
    }

    public boolean has(Player player, Currency currency, double amount) {
        return manager.has(player.getUniqueId(), currency, amount);
    }

    public void set(Player player, Currency currency, double amount) {
        manager.set(player.getUniqueId(), currency, amount);
    }

    public boolean transfer(Player from, Player to, Currency currency, double amount) {
        return manager.transfer(from.getUniqueId(), to.getUniqueId(), currency, amount);
    }
}
