package ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.plugin;

import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.config.Configuration;
import ru.mrbrikster.shoppingcartreborn.spigot.baseplugin.scheduler.Scheduler;

public interface BasePlugin {

    public Configuration getConfiguration();

    public Configuration getConfiguration(String fileName);

    public Scheduler getScheduler();

}
