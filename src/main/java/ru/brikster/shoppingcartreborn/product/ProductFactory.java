package ru.brikster.shoppingcartreborn.product;

import org.jetbrains.annotations.NotNull;

public interface ProductFactory {

    @NotNull Product createProduct(@NotNull String type, @NotNull String data);

}
