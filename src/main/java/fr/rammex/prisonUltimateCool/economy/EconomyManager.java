package fr.rammex.prisonUltimateCool.economy;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EconomyManager {

    private final PrisonUltimateCool plugin;
    private final EconomyRepository repository;
    private final Map<UUID, EconomyData> cache = new ConcurrentHashMap<>();

    public EconomyManager(PrisonUltimateCool plugin, EconomyRepository repository) {
        this.plugin = plugin;
        this.repository = repository;
    }

    public void load(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                EconomyData data = repository.load(uuid);
                cache.put(uuid, data);
            } catch (SQLException e) {
                plugin.getLogger().severe("Erreur chargement economy " + uuid + ": " + e.getMessage());
            }
        });
    }

    public void unload(UUID uuid) {
        EconomyData data = cache.remove(uuid);
        if (data != null) saveSync(data);
    }

    public void saveAll() {
        cache.values().forEach(this::saveSync);
    }

    // --- Méthodes métier ---

    public double getBalance(UUID uuid, Currency currency) {
        return getData(uuid).getBalance(currency);
    }

    public void deposit(UUID uuid, Currency currency, double amount) {
        getData(uuid).deposit(currency, amount);
        saveAsync(uuid);
    }

    public boolean withdraw(UUID uuid, Currency currency, double amount) {
        boolean success = getData(uuid).withdraw(currency, amount);
        if (success) saveAsync(uuid);
        return success;
    }

    public boolean has(UUID uuid, Currency currency, double amount) {
        return getData(uuid).has(currency, amount);
    }

    public void set(UUID uuid, Currency currency, double amount) {
        getData(uuid).setBalance(currency, amount);
        saveAsync(uuid);
    }

    // --- Transfert entre joueurs ---

    public boolean transfer(UUID from, UUID to, Currency currency, double amount) {
        if (!has(from, currency, amount)) return false;
        withdraw(from, currency, amount);
        deposit(to, currency, amount);
        return true;
    }

    // --- Interne ---

    private EconomyData getData(UUID uuid) {
        return cache.computeIfAbsent(uuid, id -> {
            try {
                return repository.load(id);
            } catch (SQLException e) {
                plugin.getLogger().severe("Erreur getData " + id + ": " + e.getMessage());
                return new EconomyData(id);
            }
        });
    }

    private void saveAsync(UUID uuid) {
        EconomyData data = cache.get(uuid);
        if (data == null) return;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> saveSync(data));
    }

    private void saveSync(EconomyData data) {
        try {
            repository.save(data);
        } catch (SQLException e) {
            plugin.getLogger().severe("Erreur sauvegarde " + data.getUuid() + ": " + e.getMessage());
        }
    }
}
