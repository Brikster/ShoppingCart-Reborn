package ru.mrbrikster.shoppingcartreborn.serialization;

import com.google.gson.JsonObject;

public interface Serializable<T> {

    JsonObject serialize(T object);

}
