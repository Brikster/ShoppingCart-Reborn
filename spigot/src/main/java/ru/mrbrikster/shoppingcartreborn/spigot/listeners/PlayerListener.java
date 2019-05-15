package ru.mrbrikster.shoppingcartreborn.spigot.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.mrbrikster.shoppingcartreborn.cart.Cart;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;
import ru.mrbrikster.shoppingcartreborn.spigot.ShoppingCartReborn;
import ru.mrbrikster.shoppingcartreborn.spigot.cart.BukkitUser;
import ru.mrbrikster.shoppingcartreborn.spigot.config.Messages;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        if (ShoppingCartReborn.getInstance().getConfiguration().getNode("auto-cart-all").getAsBoolean(false)) {
            ShoppingCartReborn.getInstance().getScheduler().schedule(() -> {
                BukkitUser bukkitUser = BukkitUser.of(playerJoinEvent.getPlayer());
                Cart cart = Cart.of(ShoppingCartReborn.getInstance(), bukkitUser);
                List<Purchase> purchases = cart.getPurchases();

                if (!purchases.isEmpty()) {
                    purchases.forEach(purchase -> {
                        if (cart.getPurchases().stream().anyMatch(streamPurchase -> streamPurchase.getId() == purchase.getId()) && cart.removePurchase(purchase.getId())) {
                            purchase.give(bukkitUser);
                        }
                    });

                    playerJoinEvent.getPlayer().sendMessage(Messages.get("cart-all"));
                }
            }, 5, TimeUnit.SECONDS, true);
        }
    }

}
