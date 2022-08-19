package ru.mrbrikster.shoppingcartreborn.providers;

import ru.mrbrikster.shoppingcartreborn.cart.User;

public interface PermissionProvider {

    boolean addToGroup(User user, String group, Long time);

    default boolean addToGroup(User user, String group) {
        return addToGroup(user, group, null);
    }

    boolean addPermission(User user, String permission, Long time);

    default boolean addPermission(User user, String permission) {
        return addPermission(user, permission, null);
    }

}
