package ru.mrbrikster.shoppingcartreborn.spigot.providers.permissions;

import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.mrbrikster.shoppingcartreborn.providers.PermissionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;

import java.util.concurrent.TimeUnit;

public class LuckPermsProvider implements PermissionProvider {

    private static RegisteredServiceProvider<LuckPermsApi> luckPermsProvider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);

    @Override
    public boolean addToGroup(ru.mrbrikster.shoppingcartreborn.cart.User user, String group, long time) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (luckPermsProvider != null) {
            LuckPermsApi api = luckPermsProvider.getProvider();

            User luckyPermsUser = api.getUser(((BukkitUser) user).getAsPlayer().getName());

            boolean result = luckyPermsUser.setPermission(api.getNodeFactory()
                    .newBuilder("group." + group)
                    .setExpiry(time, TimeUnit.SECONDS).build()).wasSuccess();

            api.getUserManager().saveUser(luckyPermsUser);

            return result;
        }

        return false;
    }

    @Override
    public boolean addToGroup(ru.mrbrikster.shoppingcartreborn.cart.User user, String group) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (luckPermsProvider != null) {
            LuckPermsApi api = luckPermsProvider.getProvider();

            User luckyPermsUser = api.getUser(((BukkitUser) user).getAsPlayer().getName());

            boolean result = luckyPermsUser.setPermission(api.getNodeFactory()
                    .newBuilder("group." + group).build()).wasSuccess();

            api.getUserManager().saveUser(luckyPermsUser);

            return result;
        }

        return false;
    }

    @Override
    public boolean addPermission(ru.mrbrikster.shoppingcartreborn.cart.User user, String permission, long time) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (luckPermsProvider != null) {
            LuckPermsApi api = luckPermsProvider.getProvider();

            User luckyPermsUser = api.getUser(((BukkitUser) user).getAsPlayer().getName());

            boolean result = luckyPermsUser.setPermission(api.getNodeFactory()
                    .newBuilder(permission)
                    .setExpiry(time, TimeUnit.SECONDS).build()).wasSuccess();

            api.getUserManager().saveUser(luckyPermsUser);

            return result;
        }

        return false;
    }

    @Override
    public boolean addPermission(ru.mrbrikster.shoppingcartreborn.cart.User user, String permission) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (luckPermsProvider != null) {
            LuckPermsApi api = luckPermsProvider.getProvider();

            User luckyPermsUser = api.getUser(((BukkitUser) user).getAsPlayer().getName());

            boolean result = luckyPermsUser.setPermission(api.getNodeFactory()
                    .newBuilder(permission).build()).wasSuccess();

            api.getUserManager().saveUser(luckyPermsUser);

            return result;
        }

        return false;
    }

}
