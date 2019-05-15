package ru.mrbrikster.shoppingcartreborn.providers;

import ru.mrbrikster.shoppingcartreborn.cart.User;

public interface PermissionProvider {

    boolean addToGroup(User user, String group, long time);

    boolean addToGroup(User user, String group);

    public boolean addPermission(User user, String permission, long time);

    public boolean addPermission(User user, String permission);

}
