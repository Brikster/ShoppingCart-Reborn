package ru.brikster.shoppingcartreborn.util;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import ru.brikster.shoppingcartreborn.ShoppingcartRebornPlugin;

public final class AdventureUtil {

    private final static BukkitAudiences AUDIENCES =
            BukkitAudiences.builder(ShoppingcartRebornPlugin.instance())
                    .build();

    public static void sendMessage(CommandSender sender, Component component) {
        AUDIENCES.sender(sender).sendMessage(component);
    }

}
