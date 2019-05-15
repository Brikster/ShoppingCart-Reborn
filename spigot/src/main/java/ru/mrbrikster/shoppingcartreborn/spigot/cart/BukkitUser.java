package ru.mrbrikster.shoppingcartreborn.spigot.cart;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.mrbrikster.shoppingcartreborn.cart.User;

import java.util.UUID;

public class BukkitUser implements User {

    private final String name;
    private final UUID uniqueId;

    private BukkitUser(String name, UUID uniqueId) {
        this.name = name;
        this.uniqueId = uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    public Player getAsPlayer() {
        if (uniqueId != null) {
            return Bukkit.getPlayer(uniqueId);
        } else {
            return Bukkit.getPlayer(name);
        }
    }

    public static BukkitUser of(Player player) {
        return new BukkitUser(player.getName(), player.getUniqueId());
    }

}
