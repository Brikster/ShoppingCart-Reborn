package ru.mrbrikster.shoppingcartreborn.serialization;

import com.google.gson.JsonObject;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;

public interface Deserializer<T> {

    T deserialize(ShoppingCartRebornPlugin shoppingCartRebornPlugin, JsonObject jsonObject);

}
