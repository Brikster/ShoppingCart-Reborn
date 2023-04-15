package ru.brikster.shoppingcartreborn.product;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Product {

    boolean give(@NotNull Player player);

    @NotNull JsonNode data();

}
