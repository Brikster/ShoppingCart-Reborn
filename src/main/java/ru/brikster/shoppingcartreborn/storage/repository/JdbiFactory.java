package ru.brikster.shoppingcartreborn.storage.repository;

import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.NotNull;

public interface JdbiFactory {

    @NotNull Jdbi createJdbi();

}
