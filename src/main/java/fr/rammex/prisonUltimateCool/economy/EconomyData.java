package fr.rammex.prisonUltimateCool.economy;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class EconomyData {

    private final UUID uuid;
    private final Map<Currency, Double> balances = new EnumMap<>(Currency.class);

    public EconomyData(UUID uuid) {
        this.uuid = uuid;
        // Initialise toutes les currencies à 0
        for (Currency currency : Currency.values()) {
            balances.put(currency, 0.0);
        }
    }

    public UUID getUuid() { return uuid; }

    public double getBalance(Currency currency) {
        return balances.getOrDefault(currency, 0.0);
    }

    public void setBalance(Currency currency, double amount) {
        balances.put(currency, Math.max(0.0, amount));
    }

    public void deposit(Currency currency, double amount) {
        balances.merge(currency, amount, Double::sum);
    }

    public boolean withdraw(Currency currency, double amount) {
        double current = getBalance(currency);
        if (current < amount) return false;
        balances.put(currency, current - amount);
        return true;
    }

    public boolean has(Currency currency, double amount) {
        return getBalance(currency) >= amount;
    }

    public Map<Currency, Double> getAll() {
        return Collections.unmodifiableMap(balances);
    }
}
