package ru.brikster.shoppingcartreborn.storage.entity;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Delivery {

    private final @NotNull Long id;
    private final @NotNull String playerName;
    private final @NotNull String productType;
    private final @NotNull String productName;
    private final @NotNull String productData;
    private final @NotNull String serverName;
    private final @Nullable LocalDateTime deliveredAt;

    public Delivery(
            @NotNull @ColumnName("id") Long id,
            @NotNull @ColumnName("player_name") String playerName,
            @NotNull @ColumnName("product_type") String productType,
            @NotNull @ColumnName("product_name") String productName,
            @NotNull @ColumnName("product_data") String productData,
            @NotNull @ColumnName("server_name") String serverName,
            @Nullable @ColumnName("delivered_at") LocalDateTime deliveredAt
    ) {
        this.id = id;
        this.playerName = playerName;
        this.productType = productType;
        this.productName = productName;
        this.productData = productData;
        this.serverName = serverName;
        this.deliveredAt = deliveredAt;
    }

    public @NotNull Long id() {
        return id;
    }

    public @NotNull String playerName() {
        return playerName;
    }

    public @NotNull String productType() {
        return productType;
    }

    public @NotNull String productName() {
        return productName;
    }

    public @NotNull String productData() {
        return productData;
    }

    public @NotNull String serverName() {
        return serverName;
    }

    public @Nullable LocalDateTime deliveredAt() {
        return deliveredAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerName, productType, productName, productData, serverName, deliveredAt);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Delivery that = (Delivery) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.playerName, that.playerName) &&
                Objects.equals(this.productType, that.productType) &&
                Objects.equals(this.productName, that.productName) &&
                Objects.equals(this.productData, that.productData) &&
                Objects.equals(this.serverName, that.serverName) &&
                Objects.equals(this.deliveredAt, that.deliveredAt);
    }

    @Override
    public String toString() {
        return "Delivery[" +
                "id=" + id + ", " +
                "playerName=" + playerName + ", " +
                "productType=" + productType + ", " +
                "productName=" + productName + ", " +
                "productData=" + productData + ", " +
                "serverName=" + serverName + ", " +
                "deliveredAt=" + deliveredAt + ']';
    }

}
