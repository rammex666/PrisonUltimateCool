package fr.rammex.prisonUltimateCool.economy;

import fr.rammex.prisonUltimateCool.PrisonUltimateCool;

import java.io.File;
import java.sql.*;
import java.util.UUID;

public class EconomyRepository {

    private final Connection connection;

    public EconomyRepository(PrisonUltimateCool plugin) throws SQLException {
        File dbFile = new File(plugin.getDataFolder(), "economy.db");
        plugin.getDataFolder().mkdirs();
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
        createTable();
    }

    private void createTable() throws SQLException {
        // Construction dynamique des colonnes depuis l'enum
        StringBuilder columns = new StringBuilder();
        for (Currency currency : Currency.values()) {
            columns.append(", ").append(currency.getColumnName()).append(" REAL DEFAULT 0.0");
        }

        String sql = "CREATE TABLE IF NOT EXISTS economy (uuid TEXT PRIMARY KEY" + columns + ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public EconomyData load(UUID uuid) throws SQLException {
        String sql = "SELECT * FROM economy WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();

            EconomyData data = new EconomyData(uuid);
            if (rs.next()) {
                for (Currency currency : Currency.values()) {
                    data.setBalance(currency, rs.getDouble(currency.getColumnName()));
                }
            } else {
                insert(uuid); // première connexion
            }
            return data;
        }
    }

    public void save(EconomyData data) throws SQLException {
        StringBuilder sets = new StringBuilder();
        for (Currency currency : Currency.values()) {
            if (sets.length() > 0) sets.append(", ");
            sets.append(currency.getColumnName()).append(" = ?");
        }

        String sql = "UPDATE economy SET " + sets + " WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int i = 1;
            for (Currency currency : Currency.values()) {
                stmt.setDouble(i++, data.getBalance(currency));
            }
            stmt.setString(i, data.getUuid().toString());
            stmt.executeUpdate();
        }
    }

    private void insert(UUID uuid) throws SQLException {
        String sql = "INSERT OR IGNORE INTO economy (uuid) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            stmt.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
