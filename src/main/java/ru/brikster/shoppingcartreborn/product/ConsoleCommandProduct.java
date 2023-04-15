package ru.brikster.shoppingcartreborn.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ConsoleCommandProduct implements Product {

    private final JsonNode data;

    public ConsoleCommandProduct(JsonNode data) {
        Preconditions.checkArgument(data.isTextual(), "Data of console command product should be JSON string");
        Preconditions.checkArgument(data.asText().contains("{player}"), "Command should contain {player} placeholder");
        this.data = data;
    }

    @Override
    public boolean give(@NotNull Player player) {
        String command = data.asText().replace("{player}", player.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        return true;
    }

    @Override
    public @NotNull JsonNode data() {
        return data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ConsoleCommandProduct that = (ConsoleCommandProduct) obj;
        return Objects.equals(this.data, that.data);
    }

    @Override
    public String toString() {
        return "ConsoleCommandProduct[" +
                "data=" + data + ']';
    }

}
