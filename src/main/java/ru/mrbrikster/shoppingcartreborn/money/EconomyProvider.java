package ru.mrbrikster.shoppingcartreborn.money;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyProvider {

    @Getter private Economy economy;

    public EconomyProvider(JavaPlugin javaPlugin) {
        RegisteredServiceProvider<Economy> serviceProvider = javaPlugin.getServer().getServicesManager().getRegistration(Economy.class);

        if (serviceProvider != null) {
            this.economy = serviceProvider.getProvider();
        }
    }

}
