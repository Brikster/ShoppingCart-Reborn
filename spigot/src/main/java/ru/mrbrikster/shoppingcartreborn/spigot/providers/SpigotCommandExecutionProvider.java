package ru.mrbrikster.shoppingcartreborn.spigot.providers;

import org.bukkit.Bukkit;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.providers.CommandExecutionProvider;
import ru.mrbrikster.shoppingcartreborn.spigot.ShoppingCartReborn;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;

import java.util.concurrent.TimeUnit;

public class SpigotCommandExecutionProvider implements CommandExecutionProvider {

    @Override
    public boolean execute(User user, String command) {
        ShoppingCartReborn.getInstance().getScheduler().schedule(() -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", ((BukkitUser) (user)).getAsPlayer().getName()));
        }, 0, TimeUnit.SECONDS);

        return true;
    }

}
