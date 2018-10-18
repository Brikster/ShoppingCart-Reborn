package ru.mrbrikster.shoppingcartreborn.permissions;

import org.bukkit.entity.Player;

public interface PermissionProvider {

    boolean addToGroup(Player player, String group, long time);

    boolean addToGroup(Player player, String group);

}
