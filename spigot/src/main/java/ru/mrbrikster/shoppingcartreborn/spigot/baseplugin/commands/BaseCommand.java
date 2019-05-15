package ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.commands;

import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.plugin.BasePlugin;

public interface BaseCommand {

    void register(BasePlugin basePlugin);

    void unregister(BasePlugin basePlugin);

}
