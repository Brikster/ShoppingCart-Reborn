package ru.mrbrikster.shoppingcartreborn.spigot.providers.permissions;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;

import lombok.var;

import java.util.concurrent.TimeUnit;

public class LuckPermsProvider implements PermissionProvider {

    private static final RegisteredServiceProvider<LuckPerms> luckPermsProvider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

    @Override
    public boolean addToGroup(ru.mrbrikster.shoppingcartreborn.cart.User user, String group, Long time) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (luckPermsProvider != null) {
            LuckPerms api = luckPermsProvider.getProvider();

            User luckyPermsUser = api.getUserManager().getUser(((BukkitUser) user).getAsPlayer().getName());

            if (luckyPermsUser == null) {
                return false;
            }

            var builder = api.getNodeBuilderRegistry()
                    .forInheritance()
                    .group(group);

            if (time != null) {
                builder = builder.expiry(time, TimeUnit.SECONDS);
            }

            boolean result = luckyPermsUser.data()
                    .add(builder.build())
                    .wasSuccessful();

            api.getUserManager().saveUser(luckyPermsUser);

            return result;
        }

        return false;
    }

    @Override
    public boolean addPermission(ru.mrbrikster.shoppingcartreborn.cart.User user, String permission, Long time) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (luckPermsProvider != null) {
            LuckPerms api = luckPermsProvider.getProvider();

            User luckyPermsUser = api.getUserManager().getUser(((BukkitUser) user).getAsPlayer().getName());

            if (luckyPermsUser == null) {
                return false;
            }

            var builder = api.getNodeBuilderRegistry()
                    .forPermission()
                    .permission(permission);

            if (time != null) {
                builder = builder.expiry(time, TimeUnit.SECONDS);
            }

            boolean result = luckyPermsUser
                    .data()
                    .add(builder.build()).wasSuccessful();

            api.getUserManager().saveUser(luckyPermsUser);

            return result;
        }

        return false;
    }

}
