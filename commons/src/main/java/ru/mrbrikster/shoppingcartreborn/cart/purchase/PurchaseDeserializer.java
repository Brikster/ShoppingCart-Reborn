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

            String purchaseType = jsonObject.get("type").getAsString().toLowerCase();
            if (purchaseDisplayData == null && !purchaseType.equalsIgnoreCase("template")) {
                return null;
            }

            JsonObject purchaseData = jsonObject.getAsJsonObject("purchaseData");
            switch(purchaseType) {
                case "money":
                    return MoneyPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, purchaseData);
                case "group":
                    return GroupPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, purchaseData);
                case "permission":
                    return PermissionPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, purchaseData);
                case "command":
                    return CommandPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, purchaseData);
                case "item":
                    return ItemPurchase.deserialize(shoppingCartRebornPlugin, purchaseDisplayData, purchaseData);
                case "template":
                    PurchaseTemplate purchaseTemplate = PurchaseTemplate.get(shoppingCartRebornPlugin, purchaseData);

                    if (purchaseTemplate == null) {
                        return null;
                    }

                    return purchaseTemplate.createPurchase();
            }
        }

        return null;
    }

}
