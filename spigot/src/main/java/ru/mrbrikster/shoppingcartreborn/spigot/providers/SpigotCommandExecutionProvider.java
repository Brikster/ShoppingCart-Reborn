package ru.mrbrikster.shoppingcartreborn.spigot.providers;

import org.bukkit.Bukkit;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.providers.CommandExecutionProvider;

public class SpigotCommandExecutionProvider implements CommandExecutionProvider {

    @Override
    public boolean execute(User user, String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", user.getName()));

        return true;
    }

}
