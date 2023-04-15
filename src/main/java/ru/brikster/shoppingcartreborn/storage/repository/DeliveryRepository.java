package ru.brikster.shoppingcartreborn.storage.repository;

import org.jetbrains.annotations.NotNull;
import ru.brikster.shoppingcartreborn.storage.entity.Delivery;

import java.util.List;

public interface DeliveryRepository {

    @NotNull List<@NotNull Delivery> findPendingDeliveries(@NotNull String playerName, @NotNull String server);

    void createDelivery(@NotNull String playerName, @NotNull Long productTemplateId, @NotNull String server);

    void markAsDelivered(@NotNull Long deliveryId);

}
