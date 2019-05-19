package ru.mrbrikster.shoppingcartreborn.spigot.providers.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.providers.EconomyProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;

public class VaultEconomyProvider implements EconomyProvider {

    private static RegisteredServiceProvider<Economy> vaultProvider = Bukkit.getServicesManager().getRegistration(Economy.class);

    @Override
    public boolean deposit(User user, double money) {
        if (!(user instanceof BukkitUser)) {
            return false;
        }

        if (vaultProvider != null) {
            return vaultProvider.getProvider().depositPlayer(((BukkitUser) user).getAsPlayer(), money).transactionSuccess();
        }

        return false;
    }

}
