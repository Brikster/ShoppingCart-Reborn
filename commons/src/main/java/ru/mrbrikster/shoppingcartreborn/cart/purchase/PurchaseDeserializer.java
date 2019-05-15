package ru.mrbrikster.shoppingcartreborn.cart.purchase;

import com.google.gson.JsonObject;
import ru.mrbrikster.shoppingcartreborn.ShoppingCartRebornPlugin;
import ru.mrbrikster.shoppingcartreborn.cart.purchase.types.*;
import ru.mrbrikster.shoppingcartreborn.serialization.Deserializer;

public class PurchaseDeserializer implements Deserializer<Purchase> {

    @Override
    public Purchase deserialize(ShoppingCartRebornPlugin shoppingCartRebornPlugin, JsonObject jsonObject) {
        if (jsonObject != null
                && jsonObject.has("type")
                && jsonObject.has("purchaseData")
                && jsonObject.get("purchaseData").isJsonObject()) {
            PurchaseDisplayData purchaseDisplayData = null;
            if (jsonObject.has("displayData")
                    && jsonObject.get("displayData").isJsonObject()) {
                purchaseDisplayData = PurchaseDisplayData.deserialize(jsonObject.getAsJsonObject("displayData"));
            }

            if (purchaseDisplayData == null && !jsonObject.get("type").getAsString().toLowerCase().equalsIgnoreCase("template")) {
                return null;
            }

            switch(jsonObject.get("type").getAsString().toLowerCase()) {
                case "money":
                    return MoneyPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, jsonObject.getAsJsonObject("purchaseData"));
                case "group":
                    return GroupPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, jsonObject.getAsJsonObject("purchaseData"));
                case "permission":
                    return PermissionPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, jsonObject.getAsJsonObject("purchaseData"));
                case "command":
                    return CommandPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, jsonObject.getAsJsonObject("purchaseData"));
                case "item":
                    return ItemPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, jsonObject.getAsJsonObject("purchaseData"));
                case "template":
                    PurchaseTemplate purchaseTemplate = PurchaseTemplate.get(shoppingCartRebornPlugin, jsonObject.getAsJsonObject("purchaseData"));

                    if (purchaseTemplate == null) {
                        return null;
                    }

                    return purchaseTemplate.createPurchase();
            }
        }

        return null;
    }

}
