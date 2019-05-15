package ru.mrbrikster.shoppingcartreborn.spigot.config;

import org.bukkit.ChatColor;
import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.config.Configuration;
import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.plugin.BukkitBasePlugin;

import java.util.function.Function;

public class Messages {

    private static final Function<String, String> COLORIZE = (string) -> string == null ? null : ChatColor.translateAlternateColorCodes('&', string);

    private static Configuration localeConfiguration;

    public Messages(BukkitBasePlugin bukkitBasePlugin) {
        Messages.localeConfiguration = bukkitBasePlugin.getConfiguration();
    }

    public static String get(String key) {
        return COLORIZE.apply(Messages.localeConfiguration == null ? "Wrong locale key" : Messages.localeConfiguration.getNode("messages").getNode(key).getAsString("Wrong locale key"));
    }

}
