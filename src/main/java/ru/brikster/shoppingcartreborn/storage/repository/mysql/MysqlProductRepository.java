package ru.brikster.shoppingcartreborn.storage.repository.mysql;

import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.NotNull;
import ru.brikster.shoppingcartreborn.storage.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public final class MysqlProductRepository implements ProductRepository {

    private final Jdbi jdbi;

    @Override
    public Optional<Long> findIdIfExists(@NotNull String templateName) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT id " +
                        "FROM shoppingcart_product_templates " +
                        "WHERE template_name = LOWER(:templateName)")
                .bind("templateName", templateName)
                .mapTo(long.class)
                .findOne());
    }

}
