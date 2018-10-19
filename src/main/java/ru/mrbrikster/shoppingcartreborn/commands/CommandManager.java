package ru.mrbrikster.shoppingcartreborn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Objects;

public class CommandManager {

    private static SimpleCommandMap commandMap;

    static {
        SimplePluginManager simplePluginManager = (SimplePluginManager) Bukkit.getServer()
                .getPluginManager();

        Field commandMapField = null;
        try {
            commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(commandMapField).setAccessible(true);

        try {
            CommandManager.commandMap = (SimpleCommandMap) commandMapField.get(simplePluginManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private final CartCommand cartCommand;

    public CommandManager() {
        this.cartCommand = new CartCommand();
        this.cartCommand.registerCommand(commandMap);
    }

    private static SimpleCommandMap getCommandMap() {
        return commandMap;
    }

    public void unregisterAll() {
        this.cartCommand.unregisterCommand(commandMap);
    }

}
