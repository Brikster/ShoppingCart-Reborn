package ru.mrbrikster.shoppingcartreborn.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartReborn;
import ru.mrbrikster.shoppingcartreborn.cart.Cart;
import ru.mrbrikster.shoppingcartreborn.cart.Purchase;
import ru.mrbrikster.shoppingcartreborn.config.Configuration;

import java.util.Set;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        if (ShoppingCartReborn.getInstance().getConfiguration()
                .getNode("auto-cart-all").getAsBoolean(false)) {
            Cart cart = Cart.of(playerJoinEvent.getPlayer());
            Set<Purchase> purchaseSet = cart.getPurchases();

            if (!purchaseSet.isEmpty()) {
                purchaseSet.forEach(purchase -> purchase.give(playerJoinEvent.getPlayer()));
                playerJoinEvent.getPlayer().sendMessage(Configuration.getMessages().get("cart-all"));
            }
        }
    }

}
