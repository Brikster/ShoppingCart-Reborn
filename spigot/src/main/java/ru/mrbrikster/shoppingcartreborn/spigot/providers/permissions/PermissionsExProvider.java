package ru.mrbrikster.shoppingcartreborn.spigot.providers.permissions;

import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsExProvider implements PermissionProvider {

    @Override
    public boolean addToGroup(User user, String group, long time) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        PermissionUser permissionUser = PermissionsEx.getUser(((BukkitUser) user).getAsPlayer());

        permissionUser.addGroup(group, null, time);
        return true;
    }

    @Override
    public boolean addToGroup(User user, String group) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        PermissionUser permissionUser = PermissionsEx.getUser(((BukkitUser) user).getAsPlayer());

        permissionUser.addGroup(group);
        return true;
    }

    @Override
    public boolean addPermission(User user, String permission, long time) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        PermissionUser permissionUser = PermissionsEx.getUser(((BukkitUser) user).getAsPlayer());

        permissionUser.addTimedPermission(permission, null, (int) time);
        return true;
    }

    @Override
    public boolean addPermission(User user, String permission) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        PermissionUser permissionUser = PermissionsEx.getUser(((BukkitUser) user).getAsPlayer());

        permissionUser.addPermission(permission);
        return true;
    }

}
