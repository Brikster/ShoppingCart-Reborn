package ru.mrbrikster.shoppingcartreborn;

import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbrikster.shoppingcartreborn.permissions.*;

public final class ShoppingCartReborn extends JavaPlugin {

    private static ShoppingCartReborn instance;
    private PermissionProvider permissionProvider;

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
    }

    public PermissionProvider getPermissionProvider() {
        return permissionProvider;
    }

}
