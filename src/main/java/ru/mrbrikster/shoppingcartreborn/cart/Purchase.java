package ru.mrbrikster.shoppingcartreborn.cart;

import org.bukkit.entity.Player;
import ru.mrbrikster.shoppingcartreborn.serialization.Deserializer;
import ru.mrbrikster.shoppingcartreborn.serialization.Serializable;

public interface Purchase extends Serializable<Purchase> {

    boolean give(Player player);

    static Deserializer<Purchase> getDeserializer() {
        return new PurchaseDeserializer();
    }

}
