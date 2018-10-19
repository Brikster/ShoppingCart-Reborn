package ru.mrbrikster.shoppingcartreborn.serialization;

import com.google.gson.JsonObject;

public interface Deserializer<T> {

    public T deserialize(JsonObject jsonObject);
}
