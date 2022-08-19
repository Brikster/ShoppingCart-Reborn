package ru.mrbrikster.shoppingcartreborn.spigot.providers.permissions;

import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;

public class DefaultProvider implements PermissionProvider {

    @Override
    public boolean addToGroup(User user, String group, Long time) {
        return false;
    }

    @Override
    public boolean addPermission(User user, String permission, Long time) {
        return false;
    }

}
