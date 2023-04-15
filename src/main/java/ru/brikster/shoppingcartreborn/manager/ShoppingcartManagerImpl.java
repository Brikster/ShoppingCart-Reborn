package ru.brikster.shoppingcartreborn.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.brikster.shoppingcartreborn.ShoppingcartRebornPlugin;
import ru.brikster.shoppingcartreborn.config.GeneralConfig;
import ru.brikster.shoppingcartreborn.product.Product;
import ru.brikster.shoppingcartreborn.product.ProductFactory;
import ru.brikster.shoppingcartreborn.storage.entity.Delivery;
import ru.brikster.shoppingcartreborn.storage.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import static ru.brikster.shoppingcartreborn.util.AdventureUtil.sendMessage;

@Slf4j
@RequiredArgsConstructor
public final class ShoppingcartManagerImpl implements ShoppingcartManager {

    private final GeneralConfig config;
    private final DeliveryRepository deliveryRepository;
    private final ProductFactory productFactory;

    @SneakyThrows
    @Override
    public @NotNull List<@NotNull Delivery> deliveryProductsSilently(@NotNull Player player) {
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("Method should be called asynchronously");
        }

        List<Delivery> pendingDeliveries = deliveryRepository.findPendingDeliveries(player.getName(), config.getServerName());
        List<Delivery> completedDeliveries = new ArrayList<>();

        if (player.isOnline()) {
            for (Delivery pendingDelivery : pendingDeliveries) {
                AtomicBoolean success = new AtomicBoolean();
                CountDownLatch latch = new CountDownLatch(1);

                Bukkit.getScheduler().runTask(ShoppingcartRebornPlugin.instance(), () -> {
                    try {
                        if (player.isOnline()) {
                            try {
                                Product product = productFactory.createProduct(pendingDelivery.productType(), pendingDelivery.productData());
                                success.set(product.give(player));
                            } catch (Throwable t) {
                                log.error("Error while processing delivery with id " + pendingDelivery.id(), t);
                            }
                        } else {
                            log.warn("Delivery with id " + pendingDelivery.id() + " is not delivered because player is not online");
                        }
                    } finally {
                        latch.countDown();
                    }
                });

                latch.await();

                if (success.get()) {
                    try {
                        deliveryRepository.markAsDelivered(pendingDelivery.id());
                        completedDeliveries.add(pendingDelivery);
                        log.info("Delivery with id " + pendingDelivery.id() + " was successfully delivered");
                    } catch (Throwable t) {
                        log.error("Error while processing delivery with id " + pendingDelivery.id(), t);
                    }
                } else {
                    log.warn("Delivery with id " + pendingDelivery.id() + " was not delivered for some reasons");
                }
            }
        }

        return completedDeliveries;
    }

    @Override
    public @NotNull List<@NotNull Delivery> deliveryProducts(@NotNull Player player) {
        List<Delivery> completed = deliveryProductsSilently(player);

        if (completed.size() != 0) {
            sendMessage(player,
                    Component.text("* ", NamedTextColor.GOLD)
                            .append(Component.text("Вы успешно получили ", NamedTextColor.WHITE))
                            .append(Component.text(String.valueOf(completed.size()), NamedTextColor.GREEN))
                            .append(Component.text(" приобретённых продуктов.", NamedTextColor.WHITE)));
        }

        return completed;
    }

}
