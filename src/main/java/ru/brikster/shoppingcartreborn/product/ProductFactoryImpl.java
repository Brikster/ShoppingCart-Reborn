package ru.brikster.shoppingcartreborn.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

public final class ProductFactoryImpl implements ProductFactory {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public @NotNull Product createProduct(@NotNull String type, @NotNull String data) {
        JsonNode node;
        try {
            node = objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot parse product data", e);
        }

        //noinspection SwitchStatementWithTooFewBranches
        switch (type) {
            case "console_command":
                return new ConsoleCommandProduct(node);
            default:
                throw new IllegalStateException("Cannot find product with type: " + type);
        }
    }

}
