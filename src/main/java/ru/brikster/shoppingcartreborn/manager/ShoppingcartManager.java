package ru.brikster.shoppingcartreborn.manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.brikster.shoppingcartreborn.storage.entity.Delivery;

import java.util.List;

public interface ShoppingcartManager {

    /** Should not be called in main thread */
    @NotNull List<@NotNull Delivery> deliveryProductsSilently(@NotNull Player player);

    /** Should not be called in main thread */
    @NotNull List<@NotNull Delivery> deliveryProducts(@NotNull Player player);

}
