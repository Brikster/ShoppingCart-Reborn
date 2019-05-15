package ru.mrbrikster.shoppingcartreborn.cart.purchase;

import com.google.gson.JsonObject;
import lombok.Getter;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;

public class PurchaseTemplate {

    @Getter private final String name;
    private final ShoppingCartRebornPlugin shoppingCartRebornPlugin;
    private final JsonObject jsonObject;

    /**
     * Template of purchase.
     * @param name Name of template at `templates` table.
     * @param jsonObject JSON-serialized purchase.
     * @param shoppingCartRebornPlugin ShoppingCartRebornPlugin.class instance.
     */
    public PurchaseTemplate(String name, JsonObject jsonObject, ShoppingCartRebornPlugin shoppingCartRebornPlugin) {
        this.name = name;
        this.jsonObject = jsonObject;
        this.shoppingCartRebornPlugin = shoppingCartRebornPlugin;
    }

    public static PurchaseTemplate get(ShoppingCartRebornPlugin shoppingCartRebornPlugin, JsonObject jsonObject) {
        if (jsonObject.has("template")
                && jsonObject.get("template").isJsonPrimitive()
                && jsonObject.getAsJsonPrimitive("template").isNumber()) {
            return new PurchaseTemplate("", shoppingCartRebornPlugin.getDatabaseManager().getTemplate(jsonObject.getAsJsonPrimitive("template").getAsInt()), shoppingCartRebornPlugin);
        }

        return null;
    }

    public Purchase createPurchase() {
        return Purchase.getDeserializer().deserialize(shoppingCartRebornPlugin, jsonObject);
    }

}
