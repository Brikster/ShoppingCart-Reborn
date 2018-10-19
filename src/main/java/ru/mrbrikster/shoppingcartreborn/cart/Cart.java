package ru.mrbrikster.shoppingcartreborn.cart;

import org.bukkit.entity.Player;

import java.util.Set;

public class Cart {

    private final Player player;

    private Cart(Player player) {
        this.player = player;
    }

    public Set<Purchase> getPurchases() {
        // TODO
        return null;
    }

    public static Cart of(Player player) {
        return new Cart(player);
    }

}
