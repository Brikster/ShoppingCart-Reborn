package ru.mrbrikster.shoppingcartreborn.permissions;

import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.concurrent.TimeUnit;

public class LuckPermsProvider implements PermissionProvider {

    private static RegisteredServiceProvider<LuckPermsApi> luckPermsProvider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);

    @Override
    public boolean addToGroup(Player player, String group, long time) {
        if (luckPermsProvider != null) {
            LuckPermsApi api = luckPermsProvider.getProvider();

            User user = api.getUser(player.getUniqueId());

            boolean result = user.setPermission(api.getNodeFactory()
                    .newBuilder("group." + group)
                    .setExpiry(time, TimeUnit.SECONDS).build()).wasSuccess();

            api.getUserManager().saveUser(user);

            return result;
        }

        return false;
    }

    @Override
    public boolean addToGroup(Player player, String group) {
        if (luckPermsProvider != null) {
            LuckPermsApi api = luckPermsProvider.getProvider();

            User user = api.getUser(player.getUniqueId());

            boolean result = user.setPermission(api.getNodeFactory()
                    .newBuilder("group." + group).build()).wasSuccess();

            api.getUserManager().saveUser(user);

            return result;
        }

        return false;
    }

}
