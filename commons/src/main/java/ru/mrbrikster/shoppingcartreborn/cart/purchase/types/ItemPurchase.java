package ru.mrbrikster.shoppingcartreborn.cart.purchase.types;

import com.google.gson.JsonObject;
import lombok.Getter;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.User;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.Purchase;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.PurchaseDisplayData;
import ru.mrbrikster.shoppingcartreborn.objects.Item;
import ru.mrbrikster.shoppingcartreborn.providers.ItemProvider;

import java.util.ArrayList;
import java.util.List;

public class ItemPurchase implements Purchase {

    @Getter private final Item item;
    private final PurchaseDisplayData purchaseDisplayData;
    private final ItemProvider itemProvider;
    @Getter private int id;

    /**
     * Purchase of Item.
     * @param item Item.
     * @param purchaseDisplayData Display data for Menu.
     * @param itemProvider ItemProvider.class implementation.
     */
    public ItemPurchase(Item item, PurchaseDisplayData purchaseDisplayData, ItemProvider itemProvider) {
        this.item = item;
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

        int dataValue;
        if (jsonObject.has("dataValue")) {
            dataValue = jsonObject.get("dataValue").getAsInt();
        } else {
            dataValue = 0;
        }

        int count;
        if (jsonObject.has("count")) {
            count = jsonObject.get("count").getAsInt();
        } else {
            count = 1;
        }

        String name;
        if (jsonObject.has("name")) {
            name = jsonObject.get("name").getAsString();
        } else {
            name = null;
        }

        List<String> lore;
        if (jsonObject.has("lore")) {
            lore = new ArrayList<>();
            jsonObject.getAsJsonArray("lore").forEach(
                    loreString -> lore.add(loreString.getAsString()));
        } else {
            lore = null;
        }

        return new ItemPurchase(Item.builder()
                .minecraftId(minecraftId)
                .dataValue(dataValue)
                .count(count)
                .name(name)
                .lore(lore)
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

        return itemProvider.give(user, item);
    }

    @Override
    public PurchaseDisplayData getDisplayData() {
        return purchaseDisplayData;
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "item");

        JsonObject purchaseData = item.serialize();

        jsonObject.add("purchaseData", purchaseData);
        jsonObject.add("displayData", purchaseDisplayData.serialize());


        return jsonObject;
    }

}
