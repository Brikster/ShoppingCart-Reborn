package ru.mrbrikster.shoppingcartreborn.spigot.permissions;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;

public class VaultPermissionProvider implements PermissionProvider {

    private static RegisteredServiceProvider<Permission> vaultProvider = Bukkit.getServicesManager().getRegistration(Permission.class);

    @Override
    public boolean addToGroup(User user, String group, long time) {
        // Vault doesn't support timed permissions groups.
        return false;
    }

    @Override
    public boolean addToGroup(User user, String group) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (vaultProvider != null) {
            return vaultProvider.getProvider().playerAddGroup(((BukkitUser) user).getAsPlayer(), group);
        }

        return false;
    }

    @Override
    public boolean addPermission(User user, String permission, long time) {
        // Vault doesn't support timed permissions.
        return false;
    }

    @Override
    public boolean addPermission(User user, String permission) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (vaultProvider != null) {
            return vaultProvider.getProvider().playerAdd(((BukkitUser) user).getAsPlayer(), permission);
        }

        return false;
    }

}
