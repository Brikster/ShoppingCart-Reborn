package ru.mrbrikster.shoppingcartreborn.spigot;

import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.database.DatabaseCredentials;
import ru.mrbrikster.shoppingcartreborn.database.DatabaseManager;
import ru.mrbrikster.shoppingcartreborn.providers.CommandExecutionProvider;
import ru.mrbrikster.shoppingcartreborn.providers.EconomyProvider;
import ru.mrbrikster.shoppingcartreborn.providers.ItemProvider;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.config.ConfigurationNode;
import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.plugin.BukkitBasePlugin;
import ru.mrbrikster.shoppingcartreborn.spigot.commands.CommandManager;
import ru.mrbrikster.shoppingcartreborn.spigot.providers.SpigotCommandExecutionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.config.Messages;
import ru.mrbrikster.shoppingcartreborn.spigot.listeners.PlayerListener;
import ru.mrbrikster.shoppingcartreborn.spigot.money.VaultEconomyProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.permissions.DefaultProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.permissions.LuckPermsProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.permissions.PermissionsExProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.permissions.VaultPermissionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.providers.SpigotItemProvider;

public final class ShoppingCartReborn extends BukkitBasePlugin implements ShoppingCartRebornPlugin {

    private static ShoppingCartReborn instance;
    private PermissionProvider permissionProvider;
    private EconomyProvider economyProvider;
    private SpigotCommandExecutionProvider commandExecutionProvider;
    private ItemProvider itemProvider;

    private CommandManager commandManager;
    private DatabaseManager databaseManager;

    public static ShoppingCartReborn getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        ShoppingCartReborn.instance = ShoppingCartReborn.this;

        if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
            permissionProvider = new PermissionsExProvider();
            getLogger().info("Loaded PermissionsEx permission provider!");
        } else if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            permissionProvider = new LuckPermsProvider();
            getLogger().info("Loaded LuckPerms permission provider!");
        } else if (getServer().getPluginManager().getPlugin("Vault") != null) {
            permissionProvider = new VaultPermissionProvider();
            getLogger().info("Loaded Vault permission provider!");
        } else permissionProvider = new DefaultProvider();

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            this.economyProvider = new VaultEconomyProvider();
        }

        this.commandExecutionProvider = new SpigotCommandExecutionProvider();
        this.itemProvider = new SpigotItemProvider();

        new Messages(this);
        this.commandManager = new CommandManager(this);
        this.commandManager.register();

        ConfigurationNode credentialsNode = getConfiguration().getNode("database");
        DatabaseCredentials databaseCredentials = new DatabaseCredentials(
                credentialsNode.getNode("address").getAsString("127.0.0.1"),
                credentialsNode.getNode("port").getAsInt(3306),
                credentialsNode.getNode("username").getAsString("root"),
                credentialsNode.getNode("password").getAsString(""),
                credentialsNode.getNode("database").getAsString("shopcart"),
                credentialsNode.getNode("ssl").getAsBoolean(false)
        );
        this.databaseManager = new DatabaseManager(this, databaseCredentials);

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        this.commandManager.unregister();
    }

    @Override
    public PermissionProvider getPermissionProvider() {
        return permissionProvider;
    }

    @Override
    public EconomyProvider getEconomyProvider() {
        return economyProvider;
    }

    @Override
    public CommandExecutionProvider getCommandExecutionProvider() {
        return commandExecutionProvider;
    }

    @Override
    public ItemProvider getItemProvider() {
        return itemProvider;
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

}
