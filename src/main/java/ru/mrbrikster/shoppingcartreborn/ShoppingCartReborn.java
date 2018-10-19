package ru.mrbrikster.shoppingcartreborn;

import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbrikster.shoppingcartreborn.commands.CommandManager;
import ru.mrbrikster.shoppingcartreborn.config.Configuration;
import ru.mrbrikster.shoppingcartreborn.listeners.PlayerListener;
import ru.mrbrikster.shoppingcartreborn.money.EconomyProvider;
import ru.mrbrikster.shoppingcartreborn.permissions.*;

public final class ShoppingCartReborn extends JavaPlugin {

    private static ShoppingCartReborn instance;
    private PermissionProvider permissionProvider;
    private Configuration configuration;
    private EconomyProvider economyProvider;
    private CommandManager commandManager;

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
            permissionProvider = new VaultProvider();
            getLogger().info("Loaded Vault permission provider!");
        } else permissionProvider = new DefaultProvider();

        this.configuration = new Configuration(this);

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            this.economyProvider = new EconomyProvider(this);
        }

        this.commandManager = new CommandManager();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        this.commandManager.unregisterAll();
    }

    public PermissionProvider getPermissionProvider() {
        return permissionProvider;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public EconomyProvider getEconomyProvider() {
        return economyProvider;
    }

}
