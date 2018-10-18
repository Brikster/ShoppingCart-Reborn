package ru.mrbrikster.shoppingcartreborn.permissions;

import org.bukkit.entity.Player;

public class DefaultProvider implements PermissionProvider {

    @Override
    public boolean addToGroup(Player player, String group, long time) {
        return false;
    }

    @Override
    public boolean addToGroup(Player player, String group) {
        return false;
    }

}
