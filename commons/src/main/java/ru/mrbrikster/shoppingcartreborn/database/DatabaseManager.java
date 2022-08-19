/*
 * MCSTUDIO (c) 2019.
 * https://vk.com/mcstudio
 * https://mcstudio.su
 */

package ru.mrbrikster.shoppingcartreborn.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class DatabaseManager {

    private final HikariDataSource dataSource;

    private static final JsonParser JSON_PARSER = new JsonParser();
    private final ShoppingCartRebornPlugin shoppingCartRebornPlugin;
    private final String purchasesTable;
    private final String templatesTable;

    public DatabaseManager(ShoppingCartRebornPlugin shoppingCartRebornPlugin, DatabaseCredentials databaseCredentials) {
        this.shoppingCartRebornPlugin = shoppingCartRebornPlugin;
        this.purchasesTable = databaseCredentials.getPurchasesTable();
        this.templatesTable = databaseCredentials.getTemplatesTable();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format(
                "jdbc:mysql://%1$s:%2$d/%3$s",
                databaseCredentials.getAddress(),
                databaseCredentials.getPort(),
                databaseCredentials.getDatabase()));

        hikariConfig.setUsername(databaseCredentials.getUsername());
        hikariConfig.setPassword(databaseCredentials.getPassword());

        hikariConfig.addDataSourceProperty("useSSL", String.valueOf(databaseCredentials.isUseSSL()));
        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        hikariConfig.addDataSourceProperty("useUnicode", "true");

        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setPoolName("shopcart");

        this.dataSource = new HikariDataSource(hikariConfig);

        runInitQueries();
    }

    private void runInitQueries() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(
                            "CREATE TABLE IF NOT EXISTS " + purchasesTable + " (" +
                                    "id INTEGER AUTO_INCREMENT, " +
                                    "player_name VARCHAR(16) NOT NULL, " +
                                    "player_uuid CHAR(36) DEFAULT NULL, " +
                                    "purchase TEXT, " +
                                    "created_at TIMESTAMP DEFAULT current_timestamp, " +
                                    "PRIMARY KEY (id))");

            statement.executeUpdate();
            statement.close();

            statement = connection
                    .prepareStatement(
                            "CREATE TABLE IF NOT EXISTS " + templatesTable + " (" +
                                    "name VARCHAR(64), " +
                                    "template TEXT, " +
                                    "PRIMARY KEY (name))");

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Purchase> getPurchases(User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement;
            if (user.getUniqueId() != null) {
                preparedStatement = connection
                        .prepareStatement(
                                "SELECT id, purchase FROM " + purchasesTable + " WHERE player_name = ? OR player_uuid = ?");
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getUniqueId().toString());
            } else {
                preparedStatement = connection
                        .prepareStatement(
                                "SELECT id, purchase FROM " + purchasesTable + " WHERE player_name = ?");
                preparedStatement.setString(1, user.getName());
            }

            List<Purchase> purchases = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Purchase purchase;

                    try {
                        JsonObject jsonObject = JSON_PARSER.parse(resultSet.getString("purchase")).getAsJsonObject();
                        purchase = Purchase.getDeserializer().deserialize(shoppingCartRebornPlugin, jsonObject);
                    } catch (Throwable t) {
                        shoppingCartRebornPlugin.getLogger().log(Level.SEVERE, "Cannot parse purchase with id " + resultSet.getInt("id"));
                        continue;
                    }

                    if (purchase != null) {
                        purchases.add(purchase.setId(resultSet.getInt("id")));
                    }
                }
            }

            preparedStatement.close();

            return purchases;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public JsonObject getTemplate(String name) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT template FROM " + templatesTable + " WHERE name = ?")) {
                preparedStatement.setString(1, name);

                JsonObject jsonObject = null;
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        jsonObject = JSON_PARSER.parse(resultSet.getString("template")).getAsJsonObject();
                    }
                }

                preparedStatement.close();

                return jsonObject;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean removePurchase(int id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                            "DELETE FROM " + purchasesTable + " WHERE id = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
