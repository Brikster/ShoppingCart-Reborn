package ru.brikster.shoppingcartreborn.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.brikster.shoppingcartreborn.manager.ShoppingcartManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public final class PlayerListener implements Listener {

    private final ShoppingcartManager shoppingcartManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CompletableFuture
                .runAsync(() -> shoppingcartManager.deliveryProducts(event.getPlayer()))
                .exceptionally(t -> {
                    log.error("Cannot check shopping cart of " + event.getPlayer().getName(), t);
                    return null;
                });
    }

}
