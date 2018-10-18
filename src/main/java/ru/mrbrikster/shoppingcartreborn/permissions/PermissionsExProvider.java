package ru.mrbrikster.shoppingcartreborn.permissions;

import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsExProvider implements PermissionProvider {

    @Override
    public boolean addToGroup(Player player, String group, long time) {
        PermissionUser user = PermissionsEx.getUser(player);

        user.addGroup(group, null, time);
        return true;
    }

    @Override
    public boolean addToGroup(Player player, String group) {
        PermissionUser user = PermissionsEx.getUser(player);

        user.addGroup(group);
        return true;
    }

}
