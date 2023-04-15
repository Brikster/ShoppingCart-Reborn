package ru.brikster.shoppingcartreborn.storage.repository.postgres;

import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.NotNull;
import ru.brikster.shoppingcartreborn.storage.entity.Delivery;
import ru.brikster.shoppingcartreborn.storage.repository.DeliveryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public final class PostgresDeliveryRepository implements DeliveryRepository {

    private final Jdbi jdbi;

    @Override
    public @NotNull List<@NotNull Delivery> findPendingDeliveries(@NotNull String playerName, @NotNull String server) {
        return jdbi.withHandle(handle -> handle.createQuery(
                        "SELECT id, player_name, product_type, product_name, product_data, server_name, delivered_at " +
                                "FROM shoppingcart_all_deliveries " +
                                "WHERE delivered_at IS NULL " +
                                "    AND LOWER(player_name) = LOWER(:playerName)" +
                                "    AND server_name = LOWER(:server)")
                .bind("playerName", playerName)
                .bind("server", server)
                .mapTo(Delivery.class)
                .collect(Collectors.toList()));
    }

    @Override
    public void createDelivery(@NotNull String playerName, @NotNull Long productTemplateId, @NotNull String server) {
        jdbi.useTransaction(handle -> {
            handle.begin();
            handle.createUpdate("INSERT INTO shoppingcart_deliveries (player_name, product_template_id, server_name) " +
                            "VALUES (:playerName, :productTemplateId, LOWER(:server))")
                    .bind("playerName", playerName)
                    .bind("productTemplateId", productTemplateId)
                    .bind("server", server).execute();
            handle.commit();
        });
    }

    @Override
    public void markAsDelivered(@NotNull Long deliveryId) {
        jdbi.useTransaction(handle -> {
            handle.begin();
            handle.createUpdate("UPDATE shoppingcart_deliveries " +
                    "SET delivered_at = NOW() " +
                    "WHERE id = :id").bind("id", deliveryId).execute();
            handle.commit();
        });
    }

}
