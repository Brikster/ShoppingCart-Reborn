package ru.mrbrikster.shoppingcartreborn.spigot.permissions;

import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;

public class DefaultProvider implements PermissionProvider {

    @Override
    public boolean addToGroup(User user, String group, long time) {
        return false;
    }

    @Override
    public boolean addToGroup(User user, String group) {
        return false;
    }

    @Override
    public boolean addPermission(User user, String permission, long time) {
        return false;
    }

    @Override
    public boolean addPermission(User user, String permission) {
        return false;
    }

}
