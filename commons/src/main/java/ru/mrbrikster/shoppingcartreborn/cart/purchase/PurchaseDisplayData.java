package ru.mrbrikster.shoppingcartreborn.cart.purchase;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import ru.mrbrikster.shoppingcartreborn.serialization.Serializable;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDisplayData implements Serializable {

    @Getter private final String name;
    @Getter private final List<String> lore;

    public PurchaseDisplayData(String name, List<String> lore) {
        this.name = name;
        this.lore = lore;
    }

    public static PurchaseDisplayData deserialize(JsonObject jsonObject) {
        String name;
        if (jsonObject.has("name")
                && jsonObject.get("name").isJsonPrimitive()
                && jsonObject.getAsJsonPrimitive("name").isString()) {
            name = jsonObject.getAsJsonPrimitive("name").getAsString();
        } else {
            name = null;
        }

        List<String> lore;
        if (jsonObject.has("lore") && jsonObject.get("lore").isJsonArray()) {
            lore = new ArrayList<>();
            jsonObject.getAsJsonArray("lore").forEach(jsonElement -> {
                        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
                            lore.add(jsonElement.getAsString());
                        }
                    });
        } else {
            lore = null;
        }

        if (name == null || lore == null) {
            return null;
        } else {
            return new PurchaseDisplayData(name, lore);
        }
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", name);

        JsonArray jsonArray = new JsonArray();
        lore.forEach(jsonArray::add);

        jsonObject.add("lore", jsonArray);

        return jsonObject;
    }

}
