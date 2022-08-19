package ru.mrbrikster.shoppingcartreborn.cart.purchase;

import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.serialization.Deserializer;
import ru.mrbrikster.shoppingcartreborn.serialization.Serializable;

public interface Purchase extends Serializable {

    int getId();

    Purchase setId(int id);

    boolean give(User user);

    PurchaseDisplayData getDisplayData();

    static Deserializer<Purchase> getDeserializer() {
        return new PurchaseDeserializer();
    }

}
