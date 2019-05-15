package ru.mrbrikster.shoppingcartreborn.spigot.commands;

import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.plugin.BukkitBasePlugin;

public class CommandManager {

    private final BukkitBasePlugin bukkitBasePlugin;
    private CartCommand cartCommand;

    public CommandManager(BukkitBasePlugin bukkitBasePlugin) {
        this.bukkitBasePlugin = bukkitBasePlugin;
    }

    public void register() {
        this.cartCommand = new CartCommand();
        this.cartCommand.register(bukkitBasePlugin);
    }

    public void unregister() {
        this.cartCommand.unregister(bukkitBasePlugin);
    }

}
