package ru.mrbrikster.shoppingcartreborn.spigot.providers.permissions;

import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsExProvider implements PermissionProvider {

    @Override
    public boolean addToGroup(User user, String group, Long time) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        PermissionUser permissionUser = PermissionsEx.getUser(((BukkitUser) user).getAsPlayer());

        if (time == null) {
            permissionUser.addGroup(group);
        } else {
            permissionUser.addGroup(group, null, time);
        }

        return true;
    }

    @Override
    public boolean addPermission(User user, String permission, Long time) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        PermissionUser permissionUser = PermissionsEx.getUser(((BukkitUser) user).getAsPlayer());

        if (time == null) {
            permissionUser.addPermission(permission);
        } else {
            permissionUser.addTimedPermission(permission, null, Math.toIntExact(time));
        }

        return true;
    }

}
