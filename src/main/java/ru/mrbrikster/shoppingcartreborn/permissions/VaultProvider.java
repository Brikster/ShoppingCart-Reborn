package ru.mrbrikster.shoppingcartreborn.permissions;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VaultProvider implements PermissionProvider {

    private static Permission vaultProvider = Bukkit.getServicesManager().getRegistration(Permission.class).getProvider();

    @Override
    public boolean addToGroup(Player player, String group, long time) {
        // Vault doesn't support timed permissions groups.
        return false;
    }

    @Override
    public boolean addToGroup(Player player, String group) {
        if (vaultProvider != null) {
            return vaultProvider.playerAddGroup(player, group);
        }

        return false;
    }

}
