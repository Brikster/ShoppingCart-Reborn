package ru.mrbrikster.shoppingcartreborn.config;

import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

public class Messages {

    private static final Function<String, String> COLORIZE = (string) -> string == null ? null : ChatColor.translateAlternateColorCodes('&', string);

    private final Map<String, String> messages = new IdentityHashMap<>();
    private final Configuration localeConfiguration;

    Messages(JavaPlugin javaPlugin, Configuration configuration) {
        File localeDir = new File(javaPlugin.getDataFolder(), "locale");

        String localeName = configuration.getNode("general.locale")
                .getAsString("en");

        if (!localeDir.exists()) {
            localeDir.mkdir();
        }

        File localeFile = new File(localeDir, localeName + ".yml");
        if (!localeFile.exists()) {
            URL localeFileUrl = getClass().getResource("/locale/" + localeName + ".yml");

            if (localeFileUrl == null) {
                javaPlugin.getLogger().warning("Locale " + '"' + localeName + '"' + " not found. Using English locale.");

                File enLocaleFile = new File(localeDir, "en.yml");

                if (!enLocaleFile.exists()) {
                    try {
                        FileUtils.copyURLToFile(getClass().getResource("/locale/en.yml"), enLocaleFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                localeName = "en";
            } else {
                try {
                    FileUtils.copyURLToFile(localeFileUrl, localeFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.localeConfiguration = new Configuration("locale/" + localeName + ".yml", javaPlugin);

        put("only-player", ChatColor.RED + "You are not a player.");
        put("cart-all", ChatColor.GREEN + "Gives all your purchases...");
        put("no-purchases", ChatColor.RED + "Your cart is empty.");
        put("command-usage", ChatColor.RED + "Usage: /cart <all|put|open>");
    }

    public String get(String key) {
        return get(key, messages.getOrDefault(key, "&cWrong message key."));
    }

    public String get(String key, String def) {
        return COLORIZE.apply(
                localeConfiguration == null
                ? def
                : localeConfiguration.getNode("messages." + key).getAsString(def));
    }

    private void put(String key, String message) {
        this.messages.put(key, message);
    }

}
