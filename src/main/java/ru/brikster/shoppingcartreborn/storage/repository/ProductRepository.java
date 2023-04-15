package ru.brikster.shoppingcartreborn.storage.repository;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ProductRepository {

    Optional<Long> findIdIfExists(@NotNull String templateName);

}
