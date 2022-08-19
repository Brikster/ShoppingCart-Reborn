package ru.mrbrikster.shoppingcartreborn.cart.purchase.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.PurchaseDisplayData;
import ru.mrbrikster.shoppingcartreborn.objects.SerializableItem;
import ru.mrbrikster.shoppingcartreborn.providers.ItemProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemPurchase implements Purchase {

    @Getter private final SerializableItem serializableItem;
    private final PurchaseDisplayData purchaseDisplayData;
    private final ItemProvider itemProvider;
    @Getter private int id;

    /**
     * Purchase of Item.
     * @param serializableItem SerializableItem.
     * @param purchaseDisplayData Display data for Menu.
     * @param itemProvider ItemProvider.class implementation.
     */
    public ItemPurchase(SerializableItem serializableItem, PurchaseDisplayData purchaseDisplayData, ItemProvider itemProvider) {
        this.serializableItem = serializableItem;
        this.purchaseDisplayData = purchaseDisplayData;
        this.itemProvider = itemProvider;
    }

    public static Purchase deserialize(ShoppingCartRebornPlugin shoppingCartRebornPlugin, PurchaseDisplayData purchaseDisplayData, JsonObject jsonObject) {
        String minecraftId;
        if (jsonObject.has("minecraftId")) {
            minecraftId = jsonObject.getAsJsonPrimitive("minecraftId").getAsString();
        } else {
            return null;
        }

        int dataValue = 0;
        if (jsonObject.has("dataValue")) {
            dataValue = jsonObject.get("dataValue").getAsInt();
        }

        int count = 1;
        if (jsonObject.has("count")) {
            count = jsonObject.get("count").getAsInt();
        }

        String name = null;
        if (jsonObject.has("name")) {
            name = jsonObject.get("name").getAsString();
        }

        List<String> lore = null;
        if (jsonObject.has("lore")) {
            lore = new ArrayList<>();
            for (JsonElement loreString : jsonObject.getAsJsonArray("lore")) {
                lore.add(loreString.getAsString());
            }
        }

        Map<String, Integer> enchants = new HashMap<>();
        if (jsonObject.has("enchants")) {
            JsonObject enchantsObject = jsonObject.getAsJsonObject("enchants");
            enchantsObject.keySet().forEach(key -> enchants.put(key, enchantsObject.getAsJsonPrimitive(key).getAsInt()));
        }

        return new ItemPurchase(SerializableItem.builder()
                .minecraftId(minecraftId)
                .dataValue(dataValue)
                .count(count)
                .name(name)
                .lore(lore)
                .enchants(enchants)
                .build(), purchaseDisplayData, shoppingCartRebornPlugin.getItemProvider());
    }

    @Override
    public Purchase setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean give(User user) {
        if (itemProvider == null) return false;

        return itemProvider.give(user, serializableItem);
    }

    @Override
    public PurchaseDisplayData getDisplayData() {
        return purchaseDisplayData;
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "item");

        JsonObject purchaseData = serializableItem.serialize();

        jsonObject.add("purchaseData", purchaseData);
        jsonObject.add("displayData", purchaseDisplayData.serialize());

        return jsonObject;
    }

}
